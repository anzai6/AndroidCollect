package com.android.collect.library.http.retrofit;

import android.content.Context;

import com.android.collect.library.http.CommDictAction;

import retrofit2.Retrofit;


public class RetrofitClient {

	private final Retrofit retrofit;
	private static RetrofitClient retrofitClient;
	private Context context;

	public static RetrofitClient getInstance(Context context) {

		if (retrofitClient == null) {
			synchronized (RetrofitClient.class) {
				if (retrofitClient == null)
					retrofitClient = new RetrofitClient();
			}
		}
		retrofitClient.context = context;
		return retrofitClient;
	}
	
	private RetrofitClient() {
		retrofit = new Retrofit.Builder()
				.baseUrl(CommDictAction.Url)
				.client(RetrofitOkHttpClient.getInstance(context).getOkHttpClient())
				.build();
	}
	
	public Retrofit getRetrofit(){
		return retrofit;
	}

}
