package com.android.collect.library.ui.web;

import android.app.Activity;
import android.os.Bundle;
import android.os.Message;
import android.webkit.JavascriptInterface;

import com.android.collect.library.callback.DataResultCallBack;
import com.android.collect.library.http.DataRepository;
import com.android.collect.library.util.DeviceInfoUtil;
import com.android.collect.library.util.Util;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * WebView与Js交互的类
 * @author anzai
 *
 */
public class MyJavaScriptInterface implements WebConstant{

	private Activity activity;
	private WebHandler webHandler;
	private MyWebViewContract.View mMyWebViewView;

	public MyJavaScriptInterface(Activity activity, WebHandler webHandler, MyWebViewContract.View myWebViewView) {
		this.activity = Util.checkNotNull(activity);
		this.mMyWebViewView = Util.checkNotNull(myWebViewView);
		this.webHandler = Util.checkNotNull(webHandler);
	}

	/**
	 * 提供vx页面调动发送请求
	 * 
	 * @param actionId
	 *            交易名
	 * @param param
	 *            参数
	 */
	@JavascriptInterface
	public void NativeCall_postRequest(String actionId, String param,
			final String key) {

		webHandler.sendEmptyMessage(WEBVIEW_DISMISS_DIALOG);

		if ("passwd".equals(actionId)) {// 获取密码
			sendMsg(key, WEBVIEW_SHOW_PSW_DIALOG);
			return;
		}
		
		if ("idcardocr1".equals(actionId)) {// 拍身份正面照片
			sendMsg(key, WEBVIEW_ID_CARD_ORC_1);
			return;
		}
		
		if ("idcardocr2".equals(actionId)) {// 拍身份反面照片
			sendMsg(key, WEBVIEW_ID_CARD_ORC_2);
			return;
		}
		
		if ("twoWayVideo".equals(actionId)) {// 多渠道视频
			sendMsg(key + "::" + param, WEBVIEW_TO_ANY_CALL);
			return;
		}
		
		JSONObject obj = null;
		if (!Util.isStringNull(param)) {
			try {
				obj = new JSONObject(param);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		} else {
			obj = new JSONObject();
		}
		
		if ("QueryProdCheckFlag.do".equals(actionId)
				|| "FundTransIn.do".equals(actionId)
				|| "SavToTim.do".equals(actionId)
				|| "InvestPurchase.do".equals(actionId)) {// 某些交易添加deviceToken
			DeviceInfoUtil deviceInfoUtil = new DeviceInfoUtil(activity);
//			JSONUtil.setString(obj, "deviceToken", Util.getPasswordByOld(deviceInfoUtil.getDeviceIMEI()));
		}

		new DataRepository(activity, actionId, obj,
				new DataResultCallBack() {
					@Override
					public void onSucceed(JSONObject jsonObject) {
						mMyWebViewView.doCallback(key, jsonObject.toString());
					}

					@Override
					public void onFailed(String statusCode, String msg) {
						mMyWebViewView.doCallback(key, "1234567");
					}
				}).showDialog().postRequest();
	}

	/**
	 * 显示提示信息
	 * 
	 * @param msg
	 */
	@JavascriptInterface
	public void NativeCall_alertMsg(String msg) {
		sendMsg(msg, WEBVIEW_ALERT_MSG);
	}

	@JavascriptInterface
	public void NativeCall_setTitle(String msg) {
		sendMsg(msg, WEBVIEW_SET_TITLE);
	}

	@JavascriptInterface
	public void NativeCall_finish() {
		webHandler.sendEmptyMessage(WEBVIEW_ACTIVITY_FINISH);
	}

	/**
	 * 去充值页面
	 */
	@JavascriptInterface
	public void NativeCall_ReFund() {
		webHandler.sendEmptyMessage(WEBVIEW_TO_RE_FUND);
	}

	/**
	 * 去充值页面
	 */
	@JavascriptInterface
	public void NativeCall_goTransfer(String target) {
		sendMsg(target, WEBVIEW_GO_TRANSFER);
	}

	/**
	 * 加载中...
	 */
	@JavascriptInterface
	public void NativeCall_showDialog() {
		webHandler.sendEmptyMessage(WEBVIEW_SHOW_CIRCLE_DIALOG);
	}

	@JavascriptInterface
	public void NativeCall_dismissDialog() {
		webHandler.sendEmptyMessage(WEBVIEW_DISMISS_DIALOG);
	}

	/**
	 * 显示日期控件
	 * 
	 * @param date
	 */
	@JavascriptInterface
	public void NativeCall_showDateDialog(String date, String key) {
		Message msg = Message.obtain();
		Bundle bundle = new Bundle();
		bundle.putString("Date", date);
		bundle.putString("Key", key);
		msg.setData(bundle);
		msg.what = WEBVIEW_SHOW_DATE_DIALOG;
		webHandler.sendMessage(msg);
	}
	
	@JavascriptInterface
	public void NativeCall_toLogin() {
		webHandler.sendEmptyMessage(WEBVIEW_TO_LOGIN);
	}
	
	/**
	 * 去人脸识别，成功后去贷款申请结果页
	 * @param js_data
	 */
	@JavascriptInterface
	public void NativeCall_FaceRecognize(String js_data, String busid) {
		sendMsg(js_data + "||" + busid, WEBVIEW_TO_FACE_RECOGNIZE);
	}
	
	/**
	 * 去双向视频，成功后去贷款申请结果页
	 * @param js_data
	 */
	@JavascriptInterface
	public void NativeCall_AnyCall(String js_data) {
		sendMsg(js_data, WEBVIEW_TO_ANY_CALL_LOAN_RESULT);
	}
	
	@JavascriptInterface
	public void NativeCall_ObtainClientData(String key) {
		sendMsg(key, WEBVIEW_OBTAIN_CLIENT_DATE);
	}
	
	@JavascriptInterface
	public void NativeCall_toMyAccount() {
		webHandler.sendEmptyMessage(WEBVIEW_TO_MYACCOUNT);
	}
	
	@JavascriptInterface
	public void NativeCall_goLottery() {
		webHandler.sendEmptyMessage(WEBVIEW_TO_LOTTERY_SHARE);
	}
	
	@JavascriptInterface
	public void NativeCall_goIdCardOCR() {
		webHandler.sendEmptyMessage(WEBVIEW_TO_ID_CARD_ORC);
	}
	
	@JavascriptInterface
	public void NativeCall_callPhone(String phone) {
		sendMsg(phone, WEBVIEW_CALL_PHONE);
	}
	
	@JavascriptInterface
	public void NativeCall_reLogin() {
		sendMsg("", WEBVIEW_RE_LOGIN);
	}
	
	@JavascriptInterface
	public void NativeCall_shareGainGift(String ShareChannel) {
		NativeCall_doShare(ShareChannel);
	}
	
	@JavascriptInterface
	public void NativeCall_doShare(String ShareChannel) {
		sendMsg(ShareChannel, WEBVIEW_DO_SHARE);
	}
	
	@JavascriptInterface
	public void NativeCall_goProdWeb(String content) {
		sendMsg(content, WEBVIEW_GO_PROD_WEB);
	}
	
	@JavascriptInterface
	public void NativeCall_goTransfer2(String target) {
		sendMsg(target, WEBVIEW_GO_TRANSFER2);
	}
	
	@JavascriptInterface
	public void NativeCall_goShareWeb() {
		sendMsg("", WEBVIEW_GO_SHARE_WEB);
	}
	
	@JavascriptInterface
	public void NativeCall_goBangCard() {
		sendMsg("", WEBVIEW_GO_BIND_CARD_CHANGE);
	}
	
	@JavascriptInterface
	public void NativeCall_showPDF(String pdfName){
		sendMsg(pdfName, WEBVIEW_SHOW_YINBAOTONG_PDF);
	}
	
	@JavascriptInterface
	public void NativeCall_goToLKL() {
		sendMsg("", WEBVIEW_GO_TO_LAKALA);
	}
	
	/**
	 * 根据文件名下载PDF文件
	 * @param pdfName
	 */
	@JavascriptInterface
	public void NativeCall_downPDF(String pdfName) {
		sendMsg(pdfName, WEBVIEW_DOWNLOAD_PDF);
	}
	
	/**
	 * 下载贷款的PDF文件
	 * @param params
	 */
	@JavascriptInterface
	public void NativeCall_downAndViewPdf(String params){
		sendMsg(params, WEBVIEW_DOWNLOAD_LOAN_PDF);
	}
	
	private void sendMsg(String obj, int what){
		Message msg = Message.obtain();
		if (!Util.isStringNull(obj))
			msg.obj = obj;
		msg.what = what;
		webHandler.sendMessage(msg);
	}
	
}
