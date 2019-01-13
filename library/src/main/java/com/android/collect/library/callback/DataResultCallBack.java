package com.android.collect.library.callback;

import org.json.JSONObject;

/**
 * anzai
 */
public interface DataResultCallBack {
	public void onSucceed(JSONObject jsonObject);

	public void onFailed(String statusCode, String msg);
}
