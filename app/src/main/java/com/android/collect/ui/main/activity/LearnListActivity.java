package com.android.collect.ui.main.activity;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.LinearLayout;

import com.android.collect.R;
import com.android.collect.bean.LearnDataDetail;
import com.android.collect.library.base.SwipeBackTitleActivity;
import com.android.collect.library.common.Constant;
import com.android.collect.library.common.KeyHelper;
import com.android.collect.library.util.Util;
import com.android.collect.ui.main.adapter.CommonLearnRvAdapter;

import java.util.ArrayList;

/**
 * 数据列表
 */
public class LearnListActivity extends SwipeBackTitleActivity {

    private RecyclerView mRecyclerView;
    private CommonLearnRvAdapter mAdapter;

    private Bundle mBundle;

    @Override
    protected void setCurrentView() {
        setContentView(R.layout.activity_learn_list);
        initTitle(Constant.TITLE1, "");
    }

    @Override
    protected void initView() {
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, LinearLayout.VERTICAL);
        dividerItemDecoration.setDrawable(ContextCompat.getDrawable(this, R.drawable.shape_recyclerview_line_1));
        mRecyclerView.addItemDecoration(dividerItemDecoration);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    @Override
    protected void setData() {
        mBundle = getIntent().getExtras();
        String title = Util.getBundleString(mBundle, KeyHelper.Title, "");
        ArrayList<LearnDataDetail> data = mBundle.getParcelableArrayList(KeyHelper.List);
        if (!Util.isListEmpty(data)) {
            mAdapter = new CommonLearnRvAdapter(this, data);
            mRecyclerView.setAdapter(mAdapter);
        }

        setTitle(title);

    }

}
