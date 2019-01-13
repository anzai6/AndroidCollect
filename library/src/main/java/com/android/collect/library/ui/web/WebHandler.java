package com.android.collect.library.ui.web;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.android.collect.library.ui.main.MainActivity;
import com.android.collect.library.util.ActUtil;
import com.android.collect.library.util.DialogUtil;
import com.android.collect.library.util.Util;

public class WebHandler extends Handler implements WebConstant {

	private Activity mActivity;
	private MyWebViewContract.View mMyWebViewView;

	public WebHandler(Activity activity, MyWebViewContract.View myWebViewView) {
		this.mActivity = Util.checkNotNull(activity);
		this.mMyWebViewView = Util.checkNotNull(myWebViewView);
	}

	@Override
	public void handleMessage(Message msg) {
		Bundle bundle = null;
		switch (msg.what) {
		case WEBVIEW_DISMISS_DIALOG:// 关闭加载中的弹框
			DialogUtil.dismissLDialog();
			break;
			
		case WEBVIEW_SHOW_PSW_DIALOG:// 弹出密码框
			break;
			
		case WEBVIEW_ALERT_MSG:// 显示信息
			DialogUtil.showError(mActivity, msg.obj.toString());
			break;
			
		case WEBVIEW_SET_TITLE:// 设置标题
			mMyWebViewView.setTitle(msg.obj.toString());
			break;
			
		case WEBVIEW_SHOW_DATE_DIALOG:// 显示日期控件
			bundle = msg.getData();
			break;
			
		case WEBVIEW_SHOW_CIRCLE_DIALOG:// 显示转圈框
			DialogUtil.showLDialog(mActivity);
			break;
			
		case WEBVIEW_ACTIVITY_FINISH:// 关闭页面
			mMyWebViewView.pageFinish();
			break;
			
		case WEBVIEW_TO_RE_FUND:// 去充值页面
			break;
			
		case WEBVIEW_GO_TRANSFER:// 去充值页面
			break;
			
		case WEBVIEW_TO_LOGIN:// 登录并回到主页
			break;
			
		case WEBVIEW_TO_FACE_RECOGNIZE:// 去人脸识别，成功后去贷款申请结果页
			break;
			
		case WEBVIEW_TO_ANY_CALL_LOAN_RESULT:// 去双向视频，成功后去贷款申请结果页
			break;
			
		case WEBVIEW_OBTAIN_CLIENT_DATE:// 給HTML页面上送参数
			mMyWebViewView.obtainClientData(msg.obj.toString());
			break;
			
		case WEBVIEW_TO_MYACCOUNT:// 去我的账户页面
			ActUtil.startMyActivity(mActivity, MainActivity.class);
			mMyWebViewView.pageFinish();
			break;
			
		case WEBVIEW_TO_LOTTERY_SHARE:// 去分享抽奖
			break;
			
		case WEBVIEW_TO_ID_CARD_ORC:// 调用识别身份证的页面
			break;
			
		case WEBVIEW_ID_CARD_ORC_1:// 调用身份证正面或反面拍照界面
			break;
			
		case WEBVIEW_ID_CARD_ORC_2:// 调用身份证正面或反面拍照界面
			break;

		case WEBVIEW_CALL_PHONE:// 打电话
			break;
			
		case WEBVIEW_DO_SHARE:// 分享
			break;
			
		case WEBVIEW_GO_PROD_WEB: // 产品跳转
			break;
			
		case WEBVIEW_GO_TRANSFER2: // 充值跳转
			break;
			
		case WEBVIEW_GO_SHARE_WEB: // 分享有礼跳转
			break;
			
		case WEBVIEW_GO_BIND_CARD_CHANGE: // 跳转变更绑定卡
			break;
			
		case WEBVIEW_SHOW_YINBAOTONG_PDF://显示银保通pdf
			break;
			
		case WEBVIEW_GO_TO_LAKALA:// 跳转拉卡拉
			break;

		case WEBVIEW_DOWNLOAD_PDF:// 根据文件名下载PDF文件
			break;
			
		case WEBVIEW_DOWNLOAD_LOAN_PDF:// 下载贷款的PDF文件
			break;
			
		case WEBVIEW_TO_ANY_CALL:// 去双向视频，成功后返回OrginSeqNo和OrginID
			break;
			
		default:
			break;
		}
		super.handleMessage(msg);
	}

}
