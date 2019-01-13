package com.android.collect.library.util;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by anzai on 2017/4/13.
 */

public class ToastUtils {

    /**
     * 提示
     *
     * @param mContext
     * @param str
     */
    public static void Toast(Context mContext, String str) {
        Toast.makeText(mContext, str, Toast.LENGTH_SHORT).show();
    }

    /**
     * 提示
     *
     * @param mContext
     * @param strId
     */
    public static void Toast(Context mContext, int strId) {
        Toast(mContext, mContext.getString(strId));
    }

    /**
     * 提示
     *
     * @param mContext
     * @param str
     */
    public static void LongToast(Context mContext, String str) {
        Toast.makeText(mContext, str, Toast.LENGTH_LONG).show();
    }

    /**
     * 提示
     *
     * @param mContext
     * @param strId
     */
    public static void LongToast(Context mContext, int strId) {
        LongToast(mContext, mContext.getString(strId));
    }
}
