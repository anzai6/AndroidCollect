package com.android.collect.data.frame;

import com.android.collect.bean.LearnDataDetail;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by qinshunan on 2017/9/28.
 */

public class FrameLearnData {

    public static List<LearnDataDetail> getData() {
        List<LearnDataDetail> data = new ArrayList<>();

        data.add(new LearnDataDetail("搭建Android框架", "从零开始搭建android框架系列", "http://www.jianshu.com/nb/3767449"));
        data.add(new LearnDataDetail("MVP模式", new MvpData()));

        return data;
    }

}
