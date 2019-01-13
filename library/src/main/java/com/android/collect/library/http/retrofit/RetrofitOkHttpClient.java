package com.android.collect.library.http.retrofit;

import android.content.Context;
import android.os.Build;
import android.text.TextUtils;

import com.android.collect.library.common.Constant;
import com.android.collect.library.http.CommDictAction;
import com.android.collect.library.http.HttpConstant;
import com.android.collect.library.util.LogUtils;

import java.io.IOException;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class RetrofitOkHttpClient implements HttpConstant {

	private static RetrofitOkHttpClient retrofitOkHttpClient;
	private final OkHttpClient okHttpClient;
	
	private Context context;
	
	private RetrofitOkHttpClient() {

		okHttpClient = new OkHttpClient.Builder()
				// 添加UA
				.addInterceptor(new Interceptor() {

					@Override
					public Response intercept(Chain chain) throws IOException {
						Request request = chain.request();
					    final Request requestWithHead = getRequestWithHeader(request);
						return chain.proceed(requestWithHead);
					}
				})
				// 失败重连
				.retryOnConnectionFailure(true)
				.readTimeout(HTTP_READ_TIMEOUT, TimeUnit.SECONDS)
				.writeTimeout(HTTP_WRITE_TIMEOUT, TimeUnit.SECONDS)
				.connectTimeout(HTTP_CONNECT_TIMEOUT, TimeUnit.SECONDS).build();
		

	}

	public static RetrofitOkHttpClient getInstance(Context context) {

		if (retrofitOkHttpClient == null) {
			synchronized (RetrofitOkHttpClient.class) {
				if (retrofitOkHttpClient == null)
					retrofitOkHttpClient = new RetrofitOkHttpClient();
			}
		}
		retrofitOkHttpClient.context = context;
		return retrofitOkHttpClient;
	}
	
	public OkHttpClient getOkHttpClient(){
		return okHttpClient;
	}
	
	private Request getRequestWithHeader(Request request){
		Request newRequest = request
				.newBuilder()
				.addHeader("Cookie", parseMap())
				.addHeader("Connection", "Keep-Alive")
				// .addHeader("Accept", "application/json");
				.addHeader("Accept", "*/*")
				.addHeader("Accept-Language", "zh-cn")
				.addHeader("User-Agent", CommDictAction.strUserAgent)
				.addHeader("User-Device",
						getModel() + "|" + Constant.APPVERSION_CODE)
				.addHeader("Content-Type",
						"application/x-www-form-urlencoded;charset=utf-8")
				.tag(context).build();
		return newRequest;
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
	
	/**
	 * 获取手机型号
	 * 
	 * @return String
	 */
	public String getModel(){ 
		Build bd = new Build();
		String model = bd.MODEL;  
		return TextUtils.isEmpty(model)?"Android":model;
	}
	
	/**
	 * 取消对应Tag的请求
	 * @param tag
	 */
	public void cancelTag(Object tag) {
		for (Call call : okHttpClient.dispatcher().queuedCalls()) {
			if (tag.equals(call.request().tag())) {
				call.cancel();
				LogUtils.i("取消请求--" + call.request().url());
			}
		}
		for (Call call : okHttpClient.dispatcher().runningCalls()) {
			if (tag.equals(call.request().tag())) {
				call.cancel();
				LogUtils.i("取消请求--" + call.request().url());
			}
		}
	}
	
}
