package com.android.collect.library.base;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.collect.library.R;
import com.android.collect.library.common.Constant;
import com.android.collect.library.util.BitmapUtil;
import com.jaeger.library.StatusBarUtil;

/**
 * 带标题的父类
 */
public abstract class BaseTitleActivity extends BaseActivity {

    protected FrameLayout ll_back, ll_finish;
    protected ImageView iv_right, iv_logo, iv_left;
    protected TextView tv_title;
    protected FrameLayout fl_mainHead;

    /**
     * 是否是滑动返回界面的标题
     */
    protected boolean isSwipeBackTitle = false;

    protected void initTitle(int titleMode, int strId) {
        initTitle(titleMode, getString(strId));
    }

    protected void initTitle(int titleMode, String strId) {
        initTitle(titleMode, strId, R.color.colorPrimary);
    }

    protected void initTitle(int titleMode, int strId, int colorId) {
        initTitle(titleMode, getString(strId), colorId);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @SuppressLint("ResourceAsColor")
    protected void initTitle(int titleMode, String strId, int colorId) {

        ll_back = (FrameLayout) findViewById(R.id.ll_back);
        ll_finish = (FrameLayout) findViewById(R.id.ll_finish);
        tv_title = (TextView) findViewById(R.id.tv_title);
        fl_mainHead = (FrameLayout) findViewById(R.id.fl_mainHead);
        iv_logo = (ImageView) findViewById(R.id.iv_logo);
        iv_left = (ImageView) findViewById(R.id.iv_left);
        iv_right = (ImageView) findViewById(R.id.iv_right);

        TitleItemClickListener titleItemClickListener = new TitleItemClickListener();
        ll_back.setOnClickListener(titleItemClickListener);
        ll_finish.setOnClickListener(titleItemClickListener);

        switch (titleMode) {
            case Constant.TITLE0:// 显示图标
                tv_title.setVisibility(View.GONE);
                iv_logo.setVisibility(View.VISIBLE);
                break;
            case Constant.TITLE1:// 显示文字
                tv_title.setVisibility(View.VISIBLE);
                iv_logo.setVisibility(View.GONE);
                break;
            default:
                break;
        }

        if (colorId == R.color.colorPrimary) {// 蓝色标题
            setSystemBarColor(R.color.colorPrimary);
            tv_title.setTextColor(ContextCompat.getColor(this, R.color.text_white));
            iv_left.setImageBitmap(BitmapUtil.decodeResource(this, R.drawable.ic_back_white));

        } else if (colorId == R.color.white) {// 白色标题(默认)
            setSystemBarColor(R.color.white);
        } else if (colorId == R.color.transparent) {// 透明标题
            setSystemBarColor(R.color.transparent);
            iv_left.setImageBitmap(BitmapUtil.decodeResource(this, R.drawable.ic_back_white));

        } else {
        }

        setTitle(strId);
    }

    protected void setSystemBarColor(int colorId) {
        int mColor = ContextCompat.getColor(this, colorId);
        if (isSwipeBackTitle)
            StatusBarUtil.setColorForSwipeBack(this, mColor);
        else
            StatusBarUtil.setColor(this, mColor);
        if (fl_mainHead != null)
            fl_mainHead.setBackgroundColor(mColor);
    }

    protected void setTitle(String str) {
        tv_title.setText(str);
    }

    public class TitleItemClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            int i = v.getId();
            if (i == R.id.ll_back) {
                backClick();

            } else if (i == R.id.ll_finish) {
                rightClick();

            } else {
            }
        }
    }

    /**
     * 回退回调
     */
    protected void backClick() {
        onBackPressed();
    }

    /**
     * 返回首页回调
     */
    protected void rightClick() {
//		ActUtil.startMyActivity(this, SettingActivity.class);
    }

}
