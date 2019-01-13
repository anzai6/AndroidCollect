package com.android.collect.library.util;

import android.app.Activity;
import android.widget.ImageView;

import com.android.collect.library.R;
import com.bumptech.glide.Glide;
import java.io.File;

/**
 * 加载图片工具类
 * Created by anzai on 2017/4/10.
 */

public class ImageLoaderUtils {

    public final static String TAG = "ImageLoader";

    /**
     * 加载本地图片文件
     *
     * @param activity(Glide图片加载会和Activity/Fragment的生命周期保持一致)
     * @param imgFile
     * @param iv
     */
    public static void loadImageWithFile(Activity activity, File imgFile, ImageView iv) {
        if (imgFile.isFile()) {
            LogUtils.i(TAG, "加载图片：" + imgFile.getAbsolutePath());
            Glide.with(activity)
                    .load(imgFile)
//                .fitCenter() // 跟ImageView的ScaleType类似
//                .centerCrop()
                    .thumbnail(0.1f)
                    .crossFade()// 淡入淡出
//                    .error(R.drawable.ic_image_load_error)
//                    .diskCacheStrategy(DiskCacheStrategy.ALL) // 让Glide既缓存全尺寸又缓存其他尺寸
                    .into(iv);
        }
    }

    /**
     * 加载本地图片文件
     *
     * @param activity(Glide图片加载会和Activity/Fragment的生命周期保持一致)
     * @param imgFile
     * @param iv
     */
    public static void loadImageWithFile(Activity activity, File imgFile, ImageView iv, int width, int height) {
        if (imgFile.isFile()) {
            LogUtils.i(TAG, "加载图片：" + imgFile.getAbsolutePath());
            Glide.with(activity)
                    .load(imgFile)
//                .fitCenter() // 跟ImageView的ScaleType类似
//                .centerCrop()
                    .thumbnail(0.1f)
                    .crossFade()
                    .override(width, height)
//                    .error(R.drawable.ic_image_load_error)
//                    .diskCacheStrategy(DiskCacheStrategy.ALL) // 让Glide既缓存全尺寸又缓存其他尺寸
                    .into(iv);
        }
    }

    /**
     * 加载本地图片文件
     *
     * @param activity(Glide图片加载会和Activity/Fragment的生命周期保持一致)
     * @param filePath
     * @param iv
     */
    public static void loadImageWithFilePath(Activity activity, String filePath, ImageView iv) {
        File file = new File(filePath);
        if (file.isFile()) {
            LogUtils.i(TAG, "加载图片：" + filePath);
            Glide.with(activity)
                    .load(file)
                    .thumbnail(0.1f)
                    .crossFade()
//                    .error(R.drawable.ic_image_load_error)
//                    .diskCacheStrategy(DiskCacheStrategy.ALL) // 让Glide既缓存全尺寸又缓存其他尺寸
                    .into(iv);
        }
    }

    /**
     * 加载本地图片文件
     *
     * @param activity(Glide图片加载会和Activity/Fragment的生命周期保持一致)
     * @param filePath
     * @param iv
     */
    public static void loadImageWithFilePath(Activity activity, String filePath, ImageView iv, int width, int height) {
        File file = new File(filePath);
        if (file.isFile()) {
            LogUtils.i(TAG, "加载图片：" + filePath);
            Glide.with(activity)
                    .load(file)
                    .override(width, height)
                    .thumbnail(0.1f)
                    .crossFade()
//                    .error(R.drawable.ic_image_load_error)
//                    .diskCacheStrategy(DiskCacheStrategy.ALL) // 让Glide既缓存全尺寸又缓存其他尺寸
                    .into(iv);
        }
    }

    /**
     * 加载本地图片文件
     *
     * @param activity(Glide图片加载会和Activity/Fragment的生命周期保持一致)
     * @param url
     * @param iv
     */
    public static void loadImageWithUrl(Activity activity, String url, ImageView iv) {
        LogUtils.i(TAG, "加载图片：" + url);
        Glide.with(activity)
                .load(url)
                .thumbnail(0.1f)
                .crossFade()
                .dontAnimate()
                .placeholder(R.drawable.ic_image_load_pre)
//                .error(R.drawable.ic_image_load_error)
//                .diskCacheStrategy(DiskCacheStrategy.ALL) // 让Glide既缓存全尺寸又缓存其他尺寸
                .into(iv);
    }

    /**
     * 加载本地图片文件
     *
     * @param activity(Glide图片加载会和Activity/Fragment的生命周期保持一致)
     * @param url
     * @param iv
     */
    public static void loadImageWithUrl(Activity activity, String url, ImageView iv, int width, int height) {
        LogUtils.i(TAG, "加载图片：" + url);
        Glide.with(activity)
                .load(url)
                .thumbnail(0.1f)
                .crossFade()
                .dontAnimate()
                .placeholder(R.drawable.ic_image_load_pre)
                .override(width, height)
//                .error(R.drawable.ic_image_load_error)
//                .diskCacheStrategy(DiskCacheStrategy.ALL) // 让Glide既缓存全尺寸又缓存其他尺寸
                .into(iv);
    }

    /**
     * 加载本地图片文件
     *
     * @param activity(Glide图片加载会和Activity/Fragment的生命周期保持一致)
     * @param resId
     * @param iv
     */
    public static void loadImageWithResource(Activity activity, int resId, ImageView iv) {
        LogUtils.i(TAG, "加载图片：Resource");
        Glide.with(activity)
                .load(resId)
                .thumbnail(0.1f)
                .crossFade()
//                .error(R.drawable.ic_image_load_error)
//                .diskCacheStrategy(DiskCacheStrategy.ALL) // 让Glide既缓存全尺寸又缓存其他尺寸
                .into(iv);
    }

    /**
     * 加载本地图片文件
     *
     * @param activity(Glide图片加载会和Activity/Fragment的生命周期保持一致)
     * @param resId
     * @param iv
     */
    public static void loadImageWithResource(Activity activity, int resId, ImageView iv, int width, int height) {
        LogUtils.i(TAG, "加载图片：Resource");
        Glide.with(activity)
                .load(resId)
                .override(width, height)
                .thumbnail(0.1f)
                .crossFade()
//                .error(R.drawable.ic_image_load_error)
//                .diskCacheStrategy(DiskCacheStrategy.ALL) // 让Glide既缓存全尺寸又缓存其他尺寸
                .into(iv);
    }

    /**
     * 释放ImageView加载的资源
     *
     * @param iv (释放图片资源)
     */
    public static void clearImageView(ImageView iv) {
        LogUtils.i(TAG, "释放ImageView: ");
        Glide.clear(iv);
    }

}
