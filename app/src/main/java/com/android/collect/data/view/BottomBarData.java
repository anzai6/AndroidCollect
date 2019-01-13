package com.android.collect.data.view;

import com.android.collect.bean.LearnDataDetail;
import com.android.collect.data.BaseData;

import java.util.ArrayList;

/**
 * 状态栏
 * Created by qinshunan on 2017/9/28.
 */

public class BottomBarData extends BaseData {
    @Override
    public ArrayList<LearnDataDetail> getData() {
        ArrayList<LearnDataDetail> data = new ArrayList<>();
        data.add(new LearnDataDetail("BottomBar（加）", "Material Design BottomBar",
                "https://github.com/roughike/BottomBar"));
        return data;
    }
}
