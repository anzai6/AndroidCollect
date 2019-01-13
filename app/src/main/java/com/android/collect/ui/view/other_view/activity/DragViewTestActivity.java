package com.android.collect.ui.view.other_view.activity;

import android.view.View;

import com.android.collect.R;
import com.android.collect.library.base.SwipeBackTitleActivity;
import com.android.collect.library.common.Constant;
import com.android.collect.library.util.SnackBarUtils;
import com.android.collect.widget.DragView;

/**
 * 悬浮的拖动控件
 */
public class DragViewTestActivity extends SwipeBackTitleActivity {

    private DragView mDragView;

    @Override
    protected void setCurrentView() {
        setContentView(R.layout.activity_drag_view_test);
        initTitle(Constant.TITLE1, R.string.drag_view_title);
    }

    @Override
    protected void initView() {
        mDragView = (DragView) findViewById(R.id.dragView);

        mDragView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SnackBarUtils.showTip(mDragView, "点击了");
            }
        });
    }

    @Override
    protected void setData() {
        mDragView.setImageResource(R.drawable.ic_default_avatar);
    }

}
