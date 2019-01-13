package com.android.collect.library.common;

import android.app.AlarmManager;
import android.app.Application;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.android.collect.library.ui.splash.SplashActivity;
import com.android.collect.library.util.LogUtils;
import com.android.collect.library.util.SharedPreferencesUtil;
import com.android.collect.library.util.Util;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.Thread.UncaughtExceptionHandler;

import cn.bingoogolapple.swipebacklayout.BGASwipeBackManager;

public class CsiiApplication extends Application {

    private static final String TAG = LogUtils.getLogTag(CsiiApplication.class);

    private MyUncaughtExceptionHandler uncaughtExceptionHandler;

    @Override
    public void onCreate() {
        super.onCreate();
        if (Constant.isNeedCaughtExeption) {
            cauchException();
        }
        // 必须在 Application 的 onCreate 方法中执行 BGASwipeBackManager.getInstance().init(this) 来初始化滑动返回
        BGASwipeBackManager.getInstance().init(this);
    }

    private void cauchException() {

        // 程序崩溃时触发线程
        uncaughtExceptionHandler = new MyUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(uncaughtExceptionHandler);
    }

    // 创建服务用于捕获崩溃异常
    private class MyUncaughtExceptionHandler implements
            UncaughtExceptionHandler {
        @Override
        public void uncaughtException(Thread thread, final Throwable ex) {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            ex.printStackTrace(pw);
            String errorMsg = sw.toString();
            LogUtils.e(TAG, errorMsg);
            SharedPreferencesUtil.saveStringNoEncry(getApplicationContext(), KeyHelper.ErrorLog, errorMsg);
            // 必须新建一个线程并开启一个消息循环才能显示Toast
//			new Thread() {
//				@Override
//				public void run() {
//					Looper.prepare();
//					StringWriter sw = new StringWriter();
//					PrintWriter pw = new PrintWriter(sw);
//					ex.printStackTrace(pw);
//					String errorMsg = sw.toString();
//					LogUtils.e(TAG, errorMsg);
//					Toast.makeText(getApplicationContext(),
//							"很抱歉,程序出现异常,即将退出并重启.", Toast.LENGTH_LONG).show();
//					Looper.loop();
//				}
//			}.start();
//			try {
//				Thread.sleep(150000);
//			} catch (InterruptedException e) {
//				Log.e("WHSMZX", "error : ", e);
//			}

//			restartApp();
            Util.exitApp();
        }
    }

    ;

    /**
     * 1秒钟后重启应用（注意：必须关闭当前应用杀掉进程才能重启）
     */
    private void restartApp() {
        Intent intent = new Intent(getApplicationContext(),
                SplashActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        PendingIntent restartIntent = PendingIntent.getActivity(
                getApplicationContext(), 0, intent, 0);
        AlarmManager mgr = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        mgr.set(AlarmManager.RTC, System.currentTimeMillis() + 500,
                restartIntent);

        // 关闭当前应用
        Util.exitApp();
    }

}
