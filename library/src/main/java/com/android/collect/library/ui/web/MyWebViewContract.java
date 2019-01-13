package com.android.collect.library.ui.web;

import android.content.Intent;
import android.net.Uri;
import android.webkit.ValueCallback;

import com.android.collect.library.base.BasePresenter;
import com.android.collect.library.base.BaseView;

/**
 * Created by anzai on 2017/7/12.
 */

public interface MyWebViewContract {

    interface Presenter extends BasePresenter {
        void onActivityResult(int requestCode, int resultCode, Intent data);

        void onNewIntent(Intent intent);

        String getTargJson();

        void startFileSelectActivity(ValueCallback<Uri> mUploadMessage, ValueCallback<Uri[]> filePathCallback);
    }

    interface View extends BaseView {

        void loadUrl(String url);

        /**
         * 设置标题
         *
         * @param title
         */
        void setTitle(String title);

        /**
         * 加载URL进度变化
         *
         * @param newProgress
         */
        void onLoadProgressChanged(int newProgress);

        /**
         * 显示进度条
         */
        void showLoadProgress();

        /**
         * 加载结束
         */
        void onLoadUrlFinished();

        /**
         * 关闭网页
         */
        void pageFinish();

        /**
         * 加载时給HTML页面上送参数
         */
        void obtainClientData(String key);

        /**
         * 給HTML页面回传参数
         */
        void doCallback(String key, String jsonData);
    }
}
