package com.android.collect.library.http.update;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Display;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.Button;
import android.widget.TextView;

import com.android.collect.library.R;


public class UpdateDefinedDialog extends Dialog implements
        View.OnClickListener {

    private Context mContext;
    private String updateInfo;
    private Button btn_left, btn_right;
    private View.OnClickListener onConfirmListener;
    private View.OnClickListener onCancelListener;
    private TextView tv_updateInfo;
    /**
     * 1提示更新，2强制更新
     */
    private String flag = "1";

    public UpdateDefinedDialog(Context context, String updateInfo, String flag, View.OnClickListener onCancelListener,
                               View.OnClickListener onConfirmListener) {
        super(context, R.style.base_dialog_style);
        this.mContext = context;
        this.updateInfo = updateInfo;
        this.flag = flag;
        this.onConfirmListener = onConfirmListener;
        this.onCancelListener = onCancelListener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_update);
        getWindow().setFlags(LayoutParams.FLAG_FULLSCREEN,
                LayoutParams.FLAG_FULLSCREEN);
        tv_updateInfo = (TextView) findViewById(R.id.tv_updateInfo);
        btn_left = (Button) findViewById(R.id.btn_left);
        btn_right = (Button) findViewById(R.id.btn_right);

        btn_left.setOnClickListener(this);
        btn_right.setOnClickListener(this);
        tv_updateInfo.setText(updateInfo);

        if ("2".equals(flag)) {
            btn_left.setText(R.string.exit);
            btn_right.setText(R.string.update_now);
        }

        WindowManager m = (WindowManager) mContext
                .getSystemService(Context.WINDOW_SERVICE);
        Display d = m.getDefaultDisplay();
        LayoutParams p = getWindow().getAttributes();
        p.width = (int) (d.getWidth() * 0.9);
        getWindow().setAttributes(p);
        getWindow().setGravity(Gravity.CENTER);

        setCanceledOnTouchOutside(false);
        setCancelable(false);
    }

    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.btn_left) {
            if (onCancelListener != null) {
                onCancelListener.onClick(v);
            }

        } else if (i == R.id.btn_right) {
            if (onConfirmListener != null) {
                onConfirmListener.onClick(v);
            }

        }
        this.cancel();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_HOME:
                return true;
            case KeyEvent.KEYCODE_BACK:
                return true;
            case KeyEvent.KEYCODE_CALL:
                return true;
            case KeyEvent.KEYCODE_SYM:
                return true;
            case KeyEvent.KEYCODE_VOLUME_DOWN:
                return true;
            case KeyEvent.KEYCODE_VOLUME_UP:
                return true;
            case KeyEvent.KEYCODE_STAR:
                return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
    }

}
