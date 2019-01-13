package com.android.collect.data.view;

import com.android.collect.bean.LearnDataDetail;
import com.android.collect.data.BaseData;

import java.util.ArrayList;

/**
 * 状态栏
 * Created by qinshunan on 2017/9/28.
 */

public class TabLayoutData extends BaseData {
    @Override
    public ArrayList<LearnDataDetail> getData() {
        ArrayList<LearnDataDetail> data = new ArrayList<>();

        data.add(new LearnDataDetail("FlycoTabLayout（加）", "顶部或者底部导航栏和左右滑动切换Tablayout",
                "https://github.com/H07000223/FlycoTabLayout"));

        data.add(new LearnDataDetail("CoordinatorTabLayout", "TabLayout和CoordinatorLayout相结合的折叠控件",
                "https://github.com/hugeterry/CoordinatorTabLayout"));

        return data;
    }
}
