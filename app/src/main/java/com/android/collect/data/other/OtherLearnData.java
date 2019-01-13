package com.android.collect.data.other;

import com.android.collect.bean.LearnDataDetail;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by qinshunan on 2017/9/28.
 */

public class OtherLearnData {

    public static List<LearnDataDetail> getData() {
        List<LearnDataDetail> data = new ArrayList<>();

        data.add(new LearnDataDetail("好的开源库", new OpenLibraryData()));
        data.add(new LearnDataDetail("好的开源项目", new OpenProjectData()));
        data.add(new LearnDataDetail("动画", null));
        data.add(new LearnDataDetail("键盘", new KeyboardData()));
        data.add(new LearnDataDetail("安全", new SaveData()));
        data.add(new LearnDataDetail("优化", new OptimizationData()));

        return data;
    }

}
