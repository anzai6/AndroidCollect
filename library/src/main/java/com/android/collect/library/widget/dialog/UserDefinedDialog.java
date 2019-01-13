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

import com.android.collect.library.R;

/**
 * 用户自定义弹框
 */
public class UserDefinedDialog extends Dialog implements
        View.OnClickListener {

    private Context mContext;
    private View includeView = null;// dialog引入布局id
    public Button btnCancel, btnConfirm;
    private View.OnClickListener onComfirmListener;
    private View.OnClickListener onCancelListener;

    public UserDefinedDialog(Context context, View view,
                             View.OnClickListener onCancelListener,
                             View.OnClickListener onComfirmListener) {
        super(context, R.style.base_dialog_style);
        this.mContext = context;
        this.includeView = view;
        if (onCancelListener != null) {
            this.onCancelListener = onCancelListener;
        }
        if (onComfirmListener != null) {
            this.onComfirmListener = onComfirmListener;
        }
    }

    public UserDefinedDialog(Context context, View view) {
        super(context, R.style.base_dialog_style);
        this.mContext = context;
        this.includeView = view;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(LayoutParams.FLAG_FULLSCREEN,
                LayoutParams.FLAG_FULLSCREEN);

        setContentView(includeView);
        btnCancel = (Button) findViewById(R.id.btn_cancel);
        btnConfirm = (Button) findViewById(R.id.btn_confirm);
        if (null != btnCancel) {
            btnCancel.setOnClickListener(this);
        }
        if (null != btnConfirm) {
            btnConfirm.setOnClickListener(this);
        }
        setParams(true, 0.3f);
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
            if (onComfirmListener != null) {
                onComfirmListener.onClick(v);
            }

        } else if (i == R.id.btn_cancel) {
            if (onCancelListener != null) {
                onCancelListener.onClick(v);
            }
            dismiss();

        }
    }

}
