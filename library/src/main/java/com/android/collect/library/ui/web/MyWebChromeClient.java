package com.android.collect.library.ui.web;

import android.net.Uri;
import android.webkit.ConsoleMessage;
import android.webkit.JsResult;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

import com.android.collect.library.util.LogUtils;
import com.android.collect.library.util.Util;

public class MyWebChromeClient extends WebChromeClient {

	public static final String TAG = "MyWebChromeClient";
	private MyWebViewContract.Presenter mMyWebViewPresenter;
	private MyWebViewContract.View mMyWebViewView;

	public MyWebChromeClient(MyWebViewContract.Presenter myWebViewPresenter, MyWebViewContract.View myWebViewView) {
		this.mMyWebViewPresenter = Util.checkNotNull(myWebViewPresenter);
		this.mMyWebViewView = Util.checkNotNull(myWebViewView);
	}

	@Override
	public void onProgressChanged(WebView view, int newProgress) {
		super.onProgressChanged(view, newProgress);
		mMyWebViewView.onLoadProgressChanged(newProgress);
	}

	@Override
	public boolean onJsAlert(WebView view, String url, String message,
			JsResult result) {
		LogUtils.i(TAG, "onJsAlert:" + message);
		return true;
	}

	@Override
	public boolean onConsoleMessage(ConsoleMessage consoleMessage) {
		LogUtils.i(TAG, "JS控制台输出信息：-----" + consoleMessage.message());
		return true;
	}

	// For Android 3.0+
	public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType) {
		mMyWebViewPresenter.startFileSelectActivity(uploadMsg, null);
		LogUtils.i("openFileChooser -- >= 3.0 && < 4.1");
	}

	// For Android < 3.0
	public void openFileChooser(ValueCallback<Uri> uploadMsg) {
		openFileChooser(uploadMsg, "");
		LogUtils.i("openFileChooser -- < 3.0");
	}

	// 大于 4.1
	public void openFileChooser(ValueCallback<Uri> uploadMsg,
			String acceptType, String capture) {
		openFileChooser(uploadMsg, acceptType);
		LogUtils.i("openFileChooser -- >= 4.1");
	}

	@Override
	public boolean onShowFileChooser(WebView webView,
			ValueCallback<Uri[]> filePathCallback,
			FileChooserParams fileChooserParams) {
		mMyWebViewPresenter.startFileSelectActivity(null, filePathCallback);
		LogUtils.i("onShowFileChooser -- > 5.0以上");
		return true;
	}
}
