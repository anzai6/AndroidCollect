package com.android.collect.ui.main.fragment;

import android.os.Bundle;

import com.android.collect.R;
import com.android.collect.library.base.BaseFragment;

/**
 * 首页
 * Created by anzai on 2017/7/11.
 */

public class ServiceLearnFragment extends BaseFragment {

    private static ServiceLearnFragment sOtherFragment;

    public static ServiceLearnFragment getInstance(Bundle args) {
        if (sOtherFragment == null) {
            synchronized (ServiceLearnFragment.class) {
                if (sOtherFragment == null) {
                    sOtherFragment = new ServiceLearnFragment();
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
        return R.layout.fragment_service_learn;
    }

    @Override
    protected void initView() {
    }

    @Override
    protected void setData() {

    }
}
