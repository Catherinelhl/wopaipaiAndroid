package cn.wopaipai.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.ByteArrayOutputStream;

import cn.wopaipai.ui.fragment.MainFrg;


/**
 * Created by pc on 2017/10/10.
 * 加载图片工具类
 */

public class GlideImgManager {
    /**
     * 圆形加载
     *
     * @param mContext
     * @param path
     * @param imageview
     */
    public static void LoadCircleImage(Context mContext, String path,
                                       ImageView imageview) {
    }

    /**
     * 自适应宽度加载图片。保持图片的长宽比例不变，通过修改imageView的高度来完全显示图片。
     */
    public static void loadIntoUseFitWidth(Context context, final String imageUrl, int errorImageId, final ImageView imageView) {

    }

    //    @Override
    public static void displayImage(Context context, String path, ImageView imageView) {
        //Glide 加载图片简单用法
        Glide.with(context.getApplicationContext()).load(path).thumbnail(0.1f).into(imageView);
    }

    public static void displayImage(Context context, Bitmap bmp,ImageView imageView){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] bytes=baos.toByteArray();
        Glide.with(context).load(bytes).into(imageView);
    }

}
