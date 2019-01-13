package com.android.collect.ui.main.activity;

import android.support.annotation.IdRes;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.view.KeyEvent;
import android.view.View;

import com.android.collect.R;
import com.android.collect.library.base.BaseActivity;
import com.android.collect.library.ui.main.adapter.ViewPagerFragmentAdapter;
import com.android.collect.library.util.AllActivityManager;
import com.android.collect.library.util.SnackBarUtils;
import com.android.collect.library.util.Util;
import com.android.collect.ui.main.fragment.FrameFragment;
import com.android.collect.ui.main.fragment.HomeFragment;
import com.android.collect.ui.main.fragment.OtherFragment;
import com.android.collect.ui.main.fragment.ViewFragment;
import com.jaeger.library.StatusBarUtil;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

import java.util.ArrayList;
import java.util.List;

public class MyMainActivity extends BaseActivity {

    private BottomBar mBottomBar;

    private ViewPager mViewPager;
    private List<Fragment> mFragmentList;

    private HomeFragment mHomeFragment;
    private ViewFragment mViewFragment;
    private FrameFragment mFrameFragment;
    private OtherFragment mOtherFragment;

    private DrawerLayout mDrawerLayout;
    private NavigationView mNavigationView;

    private long mExitTime;

    @Override
    protected void setCurrentView() {
        setContentView(R.layout.activity_my_main);
    }

    @Override
    protected void initView() {
        mBottomBar = (BottomBar) findViewById(R.id.bottomBar);
        mViewPager = (ViewPager) findViewById(R.id.vp_main);
        // 不知为何得加上延迟执行这段代码才有透明效果
        mViewPager.postDelayed(new Runnable() {
            @Override
            public void run() {
                StatusBarUtil.setTranslucentForImageViewInFragment(MyMainActivity.this, null);
            }
        },10);
        initDrawerLayout();

        mBottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(@IdRes int tabId) {
                switch (tabId) {
                    case R.id.tab_home:
                        mViewPager.setCurrentItem(0, false);
                        break;

                    case R.id.tab_view:
                        mViewPager.setCurrentItem(1, false);
                        break;

                    case R.id.tab_frame:
                        mViewPager.setCurrentItem(2, false);
                        break;

                    case R.id.tab_other:
                        mViewPager.setCurrentItem(3, false);
                        break;

                    default:

                        break;

                }
            }
        });
    }

    /**
     * inflateHeaderView 进来的布局要宽一些
     */
    private void initDrawerLayout() {
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        mNavigationView = (NavigationView) findViewById(R.id.navigationView);
        mNavigationView.inflateHeaderView(R.layout.layout_main_nav);
        View headerView = mNavigationView.getHeaderView(0);
        headerView.findViewById(R.id.ll_navPersonalInfo).setOnClickListener(mNavListener);
        headerView.findViewById(R.id.ll_navSetting).setOnClickListener(mNavListener);
        headerView.findViewById(R.id.ll_navAboutUs).setOnClickListener(mNavListener);
        headerView.findViewById(R.id.ll_navShare).setOnClickListener(mNavListener);
        headerView.findViewById(R.id.ll_navExitApp).setOnClickListener(mNavListener);

    }

    View.OnClickListener mNavListener = new View.OnClickListener() {
        @Override
        public void onClick(final View v) {
            // 关闭抽屉
            mDrawerLayout.closeDrawer(GravityCompat.START);
            mDrawerLayout.postDelayed(new Runnable() {
                @Override
                public void run() {
                    switch (v.getId()) {
                        case R.id.ll_navPersonalInfo:

                            break;

                        case R.id.ll_navSetting:

                            break;

                        case R.id.ll_navAboutUs:

                            break;

                        case R.id.ll_navShare:

                            break;

                        case R.id.ll_navExitApp:

                            break;

                        default:

                            break;

                    }

                }
            }, 260);
        }
    };

    @Override
    protected void setData() {
        mFragmentList = new ArrayList<>();
        if (mHomeFragment == null)
            mHomeFragment = HomeFragment.getInstance(null);
        if (mViewFragment == null)
            mViewFragment = ViewFragment.getInstance(null);
        if (mFrameFragment == null)
            mFrameFragment = FrameFragment.getInstance(null);
        if (mOtherFragment == null)
            mOtherFragment = OtherFragment.getInstance(null);
        mFragmentList.add(mHomeFragment);
        mFragmentList.add(mViewFragment);
        mFragmentList.add(mFrameFragment);
        mFragmentList.add(mOtherFragment);

        mViewPager.setAdapter(new ViewPagerFragmentAdapter(getSupportFragmentManager(), mFragmentList));
        mViewPager.setOffscreenPageLimit(mFragmentList.size());
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
                mDrawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }

            if ((System.currentTimeMillis() - mExitTime) > 800) {
                SnackBarUtils.showTip(mViewPager, com.android.collect.library.R.string.exit_app_tip);
                mExitTime = System.currentTimeMillis();

            } else {
                AllActivityManager.getInstance().clearActivityStack();
                finish();
                Util.exitApp();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
