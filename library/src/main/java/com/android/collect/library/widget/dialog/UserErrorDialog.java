package com.android.collect.library.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.TextView;

import com.android.collect.library.R;

import java.util.Timer;
import java.util.TimerTask;

/**
 * 错误提示弹框
 */
public class UserErrorDialog extends Dialog {

    private Context mContext;
    private String msg;
    public TextView tvContent;
    private OnDismissListener mOnDismissListener;

    /**
     * 默认两秒消失
     */
    private int countDownTime = 2;
    private View ll_countdown;
    private TextView tv_countdownNumber;
    private TextView tv_countdownText;
    private String countDownText;

    public UserErrorDialog(Context context, String message) {
        super(context, R.style.base_dialog_style);
        this.mContext = context;
        this.msg = message;
    }

    public UserErrorDialog(Context context, String message,
                         OnDismissListener mOnDismissListener) {
        super(context, R.style.base_dialog_style);
        this.mContext = context;
        this.msg = message;
        this.mOnDismissListener = mOnDismissListener;
    }

    /**
     * 设置是否有倒计时
     *
     * @param countDownTime
     */
    public void setCountDown(int countDownTime, String countDownText) {
        this.countDownTime = countDownTime;
        this.countDownText = countDownText;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(LayoutParams.FLAG_FULLSCREEN,
                LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.dialog_show_error);
        ll_countdown = findViewById(R.id.ll_countdown);
        tv_countdownNumber = (TextView) findViewById(R.id.tv_countdownNumber);
        tv_countdownText = (TextView) findViewById(R.id.tv_countdownText);

        if (countDownTime != -1) {
            tv_countdownNumber.setText(countDownTime + "");

            if (!TextUtils.isEmpty(countDownText)) {
                // 如果文字不为空
                tv_countdownText.setText(countDownText);
                ll_countdown.setVisibility(View.VISIBLE);
            } else {
                // 文字为空
                ll_countdown.setVisibility(View.GONE);
            }

            countDownTime--;

            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {

                    if (countDownTime < 1) {
                        UserErrorDialog.this.dismiss();
                        return;
                    }

                    tv_countdownNumber.post(new Runnable() {
                        @Override
                        public void run() {
                            tv_countdownNumber.setText(countDownTime--
                                    + "");
                        }
                    });
                }
            }, 1000, 1000);
        }

        setParams(false, 0f);

        tvContent = (TextView) findViewById(R.id.tv_dialog_content);
        tvContent.setText(msg);

        if (mOnDismissListener != null) {
            this.setOnDismissListener(mOnDismissListener);
        }

    }

    /**
     * 设置dialog的位置
     *
     * @param isSetWidth 是否设置宽度
     */
    public void setParams(boolean isSetWidth, float bgTransparency) {
        getWindow().setGravity(Gravity.CENTER);
        WindowManager m = (WindowManager) mContext
                .getSystemService(Context.WINDOW_SERVICE);
        Display d = m.getDefaultDisplay();
        LayoutParams p = getWindow().getAttributes();
        // 设置宽度
        if (isSetWidth)
            p.width = (int) (d.getWidth() * 0.9);
        // 背景层透明度
        p.dimAmount = bgTransparency;
        getWindow().setAttributes(p);
    }

}
