package com.android.collect.library.http;

import java.util.HashMap;

import okhttp3.MediaType;

public interface HttpConstant {

	public static HashMap<String, String> SessionMap = new HashMap<String, String>();
	
	public final static int HTTP_CONNECT_TIMEOUT =90;
	public final static int HTTP_READ_TIMEOUT=90;
	public final static int HTTP_WRITE_TIMEOUT=90;
	
	public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
	public static final MediaType FILE = MediaType.parse("application/octet-stream");

	public static final String ERROR_CODE = "_exceptionMessageCode";
	public static final String RETURN_MESSAGE = "_exceptionMessage";
	public static final String RETURN_CODE = "_RejCode";
	public static final String RETURN_CODE_SUC = "000000";
	
	public static String UNKNOWN_ERROR_MSG = "未知错误";
	public static String UNKNOWN_RESURN_CODE = "-1";
}
