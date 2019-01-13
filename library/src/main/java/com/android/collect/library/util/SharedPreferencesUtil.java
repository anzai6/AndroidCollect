package com.android.collect.library.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.android.collect.library.common.Component;

public class SharedPreferencesUtil {
	private static SharedPreferences sharedPreferences;
	private static String CONFIG = "ZBDIRECT";

	public static void saveString(Context context, String key, String value) {
		if (sharedPreferences == null) {
			sharedPreferences = context.getSharedPreferences(CONFIG,
					Context.MODE_PRIVATE);
		}

		if (TextUtils.isEmpty(value)) {
			// 设置为空
			sharedPreferences.edit().putString(key, value).commit();
		} else {
			// 如果设的值不为空
			sharedPreferences.edit()
					.putString(key, Component.encryptMode(value)).commit();
		}
	}

	public static String getString(Context context, String key,
								   String defValue) {
		if (sharedPreferences == null) {
			sharedPreferences = context.getSharedPreferences(CONFIG,
					Context.MODE_PRIVATE);
		}

		String data = sharedPreferences.getString(key, defValue);
		try {
			return Component.desDecrypt(data);
		} catch (Exception e) {
			e.printStackTrace();
			return defValue;
		}

	}

	/**
	 * 不加密
	 * @param context
	 * @param key
	 * @param value
     */
	public static void saveStringNoEncry(Context context, String key, String value) {
		if (sharedPreferences == null) {
			sharedPreferences = context.getSharedPreferences(CONFIG,
					Context.MODE_PRIVATE);
		}

		sharedPreferences.edit().putString(key, value).commit();
	}

	/**
	 * 不加密
	 * @param context
	 * @param key
	 * @param defValue
     * @return
     */
	public static String getStringNoEncry(Context context, String key,
									   String defValue) {
		if (sharedPreferences == null) {
			sharedPreferences = context.getSharedPreferences(CONFIG,
					Context.MODE_PRIVATE);
		}
		return sharedPreferences.getString(key, defValue);

	}

	public static void saveBoolean(Context context, String key,
								   boolean value) {
		if (sharedPreferences == null) {
			sharedPreferences = context.getSharedPreferences(CONFIG,
					Context.MODE_PRIVATE);
		}
		sharedPreferences.edit().putBoolean(key, value).commit();
	}

	public static boolean getBoolean(Context context, String key,
									 boolean defValue) {
		if (sharedPreferences == null) {
			sharedPreferences = context.getSharedPreferences(CONFIG,
					Context.MODE_PRIVATE);
		}
		return sharedPreferences.getBoolean(key, defValue);
	}

	public static void saveInt(Context context, String key, int value) {
		if (sharedPreferences == null) {
			sharedPreferences = context.getSharedPreferences(CONFIG,
					Context.MODE_PRIVATE);
		}
		sharedPreferences.edit().putInt(key, value).commit();
	}

	public static int getInt(Context context, String key, int value) {
		if (sharedPreferences == null) {
			sharedPreferences = context.getSharedPreferences(CONFIG,
					Context.MODE_PRIVATE);
		}
		return sharedPreferences.getInt(key, value);
	}

}
