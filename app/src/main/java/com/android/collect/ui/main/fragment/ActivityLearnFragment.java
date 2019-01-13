package com.android.collect.ui.main.fragment;

import android.os.Bundle;

import com.android.collect.R;
import com.android.collect.library.base.BaseFragment;

/**
 * 首页
 * Created by anzai on 2017/7/11.
 */

public class ActivityLearnFragment extends BaseFragment {

    private static ActivityLearnFragment sOtherFragment;

    public static ActivityLearnFragment getInstance(Bundle args) {
        if (sOtherFragment == null) {
            synchronized (ActivityLearnFragment.class) {
                if (sOtherFragment == null) {
                    sOtherFragment = new ActivityLearnFragment();
                    if (args != null) {
                        sOtherFragment.setArguments(args);
                    }
                }
            }
        }
        return sOtherFragment;
    }

    @Override
    protected int getCurrentView() {
        return R.layout.fragment_activity_learn;
    }

    @Override
    protected void initView() {
    }

    @Override
    protected void setData() {

    }
}
