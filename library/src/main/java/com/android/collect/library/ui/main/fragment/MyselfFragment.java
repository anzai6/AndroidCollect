package com.android.collect.library.ui.main.fragment;

import android.os.Bundle;

import com.android.collect.library.R;
import com.android.collect.library.base.BaseFragment;

/**
 * 贷款产品
 * Created by anzai on 2017/7/11.
 */

public class MyselfFragment extends BaseFragment {

    private static MyselfFragment myselfFragment;

    public static MyselfFragment getInstance(Bundle args) {
        if (myselfFragment == null) {
            synchronized (MyselfFragment.class) {
                if (myselfFragment == null) {
                    myselfFragment = new MyselfFragment();
                    if (args != null) {
                        myselfFragment.setArguments(args);
                    }
                }
            }
        }
        return myselfFragment;
    }

    @Override
    protected int getCurrentView() {
        return R.layout.fragment_myself;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void setData() {

    }
}
