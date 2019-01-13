package com.android.collect.library.ui.main.fragment;

import android.os.Bundle;

import com.android.collect.library.R;
import com.android.collect.library.base.BaseFragment;

/**
 * 首页
 * Created by anzai on 2017/7/11.
 */

public class HomePageFragment extends BaseFragment {

    private static HomePageFragment homePageFragment;

    public static HomePageFragment getInstance(Bundle args) {
        if (homePageFragment == null) {
            synchronized (HomePageFragment.class) {
                if (homePageFragment == null) {
                    homePageFragment = new HomePageFragment();
                    if (args != null) {
                        homePageFragment.setArguments(args);
                    }
                }
            }
        }
        return homePageFragment;
    }

    @Override
    protected int getCurrentView() {
        return R.layout.fragment_homepage;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void setData() {

    }
}
