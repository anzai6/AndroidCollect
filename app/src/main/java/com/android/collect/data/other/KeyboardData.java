package com.android.collect.data.other;

import com.android.collect.bean.LearnDataDetail;
import com.android.collect.data.BaseData;
import com.android.collect.ui.other.keyboard.KeyBoardTestActivity;

import java.util.ArrayList;

/**
 * 键盘
 * Created by qinshunan on 2017/9/28.
 */
public class KeyboardData extends BaseData {
    @Override
    public ArrayList<LearnDataDetail> getData() {
        ArrayList<LearnDataDetail> data = new ArrayList<>();

        data.add(new LearnDataDetail("WebView键盘遮挡", "WebView中软键盘会遮挡输入框",
                "https://anzai6.github.io/2017/10/12/WebView中软键盘会遮挡输入框/"));
        data.add(new LearnDataDetail("键盘弹出和收起监听", "键盘弹出和收起监听",
                KeyBoardTestActivity.class.getName(), "https://github.com/yescpu/KeyboardChangeListener"));

        return data;
    }
}
