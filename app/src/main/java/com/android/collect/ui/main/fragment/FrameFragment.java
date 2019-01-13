package com.android.collect.ui.main.fragment;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.LinearLayout;

import com.android.collect.R;
import com.android.collect.data.frame.FrameLearnData;
import com.android.collect.library.base.BaseTitleFragment;
import com.android.collect.library.common.Constant;
import com.android.collect.library.util.Util;
import com.android.collect.ui.main.adapter.CommonLearnRvAdapter;

/**
 * 首页
 * Created by anzai on 2017/7/11.
 */

public class FrameFragment extends BaseTitleFragment {

    private static FrameFragment sFrameFragment;

    private RecyclerView mRecyclerView;
    private CommonLearnRvAdapter mAdapter;

    public static FrameFragment getInstance(Bundle args) {
        if (sFrameFragment == null) {
            synchronized (FrameFragment.class) {
                if (sFrameFragment == null) {
                    sFrameFragment = new FrameFragment();
                    if (args != null) {
                        sFrameFragment.setArguments(args);
                    }
                }
            }
        }
        return sFrameFragment;
    }

    @Override
    protected int getCurrentView() {
        return R.layout.fragment_frame;
    }

    @Override
    protected void initView() {
        initTitle(Constant.TITLE1, R.string.frame_title);
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
        mAdapter = new CommonLearnRvAdapter(getActivity(), FrameLearnData.getData());
        mRecyclerView.setAdapter(mAdapter);
    }
}
