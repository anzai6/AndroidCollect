package com.android.collect.library.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.Button;
import android.widget.TextView;

import com.android.collect.library.R;
import com.android.collect.library.util.Util;

/**
 * 确认弹框
 */
public class UserConfirmDialog extends Dialog implements
        View.OnClickListener {

    private Context mContext;
    private String msg;
    public Button btnCancel, btnConfirm;
    private View.OnClickListener onConfirmListener;
    private View.OnClickListener onCancelListener;
    public TextView tvContent;
    private String leftBtnStr, rightBtnStr;

    public UserConfirmDialog(Context context, String message,
                             View.OnClickListener onCancelListener,
                             View.OnClickListener onConfirmListener) {
        super(context, R.style.base_dialog_style);
        this.mContext = context;
        this.msg = message;
        if (onCancelListener != null) {
            this.onCancelListener = onCancelListener;
        }
        if (onConfirmListener != null) {
            this.onConfirmListener = onConfirmListener;
        }
    }

    public void setLeftBtnStr(String leftBtnStr) {
        this.leftBtnStr = leftBtnStr;
    }

    public void setRightBtnStr(String rightBtnStr) {
        this.rightBtnStr = rightBtnStr;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(LayoutParams.FLAG_FULLSCREEN,
                LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.dialog_user_confirm);
        btnCancel = (Button) findViewById(R.id.btn_cancel);
        btnConfirm = (Button) findViewById(R.id.btn_confirm);

        if (onCancelListener != null) {
            btnCancel.setOnClickListener(this);
        }
        if (onConfirmListener != null) {
            btnConfirm.setOnClickListener(this);
        }

        if (!Util.isStringNull(rightBtnStr)) {
            btnConfirm.setText(rightBtnStr);
        }
        if (!Util.isStringNull(leftBtnStr))
            btnCancel.setText(leftBtnStr);
        setParams(false, 0.3f);
        tvContent = (TextView) findViewById(R.id.tv_dialog_content);
        tvContent.setText(msg);
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

    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.btn_confirm) {
            if (onConfirmListener != null) {
                onConfirmListener.onClick(v);
                this.dismiss();
            }

        } else if (i == R.id.btn_cancel) {
            if (onCancelListener != null) {
                onCancelListener.onClick(v);
            }
            dismiss();

        }
    }

}
