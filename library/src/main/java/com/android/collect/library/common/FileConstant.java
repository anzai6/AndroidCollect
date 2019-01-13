package com.android.collect.library.common;

import android.os.Environment;

/**
 * Created by WD on 2016/11/30.
 */
public class FileConstant {

    public static final String APP_FILE_SD_DIR = Environment
            .getExternalStorageDirectory() + "/com.android.collect/";
    public static final String APP_ASSET_DIR = "file:///android_asset/";

    public static final String APP_FILE_SD_DOWNLOAD_DIR = APP_FILE_SD_DIR + "download/";
    public static final String APP_FILE_SD_IMG_DIR = APP_FILE_SD_DIR + "image/";
    public static final String APP_FILE_SD_ADVERT_IMG_DIR = APP_FILE_SD_IMG_DIR + "advert/";

}
