package com.android.collect.library.util;

import android.os.Build;

/**
 * Created by anzai on 2017/4/19.
 */
public class AndroidVersionUtil {

    /**
     * 系统版本大于7.0
     * @return
     */
    public static boolean isOver7() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.N;
    }

    /**
     * 系统版本大于6.0
     * @return
     */
    public static boolean isOver6() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M;
    }

    /**
     * 系统版本大于5.0
     * @return
     */
    public static boolean isOver5() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP;
    }

    /**
     * 系统版本大于4.4
     * @return
     */
    public static boolean isOver4() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;
    }
}
