package com.android.collect.library.http.okhttp;

import android.content.Context;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;

import com.android.collect.library.callback.DataResultCallBack;
import com.android.collect.library.http.CommDictAction;
import com.android.collect.library.http.DataRepository;
import com.android.collect.library.http.HttpConstant;
import com.android.collect.library.http.IDataRequest;
import com.android.collect.library.util.DeviceInfoUtil;
import com.android.collect.library.util.JSONUtil;
import com.android.collect.library.util.LogUtils;
import com.android.collect.library.util.file.FileUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * OkHttp请求类
 *
 * @author anzai
 */
public class OkHttpRequest extends IDataRequest implements HttpConstant {

    private static OkHttpRequest okHttpUtils;
    private OkHttpClient mOkHttpClient;
    private Handler handler;

    private Context mContext;

    public static final String TAG = "OkHttp";

    private OkHttpRequest() {

        mOkHttpClient = new OkHttpClient.Builder()
                .readTimeout(HTTP_READ_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(HTTP_WRITE_TIMEOUT, TimeUnit.SECONDS)
                .connectTimeout(HTTP_CONNECT_TIMEOUT, TimeUnit.SECONDS).build();
        handler = new Handler(Looper.getMainLooper());

    }

    public static OkHttpRequest getInstance(Context context) {

        if (okHttpUtils == null) {
            synchronized (OkHttpRequest.class) {
                if (okHttpUtils == null)
                    okHttpUtils = new OkHttpRequest();
            }
        }
        okHttpUtils.mContext = context;
        return okHttpUtils;
    }

    /**
     * 获取手机型号
     *
     * @return String
     */
    public String getModel() {
        Build bd = new Build();
        String model = bd.MODEL;
        return TextUtils.isEmpty(model) ? "Android" : model;
    }

    /**
     * 获取应用程序版本号
     *
     * @return int
     */
    public int getAppVersionCode() {
        int AppVersionCode = 0;
        try {
            AppVersionCode = mContext.getPackageManager().getPackageInfo(
                    mContext.getPackageName(), 0).versionCode;
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        return AppVersionCode;
    }

    private Request getRequestWithHeader(String url, RequestBody body) {
        Request request = new Request.Builder().url(url).post(body)
                .addHeader("Cookie", parseMap())
                .addHeader("Connection", "Keep-Alive")
                // .addHeader("Accept", "application/json");
                .addHeader("Accept", "*/*")
                .addHeader("Accept-Language", "zh-cn")
                .addHeader("User-Agent", CommDictAction.strUserAgent)
                .addHeader("User-Device", getModel() + "|" + getAppVersionCode())
                .addHeader("Content-Type",
                        "application/x-www-form-urlencoded;charset=utf-8")
                .tag(mContext).build();
        return request;
    }

    private Request getRequestWithHeaderAndTag(String url, RequestBody body) {
        Request request = new Request.Builder().url(url).post(body)
                .addHeader("Cookie", parseMap())
                .addHeader("Connection", "Keep-Alive")
                // .addHeader("Accept", "application/json");
                .addHeader("Accept", "*/*")
                .addHeader("Accept-Language", "zh-cn")
                .addHeader("User-Agent", CommDictAction.strUserAgent)
                .addHeader("User-Device", getModel() + "|" + getAppVersionCode())
                .addHeader("Content-Type",
                        "application/x-www-form-urlencoded;charset=utf-8")
                .tag(url.replace(CommDictAction.Url, "")).build();
        return request;
    }

    /**
     * saveSessionID: 保存sessionID
     *
     * @param response
     * @return
     */
    public static String saveSessionID(Response response) {
        String cookie = null;
        Headers headers = response.headers();
        for (int i = 0; i < headers.size(); i++) {
            if (headers.name(i).equals("Set-Cookie")
                    && !(cookie = headers.value(i)).equals("")) {
                cookie = cookie.substring(0, cookie.indexOf(";"));
                String name = cookie.substring(0, cookie.indexOf("="));
                SessionMap.put(name, cookie);

            }
        }
        return parseMap();
    }

    public static String parseMap() {
        Set<String> keySet = SessionMap.keySet();
        Iterator<String> iterator = keySet.iterator();
        StringBuffer sb = new StringBuffer();
        while (iterator.hasNext()) {
            sb.append(SessionMap.get(iterator.next())).append(";");
        }
        return sb.toString();
    }

    @Override
    public void postRequst(String url, String jsonParams,
                           final DataResultCallBack dataResultCallBack) {

        RequestBody body = RequestBody.create(JSON, jsonParams);

        Request request = getRequestWithHeader(url, body);
        LogUtils.i(TAG, "加载连接--" + url + "--参数--" + jsonParams);
        mOkHttpClient.newCall(request).enqueue(new Callback() {

            @Override
            public void onResponse(Call call, final Response response)
                    throws IOException {
                //此时还在异步线程中
                saveSessionID(response);
                JSONObject obj = null;
                try {
                    LogUtils.i(TAG, "返回码：---" + response.code());
                    obj = new JSONObject(response.body().string());
                    LogUtils.i(TAG, "返回数据：---" + obj.toString());
                    handleSuccessMsg(obj, dataResultCallBack);
                } catch (JSONException e) {
                    e.printStackTrace();
                    LogUtils.i(TAG, e.getMessage());
                    handleFailedMsg(dataResultCallBack);
                } catch (IOException e) {
                    e.printStackTrace();
                    LogUtils.i(TAG, e.getMessage());
                    handleFailedMsg(dataResultCallBack);
                }

            }

            @Override
            public void onFailure(final Call call, final IOException ioexception) {
                //此时还在异步线程中
                LogUtils.e(TAG, ioexception.getMessage());
                handleFailedMsg(dataResultCallBack);
            }
        });
    }

    private void handleSuccessMsg(final JSONObject obj, final DataResultCallBack dataResultCallBack) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                dataResultCallBack.onSucceed(obj);
            }
        });
    }

    private void handleFailedMsg(final DataResultCallBack dataResultCallBack) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                dataResultCallBack.onFailed(DataRepository.UNKNOWN_RESURN_CODE, DataRepository.UNKNOWN_ERROR_MSG);
            }
        });
    }

    @Override
    public JSONObject postRequst(String url, String jsonParams) {
        RequestBody body = RequestBody.create(JSON, jsonParams);
        Request request = getRequestWithHeader(url, body);
        LogUtils.i(TAG, "加载连接--" + url + "--参数--" + jsonParams);
        JSONObject obj = null;
        try {
            Response response = mOkHttpClient.newCall(request).execute();
            saveSessionID(response);
            obj = new JSONObject(response.body().string());
            LogUtils.i(TAG, "返回数据：---" + obj.toString());
        } catch (IOException e) {
            e.printStackTrace();
            LogUtils.e(TAG, e.getMessage());
            obj = null;
        } catch (JSONException e) {
            e.printStackTrace();
            LogUtils.e(TAG, e.getMessage());
            obj = null;
        }
        return obj;
    }

    @Override
    public Call downloadFile(String url, final String saveFileDir, final String fileName,
                             final FileRequestCallBack downloadFileCallBack) {
        LogUtils.i(TAG, "下载链接：-" + url);
        Request request = new Request.Builder().url(url).build();
        final Call call = mOkHttpClient.newCall(request);
        call.enqueue(new Callback() {

            @Override
            public void onFailure(Call arg0, final IOException ioexception) {
                //此时还在异步线程中
                LogUtils.e(TAG, ioexception.getMessage());
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        downloadFileCallBack.onFailed();
                    }
                });

            }

            @Override
            public void onResponse(Call arg0, Response response) throws IOException {
                //此时还在异步线程中
                final boolean isSave = FileUtil.saveFile(response.body().byteStream(), saveFileDir, fileName);
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (isSave)
                            downloadFileCallBack.onSucceed();
                        else
                            downloadFileCallBack.onFailed();
                    }
                });
            }
        });
        return call;

    }

    @Override
    public boolean downloadFile(String url, String saveFileDir, String fileName) {
        LogUtils.i(TAG, "下载链接：-" + url);
        Request request = new Request.Builder().url(url).addHeader("Cookie", parseMap()).build();
        try {
            Response response = mOkHttpClient.newCall(request).execute();
            LogUtils.i(TAG, "返回码：---" + response.code());
            return FileUtil.saveFile(response.body().byteStream(), saveFileDir, fileName);
        } catch (IOException e) {
            e.printStackTrace();
            LogUtils.e(TAG, e.getMessage());
            return false;
        }
    }

    @Override
    public boolean downloadFileWithParams(String url, String saveFileDir, String fileName, JSONObject json) {
        url = CommDictAction.Url + url;
        LogUtils.i(TAG, "下载链接：-" + url);
        try {
            // 设备类型（ANDROID，IOS）
            json.put("EqmtVerCd", "ANDROID");
            // EqmtIdNo
            json.put("EqmtIdNo", DeviceInfoUtil.getDeviceIMEI(mContext));
            // 设备型号
            json.put("EqmtModel", DeviceInfoUtil.getModel());
            // App版本号
            json.put("EqmtAppVersion", DeviceInfoUtil.getAppVersionCode(mContext));
            json.put("_ChannelId", "PBOP");
            json.put("BankId", "9999");

            json.put("_local", "zh_CN");
            if (JSONUtil.getString(json, "LoginType").equals("P")) {
                json.put("LoginType", "P");
            } else {
                json.put("LoginType", "P");
            }
        } catch (JSONException e1) {
            e1.printStackTrace();
        }
        RequestBody body = RequestBody.create(JSON, json.toString());
        Request request = getRequestWithHeader(url, body);
        try {
            Response response = mOkHttpClient.newCall(request).execute();
            LogUtils.i(TAG, "返回码：---" + response.code());
            byte[] data = response.body().bytes();
            return FileUtil.saveByteArrayToFile(data,saveFileDir,fileName);
        } catch (IOException e) {
            e.printStackTrace();
            LogUtils.e(TAG, e.getMessage());
            return false;
        }
    }

    @Override
    public Call downloadFileWithProgress(String url,
                                         final String saveFileDir, final String fileName,
                                         final FileRequestProgressCallBack downloadProgressCallBack) {

        final ProgressResponseListener progressResponseListener = new ProgressResponseListener() {

            @Override
            public void onResponseProgress(final long current, final long total,
                                           final boolean done) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        BigDecimal bd = new BigDecimal(current);
                        MathContext mc = new MathContext(2,
                                RoundingMode.HALF_EVEN);
                        BigDecimal bd1 = bd.divide(new BigDecimal(total), mc);
                        BigDecimal bd2 = bd1.multiply(new BigDecimal(100));
                        downloadProgressCallBack.onProgress(bd2.intValue(), done);
                    }
                });
            }
        };

        //其实还是另外写一个类进行单例复用OkHttpClient好，不用每次都创建
        LogUtils.i(TAG, "下载：-" + url);
        Request request = new Request.Builder().url(url).build();
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .readTimeout(HTTP_READ_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(HTTP_WRITE_TIMEOUT, TimeUnit.SECONDS)
                .connectTimeout(HTTP_CONNECT_TIMEOUT, TimeUnit.SECONDS)
                .addInterceptor(new Interceptor() {//添加拦截器，自定义ResponseBody，添加下载进度
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Response originalResponse = chain.proceed(chain.request());
                        return originalResponse
                                .newBuilder()
                                .body(new ProgressResponseBody(originalResponse.body(),
                                        progressResponseListener)).build();

                    }
                }).build();

        Call call = okHttpClient.newCall(request);
        //发送异步请求
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call arg0, IOException ioexception) {
                //此时还在异步线程中
                LogUtils.e(TAG, ioexception.getMessage());
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        downloadProgressCallBack.onFailed();
                    }
                });
            }

            @Override
            public void onResponse(Call arg0, Response response) throws IOException {
                //此时还在异步线程中
                final boolean isSave = FileUtil.saveFile(response.body().byteStream(), saveFileDir, fileName);
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (isSave)
                            downloadProgressCallBack.onSucceed();
                        else
                            downloadProgressCallBack.onFailed();
                    }
                });
            }
        });
        return call;
    }

    @Override
    public Call uploadFile(String url, String fileDir, String fileName, final FileRequestCallBack fileRequestCallBack) {
        File file = new File(fileDir, fileName);
        // 类似map的key
        String key = "file";
        RequestBody fileBody = RequestBody.create(FILE, file);
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart(key, fileName, fileBody)
                .build();

        LogUtils.i(TAG, "上传链接：-" + url);
        Request request = getRequestWithHeader(url, requestBody);
        Call call = mOkHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                //此时还在异步线程中
                LogUtils.e(TAG, e.getMessage());
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        fileRequestCallBack.onFailed();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final int returnCode = response.code();
                LogUtils.i(TAG, "返回码：---" + returnCode);
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (returnCode == 200)
                            fileRequestCallBack.onSucceed();
                        else
                            fileRequestCallBack.onFailed();
                    }
                });
            }
        });

        return call;
    }

    @Override
    public boolean uploadFile(String url, String fileDir, String fileName) {
        //		RequestBody body = RequestBody.create(JSON, jsonParams);
        File file = new File(fileDir, fileName);
        // 类似map的key
        String key = "file";
        RequestBody fileBody = RequestBody.create(FILE, file);
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart(key, fileName, fileBody)
                // 添加参数
//				.addFormDataPart("key", "value")
                // 添加参数
//				.addPart(body)
                .build();

        LogUtils.i(TAG, "上传链接：-" + url);
//		Request request = new Request.Builder().url(url).post(requestBody).build();
        Request request = getRequestWithHeader(url, requestBody);

        try {
            Response response = mOkHttpClient.newCall(request).execute();
            int returnCode = response.code();
            LogUtils.i(TAG, "返回码：---" + returnCode);
            if (returnCode == 200)
                return true;
        } catch (IOException e) {
            e.printStackTrace();
            LogUtils.e(TAG, e.getMessage());
            return false;
        }
        return false;
    }

    @Override
    public Call uploadFileWithProgress(String url, String fileDir, String fileName, final FileRequestProgressCallBack fileRequestProgressCallBack) {
        final ProgressRequestListener progressRequestListener = new ProgressRequestListener() {
            @Override
            public void onRequestProgress(final long current, final long total, final boolean done) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        BigDecimal bd = new BigDecimal(current);
                        MathContext mc = new MathContext(2,
                                RoundingMode.HALF_EVEN);
                        BigDecimal bd1 = bd.divide(new BigDecimal(total), mc);
                        BigDecimal bd2 = bd1.multiply(new BigDecimal(100));
                        fileRequestProgressCallBack.onProgress(bd2.intValue(), done);
                    }
                });
            }
        };

        File file = new File(fileDir, fileName);
        // 类似map的key
        String key = "file";
        RequestBody fileBody = RequestBody.create(FILE, file);
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart(key, fileName, fileBody)
                .build();

        LogUtils.i(TAG, "上传链接：-" + url);
        Request request = getRequestWithHeader(url, new ProgressRequestBody(requestBody, progressRequestListener));
        Call call = mOkHttpClient.newCall(request);

        //发送异步请求
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call arg0, IOException ioexception) {
                //此时还在异步线程中
                LogUtils.e(TAG, ioexception.getMessage());
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        fileRequestProgressCallBack.onFailed();
                    }
                });
            }

            @Override
            public void onResponse(Call arg0, Response response) throws IOException {
                //此时还在异步线程中
                final int returnCode = response.code();
                LogUtils.i(TAG, "返回码：---" + returnCode);
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (returnCode == 200)
                            fileRequestProgressCallBack.onSucceed();
                        else
                            fileRequestProgressCallBack.onFailed();
                    }
                });
            }
        });
        return call;
    }

    /**
     * 取消对应Tag的请求
     *
     * @param tag
     */
    public void cancelTag(Object tag) {
        if (handler != null)
            handler.removeCallbacksAndMessages(null);
        for (Call call : mOkHttpClient.dispatcher().queuedCalls()) {
            if (tag.equals(call.request().tag())) {
                call.cancel();
                LogUtils.i(TAG, "取消请求--" + call.request().url());
            }
        }
        for (Call call : mOkHttpClient.dispatcher().runningCalls()) {
            if (tag.equals(call.request().tag())) {
                call.cancel();
                LogUtils.i(TAG, "取消请求--" + call.request().url());
            }
        }
    }

}
