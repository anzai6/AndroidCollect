package com.android.collect.data.other;

import com.android.collect.bean.LearnDataDetail;
import com.android.collect.data.BaseData;
import com.android.collect.library.common.FileConstant;

import java.util.ArrayList;

/**
 * 优化
 * Created by qinshunan on 2017/9/28.
 */
public class OptimizationData extends BaseData {
    @Override
    public ArrayList<LearnDataDetail> getData() {
        ArrayList<LearnDataDetail> data = new ArrayList<>();

        data.add(new LearnDataDetail("布局优化", "布局优化",
                FileConstant.APP_ASSET_DIR + "other/优化/布局优化.html"));

        data.add(new LearnDataDetail("小知识", "小知识",
                FileConstant.APP_ASSET_DIR + "other/优化/Android 小知识.html"));

        return data;
    }
}
