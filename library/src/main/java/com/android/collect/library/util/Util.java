package com.android.collect.library.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import com.android.collect.library.common.Constant;
import com.android.collect.library.common.KeyHelper;

import java.util.List;

/**
 * Created by anzai on 2017/4/13.
 */

public class Util {

    /**
     * 字符串是否为空(为空返回：true)
     *
     * @param str
     * @return
     */
    public static boolean isStringNull(String str) {
        if (str == null || "".equals(str.trim()) || "null".equals(str.toLowerCase()))
            return true;
        return false;
    }

    /**
     * List是否为空(为空返回：true)
     *
     * @param list
     * @return
     */
    public static boolean isListEmpty(List list) {
        if (list == null || list.size() == 0)
            return true;
        return false;
    }

    /**
     * 去Bundle的值
     *
     * @param bundle
     * @param name
     * @param defaultStr
     * @return
     */
    public static String getBundleString(Bundle bundle, String name,
                                         String defaultStr) {
        if (bundle != null) {
            String str = bundle.getString(name);
            if (isStringNull(str))
                return defaultStr;
            else
                return str;
        } else
            return defaultStr;
    }

    /**
     * 非空
     */
    public static String toStr(String str) {
        if (isStringNull(str)) {
            return "";
        }
        return str;
    }

    /**
     * 退出APP
     */
    public static void exitApp() {
        // 友盟统计
        android.os.Process.killProcess(android.os.Process.myPid()); // 获取PID
        System.exit(0); // 常规java、c#的标准退出法，返回值为0代表正常退出
    }

    /**
     * 用于检测参数非空
     *
     * @param reference
     * @return
     */
    public static <T> T checkNotNull(T reference) {
        if (reference == null) {
            throw new NullPointerException();
        } else {
            return reference;
        }
    }

    /**
     * 用于检测参数非空
     *
     * @param reference
     * @param errorMessage
     * @return
     */
    public static <T> T checkNotNull(T reference, @Nullable Object errorMessage) {
        if (reference == null) {
            throw new NullPointerException(String.valueOf(errorMessage));
        } else {
            return reference;
        }
    }

    /**
     * 用于检测参数非空
     *
     * @param reference
     * @param errorParam
     * @return
     */
    public static <T> T checkNotNull(T reference, @Nullable String errorParam) {
        if (reference == null) {
            throw new NullPointerException(errorParam + "Can not be empty");
        } else {
            return reference;
        }
    }

    /**
     * 隐藏软键盘(适合在oncreate()调用，只能有效一次)
     */
    public static void hideSoftInput(Context context) {
        ((Activity) context).getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    /**
     * 隐藏软键盘(可实时隐藏)
     */
    public static void hideSoftInput2(Context context) {
        final InputMethodManager imm = (InputMethodManager) context
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        View currentFocusView = ((Activity) context).getCurrentFocus();
        if (currentFocusView != null) {
            IBinder windowToken = currentFocusView.getWindowToken();
            if (windowToken != null)
                imm.hideSoftInputFromWindow(windowToken, 0);

        }
    }

    /**
     * 登录成功广播
     *
     * @param context
     */
    public static void sendLoginSuccessBroadcast(Context context) {
        Intent login_intent = new Intent();
        login_intent.putExtra(KeyHelper.LoginStatus, Constant.LOGIN);
        login_intent.setAction(Constant.LOGIN_APP_ACTION);
        LocalBroadcastManager.getInstance(context).sendBroadcast(login_intent);
    }

    /**
     * 退出登录广播
     *
     * @param context
     */
    public static void sendLogoutBroadcast(Context context) {
        Intent login_intent = new Intent();
        login_intent.putExtra(KeyHelper.LoginStatus, Constant.LOGOUT);
        login_intent.setAction(Constant.LOGIN_APP_ACTION);
        LocalBroadcastManager.getInstance(context).sendBroadcast(login_intent);
    }

    /**
     * 增加View的paddingTop,增加的值为状态栏高度 (智能判断，并设置高度)
     */
    public static void setPaddingSmart(Context context, View view) {
        if (Build.VERSION.SDK_INT >= 16) {
            ViewGroup.LayoutParams lp = view.getLayoutParams();
            if (lp != null && lp.height > 0) {
                lp.height += getStatusBarHeight(context);//增高
            }
            view.setPadding(view.getPaddingLeft(), view.getPaddingTop() + getStatusBarHeight(context),
                    view.getPaddingRight(), view.getPaddingBottom());
        }
    }

    /**
     * 获取状态栏高度
     */
    public static int getStatusBarHeight(Context context) {
        int result = 24;
        int resId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resId > 0) {
            result = context.getResources().getDimensionPixelSize(resId);
        } else {
            result = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                    result, Resources.getSystem().getDisplayMetrics());
        }
        return result;

    }

}
