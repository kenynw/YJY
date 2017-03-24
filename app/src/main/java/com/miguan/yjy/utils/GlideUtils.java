package com.miguan.yjy.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.miguan.yjy.R;

import java.util.concurrent.ExecutionException;

/**
 * @作者 cjh
 * @日期 2016/8/18 9:08
 * @描述 GlideUtils
 */

public class GlideUtils {

    public static Bitmap getBitmap(Context context, String url) {
        Bitmap myBitmap = null;
        try {
            myBitmap = Glide.with(context).load(url).asBitmap() // 必须
                    .centerCrop().into(600, 600).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return myBitmap;

    }

    public static void showImgLocal(Context context, int url, ImageView iv) {
        Glide.with(context).load(url).placeholder(R.mipmap.def_image_loading)
                .error(R.mipmap.def_image_loading).dontAnimate().into(iv);
    }

    /**
     * 默认正方形
     * @param context
     * @param url
     * @param iv
     */
    public static void showImgSquare(Context context, String url, ImageView iv) {
        showImgSimple(context, url, iv, 0, false, R.mipmap.def_image_loading, R.mipmap.def_image_loading);
    }

    /**
     *  默认长方形
     * @param context
     * @param url
     * @param iv
     */
    public static void showImgRectangle(Context context, String url, ImageView iv) {
        showImgSimple(context, url, iv, 0, false, R.mipmap.def_image_loading, R.mipmap.def_image_loading);
    }

    /**
     * 默认正方形带动画
     * @param context
     * @param url
     * @param iv
     * @param isAnim
     */
    public static void showImgSquare(Context context, String url, ImageView iv, boolean isAnim) {
        showImgSimple(context, url, iv, 0, isAnim, R.mipmap.def_image_loading, R.mipmap.def_image_loading);
    }

    /**
     * 默认长方形带动画
     * @param context
     * @param url
     * @param iv
     * @param isAnim
     */
    public static void showImgRectangle(Context context, String url, ImageView iv, boolean isAnim) {
        showImgSimple(context, url, iv, 0, isAnim, R.mipmap.def_image_loading, R.mipmap.def_image_loading);
    }

    /**
     * 默认长方形带圆角
     * @param context
     * @param url
     * @param iv
     * @param angle
     */
    public static void showImgRectangle(Context context, String url, ImageView iv, int angle) {
        showImgSimple(context, url, iv, angle, false, R.mipmap.def_image_loading, R.mipmap.def_image_loading);
    }

    /**
     * 手动设置占位图、失败图、是否动画
     * @param context
     * @param url
     * @param iv
     * @param placeholderImg
     * @param failImg (设置占位图)
     * @param isAnim (设置加载失败图)
     */
    public static void showImgSimple(Context context, String url, ImageView iv, int placeholderImg, int failImg, boolean isAnim) {
        showImgSimple(context, url, iv, 0, isAnim, placeholderImg, failImg);
    }

    /**
     * 正方形的圆角图
     * @param context
     * @param url
     * @param iv
     * @param angle
     */
    public static void showImgSimple(Context context, String url, ImageView iv, int angle) {
        showImgSimple(context, url, iv, angle, false, R.mipmap.def_image_loading, R.mipmap.def_image_loading);
    }

    /**
     * 全部手动设置
     * @param context
     * @param url
     * @param iv
     * @param angle
     * @param isAnim
     * @param placeholderImg
     * @param failImg
     */
    public static void showImgSimple(Context context, String url, ImageView iv, int angle, boolean isAnim, int placeholderImg, int failImg) {
        if (isAnim) {
            if (angle == 90) {
                Glide.with(context).load(url).transform(new GlideCircleTransform(context)).placeholder(placeholderImg)
                        .error(failImg).crossFade(1000).into(iv);
            } else {
                Glide.with(context).load(url).transform(new GlideRoundTransform(context, angle)).placeholder(placeholderImg)
                        .error(failImg).crossFade(1000).into(iv);
            }
        } else {
            if (angle == 90) {
                Glide.with(context).load(url).transform(new GlideCircleTransform(context)).placeholder(placeholderImg)
                        .error(failImg).dontAnimate().into(iv);
            } else {
                Glide.with(context).load(url).transform(new GlideRoundTransform(context, angle)).placeholder(placeholderImg)
                        .error(failImg).dontAnimate().into(iv);
            }
        }
    }

}
