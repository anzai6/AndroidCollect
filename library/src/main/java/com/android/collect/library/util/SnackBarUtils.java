package com.android.collect.library.util;

import android.app.Activity;
import android.support.design.widget.Snackbar;
import android.view.View;

/**
 * Created by anzai on 2017/4/13.
 */

public class SnackBarUtils {

    /**
     * 提示
     *
     * @param view
     * @param str
     */
    public static void showTip(View view, String str) {
        Snackbar.make(view, str, Snackbar.LENGTH_SHORT).show();
        Activity activity;
    }

    /**
     * 提示
     *
     * @param view
     * @param strId
     */
    public static void showTip(View view, int strId) {
        Snackbar.make(view, strId, Snackbar.LENGTH_SHORT).show();
    }

    /**
     * 提示
     *
     * @param view
     * @param str
     */
    public static void showLongTip(View view, String str) {
        Snackbar.make(view, str, Snackbar.LENGTH_LONG).show();
    }

    /**
     * 提示
     *
     * @param view
     * @param strId
     */
    public static void showLongTip(View view, int strId) {
        Snackbar.make(view, strId, Snackbar.LENGTH_LONG).show();
    }
}
