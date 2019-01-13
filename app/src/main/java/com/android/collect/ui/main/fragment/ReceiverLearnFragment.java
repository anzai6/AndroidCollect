package com.android.collect.ui.main.fragment;

import android.os.Bundle;

import com.android.collect.R;
import com.android.collect.library.base.BaseFragment;

/**
 * 首页
 * Created by anzai on 2017/7/11.
 */

public class ReceiverLearnFragment extends BaseFragment {

    private static ReceiverLearnFragment sOtherFragment;

    public static ReceiverLearnFragment getInstance(Bundle args) {
        if (sOtherFragment == null) {
            synchronized (ReceiverLearnFragment.class) {
                if (sOtherFragment == null) {
                    sOtherFragment = new ReceiverLearnFragment();
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
        return R.layout.fragment_receiver_learn;
    }

    @Override
    protected void initView() {
    }

    @Override
    protected void setData() {

    }
}
