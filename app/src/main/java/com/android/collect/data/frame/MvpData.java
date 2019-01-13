package com.android.collect.data.frame;

import com.android.collect.bean.LearnDataDetail;
import com.android.collect.data.BaseData;

import java.util.ArrayList;

/**
 * 键盘
 * Created by qinshunan on 2017/9/28.
 */
public class MvpData extends BaseData {
    @Override
    public ArrayList<LearnDataDetail> getData() {
        ArrayList<LearnDataDetail> data = new ArrayList<>();

        data.add(new LearnDataDetail("MVP介绍", "MVP 模式简单易懂的介绍方式",
                "http://kaedea.com/2015/10/11/android-mvp-pattern/"));

        return data;
    }
}
