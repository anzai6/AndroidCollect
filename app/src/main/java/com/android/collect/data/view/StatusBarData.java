package com.android.collect.data.view;

import com.android.collect.bean.LearnDataDetail;
import com.android.collect.data.BaseData;

import java.util.ArrayList;

/**
 * 状态栏
 * Created by qinshunan on 2017/9/28.
 */

public class StatusBarData extends BaseData {
    @Override
    public ArrayList<LearnDataDetail> getData() {
        ArrayList<LearnDataDetail> data = new ArrayList<>();
        data.add(new LearnDataDetail("StatusBarUtil（加）", "这是一个为Android App 设置状态栏的工具类",
                "https://jaeger.itscoder.com/android/2016/03/27/statusbar-util.html"));
        return data;
    }
}
