package com.android.collect.library.ui.main.presenter;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;

import com.android.collect.library.common.Constant;
import com.android.collect.library.common.KeyHelper;
import com.android.collect.library.manager.UserManager;
import com.android.collect.library.ui.main.contract.MainActivityContract;
import com.android.collect.library.util.Util;

public class MainActivityPresenter implements MainActivityContract.Presenter {

    private MainActivityContract.View mMainActivityView;
    private Activity activity;
    private LoginStatusChangeReceiver changeReceiver;
    private Bundle bundle;

    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
        }
    };

    public MainActivityPresenter(Activity activity, MainActivityContract.View mMainActivityView) {
        this.activity = activity;
        this.mMainActivityView = Util.checkNotNull(mMainActivityView);
    }

    @Override
    public void subscribe(Bundle bundle) {
        this.bundle = bundle;
        initLoginStatuReceiver();
    }

    private void initLoginStatuReceiver() {
        // 注册 登录状态改变的广播接受者
        changeReceiver = new LoginStatusChangeReceiver();
        IntentFilter loginFilter = new IntentFilter(Constant.LOGIN_APP_ACTION);
        activity.registerReceiver(changeReceiver, loginFilter);
    }

    /**
     * 登录状态广播
     */
    class LoginStatusChangeReceiver extends BroadcastReceiver {

        public void onReceive(Context context, Intent intent) {
            if (context != null && intent != null
                    && Constant.LOGIN_APP_ACTION.equals(intent.getAction())) {
                int action = intent.getIntExtra(KeyHelper.LoginStatus,
                        Constant.LOGIN);
                switch (action) {
                    case Constant.LOGIN:
                        UserManager.isLogin = true;
                        handler.sendEmptyMessage(1);
                        mMainActivityView.onLoginSuccess();
                        break;
                    case Constant.LOGOUT:

                        UserManager.isLogin = false;
                        UserManager.getInstance().clearUser();
                        mMainActivityView.onLoginOut();
                        break;
                }
            }
        }
    }

    @Override
    public void unSubscribe() {
        if (changeReceiver != null)
            activity.unregisterReceiver(changeReceiver);
        if (handler != null)
            handler.removeCallbacksAndMessages(null);
    }

}
