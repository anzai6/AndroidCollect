package com.android.collect.library.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;

import com.android.collect.library.R;
import com.android.collect.library.base.BaseActivity;
import com.android.collect.library.common.KeyHelper;
import com.android.collect.library.ui.main.adapter.ViewPagerFragmentAdapter;
import com.android.collect.library.ui.main.contract.MainActivityContract;
import com.android.collect.library.ui.main.fragment.HomePageFragment;
import com.android.collect.library.ui.main.fragment.LoanProductFragment;
import com.android.collect.library.ui.main.fragment.MyselfFragment;
import com.android.collect.library.ui.main.presenter.MainActivityPresenter;
import com.android.collect.library.util.AllActivityManager;
import com.android.collect.library.util.SnackBarUtils;
import com.android.collect.library.util.Util;
import com.jaeger.library.StatusBarUtil;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity implements MainActivityContract.View {

    public ViewPager vp_main;
    public static int CurrentPage;
    private List<Fragment> fragments;

    protected FragmentTransaction fragmentTrans;
    private HomePageFragment mHomePageFragment;
    private LoanProductFragment mLoanProductFragment;
    private MyselfFragment mMyselfFragment;

    private LinearLayout ll_homepage;
    private LinearLayout ll_loan_product;
    private LinearLayout ll_myself;
    private LinearLayout[] ll_bottomMenu;

    private MainActivityContract.Presenter mMainActivityPresenter;
    private long mExitTime;

    @Override
    protected void setCurrentView() {
        StatusBarUtil.setColor(this, ContextCompat.getColor(this, R.color.colorPrimaryDark));
        setContentView(R.layout.activity_main);
    }


    @Override
    protected void initView() {
        vp_main = (ViewPager) findViewById(R.id.vp_main);
        vp_main.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int arg0) {
                changeBottomMenuState(ll_bottomMenu, arg0);
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {

            }

            @Override
            public void onPageScrollStateChanged(int arg0) {

            }
        });
        initFragment();
        vp_main.setAdapter(new ViewPagerFragmentAdapter(
                getSupportFragmentManager(), fragments));
        initBottomMenu();
        vp_main.setCurrentItem(0, false);
        vp_main.setOffscreenPageLimit(ll_bottomMenu.length);
        changeBottomMenuState(ll_bottomMenu, 0);
    }

    /**
     * 初始化底部菜单
     */
    private void initBottomMenu() {

        ll_homepage = (LinearLayout) findViewById(R.id.ll_homepage);
        ll_loan_product = (LinearLayout) findViewById(R.id.ll_loan_product);
        ll_myself = (LinearLayout) findViewById(R.id.ll_myself);

        // 存放底部的四个按钮
        ll_bottomMenu = new LinearLayout[]{ll_homepage, ll_loan_product, ll_myself};

        // 为按钮设置点击事件
        ll_homepage.setOnClickListener(bottomMenuClickListener);
        ll_loan_product.setOnClickListener(bottomMenuClickListener);
        ll_myself.setOnClickListener(bottomMenuClickListener);

    }

    @Override
    protected void setData() {
        mMainActivityPresenter = new MainActivityPresenter(this, this);
    }

    private void initFragment() {
        fragments = new ArrayList<Fragment>();
        if (mHomePageFragment == null)
            mHomePageFragment = HomePageFragment.getInstance(null);
        fragments.add(mHomePageFragment);
        if (mLoanProductFragment == null)
            mLoanProductFragment = mLoanProductFragment.getInstance(null);
        fragments.add(mLoanProductFragment);
        if (mMyselfFragment == null)
            mMyselfFragment = mMyselfFragment.getInstance(null);
        fragments.add(mMyselfFragment);
    }

    /**
     * 底部菜单按钮点击监听器
     */
    public View.OnClickListener bottomMenuClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            int i = v.getId();
            if (i == R.id.ll_homepage) {
                vp_main.setCurrentItem(0, false);
                CurrentPage = 0;

            } else if (i == R.id.ll_loan_product) {
                vp_main.setCurrentItem(1, false);
                CurrentPage = 1;

            } else if (i == R.id.ll_myself) {
                vp_main.setCurrentItem(2, false);
                CurrentPage = 1;
//                    String NoAcctStatus = SharedPreferencesUtil.getString(MainActivity.this,
//                            KeyHelper.NoAcctStatus, "");
//                    if (UserManager.isLogin && "0".equals(NoAcctStatus)) {// 登录且开户
//                        vp_main.setCurrentItem(3, false);
//                        CurrentPage=3;
//                        tintManager.setStatusBarTintResource(R.color.colorPrimary);
//                        myselfFragment.getLoginData();
//                    } else {
//                        Bundle bundle = new Bundle();
//                        bundle.putString(KeyHelper.CheckPage, Constant.CHECK_MYSELF);
//                        ActUtil.startActivityNeedLoginAndOpen(MainActivity.this,
//                                MainActivity.class, bundle);
//                    }


            }
        }
    };

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {

            if ((System.currentTimeMillis() - mExitTime) > 800) {
                SnackBarUtils.showTip(vp_main, R.string.exit_app_tip);
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

    /**
     * 根据currentFragment改变底部菜单状态
     */
    public static void changeBottomMenuState(LinearLayout[] ll_bottomMenu,
                                             int index) {
        for (int i = 0; i < ll_bottomMenu.length; i++) {// 遍历底部按钮数组
            int childCount = ll_bottomMenu[i].getChildCount();
            if (i == index) {
                // 如果当前按钮的标签与当前碎片标识相符，则遍历该按钮子控件
                for (int j = 0; j < childCount; j++) {
                    ll_bottomMenu[i].getChildAt(j).setSelected(true);// 将各个子控件的selected属性设为true
                    ll_bottomMenu[i].setSelected(true);
                }
            } else {// 如果当前按钮的标签与当前碎片标识不相符，则遍历该按钮子控件
                for (int j = 0; j < childCount; j++) {
                    ll_bottomMenu[i].getChildAt(j).setSelected(false);// 将各个子控件的selected属性设为false
                    ll_bottomMenu[i].setSelected(false);
                }
            }
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Bundle newBundle = intent.getExtras();
        String check_page = Util.getBundleString(newBundle, KeyHelper.CheckPage, "");
//        if(Constant.CHECK_MYSELF.equals(check_page)){//选择我的
//            ll_myself.performClick();
//        } else if(Constant.CHECK_HOMEPAGE.equals(check_page)){//选择首页
//            ll_homepage.performClick();
//        } else {
//            OnNewIntentManager.toTarget(this, newBundle);
//        }


    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMainActivityPresenter.unSubscribe();
    }

    @Override
    public void onLoginSuccess() {

    }

    @Override
    public void onLoginOut() {

    }

}
