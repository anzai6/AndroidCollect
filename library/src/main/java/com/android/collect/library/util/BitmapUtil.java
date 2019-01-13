package com.android.collect.library.util;

import android.annotation.SuppressLint;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.PixelFormat;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.ImageView;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * 图片处理（压缩图片最好通过流、字节数组、文件路径直接压缩图片，
 * 不要转换成bitmap在压缩--耗时太多，当逼不得已对bitmap压缩时应该使用zoomBitmap通过缩放来压缩图片）
 *
 * @author anzai
 */
public class BitmapUtil {

    /**
     * 常用的BitmapFactory.Options
     */
    public static Options getCommonBitmapOptions() {
        Options opt = new Options();
        // 这个值是设置色彩模式，默认值是ARGB_8888，在这个模式下，一个像素点占用4bytes空间，一般对透明度不做要求的话，一般采用RGB_565模式，这个模式下一个像素点占用2bytes
        opt.inPreferredConfig = Bitmap.Config.RGB_565;
        /* 下面两个字段需要组合使用 */
        opt.inPurgeable = true;// 允许可清除
        opt.inInputShareable = true;// 以上options的两个属性必须联合使用才会有效果
        return opt;

    }

    /**
     * 根据资源ID读取图片
     *
     * @param context
     * @param resId
     * @return
     */
    public static Bitmap decodeResource(Context context, int resId) {
        // 获取资源图片
        InputStream is = context.getResources().openRawResource(resId);
        return decodeStream(is);

    }

    /**
     * 根据官方介绍BitmapFactory.decodeStream()有可能解析BItmap失败，返回null，
     * 所以需转变为字节数组读取：BitmapFactory.decodeByteArray()
     *
     * @param is
     * @return
     */
    public static Bitmap decodeStream(InputStream is) {
        Bitmap bmp = BitmapFactory.decodeStream(is, null,
                getCommonBitmapOptions());
        if (bmp == null) {
            try {
                byte[] data = readStreamToByteArray(is);
                return BitmapFactory.decodeByteArray(data, 0, data.length,
                        getCommonBitmapOptions());
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        } else
            return bmp;
    }

    /**
     * 根据官方介绍BitmapFactory.decodeStream()有可能解析BItmap失败，返回null，因为流读取过一次之后再读就会失败
     * 所以需转变为字节数组读取：BitmapFactory.decodeByteArray()
     *
     * @param is
     * @param option
     * @return
     */
    public static Bitmap decodeStream(InputStream is,
                                      Options option) {
        Bitmap bmp = BitmapFactory.decodeStream(is, null, option);
        if (bmp == null) {
            try {
                byte[] data = readStreamToByteArray(is);
                return BitmapFactory.decodeByteArray(data, 0, data.length,
                        option);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        } else
            return bmp;
    }

    /**
     * 根据文件路径获取图片
     *
     * @param path
     * @return
     */
    public static Bitmap decodeFile(String path) {
        return BitmapFactory.decodeFile(path, getCommonBitmapOptions());
    }

    /**
     * 根据字节数组获取图片
     *
     * @param data
     * @return
     */
    public static Bitmap decodeByteArray(byte[] data) {
        return BitmapFactory.decodeByteArray(data, 0, data.length,
                getCommonBitmapOptions());

    }

    /**
     * 根据uri获取图片bitmap
     *
     * @param context
     * @param uri
     * @return
     */
    public static Bitmap decodeUri(Context context, Uri uri) {
        Bitmap bitmap = null;
        try {
            bitmap = MediaStore.Images.Media.getBitmap(
                    context.getContentResolver(), uri);
            return bitmap;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 判断文件夹内是否存在对于图片文件，有则读取返回bitmap
     *
     * @param file_path
     * @param cxt
     * @param imgName   （包含后缀名）
     * @return
     */
    public static Bitmap getImgFromFileLocal(String file_path, Context cxt,
                                             String imgName) {
        Bitmap bitmap = null;
        if (!Util.isStringNull(imgName)) {
            File file = new File(file_path);
            if (file.exists()) {
                File[] files = file.listFiles();
                for (int j = 0; j < files.length; j++) {
                    String name = files[j].getName();
                    if (files[j].isFile()) {
                        if (name.equals(imgName)) {
                            bitmap = decodeFile(files[j].getPath());
                            return bitmap;
                        }
                    }
                }
            }
        }
        return bitmap;
    }

    /**
     * 降低图片质量来压缩图片大小。size（单位为byte,1024代表1k,1024*1024代表1M）
     *
     * @param bmp
     * @param size （把图片压缩至低于size大小）
     * @return
     * @throws Exception
     */
    public static Bitmap compressBitmapByQuality2(Bitmap bmp, int size) {
        if (bmp != null) {
            return decodeByteArray(compressBitmapByQuality1(bmp, size));
        } else {
            return null;
        }
    }

    /**
     * 降低图片质量来压缩图片大小。size（单位为byte,1024代表1k,1024*1024代表1M）
     *
     * @param bmp
     * @param size （把图片压缩至低于size大小）
     * @return
     * @throws Exception
     */
    public static byte[] compressBitmapByQuality1(Bitmap bmp, int size) {
        if (bmp != null) {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            bmp.compress(CompressFormat.PNG, 100, bos);
            int quality = 100;

            // 算出需要压缩的比例
            int options = size * 100 / bos.toByteArray().length;
            // 最低不超过20
            if (options < 20) {
                options = 20;
            }

            // quality=90代表压缩10%的质量
            bmp.compress(CompressFormat.PNG, quality, bos);
            return bos.toByteArray();
        } else {
            return null;
        }
    }

    /**
     * 根据需要压缩的宽高计算出最大压缩比例
     *
     * @param options
     * @param rqsW
     * @param rqsH
     * @return
     */
    public final static int caculateInSampleSize(Options options,
                                                 int rqsW, int rqsH) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;
        if (rqsW == 0 || rqsH == 0)
            return 1;
        if (height > rqsH || width > rqsW) {
            final int heightRatio = Math.round((float) height / (float) rqsH);
            final int widthRatio = Math.round((float) width / (float) rqsW);
            // 取最小比例
            // inSampleSize = heightRatio < widthRatio ? heightRatio :
            // widthRatio;
            // 取最大比例
            inSampleSize = heightRatio < widthRatio ? widthRatio : heightRatio;
        }
        if (inSampleSize <= 1) {// 小于1则不处理
            inSampleSize = 0;
        }
        return inSampleSize;
    }

    /**
     * 根据目标宽高的最大压缩比例，压缩指定路径的图片，并得到图片对象（图片宽高比例不变）
     *
     * @param path bitmap source path
     * @param rqsW
     * @param rqsH
     * @return Bitmap {@link Bitmap}
     */
    public final static Bitmap compressBitmap(String path, int rqsW, int rqsH) {
        Options options = getCommonBitmapOptions();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, options);
        options.inSampleSize = caculateInSampleSize(options, rqsW, rqsH);
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(path, options);
    }

    /**
     * 根据目标宽高的最大压缩比例，压缩指定路径的图片，并得到图片对象（图片宽高比例不变）
     *
     * @param context
     * @param resId
     * @param rqsW
     * @param rqsH
     * @return Bitmap {@link Bitmap}
     */
    public final static Bitmap compressBitmap(Context context, int resId,
                                              int rqsW, int rqsH) {
        InputStream is = context.getResources().openRawResource(resId);
        return compressBitmap(is, rqsW, rqsH);
    }

    /**
     * 根据目标宽高的最大压缩比例，压缩指定InputStream图片，并得到压缩后的图像（图片宽高比例不变）
     *
     * @param is
     * @param reqsW
     * @param reqsH
     * @return Bitmap {@link Bitmap}
     */
    public final static Bitmap compressBitmap(InputStream is, int reqsW,
                                              int reqsH) {
        try {
            byte[] data = readStreamToByteArray(is);
            return compressBitmap(data, reqsW, reqsH);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 根据目标宽高的最大压缩比例，压缩指定byte[]图片，并得到压缩后的图像（图片宽高比例不变）
     *
     * @param bts
     * @param reqsW
     * @param reqsH
     * @return
     */
    public final static Bitmap compressBitmap(byte[] bts, int reqsW, int reqsH) {
        Options options = getCommonBitmapOptions();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeByteArray(bts, 0, bts.length, options);
        options.inSampleSize = caculateInSampleSize(options, reqsW, reqsH);
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeByteArray(bts, 0, bts.length, options);
    }

    /**
     * 对bitmap进行压缩耗时比较多，应该使用zoom方法缩放(根据所给的宽度按图片宽高比例压缩图片)
     *
     * @param bitmap
     * @param newWidth
     * @return
     */
    public final static Bitmap compressBitmap(Bitmap bitmap, int newWidth) {
        return compressBitmap(bitmap, newWidth,
                getBitmapNewHeight(bitmap, newWidth));
    }

    /**
     * 对bitmap进行压缩耗时比较多，应该使用zoom方法缩放
     *
     * @param bitmap
     * @param reqsW
     * @param reqsH
     * @return
     */
    public final static Bitmap compressBitmap(Bitmap bitmap, int reqsW,
                                              int reqsH) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(CompressFormat.PNG, 100, baos);
            byte[] bts = baos.toByteArray();
            Bitmap res = compressBitmap(bts, reqsW, reqsH);
            baos.close();
            return res;
        } catch (IOException e) {
            e.printStackTrace();
            return bitmap;
        }
    }

    /**
     * 根据所给的宽度按比例缩放图片,缩放比等比压缩耗时少得多
     *
     * @param bm
     * @param newWidth
     * @return
     */
    public static Bitmap zoomBitmap(Bitmap bm, int newWidth) {
        return zoomBitmap(bm, newWidth, getBitmapNewHeight(bm, newWidth));
    }

    /**
     * 根据所给的宽度和高度缩放图片
     *
     * @param bm
     * @param newWidth
     * @param newHeight
     * @return
     */
    public static Bitmap zoomBitmap(Bitmap bm, int newWidth, int newHeight) {
        // 获得图片的宽高
        int width = bm.getWidth();
        int height = bm.getHeight();
        // 计算缩放比例
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // 取得想要缩放的matrix参数
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        // 得到新的图片
        Bitmap newbm = Bitmap.createBitmap(bm, 0, 0, width, height, matrix,
                true);
        return newbm;
    }

    /**
     * 根据所给的新宽度按图片宽高比例获取新的高度
     *
     * @param bm
     * @param newWidth
     * @return
     */
    public static int getBitmapNewHeight(Bitmap bm, int newWidth) {
        int width = bm.getWidth();
        int height = bm.getHeight();
        BigDecimal bd1 = new BigDecimal(newWidth);
        BigDecimal bd2 = bd1.multiply(new BigDecimal(height));
        int newHeight = bd2.divide(new BigDecimal(width), 2,
                BigDecimal.ROUND_HALF_UP).intValue();
        return newHeight;
    }

    /**
     * drawable 转成bitmap
     *
     * @param drawable
     * @return
     */
    public static Bitmap drawableToBitmap(Drawable drawable) {
        // 取 drawable 的长宽
        int w = drawable.getIntrinsicWidth();
        int h = drawable.getIntrinsicHeight();
        // 取 drawable 的颜色格式
        Bitmap.Config config = drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
                : Bitmap.Config.RGB_565;
        // 建立对应 bitmap
        Bitmap bitmap = Bitmap.createBitmap(w, h, config);
        // 建立对应 bitmap 的画布
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, w, h);
        // 把 drawable 内容画到画布中
        drawable.draw(canvas);
        return bitmap;
    }

    /**
     * 通过URL获取图片文件路径
     *
     * @param context
     * @param uri
     * @return
     */
    @SuppressLint("NewApi")
    public static String getPathFromUri(Context context, Uri uri) {

        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

        // DocumentProvider
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/"
                            + split[1];
                } else {
                    return Environment.getExternalStorageDirectory() + "/"
                            + split[1];
                }
            } else if (isDownloadsDocument(uri)) {

                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"),
                        Long.valueOf(id));

                return getDataColumn(context, contentUri, null, null);
            }
            // MediaProvider
            else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[]{split[1]};

                return getDataColumn(context, contentUri, selection,
                        selectionArgs);
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {

            // Return the remote address
            if (isGooglePhotosUri(uri))
                return uri.getLastPathSegment();

            return getDataColumn(context, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        return null;
    }

    /**
     * Get the value of the data column for this Uri. This is useful for
     * MediaStore Uris, and other file-based ContentProviders.
     *
     * @param context       The context.
     * @param uri           The Uri to query.
     * @param selection     (Optional) Filter used in the query.
     * @param selectionArgs (Optional) Selection arguments used in the query.
     * @return The value of the _data column, which is typically a file path.
     */
    public static String getDataColumn(Context context, Uri uri,
                                       String selection, String[] selectionArgs) {

        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {column};

        try {
            cursor = context.getContentResolver().query(uri, projection,
                    selection, selectionArgs, null);
            if (cursor != null && cursor.moveToFirst()) {
                final int index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri
                .getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri
                .getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri
                .getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is Google Photos.
     */
    public static boolean isGooglePhotosUri(Uri uri) {
        return "com.google.android.apps.photos.content".equals(uri
                .getAuthority());
    }

    /**
     * 获取外置SD卡路径
     *
     * @return 应该就一条记录或空
     */
    public static String getExtSDCardPath() {
        List<String> lResult = new ArrayList<String>();
        try {
            Runtime rt = Runtime.getRuntime();
            Process proc = rt.exec("mount");
            InputStream is = proc.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            String line;
            while ((line = br.readLine()) != null) {
                if (line.contains("extSdCard")) {
                    String[] arr = line.split(" ");
                    String path = arr[1];
                    File file = new File(path);
                    if (file.isDirectory()) {
                        lResult.add(path);
                    }
                }
            }
            isr.close();
        } catch (Exception e) {
        }
        String sd_path = "";
        if (lResult.size() > 0) {
            sd_path = lResult.get(0);
        }
        return sd_path;
    }

    /**
     * 释放ImageView里的图片，防止加载过多内存溢出
     * 注意：通过XML给控件设置的Drawable最好不要recycle()，除非该Drawable只使用一次
     *
     * @param imageView
     */
    @SuppressWarnings("deprecation")
    public static void releaseImageViewResouce(ImageView imageView) {

        if (imageView == null)
            return;
        Drawable drawable = imageView.getDrawable();
        if (drawable != null && drawable instanceof BitmapDrawable) {
            BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
            Bitmap bitmap = bitmapDrawable.getBitmap();
            recycleBitmap(bitmap);
        }
        imageView.setImageDrawable(null);
        imageView.setBackgroundDrawable(null);
        if (drawable != null) {
            drawable.setCallback(null);
        }
        imageView = null;
        System.gc();
    }

    /**
     * 释放View里的背景图片，防止加载过多内存溢出
     * 注意：通过XML给控件设置的Drawable最好不要recycle()，除非该Drawable只使用一次
     *
     * @param view
     */
    @SuppressWarnings("deprecation")
    public static void releaseViewBackground(View view) {

        if (view == null)
            return;
        Drawable drawable = view.getBackground();

        view.setBackgroundDrawable(null);
        if (drawable != null)
            drawable.setCallback(null);
        if (drawable != null && drawable instanceof BitmapDrawable) {
            BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
            Bitmap bitmap = bitmapDrawable.getBitmap();
            recycleBitmap(bitmap);
        }
        view = null;
    }

    /**
     * 获取图片资源在工程中的id
     */
    public static int getDrawableIdByName(Context context, String xmlName) {
        return getResourceId(context, "drawable", xmlName);
    }

    /**
     * 根据资源文件名获取相应实例
     */
    public static int getResourceId(Context context, String xmlFile,
                                    String xmlName) {
        Class localClass = null;
        try {
            localClass = Class.forName(context.getPackageName() + ".R$"
                    + xmlFile);
        } catch (ClassNotFoundException exception) {
            exception.printStackTrace();
            return 0;
        }
        Field localField = null;
        try {
            localField = localClass.getField(xmlName);
        } catch (SecurityException exception) {
            exception.printStackTrace();
            return 0;
        } catch (NoSuchFieldException exception) {
            exception.printStackTrace();
            return 0;
        }
        try {
            return Integer.parseInt(localField.get(localField.getName())
                    .toString());
        } catch (NumberFormatException exception) {
            exception.printStackTrace();
            return 0;
        } catch (IllegalArgumentException exception) {
            exception.printStackTrace();
            return 0;
        } catch (IllegalAccessException exception) {
            exception.printStackTrace();
            return 0;
        }
    }

    /**
     * 将Bitmap转换成字符串
     *
     * @param bitmap
     * @return
     * @author guo_fenghua
     */
    public static String bitmapToString(Bitmap bitmap) {

        byte[] bytes = bitmapToByteArray(bitmap);
        return Base64.encodeToString(bytes, Base64.DEFAULT);
    }

    /**
     * 将Bitmap转换成字符串(科蓝使用)
     *
     * @param bitmap
     * @return
     * @author guo_fenghua
     */
    public static String bitmapToStrOnCompany(Bitmap bitmap) {

        byte[] bytes = bitmapToByteArray(bitmap);
        return com.android.collect.library.common.Base64.encode(bytes);
    }

    /**
     * 将字符串转换成Bitmap类型
     *
     * @param string
     * @return
     * @author guo_fenghua
     */
    public static Bitmap strToBitmap(String string) {
        Bitmap bitmap = null;
        try {
            byte[] bitmapArray;
            bitmapArray = Base64.decode(string, Base64.DEFAULT);
            bitmap = BitmapFactory.decodeByteArray(bitmapArray, 0,
                    bitmapArray.length);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return bitmap;
    }

    /**
     * 将字符串转换成Bitmap类型
     *
     * @param bitmap
     * @return
     */
    public static byte[] bitmapToByteArray(Bitmap bitmap) {

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        bitmap.compress(CompressFormat.PNG, 100, bos);
        byte[] bytes = bos.toByteArray();

        return bytes;
    }

    /**
     * 回收bitmap,释放内存，释放多个Bitmap时，System.gc()最好只调用一次，
     *
     * @param bitmap
     */
    public static void recycleBitmap(Bitmap bitmap) {
        if (bitmap != null && !bitmap.isRecycled()) {
            bitmap.recycle();
            bitmap = null;
        }
    }

    /**
     * 获取图片需要旋转的角度 ，然后通过下面图片旋转方法rotateBitmap()旋转图片
     *
     * @param path 图片绝对路径
     * @return degree旋转的角度
     */
    public static int readPictureDegree(String path) {
        int degree = 0;
        try {
            ExifInterface exifInterface = new ExifInterface(path);
            int orientation = exifInterface.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return degree;
        }
        return degree;
    }

    /**
     * 旋转图片，使图片保持正确的方向。
     *
     * @param bitmap  原始图片
     * @param degrees 原始图片的角度
     * @return Bitmap 旋转后的图片
     */
    public static Bitmap rotateBitmap(Bitmap bitmap, int degrees) {
        if (degrees == 0 || null == bitmap) {
            return bitmap;
        }
        Matrix matrix = new Matrix();
        matrix.setRotate(degrees, bitmap.getWidth() / 2, bitmap.getHeight() / 2);
        Bitmap bmp = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
                bitmap.getHeight(), matrix, true);
        if (null != bitmap) {
            bitmap.recycle();
        }
        return bmp;
    }

    /**
     * 得到图片字节流 数组大小
     */
    public static byte[] readStreamToByteArray(InputStream is) throws Exception {
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = 0;
        while ((len = is.read(buffer)) != -1) {
            outStream.write(buffer, 0, len);
        }
        outStream.close();
        is.close();
        return outStream.toByteArray();
    }

    /**
     * 获取图片大小
     *
     * @param bitmap
     * @return
     */
    @SuppressLint("NewApi")
    public static int getBitmapSize(Bitmap bitmap) {
        if (Build.VERSION.SDK_INT >= 19)
            return bitmap.getAllocationByteCount();
        if (Build.VERSION.SDK_INT >= 12)
            return bitmap.getByteCount();
        else
            return bitmap.getRowBytes() * bitmap.getHeight();
    }

}
