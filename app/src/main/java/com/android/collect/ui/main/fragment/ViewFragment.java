package com.android.collect.ui.main.fragment;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.LinearLayout;

import com.android.collect.R;
import com.android.collect.data.view.ViewLearnData;
import com.android.collect.library.base.BaseTitleFragment;
import com.android.collect.library.common.Constant;
import com.android.collect.library.util.Util;
import com.android.collect.ui.main.adapter.CommonLearnRvAdapter;

/**
 * 首页
 * Created by anzai on 2017/7/11.
 */

public class ViewFragment extends BaseTitleFragment {

    private static ViewFragment sViewFragment;

    private RecyclerView mRecyclerView;
    private CommonLearnRvAdapter mAdapter;

    public static ViewFragment getInstance(Bundle args) {
        if (sViewFragment == null) {
            synchronized (ViewFragment.class) {
                if (sViewFragment == null) {
                    sViewFragment = new ViewFragment();
                    if (args != null) {
                        sViewFragment.setArguments(args);
                    }
                }
            }
        }
        return sViewFragment;
    }

    @Override
    protected int getCurrentView() {
        return R.layout.fragment_view;
    }

    @Override
    protected void initView() {
        initTitle(Constant.TITLE1, R.string.view_title);
        Util.setPaddingSmart(getActivity(), fl_mainHead);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getActivity(), LinearLayout.VERTICAL);
        dividerItemDecoration.setDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.shape_recyclerview_line_1));
        mRecyclerView.addItemDecoration(dividerItemDecoration);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    @Override
    protected void setData() {
        mAdapter = new CommonLearnRvAdapter(getActivity(), ViewLearnData.getData());
        mRecyclerView.setAdapter(mAdapter);
    }
}
