package com.android.collect.library.util;

import android.util.Log;

import com.android.collect.library.common.Constant;

/**
 * 统一日志打印
 */
public class LogUtils {
    public static String getLogTag(Class cls) {
        return cls.getSimpleName();
    }

    private static final String APP_TAG = "ZBDIRECT";

    private static final String NULL_ERROR = "需打印数据为空";
    private static final String COMMON_SPACE = ">>>>>> ";

    public static void v(String msg) {
        if (Constant.isPrintMsg) {
            if (Util.isStringNull(msg))
                msg = NULL_ERROR;
            Log.v(APP_TAG, COMMON_SPACE + msg);
        }
    }

    public static void v(String tag, String msg) {
        if (Constant.isPrintMsg) {
            if (Util.isStringNull(msg))
                msg = NULL_ERROR;
            Log.v(APP_TAG + ":" + tag, COMMON_SPACE + msg);
        }
    }

    public static void d(String msg) {
        if (Constant.isPrintMsg) {
            if (Util.isStringNull(msg))
                msg = NULL_ERROR;
            Log.i(APP_TAG, COMMON_SPACE + msg);
        }
    }

    public static void d(String tag, String msg) {
        if (Constant.isPrintMsg) {
            if (Util.isStringNull(msg))
                msg = NULL_ERROR;
            Log.i(APP_TAG + ":" + tag, COMMON_SPACE + msg);
        }
    }

    public static void i(String msg) {
        if (Constant.isPrintMsg) {
            if (Util.isStringNull(msg))
                msg = NULL_ERROR;
            Log.i(APP_TAG, COMMON_SPACE + msg);
        }
    }

    public static void i(String tag, String msg) {
        if (Constant.isPrintMsg) {
            if (Util.isStringNull(msg))
                msg = NULL_ERROR;
            Log.i(APP_TAG + ":" + tag, COMMON_SPACE + msg);
        }
    }

    public static void w(String msg) {
        if (Constant.isPrintMsg) {
            if (Util.isStringNull(msg))
                msg = NULL_ERROR;
            Log.w(APP_TAG, COMMON_SPACE + msg);
        }
    }

    public static void w(String tag, String msg) {
        if (Constant.isPrintMsg) {
            if (Util.isStringNull(msg))
                msg = NULL_ERROR;
            Log.w(APP_TAG + ":" + tag, COMMON_SPACE + msg);
        }
    }

    public static void e(String msg) {
        if (Constant.isPrintMsg) {
            if (Util.isStringNull(msg))
                msg = NULL_ERROR;
            Log.e(APP_TAG, COMMON_SPACE + msg);
        }
    }

    public static void e(String tag, String msg) {
        if (Constant.isPrintMsg) {
            if (Util.isStringNull(msg))
                msg = NULL_ERROR;
            Log.e(APP_TAG + ":" + tag, COMMON_SPACE + msg);
        }
    }
}
