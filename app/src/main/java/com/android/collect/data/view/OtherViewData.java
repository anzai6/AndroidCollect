package com.android.collect.data.view;

import com.android.collect.bean.LearnDataDetail;
import com.android.collect.data.BaseData;
import com.android.collect.ui.view.other_view.activity.AutoCompleteTvTestActivity;
import com.android.collect.ui.view.other_view.activity.CircleProgressTestActivity;
import com.android.collect.ui.view.other_view.activity.DragViewTestActivity;
import com.android.collect.ui.view.other_view.activity.IndexBarTestActivity;
import com.android.collect.ui.view.other_view.activity.SixGridPswTestActivity;
import com.android.collect.ui.view.other_view.praise.PraiseListViewTestActivity;

import java.util.ArrayList;

/**
 * Created by qinshunan on 2017/9/28.
 */
public class OtherViewData extends BaseData {
    @Override
    public ArrayList<LearnDataDetail> getData() {
        ArrayList<LearnDataDetail> data = new ArrayList<>();

        data.add(new LearnDataDetail("点赞控件", "文字点赞",
                PraiseListViewTestActivity.class.getName(), ""));

        data.add(new LearnDataDetail("六格密码输入框", "仿支付宝微信六格密码输入框",
                SixGridPswTestActivity.class.getName(), "https://github.com/lygttpod/AndroidCustomView"));

        data.add(new LearnDataDetail("圆形进度条", "可两种颜色渐变的圆形进度条",
                    CircleProgressTestActivity.class.getName(), "https://github.com/MyLifeMyTravel/CircleProgress"));

        data.add(new LearnDataDetail("AutoCompleteTextView", "输入框自动补全",
                AutoCompleteTvTestActivity.class.getName(), ""));

        data.add(new LearnDataDetail("DragView", "悬浮的可拖动的控件",
                DragViewTestActivity.class.getName(), ""));

        data.add(new LearnDataDetail("IndexBar", "右侧索引导航栏",
                IndexBarTestActivity.class.getName(), ""));

        return data;
    }
}
