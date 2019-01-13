package com.android.collect.library.widget.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.collect.library.R;
import com.android.collect.library.common.Constant;
import com.android.collect.library.util.LogUtils;
import com.android.collect.library.util.Util;

import java.util.Timer;
import java.util.TimerTask;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

/**
 * 加载弹框
 */
public class LoadingDialog extends Dialog {

    private final String TAG = LogUtils.getLogTag(LoadingDialog.class);

    private Activity mActivity;
    private ImageView iv_loading;
    private TextView tv_loading;

    private Timer mTimer;
    private TimerTask mTimerTask;
    private Disposable mDisposable;
    private Observable<Long> mObservable;

    public LoadingDialog(final Activity context) {
        super(context, R.style.dialog_loading);
        mActivity = Util.checkNotNull(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(LayoutParams.FLAG_FULLSCREEN,
                LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.dialog_loading);
        tv_loading = (TextView) findViewById(R.id.tv_loading);
        iv_loading = (ImageView) findViewById(R.id.iv_loading);

        // 加载动画
        Animation hyperspaceJumpAnimation = AnimationUtils.loadAnimation(
                mActivity, R.anim.anim_dialog_loading);
        // 使用ImageView显示动画
        iv_loading.startAnimation(hyperspaceJumpAnimation);

        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.dimAmount = 0.3f;// 黑暗度
        getWindow().setAttributes(lp);

        setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                clearTimer();
            }
        });

    }

    private void clearTimer() {
        if (mTimer != null) {
            mTimer.cancel();
            mTimer = null;
        }
        if (mTimerTask != null) {
            mTimerTask.cancel();
            mTimerTask = null;
        }
    }

    @Override
    public void dismiss() {
        super.dismiss();
        clearTimer();
        if (mDisposable != null && !mDisposable.isDisposed()) {
            mDisposable.dispose();
            mDisposable = null;
        }
        if (mObservable != null) {
            mObservable.unsubscribeOn(AndroidSchedulers.mainThread());
            mObservable = null;
        }
    }

    @Override
    public void show() {
        super.show();
        if (mTimer == null) {
            mTimer = new Timer();
            mTimerTask = new TimerTask() {
                @Override
                public void run() {
                    dismiss();

                }
            };
            mTimer.schedule(mTimerTask, Constant.DIALOG_TIMEOUT);
        }

    }

    public Activity getActivity() {
        return mActivity;
    }

    public void onDestroy() {
        if (isShowing())
            dismiss();
    }
}
