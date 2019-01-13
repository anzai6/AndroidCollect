package com.android.collect.ui.main.fragment;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.widget.FrameLayout;

import com.android.collect.R;
import com.android.collect.library.base.BaseFragment;
import com.android.collect.library.listener.AppBarStateChangeListener;
import com.android.collect.library.util.DpPxUtils;
import com.android.collect.library.util.Util;
import com.flyco.tablayout.SlidingTabLayout;
import com.flyco.tablayout.listener.OnTabSelectListener;

import java.util.ArrayList;

/**
 * 首页
 * Created by anzai on 2017/7/11.
 */

public class HomeFragment extends BaseFragment {

    private static HomeFragment sHomeFragment;

    private SlidingTabLayout mSlidingTabLayout;
    private ViewPager mViewPager;
    private FrameLayout fl_tab;
    private CollapsingToolbarLayout mCollapsingToolbarLayout;
    private AppBarLayout mAppBarLayout;
    private ArrayList<Fragment> mFragmentList;

    private ActivityLearnFragment mActivityLearnFragment;
    private FragmentLearnFragment mFragmentLearnFragment;
    private ServiceLearnFragment mServiceLearnFragment;
    private ReceiverLearnFragment mReceiverLearnFragment;
    private ProviderLearnFragment mProviderLearnFragment;

    private AdvertFragment mAdvertFragment;
    private final String ADVERT_FRAGMENT_TAG = "Home_AdvertFragment";

    public static HomeFragment getInstance(Bundle args) {
        if (sHomeFragment == null) {
            synchronized (HomeFragment.class) {
                if (sHomeFragment == null) {
                    sHomeFragment = new HomeFragment();
                    if (args != null) {
                        sHomeFragment.setArguments(args);
                    }
                }
            }
        }
        return sHomeFragment;
    }

    @Override
    protected int getCurrentView() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initView() {
        mSlidingTabLayout = (SlidingTabLayout) view.findViewById(R.id.slidingTabLayout);
        mViewPager = (ViewPager) view.findViewById(R.id.vp_home);

        mCollapsingToolbarLayout = (CollapsingToolbarLayout) view.findViewById(R.id.collapsingToolbarLayout);
        mCollapsingToolbarLayout.setMinimumHeight(Util.getStatusBarHeight(getActivity()) + DpPxUtils.dip2px(getActivity(), 48));
        fl_tab = (FrameLayout) view.findViewById(R.id.fl_tab);
        Util.setPaddingSmart(getActivity(), fl_tab);
        mAppBarLayout = (AppBarLayout) view.findViewById(R.id.appBarLayout);
        mAppBarLayout.addOnOffsetChangedListener(new AppBarStateChangeListener() {
            @Override
            public void onStateChanged(AppBarLayout appBarLayout, State state) {
                switch (state) {
                    case EXPANDED: //展开状态
                        fl_tab.setBackgroundColor(ContextCompat.getColor(getActivity(),R.color.transparent));
                        break;

                    case COLLAPSED: // 折叠状态
                        fl_tab.setBackgroundColor(ContextCompat.getColor(getActivity(),R.color.colorPrimaryDark));
                        break;

                    case IDLE: //中间状态
                        fl_tab.setBackgroundColor(ContextCompat.getColor(getActivity(),R.color.transparent));
                        break;

                    default:

                        break;

                }
            }
        });

        mSlidingTabLayout.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {

            }

            @Override
            public void onTabReselect(int position) {

            }
        });
    }

    @Override
    protected void setData() {
        mFragmentList = new ArrayList<>();
        if (mActivityLearnFragment == null)
            mActivityLearnFragment = ActivityLearnFragment.getInstance(null);
        if (mFragmentLearnFragment == null)
            mFragmentLearnFragment = FragmentLearnFragment.getInstance(null);
        if (mServiceLearnFragment == null)
            mServiceLearnFragment = ServiceLearnFragment.getInstance(null);
        if (mReceiverLearnFragment == null)
            mReceiverLearnFragment = ReceiverLearnFragment.getInstance(null);
        if (mProviderLearnFragment == null)
            mProviderLearnFragment = ProviderLearnFragment.getInstance(null);

        mFragmentList.add(mActivityLearnFragment);
        mFragmentList.add(mFragmentLearnFragment);
        mFragmentList.add(mServiceLearnFragment);
        mFragmentList.add(mReceiverLearnFragment);
        mFragmentList.add(mProviderLearnFragment);
        String[] titles = getResources().getStringArray(R.array.array_home_title);
        mSlidingTabLayout.setViewPager(mViewPager, titles, getActivity(), mFragmentList);
        // 设置ViewPager切换无动画
        mSlidingTabLayout.setSnapOnTabClick(true);
        initAdvert();
    }

    /**
     * 广告
     */
    private void initAdvert() {
        FragmentManager fragmentManager = getActivity()
                .getSupportFragmentManager();
        mAdvertFragment = (AdvertFragment) fragmentManager
                .findFragmentByTag(ADVERT_FRAGMENT_TAG);
        if (mAdvertFragment == null)
            mAdvertFragment = AdvertFragment.newInstance(null);
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.fl_advertContainer, mAdvertFragment, ADVERT_FRAGMENT_TAG);
        transaction.commitAllowingStateLoss();
    }
}
