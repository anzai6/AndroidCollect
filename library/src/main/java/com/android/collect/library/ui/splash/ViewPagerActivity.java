package com.android.collect.library.ui.splash;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.android.collect.library.R;
import com.android.collect.library.base.BaseActivity;
import com.android.collect.library.common.KeyHelper;
import com.android.collect.library.ui.main.MainActivity;
import com.android.collect.library.util.ActUtil;
import com.android.collect.library.util.BitmapUtil;
import com.android.collect.library.util.DialogUtil;
import com.android.collect.library.util.SharedPreferencesUtil;
import com.jaeger.library.StatusBarUtil;

import java.util.ArrayList;
import java.util.List;


public class ViewPagerActivity extends BaseActivity {
    private ViewPager vp;
    private GuideViewPagerAdapter vpAdapter;
    private List<ImageView> views;
    // 引导图片资源
    private final int[] pics = {R.drawable.ic_guide_welcome0, R.drawable.ic_guide_welcome1,
            R.drawable.ic_guide_welcome2};

    private Bundle bundle;

    @Override
    protected void setCurrentView() {
        StatusBarUtil.setTranslucentDiff(this);
        setContentView(R.layout.activity_guide_viewpager);
    }

    @Override
    protected void initView() {
        views = new ArrayList<ImageView>();

        LinearLayout.LayoutParams mParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);

        // 初始化引导图片列表
        for (int i = 0; i < pics.length; i++) {
            try {
                ImageView iv = new ImageView(this);
                iv.setScaleType(ImageView.ScaleType.FIT_XY);
                iv.setLayoutParams(mParams);
                views.add(iv);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        vp = (ViewPager) findViewById(R.id.viewpager);

    }

    @Override
    protected void setData() {
        // 初始化Adapter
        vpAdapter = new GuideViewPagerAdapter(views);
        vp.setAdapter(vpAdapter);
        vp.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (vp.getCurrentItem() == 2) {
                    goToTarget();
                }
            }
        });
        ;

        bundle = getIntent().getExtras();
        if (bundle == null)
            bundle = new Bundle();
    }

    class GuideViewPagerAdapter extends PagerAdapter {
        // 界面列表
        private List<ImageView> views;

        public GuideViewPagerAdapter(List<ImageView> views) {
            this.views = views;
        }

        // 销毁position位置的界面
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            BitmapUtil.releaseImageViewResouce(views.get(position));
            View contentView = (View) object;
            container.removeView(contentView);
        }

        @Override
        public int getCount() {
            if (views != null) {
                return views.size();
            }

            return 0;
        }

        @Override
        public Object instantiateItem(ViewGroup container, final int position) {
            ImageView view = views.get(position);

            Bitmap bm = BitmapUtil.decodeResource(ViewPagerActivity.this, pics[position]);
            view.setImageBitmap(bm);

            view.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    if (position != views.size() - 1) {
                        return;
                    }
                    goToTarget();
                }
            });

            container.addView(view, 0);
            return views.get(position);
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return (arg0 == arg1);
        }
    }

    private void goToTarget() {
        DialogUtil.showLDialog(this);
        SharedPreferencesUtil.saveBoolean(ViewPagerActivity.this,
                KeyHelper.GuideStatue, false);
        ActUtil.startMyActivity(ViewPagerActivity.this, MainActivity.class, bundle);
        finish();
    }

    @Override
    protected void onDestroy() {
        for (int i = 0; i < views.size(); i++) {
            BitmapUtil.releaseImageViewResouce(views.get(i));
        }
        DialogUtil.dismissLDialog();
        System.gc();
        super.onDestroy();
    }
}
