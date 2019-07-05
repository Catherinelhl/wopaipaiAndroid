package cn.wopaipai.utils;


import android.Manifest;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;

import com.google.zxing.BinaryBitmap;
import com.google.zxing.ChecksumException;
import com.google.zxing.FormatException;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.NotFoundException;
import com.google.zxing.RGBLuminanceSource;
import com.google.zxing.Reader;
import com.google.zxing.Result;
import com.google.zxing.common.HybridBinarizer;
import com.journeyapps.barcodescanner.CaptureManager;
import com.journeyapps.barcodescanner.DecoratedBarcodeView;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.engine.impl.GlideEngine;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.util.List;

import cn.wopaipai.R;
import cn.wopaipai.base.BaseException;
import cn.wopaipai.event.ChoicePicture;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;

public class ScanActivity extends AppCompatActivity implements EasyPermissions.PermissionCallbacks, View.OnClickListener {

    private CaptureManager captureManager;     //捕获管理器
    final int REQUEST_CODE_CHOOSE = 0002;
    private DecoratedBarcodeView mDBV;
    private Button choice_picture;
    private Button btn_choice_cancal;


    @Override
    protected void onPause() {
        super.onPause();
        captureManager.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        captureManager.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        captureManager.onDestroy();
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        captureManager.onSaveInstanceState(outState);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return mDBV.onKeyDown(keyCode, event) || super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);
        mDBV = (DecoratedBarcodeView) findViewById(R.id.dbv);
        choice_picture = (Button) findViewById(R.id.choice_picture);
        choice_picture.setOnClickListener(this);
        btn_choice_cancal = (Button) findViewById(R.id.btn_choice_cancal);
        btn_choice_cancal.setOnClickListener(this);
        captureManager = new CaptureManager(this, mDBV);
        captureManager.initializeFromIntent(getIntent(), savedInstanceState);
        captureManager.decode();
    }

    List<Uri> mSelected;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_CHOOSE && resultCode == RESULT_OK) {
            mSelected = Matisse.obtainResult(data);
            Log.d("图片路径", "mSelected: " + mSelected.get(0));
            try {
                // 下面这句话可以通过URi获取到文件的bitmap
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), mSelected.get(0));

                // 在这里我用到的 getSmallerBitmap 非常重要，下面就要说到
                bitmap = getSmallerBitmap(bitmap);

                // 获取bitmap的宽高，像素矩阵
                int width = bitmap.getWidth();
                int height = bitmap.getHeight();
                int[] pixels = new int[width * height];
                bitmap.getPixels(pixels, 0, width, 0, 0, width, height);

                // 最新的库中，RGBLuminanceSource 的构造器参数不只是bitmap了
                RGBLuminanceSource source = new RGBLuminanceSource(width, height, pixels);
                BinaryBitmap binaryBitmap = new BinaryBitmap(new HybridBinarizer(source));
                Reader reader = new MultiFormatReader();
                Result result = null;

                // 尝试解析此bitmap，！！注意！！ 这个部分一定写到外层的try之中，因为只有在bitmap获取到之后才能解析。写外部可能会有异步的问题。（开始解析时bitmap为空）
                try {
                    result = reader.decode(binaryBitmap);
                    EventBus.getDefault().post(new ChoicePicture(result.getText()));
                    Log.d("图片路径字符串", result.getText().toString());
                    finish();
                } catch (NotFoundException e) {
                    Log.i("文件没有找到", "onActivityResult: notFind");
                    BaseException.INSTANCE.print(e);
                } catch (ChecksumException e) {
                    BaseException.INSTANCE.print(e);
                } catch (FormatException e) {
                    BaseException.INSTANCE.print(e);
                }
            } catch (IOException e) {
                BaseException.INSTANCE.print(e);
            }

        }
    }

    public static Bitmap getSmallerBitmap(Bitmap bitmap) {
        int size = bitmap.getWidth() * bitmap.getHeight() / 160000;
        if (size <= 1) return bitmap; // 如果小于
        else {
            Matrix matrix = new Matrix();
            matrix.postScale((float) (1 / Math.sqrt(size)), (float) (1 / Math.sqrt(size)));
            Bitmap resizeBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
            return resizeBitmap;
        }
    }

    @AfterPermissionGranted(104)
    private void choice_picture() {
        String[] perms = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA};
        if (EasyPermissions.hasPermissions(this, perms)) {
            // Already have permission, do the thing
            Matisse.from(this)
                    .choose(MimeType.ofImage())//照片视频全部显示
                    .countable(false)//显示选择的数量
                    .maxSelectable(1) // 图片选择的最多数量
//                    .gridExpectedSize(MyApplication.getApp().getWidth() / 3 - 5)//图片显示在列表中的大小
                    .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
                    .thumbnailScale(0.85f) // 缩略图的比例
                    .imageEngine(new GlideEngine()) // 使用的图片加载引擎
                    .theme(R.style.Matisse_Dracula)//主题
                    .capture(false) //是否提供拍照功能
                    .forResult(REQUEST_CODE_CHOOSE); // 设置作为标记的请求码
        } else {
            // Do not have permissions, request them now
            EasyPermissions.requestPermissions(this, getApplicationContext().getResources().getString(R.string.choice_picture_msg), 104, perms);
        }
    }

    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {

    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            new AppSettingsDialog.Builder(this).build().show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_choice_cancal:
                finish();
                break;
            case R.id.choice_picture:
                choice_picture();
                break;
        }
    }
}
