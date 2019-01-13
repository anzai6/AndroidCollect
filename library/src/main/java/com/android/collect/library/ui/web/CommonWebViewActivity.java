package com.android.collect.library.ui.web;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.webkit.JavascriptInterface;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.collect.library.R;
import com.android.collect.library.base.SwipeBackTitleActivity;
import com.android.collect.library.common.Constant;
import com.android.collect.library.common.KeyHelper;
import com.android.collect.library.ui.web.sonic.SonicJavaScriptInterface;
import com.android.collect.library.ui.web.sonic.SonicRuntimeImpl;
import com.android.collect.library.ui.web.sonic.SonicSessionClientImpl;
import com.android.collect.library.util.LogUtils;
import com.android.collect.library.util.Util;
import com.tencent.sonic.sdk.SonicCacheInterceptor;
import com.tencent.sonic.sdk.SonicConfig;
import com.tencent.sonic.sdk.SonicConstants;
import com.tencent.sonic.sdk.SonicEngine;
import com.tencent.sonic.sdk.SonicSession;
import com.tencent.sonic.sdk.SonicSessionConfig;
import com.tencent.sonic.sdk.SonicSessionConnection;
import com.tencent.sonic.sdk.SonicSessionConnectionInterceptor;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.webkit.WebSettings.LayoutAlgorithm.NARROW_COLUMNS;


/**
 * 加载网址的页面
 */
@SuppressLint("SetJavaScriptEnabled")
public class CommonWebViewActivity extends SwipeBackTitleActivity {

    private WebView webView;
    private ProgressBar mProgressBar;
    private String title, mUrl;

    /**
     * 默认加载模式
     */
    public static final int MODE_DEFAULT = 0;
    /**
     * SONIC提速加载模式
     */
    public static final int MODE_SONIC = 1;
    /**
     * SONIC离线加载模式
     */
    public static final int MODE_SONIC_WITH_OFFLINE_CACHE = 2;

    private SonicSession sonicSession;
    private SonicSessionClientImpl sonicSessionClient = null;

    private Bundle mBundle;

    private int mode = 0;

    @Override
    public void setCurrentView() {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
        mBundle = getIntent().getExtras();
        mUrl = Util.getBundleString(mBundle, KeyHelper.WebUrl, "");
        title = Util.getBundleString(mBundle, KeyHelper.WebTitle, "");
        mode = mBundle.getInt(KeyHelper.WebMode, 0);
        initSonic();

        setContentView(R.layout.activity_common_webview);
        initTitle(Constant.TITLE1, "");
    }

    @JavascriptInterface
    protected void initView() {
        webView = (WebView) findViewById(R.id.webview);
        setWebView();
        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);

        // 禁止复制
        webView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                return true;
            }
        });

    }

    @Override
    protected void setData() {
        setTitle(title);
        setSonicJs();
        loadUrl();
    }

    /**
     * 初始化首屏加载优化
     */
    private void initSonic() {
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED);

        // init sonic engine if necessary, or maybe u can do this when application created
        if (!SonicEngine.isGetInstanceAllowed()) {
            SonicEngine.createInstance(new SonicRuntimeImpl(getApplication()), new SonicConfig.Builder().build());
        }

        // if it's sonic mode , startup sonic session at first time
        if (MODE_DEFAULT != mode) { // sonic mode
            SonicSessionConfig.Builder sessionConfigBuilder = new SonicSessionConfig.Builder();

            // if it's offline pkg mode, we need to intercept the session connection
            if (MODE_SONIC_WITH_OFFLINE_CACHE == mode) {
                sessionConfigBuilder.setCacheInterceptor(new SonicCacheInterceptor(null) {
                    @Override
                    public String getCacheData(SonicSession session) {
                        return null; // offline pkg does not need cache
                    }
                });

                sessionConfigBuilder.setConnectionIntercepter(new SonicSessionConnectionInterceptor() {
                    @Override
                    public SonicSessionConnection getConnection(SonicSession session, Intent intent) {
                        return new OfflinePkgSessionConnection(CommonWebViewActivity.this, session, intent);
                    }
                });
            }

            // create sonic session and run sonic flow
            sonicSession = SonicEngine.getInstance().createSession(mUrl, sessionConfigBuilder.build());
            if (null != sonicSession) {
                sonicSession.bindClient(sonicSessionClient = new SonicSessionClientImpl());
            } else {
                // this only happen when a same sonic session is already running,
                // u can comment following codes to feedback as a default mode.
                // throw new UnknownError("create session fail!");
                Toast.makeText(this, "create sonic session fail!", Toast.LENGTH_LONG).show();
            }
        }

        // start init flow ...
        // in the real world, the init flow may cost a long time as startup
        // runtime、init configs....
    }

    /**
     * 设置sonic Js
     */
    private void setSonicJs() {
        // add java script interface
        // note:if api level lower than 17(android 4.2), addJavascriptInterface has security
        // issue, please use x5 or see https://developer.android.com/reference/android/webkit/
        // WebView.html#addJavascriptInterface(java.lang.Object, java.lang.String)
        webView.removeJavascriptInterface("searchBoxJavaBridge_");
        Intent intent = new Intent();
        intent.putExtra(SonicJavaScriptInterface.PARAM_LOAD_URL_TIME, System.currentTimeMillis());
        webView.addJavascriptInterface(new SonicJavaScriptInterface(sonicSessionClient, intent), "sonic");
    }

    /**
     * 加载URL
     */
    private void loadUrl() {
        // webview is ready now, just tell session client to bind
        if (sonicSessionClient != null) {
            sonicSessionClient.bindWebView(webView);
            sonicSessionClient.clientReady();
        } else { // default mode
            webView.loadUrl(mUrl);
        }
        LogUtils.i(TAG, "加载--" + mUrl);
    }

    /**
     * WebView设置
     */
    private void setWebView() {

        // 滚动条风格，为0指滚动条不占用空间，直接覆盖在网页上
        webView.setScrollBarStyle(View.SCROLLBARS_OUTSIDE_OVERLAY);
        webView.requestFocus();
        webView.setWebViewClient(new MyWebViewClient());
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                mProgressBar.setProgress(newProgress);
            }
        });

        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);

        // -- webview常用设置

        // 启用WebView访问文件数据
        settings.setAllowFileAccess(true);
        // 设置是否支持缩放
        settings.setBuiltInZoomControls(true);
        // 设置布局方式：
        // NORMAL：正常显示，没有渲染变化。
        // SINGLE_COLUMN：把所有内容放到WebView组件等宽的一列中。   //这个是强制的，把网页都挤变形了
        // NARROW_COLUMNS：可能的话，使所有列的宽度不超过屏幕宽度。 //好像是默认的
        settings.setLayoutAlgorithm(NARROW_COLUMNS);
        // 设置缓冲的模式
//        settings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        // 设置是否支持变焦
        settings.setSupportZoom(true);
        setWebViewZoom(this, settings);
        // 拼接url字符串
        settings.setDefaultTextEncodingName("UTF-8");
        //隐藏webview缩放按钮
        settings.setDisplayZoomControls(false);
        // 下面两个方式使得页面适应手机屏幕的分辨率，完整的显示在屏幕上，可以放大缩小
        // 设置webview推荐使用的窗口
        settings.setUseWideViewPort(true);
        // 设置webview加载的页面的模式
        settings.setLoadWithOverviewMode(true);

        // -- webview常用设置

        // sonic init webview settings
        // 设置WebView是否使用其内置的变焦机制，该机制结合屏幕缩放控件使用，默认是false，不使用内置变焦机制。
        settings.setAllowContentAccess(true);
        // 设置是否开启数据库存储API权限，默认false，未开启，可以参考setDatabasePath(String path)
        settings.setDatabaseEnabled(true);
        // 设置是否开启DOM存储API权限，默认false，未开启，设置为true，WebView能够使用DOM storage API
        settings.setDomStorageEnabled(true);// 限制在WebView中打开网页，而不用默认浏览器
        // 设置Application缓存API是否开启，默认false
        settings.setAppCacheEnabled(true);
        // 设置有效的缓存路径参考方法
//        settings.setAppCachePath(String path);
        // 关闭密码保存
        settings.setSavePassword(false);
        // 设置WebView是否保存表单数据，默认true，保存数据。
        settings.setSaveFormData(false);
    }

    /**
     * 适配webview
     *
     * @param context
     * @param webSettings
     */
    @SuppressWarnings("deprecation")
    public static void setWebViewZoom(Context context, WebSettings webSettings) {
        float density = getDensity(context);
        if (density == 0.75)
            webSettings.setDefaultZoom(WebSettings.ZoomDensity.CLOSE);
        else if (density == 1.0)
            webSettings.setDefaultZoom(WebSettings.ZoomDensity.MEDIUM);
        else
            webSettings.setDefaultZoom(WebSettings.ZoomDensity.FAR);
    }

    private static float getDensity(Context context) {
        float density = getDisplayMetrics(context).density;// 密度
        return density;
    }

    public static DisplayMetrics getDisplayMetrics(Context context) {
        return context.getResources().getDisplayMetrics();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            if (webView.canGoBack()) {
                webView.goBack(); // goBack()表示返回WebView的上一页面
                return true;
            } else {
                onBackPressed();
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);

    }

    @Override
    protected void onDestroy() {
        if (null != sonicSession) {
            sonicSession.destroy();
            sonicSession = null;
        }
        super.onDestroy();
    }

    private class MyWebViewClient extends WebViewClient {

        @RequiresApi(api = Build.VERSION_CODES.M)
        @Override
        public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
            super.onReceivedError(view, request, error);
            LogUtils.i(TAG, "加载页面出错" + error.getDescription() + "errorCode:" + error.getErrorCode()
                    + "," + request.getUrl().toString());
            mProgressBar.setVisibility(View.GONE);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            LogUtils.i(TAG, "加载页面完成  " + url);
            mProgressBar.setVisibility(View.GONE);
            if (sonicSession != null) {
                sonicSession.getSessionClient().pageFinish(url);
            }
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            LogUtils.i(TAG, "加载开始" + url);
            mProgressBar.setVisibility(View.VISIBLE);
        }

        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            // 1、 默认返回：return super.shouldOverrideUrlLoading(view,
            // url);这个返回的方法会调用父类方法，也就是跳转至手机浏览器，
            // 平时写webview一般都在方法里面写 webView.loadUrl(url);
            // 然后把这个返回值改成下面的false。
            // 2、返回: return true; webview处理url是根据程序来执行的。
            // 3、返回: return false; webview处理url是在webview内部执行。
            view.loadUrl(request.getUrl().toString());
            view.invalidate();
            return true;
        }

        @TargetApi(21)
        @Override
        public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
            return shouldInterceptRequest(view, request.getUrl().toString());
        }

        @Override
        public WebResourceResponse shouldInterceptRequest(WebView view, String url) {
            if (sonicSession != null) {
                return (WebResourceResponse) sonicSession.getSessionClient().requestResource(url);
            }
            return null;
        }

        @Override
        public void onReceivedSslError(WebView view,
                                       SslErrorHandler handler, SslError error) {
            handler.proceed(); // 接受所有网站的证书
        }
    }

    private static class OfflinePkgSessionConnection extends SonicSessionConnection {

        private final WeakReference<Context> context;

        public OfflinePkgSessionConnection(Context context, SonicSession session, Intent intent) {
            super(session, intent);
            this.context = new WeakReference<Context>(context);
        }

        @Override
        protected int internalConnect() {
            Context ctx = context.get();
            if (null != ctx) {
                try {
                    InputStream offlineHtmlInputStream = ctx.getAssets().open("sonic-demo-index.html");
                    responseStream = new BufferedInputStream(offlineHtmlInputStream);
                    return SonicConstants.ERROR_CODE_SUCCESS;
                } catch (Throwable e) {
                    e.printStackTrace();
                }
            }
            return SonicConstants.ERROR_CODE_UNKNOWN;
        }

        @Override
        protected BufferedInputStream internalGetResponseStream() {
            return responseStream;
        }

        @Override
        public void disconnect() {
            if (null != responseStream) {
                try {
                    responseStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        @Override
        public int getResponseCode() {
            return 200;
        }

        @Override
        public Map<String, List<String>> getResponseHeaderFields() {
            return new HashMap<>(0);
        }

        @Override
        public String getResponseHeaderField(String key) {
            return "";
        }
    }

}
