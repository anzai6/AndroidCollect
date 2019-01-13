package com.android.collect.library.http.update;

import org.json.JSONObject;

public interface UpdateIsFinish {

	public void isSucceed(JSONObject jsonObject);
	public void isFailed();
}
