package com.android.collect.ui.splash;

import android.Manifest;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.collect.R;
import com.android.collect.library.base.BaseActivity;
import com.android.collect.library.callback.OnPermissionCallBack;
import com.android.collect.library.http.update.UpdateIsFinish;
import com.android.collect.library.http.update.UpdateManager;
import com.android.collect.library.manager.PermissionManager;
import com.android.collect.library.util.ActUtil;
import com.android.collect.library.util.BitmapUtil;
import com.android.collect.library.util.EmulatorCheckUtil;
import com.android.collect.library.util.JSONUtil;
import com.android.collect.library.util.Util;
import com.android.collect.ui.main.activity.MyMainActivity;
import com.jaeger.library.StatusBarUtil;

import org.json.JSONObject;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class MySplashActivity extends BaseActivity {

    private ImageView iv_bg;
    private LinearLayout ll_loading, ll_skip;
    private ImageView iv_loading;
    private Animation hyperspaceJumpAnimation;
    private TextView tv_loading, tvTime;

    private PermissionManager mPermissionManager;

    /**
     * 数据是否准备就绪
     */
    private boolean isDataPrepare = false;

    private Disposable mDisposable;

    /**
     * 多少秒后跳转下一页
     */
    private int SKIP_PERIOD = 1;

    private Bundle bundle;

    @Override
    protected void setCurrentView() {
        setContentView(R.layout.activity_my_splash);
        StatusBarUtil.setTranslucentForImageView(this, null);
        if (EmulatorCheckUtil.isEmulator(this)) { // 不能运行在模拟器
            finish();
            Util.exitApp();
        }
    }

    @Override
    protected void initView() {
        iv_bg = (ImageView) findViewById(R.id.iv_bg);
        iv_bg.setImageBitmap(BitmapUtil.decodeResource(this, R.drawable.ic_my_splash_bg));

        iv_loading = (ImageView) findViewById(R.id.iv_loading);
        tv_loading = (TextView) findViewById(R.id.tv_loading);
        ll_loading = (LinearLayout) findViewById(R.id.ll_loading);


        ll_skip = (LinearLayout) findViewById(R.id.ll_skip);
        tvTime = (TextView) findViewById(R.id.tv_time);

        hyperspaceJumpAnimation = AnimationUtils.loadAnimation(MySplashActivity.this, R.anim.anim_dialog_loading);
        iv_loading.startAnimation(hyperspaceJumpAnimation);

        ll_skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isDataPrepare) {
                    toTarget();
                }
            }
        });
    }

    @Override
    protected void setData() {
        bundle = getIntent().getExtras();
        if (bundle == null)
            bundle = new Bundle();

        mPermissionManager = new PermissionManager(this);
        mPermissionManager.requestPermissions(100, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_PHONE_STATE}, new OnPermissionCallBack() {
            @Override
            public void onSuccess(int requestCode) {
                update();
            }

            @Override
            public void onError(int requestCode) {
                finish();
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @android.support.annotation.NonNull String[] permissions, @android.support.annotation.NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        mPermissionManager.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    /**
     * 检测更新
     */
    private void update() {
        UpdateManager manager = new UpdateManager(this, new UpdateIsFinish() {

            @Override
            public void isSucceed(JSONObject jsonObject) {
                dataPrepare(jsonObject);
            }

            @Override
            public void isFailed() {
            }
        });
        manager.checkUpdate();
    }

    /**
     * 数据准备
     */
    private void dataPrepare(final JSONObject jsonObject) {
        tv_loading.setText(R.string.update_prepare_data);
        Observable.interval(0, 1, TimeUnit.SECONDS)
                .map(new Function<Long, Long>() {
                    @Override
                    public Long apply(@NonNull Long aLong) throws Exception {
                        if (!isDataPrepare) {
                            String str = JSONUtil.getString(jsonObject, "");

                            isDataPrepare = true;
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    iv_loading.clearAnimation();
                                    ll_loading.setVisibility(View.GONE);
                                }
                            });
                        }
                        return SKIP_PERIOD - aLong;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        mDisposable = d;
                    }

                    @Override
                    public void onNext(@NonNull Long aLong) {
                        tvTime.setText(aLong + "");
                        if (aLong == 0) {
                            toTarget();
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void toTarget() {
        if (mDisposable != null && !mDisposable.isDisposed())
            mDisposable.dispose();
        ActUtil.startMyActivity(MySplashActivity.this, MyMainActivity.class, bundle);
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            return true;
        } else {
            return super.onKeyDown(keyCode, event);
        }
    }

    @Override
    protected void onDestroy() {
        BitmapUtil.releaseImageViewResouce(iv_bg);
        super.onDestroy();
    }

}
