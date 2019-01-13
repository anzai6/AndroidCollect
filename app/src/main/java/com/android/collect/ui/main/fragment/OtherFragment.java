package com.android.collect.ui.main.fragment;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.LinearLayout;

import com.android.collect.R;
import com.android.collect.data.other.OtherLearnData;
import com.android.collect.library.base.BaseTitleFragment;
import com.android.collect.library.common.Constant;
import com.android.collect.library.util.Util;
import com.android.collect.ui.main.adapter.CommonLearnRvAdapter;

/**
 * 首页
 * Created by anzai on 2017/7/11.
 */

public class OtherFragment extends BaseTitleFragment {

    private static OtherFragment sOtherFragment;

    private RecyclerView mRecyclerView;
    private CommonLearnRvAdapter mAdapter;

    public static OtherFragment getInstance(Bundle args) {
        if (sOtherFragment == null) {
            synchronized (OtherFragment.class) {
                if (sOtherFragment == null) {
                    sOtherFragment = new OtherFragment();
                    if (args != null) {
                        sOtherFragment.setArguments(args);
                    }
                }
            }
        }
        return sOtherFragment;
    }

    @Override
    protected int getCurrentView() {
        return R.layout.fragment_other;
    }

    @Override
    protected void initView() {
        initTitle(Constant.TITLE1, R.string.other_title);
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
        mAdapter = new CommonLearnRvAdapter(getActivity(), OtherLearnData.getData());
        mRecyclerView.setAdapter(mAdapter);
    }
}
