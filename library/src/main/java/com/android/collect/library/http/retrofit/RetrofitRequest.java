package com.android.collect.library.http.retrofit;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import com.android.collect.library.callback.DataResultCallBack;
import com.android.collect.library.http.CommDictAction;
import com.android.collect.library.http.DataRepository;
import com.android.collect.library.http.HttpConstant;
import com.android.collect.library.http.IDataRequest;
import com.android.collect.library.http.okhttp.FileRequestCallBack;
import com.android.collect.library.http.okhttp.FileRequestProgressCallBack;
import com.android.collect.library.util.LogUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Iterator;
import java.util.Set;

import okhttp3.Headers;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class RetrofitRequest extends IDataRequest implements HttpConstant {

	private static RetrofitRequest retrofitRequest;
	private Context context;
	private Handler handler;
	
	public static final String TAG = "Retrofit";
	
	private RetrofitRequest() {
		handler = new Handler(Looper.getMainLooper());
	}

	public static RetrofitRequest getInstance(Context context) {

		if (retrofitRequest == null) {
			synchronized (RetrofitRequest.class) {
				if (retrofitRequest == null)
					retrofitRequest = new RetrofitRequest();
			}
		}
		retrofitRequest.context = context;
		return retrofitRequest;
	}
	
	@Override
	public void postRequst(String url, String jsonParams,
			final DataResultCallBack dataResultCallBack) {
		RequestBody body = RequestBody.create(JSON, jsonParams);
		Call<ResponseBody> call = RetrofitClient.getInstance(context).getRetrofit().create(RetrofitService.class).getData(url.replace(CommDictAction.Url, ""), body);
		LogUtils.i(TAG, "加载连接--" + url + "--参数--" + jsonParams);
		call.enqueue(new Callback<ResponseBody>() {
			
			@Override
			public void onResponse(Call<ResponseBody> call, final Response<ResponseBody> response) {
				//此时还在异步线程中
				handler.post(new Runnable() {
					
					@Override
					public void run() {
						saveSessionID(response);
						JSONObject obj = null;
						try {
							obj = new JSONObject(response.body().string());
							LogUtils.i(TAG, "返回数据：---" + obj.toString());
							dataResultCallBack.onSucceed(obj);
						} catch (JSONException e) {
							e.printStackTrace();
							LogUtils.e(TAG, e.getMessage());
							dataResultCallBack.onFailed(DataRepository.UNKNOWN_RESURN_CODE, DataRepository.UNKNOWN_ERROR_MSG);
						} catch (IOException e) {
							e.printStackTrace();
							LogUtils.e(TAG, e.getMessage());
							dataResultCallBack.onFailed(DataRepository.UNKNOWN_RESURN_CODE, DataRepository.UNKNOWN_ERROR_MSG);
						}
						
					}
				});
			}
			
			@Override
			public void onFailure(Call<ResponseBody> call, final Throwable throwable) {
				//此时还在异步线程中
				handler.post(new Runnable() {
					@Override
					public void run() {
						LogUtils.e(TAG, throwable.getMessage());
						dataResultCallBack.onFailed(DataRepository.UNKNOWN_RESURN_CODE, DataRepository.UNKNOWN_ERROR_MSG);
					}
				});
			}
		});
	}

	@Override
	public JSONObject postRequst(String url, String jsonParams) {
		RequestBody body = RequestBody.create(JSON, jsonParams);
		Call<ResponseBody> call = RetrofitClient.getInstance(context).getRetrofit().create(RetrofitService.class).getData(url.replace(CommDictAction.Url, ""), body);
		LogUtils.i(TAG, "加载连接--" + url + "--参数--" + jsonParams);
		JSONObject obj = null;
		try {
			Response<ResponseBody> response = call.execute();
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
	public okhttp3.Call downloadFile(String url, String saveFileDir, String fileName,
									 FileRequestCallBack downloadFileCallBack) {
		return null;
	}

	@Override
	public boolean downloadFile(String url, String saveFileDir, String fileName) {
		return false;
	}

	@Override
	public boolean downloadFileWithParams(String url, String saveFileDir, String fileName, JSONObject json) {
		return false;
	}

	@Override
	public okhttp3.Call downloadFileWithProgress(String url, String saveFileDir,
												 String fileName, FileRequestProgressCallBack downloadProgressCallBack) {
		return null;
	}

	@Override
	public okhttp3.Call uploadFile(String url, String fileDir, String fileName, FileRequestCallBack downloadFileCallBack) {
		return null;
	}

	@Override
	public boolean uploadFile(String url, String fileDir, String fileName) {
		return false;
	}

	@Override
	public okhttp3.Call uploadFileWithProgress(String url, String fileDir, String fileName, FileRequestProgressCallBack downloadProgressCallBack) {
		return null;
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

}
