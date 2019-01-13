package com.android.collect.data.view;

import com.android.collect.bean.LearnDataDetail;
import com.android.collect.data.BaseData;

import java.util.ArrayList;

/**
 * 键盘
 * Created by qinshunan on 2017/9/28.
 */
public class WebViewData extends BaseData {
    @Override
    public ArrayList<LearnDataDetail> getData() {
        ArrayList<LearnDataDetail> data = new ArrayList<>();

        data.add(new LearnDataDetail("WebView键盘遮挡", "WebView中软键盘会遮挡输入框",
                "http://blog.csdn.net/qq_22274713/article/details/78180871"));

        return data;
    }
}
