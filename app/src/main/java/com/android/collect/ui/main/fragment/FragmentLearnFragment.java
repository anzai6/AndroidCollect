package com.android.collect.ui.main.fragment;

import android.os.Bundle;

import com.android.collect.R;
import com.android.collect.library.base.BaseFragment;

/**
 * 首页
 * Created by anzai on 2017/7/11.
 */

public class FragmentLearnFragment extends BaseFragment {

    private static FragmentLearnFragment sOtherFragment;

    public static FragmentLearnFragment getInstance(Bundle args) {
        if (sOtherFragment == null) {
            synchronized (FragmentLearnFragment.class) {
                if (sOtherFragment == null) {
                    sOtherFragment = new FragmentLearnFragment();
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
        return R.layout.fragment_fragment_learn;
    }

    @Override
    protected void initView() {
    }

    @Override
    protected void setData() {

    }
}
