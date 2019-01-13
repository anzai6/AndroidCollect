package com.android.collect.library.manager;

import android.app.Activity;
import android.os.Bundle;

import com.android.collect.library.common.KeyHelper;
import com.android.collect.library.ui.main.MainActivity;
import com.android.collect.library.util.ActUtil;
import com.android.collect.library.util.Util;

import org.json.JSONObject;

public class LoginSuccessManager {

	/**
	 * 登录成功后的统一处理
	 * 
	 * @param jsonObject
	 */
	public static void isLoginSuccess(JSONObject jsonObject, final Activity activity,
			Bundle bundle) {
		
//		String NeedDeviceBind = SharedPreferencesUtil.getStringData(activity, KeyHelper.NeedDeviceBind, "");
//		if("1".equals(NeedDeviceBind)){//设备绑定：1未绑定 2绑定
//			bundle.putString(KeyHelper.JSON, jsonObject.toString());
//			ActUtil.startMyActivity(activity, LoginDeviceBindActivity.class, bundle);
//			activity.finish();
//			return;
//		}
//
//		String IsNeedUpIdCard = SharedPreferencesUtil.getStringData(activity, KeyHelper.IsNeedUpIdCard, "");// 是否需要上传身份证照片  0是  1否
//		if ("0".equals(IsNeedUpIdCard)) {// 需要上传身份证照片
//			bundle.putString(KeyHelper.JSON, jsonObject.toString());
//			ActUtil.startMyActivity(activity, UploadIdImgActivity.class,
//					bundle);
//			activity.finish();
//			return;
//		}
//
//		HttpUtils.PushSign(jsonObject, activity);
//
//		Constant.UserImgName = JSONUtil.getString(jsonObject, "UserImgName");
//		UserManager.isLogin = true;
//
//		// 登录成功广播
//		Intent login_intent = new Intent();
//		login_intent.putExtra(KeyHelper.LOGIN_STATUS, Constant.LOGIN);
//		login_intent.setAction(Constant.LOGIN_APP_ACTION);
//		activity.sendBroadcast(login_intent);
//
//		LoginDataDao.saveLoginData(activity, jsonObject);
//
//		String className = Util.getBundleString(bundle, Constant.CLASSNAME,
//				MainActivity.class.getName());
//
//		// 0设置过，需重新设置，1没有设置
//		if (JSONUtil.getString(jsonObject, "isSetting").equals("0")) {
//			bundle.putString("from", LoginActivity.class.getName());
//			bundle.putString(Constant.CLASSNAME, className);
//			bundle.putString(KeyHelper.FROM, LoginActivity.class.getName());
//
//			Intent intent1 = new Intent(activity, CreatePatternActivity.class);
//			intent1.putExtras(bundle);
//			activity.startActivity(intent1);
//			activity.finish();
//			return;
//		}
		
		loginOnGO2Target(activity, bundle);
		
	}
	
	/**
	 * 登录之后的跳转
	 */
	public static void loginOnGO2Target(final Activity activity, final Bundle bundle){
//		String BindStatus = SharedPreferencesUtil.getStringData(activity,
//				KeyHelper.BindStatus, "1");// 1-没有绑定卡，0登录
//		String NoAcctStatus = SharedPreferencesUtil.getStringData(activity,
//				KeyHelper.NoAcctStatus, "0");// 0没有电子账户
//		String AcStatus = SharedPreferencesUtil.getStringData(activity,
//				KeyHelper.AcStatus, "");// 5需要重置交易密码
//		String IsNeedReBindCard = SharedPreferencesUtil.getStringData(activity,
//				KeyHelper.IsNeedReBindCard, "");// 是否需要重新绑卡  0是  1否
//
//		String open_flag = Util.getBundleString(bundle, KeyHelper.OPENFLAG, "");
		final String className = Util.getBundleString(bundle, KeyHelper.ClassName, MainActivity.class.getName());
		
//		if ("0".equals(BindStatus) && "1".equals(NoAcctStatus)) {// 绑卡未开户
//			bundle.putString("Class", className);
//			bundle.putString(KeyHelper.FROM, LoginActivity.class.getName());
//			ActUtil.startMyActivity(activity, BindCardTrdPwdActivity.class,
//					bundle);
//			activity.finish();
//			return;
//		}
//
//		if ("1".equals(open_flag) && "1".equals(NoAcctStatus)){//需要开户且未开户
//			ActUtil.startActivityNeedOpen(activity, className, bundle);
//			return;
//		}
//
//		if ("1".equals(BindStatus)) {// 没有绑卡强制绑卡
//			ActUtil.startActivityNeedBind(activity, className, bundle);
//			return;
//		}
//
//		if ("0".equals(IsNeedReBindCard)) {// 需要重新绑卡
//			/*Component.alertDialog(activity, R.string.bindcard_str, new View.OnClickListener() {
//				@Override
//				public void onClick(View arg0) {
//
//				}
//			}, new View.OnClickListener() {
//				@Override
//				public void onClick(View arg0) {
//
//					activity.finish();
//				}
//			});*/
//			bundle.putString(KeyHelper.FACE_FROM, Constant.FACE_BINDCARDCHANGE_FROM);
//			ActUtil.startMyActivity(activity, BindCardActivity.class, bundle);
//			activity.finish();
//			return;
//		}
//
//		if ("5".equals(AcStatus)) {
//			bundle.putString(KeyHelper.CLASSNAME, className);
//			bundle.putString(KeyHelper.ReSetPswBack, "0");
//			ActUtil.startMyActivity(activity, ResetPswConfirmActivity.class,
//					bundle);
//			activity.finish();
//			return;
//		}
		
		ActUtil.startMyActivity(activity, className, bundle);
		if(!(activity instanceof MainActivity))
			activity.finish();
	}

}
