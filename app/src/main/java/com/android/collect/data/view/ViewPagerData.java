package com.android.collect.data.view;

import com.android.collect.bean.LearnDataDetail;
import com.android.collect.data.BaseData;

import java.util.ArrayList;

/**
 * 轮播
 * Created by qinshunan on 2017/9/28.
 */

public class ViewPagerData extends BaseData {
    @Override
    public ArrayList<LearnDataDetail> getData() {
        ArrayList<LearnDataDetail> data = new ArrayList<>();

        data.add(new LearnDataDetail("MZBannerView", "仿魅族BannerView,图片轮播控件,支持多种模式切换",
                "https://github.com/pinguo-zhouwei/MZBannerView"));

        data.add(new LearnDataDetail("InfiniteCycleViewPager", "无限循环双向取向的ViewPager互动的效果",
                "https://github.com/Devlight/InfiniteCycleViewPager"));

        data.add(new LearnDataDetail("Banner", "Android广告图片轮播控件，支持无限循环和多种主题，可以灵活设置轮播样式、动画、轮播和切换时间、位置、图片加载框架等！",
                "https://github.com/youth5201314/banner"));

        data.add(new LearnDataDetail("ViewPagerCards", "ViewPager cards inspired by Duolingo",
                "https://github.com/rubensousa/ViewPagerCards"));

        return data;
    }
}
