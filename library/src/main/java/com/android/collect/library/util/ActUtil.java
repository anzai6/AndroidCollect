package com.android.collect.library.util;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.view.View;

import com.android.collect.library.callback.DataResultCallBack;
import com.android.collect.library.common.Constant;
import com.android.collect.library.common.KeyHelper;
import com.android.collect.library.http.DataRepository;
import com.android.collect.library.http.HttpHelper;
import com.android.collect.library.manager.OnNewIntentManager;
import com.android.collect.library.manager.UserManager;

import org.json.JSONObject;

/**
 * 启动页面公共类
 */
public class ActUtil {


    public static void startMyActivity(Activity activity, Class clazz) {
        startMyActivity(activity, clazz, new Bundle());
    }

    public static void startMyActivity(Activity activity, String className) {
        startMyActivity(activity, className, new Bundle());
    }

    public static void startMyActivity(Activity activity, Class clazz,
                                       Bundle bundle) {
        startMyActivity(activity, clazz.getName(), bundle);
    }

    public static void startMyActivity(Activity activity, String className,
                                       Bundle bundle) {
        Intent targetIntent = new Intent();
        targetIntent.putExtras(bundle);
        targetIntent.setClassName(activity, className);

        int flag = bundle.getInt(KeyHelper.IntentFlag);
        if (flag == Intent.FLAG_ACTIVITY_CLEAR_TOP) {
            targetIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        }
        activity.startActivity(targetIntent);

    }

    /**
     * 公共登录跳转
     */
    public static void startActivityNeedLogin(Activity activity, Class clazz) {
        startActivityNeedLogin(activity, clazz, new Bundle());
    }

    public static void startActivityNeedLogin(Activity activity,
                                              String className) {
        startActivityNeedLogin(activity, className, new Bundle());
    }

    public static void startActivityNeedLogin(Activity activity, Class clazz,
                                              Bundle bundle) {
        startActivityNeedLogin(activity, clazz.getName(), bundle);
    }

    public static void startActivityNeedLogin(final Activity activity,
                                              String className, final Bundle bundle) {
        bundle.putString(KeyHelper.From, activity.getClass().getName());
        bundle.putString(KeyHelper.ClassName, className);
        if (!UserManager.isLogin) { // 没有登录
//            startMyActivity(activity, LoginActivity.class, bundle);
        } else { // 正常跳转
            startMyActivity(activity, className, bundle);
        }
    }

    public static void startActivityForResult(Activity activity, Class clazz,
                                              Bundle bundle, int requestCode) {
        Intent intent = new Intent();
        intent.putExtras(bundle);
        intent.setClassName(activity, clazz.getName());
        activity.startActivityForResult(intent, requestCode);
    }

    /**
     * 根据包名，跳转应用市场APP详情页
     *
     * @param context
     * @param packageName
     */
    public static void goToMarket(Context context, String packageName) {
        Uri uri = Uri.parse("market://details?id=" + packageName);
        Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
        try {
            context.startActivity(goToMarket);
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * 拨打电话
     *
     * @param context
     * @param phone
     */
    public static void startSystemtPhone(Context context, String phone) {
        Intent intent1 = new Intent(Intent.ACTION_CALL, Uri
                .parse("tel:" + phone));
        context.startActivity(intent1);
    }

    /**
     * 需要登录回到当前页面做交易
     */
    public static void reStartCurrentActivityNeedLogin(Activity activity, int targetFlag) {
        reStartCurrentActivityNeedLogin(activity, new Bundle(), targetFlag);
    }

    /**
     * 需要登录回到当前页面做交易
     */
    public static void reStartCurrentActivityNeedLogin(Activity activity, Bundle bundle, int targetFlag) {
        bundle.putInt(KeyHelper.TargetFlag, targetFlag);
        if (UserManager.isLogin) { // 登录且绑卡
            OnNewIntentManager.toTarget(activity, bundle);
        } else {
            startActivityNeedLogin(activity, activity.getClass(), bundle);
        }
    }

    /**
     * 共享元素启动activity
     */
    public static void startActivityWithTransition(Activity activity, Class clazz,
                                                   Bundle bundle, View view, int shareNameId) {
        Intent intent = new Intent(activity, clazz);
        intent.putExtras(bundle);
        ActivityOptionsCompat compat = ActivityOptionsCompat.makeSceneTransitionAnimation(activity,
                view, activity.getString(shareNameId));
        ActivityCompat.startActivity(activity, intent, compat.toBundle());
    }

}
