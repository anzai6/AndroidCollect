package com.android.collect.ui.main.fragment;

import android.os.Bundle;

import com.android.collect.R;
import com.android.collect.library.base.BaseFragment;

/**
 * 首页
 * Created by anzai on 2017/7/11.
 */

public class ProviderLearnFragment extends BaseFragment {

    private static ProviderLearnFragment sOtherFragment;

    public static ProviderLearnFragment getInstance(Bundle args) {
        if (sOtherFragment == null) {
            synchronized (ProviderLearnFragment.class) {
                if (sOtherFragment == null) {
                    sOtherFragment = new ProviderLearnFragment();
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
        return R.layout.fragment_provider_learn;
    }

    @Override
    protected void initView() {
    }

    @Override
    protected void setData() {

    }
}
