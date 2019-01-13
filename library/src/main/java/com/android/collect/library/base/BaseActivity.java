package com.android.collect.library.base;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.android.collect.library.http.DataRepository;
import com.android.collect.library.util.AllActivityManager;
import com.android.collect.library.util.DialogUtil;
import com.android.collect.library.util.LogUtils;

public abstract class BaseActivity extends AppCompatActivity {

    protected final String TAG = LogUtils.getLogTag(getClass());

    protected abstract void setCurrentView();

    protected abstract void initView();

    protected abstract void setData();

    protected boolean isSaveData = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AllActivityManager.getInstance().pushActivity(this);
//        Util.hideSoftInput2(this);
        super.onCreate(savedInstanceState);
        setCurrentView();
        initView();
        setData();
    }

    /**
     * 字体默认
     */
    @Override
    public Resources getResources() {
        Resources res = super.getResources();
        Configuration config = new Configuration();
        config.setToDefaults();
        res.updateConfiguration(config, res.getDisplayMetrics());
        return res;
    }

    @Override
    protected void onSaveInstanceState(Bundle bundle) {
        if (isSaveData)
            super.onSaveInstanceState(bundle);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        DataRepository.cancelRequest(this);
        AllActivityManager.getInstance().removeActivity(this);
        DialogUtil.dismissLDialog();
    }

    protected Context getContext() {
        return this;
    }

    protected void removeHandlerCallAndMsg(Handler handler) {
        if (handler != null)
            handler.removeCallbacksAndMessages(null);
    }
}
