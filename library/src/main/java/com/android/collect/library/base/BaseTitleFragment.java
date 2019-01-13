package com.android.collect.library.base;

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
 * 携带标题的Fragment
 *
 * @author anzai
 */
public abstract class BaseTitleFragment extends BaseFragment {

    protected FrameLayout ll_back, ll_finish;
    protected ImageView iv_right, iv_logo, iv_left;
    protected TextView tv_title;
    protected FrameLayout fl_mainHead;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    protected void initTitle(int titleMode, int strId) {
        initTitle(titleMode, getString(strId));
    }

    protected void initTitle(int titleMode, String title) {
        initTitle(titleMode, title, R.color.colorPrimary);
    }

    protected void initTitle(int titleMode, int strId, int colorId) {
        initTitle(titleMode, getString(strId), colorId);
    }

    protected void initTitle(int titleMode, String title, int colorId) {

        ll_back = (FrameLayout) view.findViewById(R.id.ll_back);
        ll_finish = (FrameLayout) view.findViewById(R.id.ll_finish);
        tv_title = (TextView) view.findViewById(R.id.tv_title);
        fl_mainHead = (FrameLayout) view.findViewById(R.id.fl_mainHead);
        iv_logo = (ImageView) view.findViewById(R.id.iv_logo);
        iv_left = (ImageView) view.findViewById(R.id.iv_left);
        iv_right = (ImageView) view.findViewById(R.id.iv_right);

        ll_finish.setVisibility(View.VISIBLE);

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
            tv_title.setTextColor(ContextCompat.getColor(getActivity(), R.color.text_white));
            iv_left.setImageBitmap(BitmapUtil.decodeResource(getActivity(), R.drawable.ic_back_white));

        } else if (colorId == R.color.white) {// 白色标题(默认)

        } else if (colorId == R.color.transparent) {// 透明标题
            setSystemBarColor(R.color.transparent);
            iv_left.setImageBitmap(BitmapUtil.decodeResource(getActivity(), R.drawable.ic_back_white));

        } else {
        }

        setTitle(title);
    }

    protected void setTitle(String str) {
        tv_title.setText(str);
    }

    protected void setSystemBarColor(int colorId) {
        int mColor = ContextCompat.getColor(getActivity(), colorId);
        StatusBarUtil.setColor(getActivity(), mColor);
        if (fl_mainHead != null)
            fl_mainHead.setBackgroundColor(mColor);
    }

    public class TitleItemClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            int i = v.getId();
            if (i == R.id.ll_back) {
                backClick();

            } else if (i == R.id.ll_finish) {
                finishClick();

            } else {
            }
        }
    }

    /**
     * 个人中心
     */
    protected void backClick() {
//		ActUtil.startMyActivity(context, MyActivity.class);
    }

    /**
     * 设置
     */
    protected void finishClick() {
//		ActUtil.startMyActivity(context, SettingActivity.class);
    }

}
