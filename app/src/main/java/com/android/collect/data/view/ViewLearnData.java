package com.android.collect.data.view;

import com.android.collect.bean.LearnDataDetail;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by qinshunan on 2017/9/28.
 */

public class ViewLearnData {

    public static List<LearnDataDetail> getData() {
        List<LearnDataDetail> data = new ArrayList<>();

        data.add(new LearnDataDetail("状态栏", new StatusBarData()));
        data.add(new LearnDataDetail("底部导航栏", new BottomBarData()));
        data.add(new LearnDataDetail("TabLayout", new TabLayoutData()));
        data.add(new LearnDataDetail("ViewPager", new ViewPagerData()));
        data.add(new LearnDataDetail("OtherView", new OtherViewData()));

        return data;
    }

}
