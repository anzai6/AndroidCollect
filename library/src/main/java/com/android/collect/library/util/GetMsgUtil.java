package com.android.collect.library.util;

import android.app.Activity;
import android.content.ContentValues;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.widget.EditText;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 获取短信验证码
 * 
 * @author anzai
 * 
 */
public class GetMsgUtil {

	private Activity context;
	private EditText et_YZM;
	private SmsContent content;
	int msgFlag = 0;
	private String phoneNum;

	public GetMsgUtil(Activity context, EditText et_YZM, int msgFlag, String phoneNum) {
		this.context = context;
		this.et_YZM = et_YZM;
		this.msgFlag = msgFlag;
		this.phoneNum = phoneNum;
	}

	/**
	 * 设置验证码按钮的监听
	 */
	public void setSmsListener() {
		content = null;
		if (null == content) {
			content = new SmsContent(new Handler());
		}
		// 注册短信变化监听
		context.getContentResolver().registerContentObserver(
				Uri.parse("content://sms/"), true, content);

	}

	/*
	 * 监听短信数据库
	 */
	class SmsContent extends ContentObserver {
		private Cursor cursor = null;

		public SmsContent(Handler handler) {
			super(handler);
		}

		@SuppressWarnings("deprecation")
		@Override
		public void onChange(boolean selfChange) {
			super.onChange(selfChange);
			// 读取收件箱中指定号码的短信
			cursor = context.managedQuery(Uri.parse("content://sms/inbox"),
					new String[] { "_id", "address", "read", "body" },
					" address=? and read=?", new String[] { phoneNum,
							"0" }, "_id desc");
			// 按id排序，如果按date排序的话，修改手机时间后，读取的短信就不准了
			if (cursor != null && cursor.getCount() > 0) {
				ContentValues values = new ContentValues();
				values.put("read", "1"); // 修改短信为已读模式
				cursor.moveToNext();
				int smsbodyColumn = cursor.getColumnIndex("body");
				// int smsSenderColumn = cursor.getColumnIndex("address");
				String smsBody = cursor.getString(smsbodyColumn);
				// String sender = cursor.getString(smsSenderColumn);// 发送者
				String code = "";
				switch (msgFlag) {
				case 0:
					code = getCodeMsg(smsBody);
					break;

				default:
					break;
				}
				if (!Util.isStringNull(code)) {
					et_YZM.setText(code);
				}
			}
			// 在用managedQuery的时候，不能主动调用close()方法， 否则在Android 4.0+的系统上， 会发生崩溃
			if (Build.VERSION.SDK_INT < 14) {
				cursor.close();
			}
		}
	}

	public void unregisterSmsListener() {
		if (context != null && content != null)
			context.getContentResolver().unregisterContentObserver(content);
	}

	/**
	 * 从短信字符串中读取6位数字验证码
	 *
	 * @param msg
	 * @return
	 */
	private static String getCodeMsg(String msg) {
		if (msg.length() > 6) {
			for (int i = 0; i < msg.length() - 6; i++) {
				String msg_code = msg.substring(i, i + 6);
				Pattern pattern = Pattern.compile("[0-9]*");
				Matcher isNum = pattern.matcher(msg_code);
				if (isNum.matches()) {
					if (msg.substring(i - 3, i).contains("验证码")) {
						LogUtils.i(msg_code);
						return msg_code;
					}
				}
			}
			return "";
		} else
			return "";

	}
}
