package com.android.collect.library.ui.web;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ProgressBar;

import com.android.collect.library.R;
import com.android.collect.library.base.SwipeBackTitleActivity;
import com.android.collect.library.common.Constant;
import com.android.collect.library.util.DialogUtil;
import com.android.collect.library.util.LogUtils;
import com.android.collect.library.util.Util;

/**
 * 加载VX网页
 *
 * @author anzai
 */
public class MyWebViewActivity extends SwipeBackTitleActivity implements MyWebViewContract.View {

    private WebView mWebview;
    private ProgressBar mProgressBar;

    private MyWebViewContract.Presenter mMyWebViewPresenter;
    private WebHandler mWebHandler;

    @Override
    protected void setCurrentView() {
        setContentView(R.layout.activity_my_webview);
        initTitle(Constant.TITLE1, "");

    }

    @Override
    protected void initView() {
        // webView初始化控件
        mWebview = (WebView) findViewById(R.id.webview);
        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);

        webViewSetting();

    }

    /**
     * WebView的属性设置
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void webViewSetting() {

//		String UserAgentString = menuWebSettings.getUserAgentString();
//		if (!UserAgentString.contains(CircleConstant.LOCAL_USERAGENT))
//			menuWebSettings.setUserAgentString(UserAgentString + " "
//					+ CircleConstant.LOCAL_USERAGENT);


        // 解决软键盘遮挡输入框的问题
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE
                        | WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        // 硬件加速
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED,
                WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED);

        WebSettings menuWebSettings = mWebview.getSettings();
        menuWebSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        menuWebSettings.setJavaScriptEnabled(true);
        menuWebSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        menuWebSettings.setDatabaseEnabled(true);
        menuWebSettings.setDomStorageEnabled(true);
        menuWebSettings.setAppCacheEnabled(false);
        // 允许通过file域url中的Javascript读取其他本地文件
        menuWebSettings.setAllowFileAccessFromFileURLs(true);
        menuWebSettings.setAllowFileAccess(true);

        mWebHandler = new WebHandler(this, this);
        mWebview.addJavascriptInterface(new MyJavaScriptInterface(this,
                mWebHandler, this), "CSII");
        mWebview.setWebChromeClient(new MyWebChromeClient(mMyWebViewPresenter, this));
        mWebview.setWebViewClient(new MyWebViewClient(this));
        mWebview.requestFocus();
        mWebview.requestFocusFromTouch();
    }

    @Override
    protected void setData() {
        mMyWebViewPresenter = new MyWebViewPresenter(this, this);
        mMyWebViewPresenter.subscribe(getIntent().getExtras());
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            backClick();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void backClick() {
        mWebview.loadUrl("javascript:CLIENT_CALLBACK.back()");
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        mMyWebViewPresenter.onNewIntent(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        LogUtils.i(TAG, "onActivityResult");
        mMyWebViewPresenter.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void loadUrl(String url) {
        DialogUtil.showLDialog(this);
        mWebview.loadUrl(url);
    }

    @Override
    public void setTitle(String title) {
        tv_title.setText(title);
    }

    @Override
    public void onLoadProgressChanged(int newProgress) {
        mProgressBar.setProgress(newProgress);
    }

    @Override
    public void showLoadProgress() {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void onLoadUrlFinished() {
        mProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void pageFinish() {
        finish();
    }

    @Override
    public void obtainClientData(String key) {
        String jsonData = mMyWebViewPresenter.getTargJson();
        if (!Util.isStringNull(jsonData)) {
            mWebview.loadUrl("javascript:CLIENT_CALLBACK.obtainClientData('"
                    + key + "','" + jsonData + "')");
            mWebview.invalidate();
            LogUtils.i(TAG, "webview传值-----" + key + "-----" + jsonData);
        }
    }

    @Override
    public void doCallback(String key, String jsonData) {
        mWebview.loadUrl("javascript:CLIENT_CALLBACK.doCallback('"
                + key + "','" + jsonData + "')");
        mWebview.invalidate();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        removeHandlerCallAndMsg(mWebHandler);
        if (null != mWebview) {
            mWebview.removeAllViews();
            mWebview.destroy();
        }
        if (mMyWebViewPresenter != null) {
            mMyWebViewPresenter.unSubscribe();
        }
    }

}
