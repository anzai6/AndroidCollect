package com.android.collect.library.ui.web;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.webkit.SslErrorHandler;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.android.collect.library.util.DialogUtil;
import com.android.collect.library.util.LogUtils;
import com.android.collect.library.util.Util;

import java.io.IOException;
import java.net.FileNameMap;
import java.net.URLConnection;

@SuppressLint("NewApi")
public class MyWebViewClient extends WebViewClient {

	public static final String TAG = "MyWebViewClient";
	
	private MyWebViewContract.View mMyWebViewView;

	public MyWebViewClient(MyWebViewContract.View myWebViewView) {
		this.mMyWebViewView = Util.checkNotNull(myWebViewView);
	}

	@Override
	public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
		super.onReceivedError(view, request, error);
		LogUtils.i(TAG, "加载页面出错" + error.getDescription() + "errorCode:" + error.getErrorCode()
				+ "," + request.getUrl().toString());
		DialogUtil.dismissLDialog();
	}

	@Override
	public void onPageFinished(WebView view, String url) {
		super.onPageFinished(view, url);
		LogUtils.i(TAG, "加载  " + url + "  页面完成");
		mMyWebViewView.onLoadUrlFinished();
		DialogUtil.dismissLDialog();
	}

	@Override
	public void onPageStarted(WebView view, String url, Bitmap favicon) {
		super.onPageStarted(view, url, favicon);
		LogUtils.i(TAG, "加载开始" + url);
		mMyWebViewView.showLoadProgress();
	}

	@Override
	public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
		// 1、 默认返回：return super.shouldOverrideUrlLoading(view,
		// request);这个返回的方法会调用父类方法，也就是跳转至手机浏览器，
		// 平时写webview一般都在方法里面写 webView.loadUrl(url);
		// 然后把这个返回值改成下面的false。
		// 2、返回: return true; webview处理url是根据程序来执行的。
		// 3、返回: return false; webview处理url是在webview内部执行。
		view.loadUrl(request.getUrl().toString());
		view.invalidate();
		return true;
	}

	@Override
	public void onReceivedSslError(WebView view, SslErrorHandler handler,
			SslError error) {
		handler.proceed(); // 接受所有网站的证书
	}

	@Override
	public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
//		String url = request.getUrl().toString();
//		if (CircleConstant.isReedHtmlFromLocal && url.contains(CommDictAction.Url)
//				&& !url.contains(".do")) {
//			try {
//
//				String url_file_name = FileTool.getFileNameByPath(url);
////				if (url.equals(CircleConstant.LocalIndexHtml)) {
////					url_end = index_html;
////				} else if (url.contains(CommDictAction.Url)) {
////					url_end = "local_html/product/"
////							+ url.substring(CommDictAction.Url.length());
////				}
//
//				ZipFileInfoDetail detail = RecordDataTable.findZipFileInfoDetailByFileName(context, url_file_name);
//				if(detail != null){
//					String strResult = "";
//					int index = url.lastIndexOf(".");
//					if (index != -1) {
//						strResult = url.substring(index);
//					}
//
//					InputStream is = ZipUtil.zipPassword(detail.getZip_file_path(), detail.getZip_psw(), detail.getFile_path());
//					if (is != null)
//						return new WebResourceResponse(getMimeType(strResult),
//								"UTF-8", is);
//				}
//			} catch (ZipException e) {
//				e.printStackTrace();
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//		}
		return super.shouldInterceptRequest(view, request);
	}

	public static String getMimeType(String fileUrl) throws IOException {
		FileNameMap fileNameMap = URLConnection.getFileNameMap();
		String type = fileNameMap.getContentTypeFor(fileUrl);

		return type;
	}
}
