package cn.wopaipai.ui.my;

import android.Manifest;
import android.app.Activity;
import android.content.ClipboardManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import org.jetbrains.annotations.NotNull;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import cn.catherine.token.http.retrofit.RetrofitFactory;
import cn.wopaipai.R;
import cn.wopaipai.base.BaseApplication;
import cn.wopaipai.base.BaseAty;
import cn.wopaipai.base.BaseException;
import cn.wopaipai.base.BasePresenterImp;
import cn.wopaipai.constant.Constants;
import cn.wopaipai.constant.MessageConstants;
import cn.wopaipai.gson.ResponseJson;
import cn.wopaipai.http.HttpApi;
import cn.wopaipai.tool.LogTool;
import cn.wopaipai.utils.MD5;
import cn.wopaipai.utils.ZxingUtils;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.ResourceObserver;
import io.reactivex.schedulers.Schedulers;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;

public class WalletIntoActivity extends BaseAty implements View.OnClickListener, EasyPermissions.PermissionCallbacks {

    // 转入
    private ImageButton back;
    private TextView title;
    private ImageView imgErcode;
    private Button btnCopyAddress;
    private Button btnSetPic;
    private TextView tv_address;
    private String coin_type;
    private Bitmap bitmap;

    @Override
    public void getArgs(@NotNull Bundle bundle) {
        coin_type = bundle.getString("coin_type");
    }

    @Override
    public int getLayoutRes() {
        return R.layout.activity_wallet_into;
    }

    @Override
    public void initViews() {
        Bundle bundle=this.getIntent().getExtras();

        back = (ImageButton) findViewById(R.id.ib_back);
        back.setOnClickListener(this);
        title = (TextView) findViewById(R.id.set_title);
        title.setText(getResources().getString(R.string.turn_into_address));
        imgErcode = (ImageView) findViewById(R.id.img_ercode);
        btnCopyAddress = (Button) findViewById(R.id.btn_copy_address);
        btnCopyAddress.setOnClickListener(this);
        btnSetPic = (Button) findViewById(R.id.btn_set_pic);
        btnSetPic.setOnClickListener(this);
        tv_address = (TextView) findViewById(R.id.tv_address);
    }

    @Override
    public void initData() {
        int PassportId = BaseApplication.Companion.getPassportId();
        String CoinCode=coin_type;
        String sign= MD5.Md5Sign(PassportId+CoinCode);
        Observable<ResponseJson> wallet = RetrofitFactory.INSTANCE.getAPIInstance().create(HttpApi.class).getAddress(CoinCode,PassportId,sign);
        wallet.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ResourceObserver<ResponseJson>() {
                    @Override
                    public void onNext(ResponseJson responseJson) {
                        int code=responseJson.getStatusCode();
                        if (code == MessageConstants.CODE_200) {
//                            WalletResponsesBean responsesBean = GsonTool.INSTANCE.convert(data,WalletResponsesBean.class);
                            LogTool.d(getTag(),responseJson.getData().toString());
                            bitmap = ZxingUtils.createBitmap(responseJson.getData().toString(),200,200);
                            imgErcode.setImageBitmap(bitmap);
                            tv_address.setText(responseJson.getData().toString());
                        } else {
                            showToast(new BasePresenterImp().getExceptionInfoByCode(code));
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        showToast(e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public void initListener() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ib_back:
                finish();
                break;
            case R.id.btn_copy_address:
                ClipboardManager clipboardManager= (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                clipboardManager.setText(tv_address.getText().toString());
                showToast(getResources().getString(R.string.copy_success));
                break;
            case R.id.btn_set_pic:
                methodRequiresTwoPermission();
                break;
        }
    }

    // Android 6.0以上动态申请权限(读写)
    @AfterPermissionGranted(100)
    private void methodRequiresTwoPermission() {
        String[] perms = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};
        if (EasyPermissions.hasPermissions(this, perms)) {
            // Already have permission, do the thing
            saveBitmapFile(capture(WalletIntoActivity.this));
        } else {
            // Do not have permissions, request them now
            EasyPermissions.requestPermissions(this,getResString(R.string.save_address_picture), 100, perms);
        }
    }
    public Bitmap capture(Activity activity) {//全屏截屏
        activity.getWindow().getDecorView().setDrawingCacheEnabled(true);
        Bitmap bmp = activity.getWindow().getDecorView().getDrawingCache();
        return bmp;
    }
    /**
     * @param saveDir
     * @return
     * @throws IOException 判断下载目录是否存在
     */
    private String isExistDir(String saveDir) throws IOException {
        // 下载位置
        File downloadFile = new File(Environment.getExternalStorageDirectory(), saveDir);
        if (!downloadFile.mkdirs()) {
            downloadFile.createNewFile();
        }
        String savePath = downloadFile.getAbsolutePath();
        return savePath;
    }
    private void saveBitmapFile(Bitmap bitmap) {
        long time=System.currentTimeMillis();
        String savePath = null;
        try {
            savePath = isExistDir(Constants.NAME);
        } catch (IOException e) {
            BaseException.INSTANCE.print(e);
        }
        File file = new File(savePath,time+".jpg");//将要保存图片的路径
        try {
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
            Log.e("path", "saveBitmapFile: ---------->"+file);
            bos.flush();
            bos.close();
//            ToastUtils.showToastShort(R.string.save_img_success);
            showToast(getResString(R.string.save_img_success)+savePath);
        } catch (IOException e) {
            BaseException.INSTANCE.print(e);
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
        EasyPermissions.onRequestPermissionsResult(requestCode,permissions,grantResults,this);
    }
}
