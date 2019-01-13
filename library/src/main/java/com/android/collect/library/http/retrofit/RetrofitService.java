package com.android.collect.library.http.retrofit;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface RetrofitService {
	@POST("{action}")
	public abstract Call<ResponseBody> getData(@Path("action") String url, @Body RequestBody body);
}
