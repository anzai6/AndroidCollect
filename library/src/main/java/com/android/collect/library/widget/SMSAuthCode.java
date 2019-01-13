package com.android.collect.library.widget;


import android.content.Context;
import android.os.CountDownTimer;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;

import com.android.collect.library.R;


/**
 * 短信验证码 自定义View
 *
 */
public class SMSAuthCode extends Button {
	private Context mContext;
	private CountDownTimer countDownTimer;
	private long countDownTime = 90000;

	public SMSAuthCode(Context context) {
		super(context, null);
	}

	public SMSAuthCode(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.mContext = context;
		initSetupView();
		setListener();
	}

	private void initSetupView() {
		setText(R.string.get_msg);
	}

	//设置短信验证码的监听，倒计时
	private void setListener() {
		setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (onGetSms != null) {
					onGetSms.doSubmitSms();
				}
			}
		});

		//设置定时器
		countDownTimer = new CountDownTimer(countDownTime, 1000) {

			@Override
			public void onTick(long millisUntilFinished) {
				btnResendSet(false, millisUntilFinished);
			}

			@Override
			public void onFinish() {
				btnResendSet(true, 0);
			}
		};
	}

	//设置btn的事件
	protected void btnResendSet(boolean b, long millisUntilFinished) {
		if (b) {
			setText(R.string.reset_msg);
			setEnabled(true);
		} else {
			String msg = mContext.getString(R.string.reset_msg_second_format);
			setText(String.format(msg, millisUntilFinished / 1000 + ""));
			setEnabled(false);
		}
	}

	//设置btn的事件
	protected void btnResendSetFirst() {
		setText(R.string.get_msg);
		setEnabled(true);
	}

	//接口回调
	private OnGetSms onGetSms;

	public interface OnGetSms {
		//获取短信验证码
		public void doSubmitSms();
	}

	public void setOnGetSms(OnGetSms onGetSms) {
		this.onGetSms = onGetSms;
	}


	//countDownTimer 属性设置
	public void init() {
		countDownTimer.cancel();
	}

	public void doTimeStart() {
		countDownTimer.start();
	}

	public void doTimeEnd() {
		btnResendSet(true, 0);
		countDownTimer.cancel();

	}

	public void doTimeEndFirst() {
		btnResendSetFirst();
		countDownTimer.cancel();

	}

	public void setCountDownTime(long countDownTime) {
		this.countDownTime = countDownTime;
	}

}
