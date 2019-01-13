package com.android.collect.data.other;

import com.android.collect.bean.LearnDataDetail;
import com.android.collect.data.BaseData;

import java.util.ArrayList;

/**
 * 开源库
 * Created by qinshunan on 2017/9/28.
 */
public class OpenLibraryData extends BaseData {
    @Override
    public ArrayList<LearnDataDetail> getData() {
        ArrayList<LearnDataDetail> data = new ArrayList<>();

        data.add(new LearnDataDetail("BGASwipeBackLayout（加）", "Android Activity 滑动返回",
                "https://github.com/bingoogolapple/BGASwipeBackLayout-Android"));

        data.add(new LearnDataDetail("VasSonic（加）", "提升H5首屏加载速度",
                "https://github.com/Tencent/VasSonic"));

        return data;
    }
}
