package com.android.collect.data.other;

import com.android.collect.bean.LearnDataDetail;
import com.android.collect.data.BaseData;

import java.util.ArrayList;

/**
 * 开源项目
 * Created by qinshunan on 2017/9/28.
 */

public class OpenProjectData extends BaseData {
    @Override
    public ArrayList<LearnDataDetail> getData() {
        ArrayList<LearnDataDetail> data = new ArrayList<>();
        data.add(new LearnDataDetail("AiYaGirl（阅）", "Retrofit + RxJava + MVP 架构 APP 体验代码家的干货集中营 Gank.io，福利多多，不容错过",
                "https://github.com/nanchen2251/AiYaGirl"));
        return data;
    }
}
