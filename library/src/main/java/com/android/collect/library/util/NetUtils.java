package com.android.collect.library.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * ClassName NetUtils
 * 
 * @author LL
 * @Date 2013-7-5 下午4:25:46
 */
public class NetUtils {
	public static final int POST = 1;
	public static final int GET = 0;

	public static boolean isNetAvailable(Context context) {
		boolean isNetAvailable = false;
		ConnectivityManager nManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = nManager.getActiveNetworkInfo();
		if (networkInfo != null) {
			isNetAvailable = networkInfo.isAvailable();
		}
		return isNetAvailable;
	}


	public static boolean isWifiAvailable(Context context) {
		boolean isWifiAvailable = false;
		ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeNetInfo = connectivityManager.getActiveNetworkInfo();
		if (activeNetInfo != null && activeNetInfo.getType() == ConnectivityManager.TYPE_WIFI) {
			isWifiAvailable = true;
		}
		return isWifiAvailable;
	}
}
