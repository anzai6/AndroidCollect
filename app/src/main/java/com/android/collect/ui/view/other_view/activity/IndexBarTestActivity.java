package com.android.collect.ui.view.other_view.activity;

import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.collect.R;
import com.android.collect.bean.IndexBarDetail;
import com.android.collect.library.base.SwipeBackTitleActivity;
import com.android.collect.library.common.Constant;
import com.android.collect.library.util.JSONUtil;
import com.android.collect.library.util.Util;
import com.android.collect.ui.view.other_view.adapter.IndexBarRvAdapter;
import com.mcxtzhang.indexlib.IndexBar.widget.IndexBar;
import com.mcxtzhang.indexlib.suspension.SuspensionDecoration;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by anzai on 2017/7/13.
 * 右侧索引导航栏
 */
public class IndexBarTestActivity extends SwipeBackTitleActivity {

    private RecyclerView mRecyclerView;
    private IndexBarRvAdapter mAdapter;
    private LinearLayoutManager mManager;
    private List<IndexBarDetail> mDatas;

    private SuspensionDecoration mDecoration;

    /**
     * 右侧边栏导航区域
     */
    private IndexBar mIndexBar;

    /**
     * 显示指示器DialogText
     */
    private TextView mTvSideBarHint;

    @Override
    protected void setCurrentView() {
        setContentView(R.layout.activity_index_bar_test);
        initTitle(Constant.TITLE1, R.string.index_bar_title, R.color.white);

    }

    @Override
    protected void initView() {

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        mRecyclerView.setLayoutManager(mManager = new LinearLayoutManager(this));

        mRecyclerView.addItemDecoration(mDecoration = new SuspensionDecoration(this, mDatas));
        //如果add两个，那么按照先后顺序，依次渲染。
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, LinearLayout.VERTICAL);
        dividerItemDecoration.setDrawable(ContextCompat.getDrawable(this, R.drawable.shape_recyclerview_line_1));
        mRecyclerView.addItemDecoration(dividerItemDecoration);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        //使用indexBar
        mTvSideBarHint = (TextView) findViewById(R.id.tvSideBarHint);//HintTextView
        mIndexBar = (IndexBar) findViewById(R.id.indexBar);//IndexBar

        //indexbar初始化
        mIndexBar.setmPressedShowTextView(mTvSideBarHint)//设置HintTextView
                .setNeedRealIndex(true)//设置需要真实的索引
                .setmLayoutManager(mManager);//设置RecyclerView的LayoutManager

    }

    @Override
    protected void setData() {
        mDatas = new ArrayList<>();

        JSONObject jsonObject = JSONUtil.parseAssetsJson(this, "support_bank.json");
        mDatas = IndexBarDetail.getList(jsonObject);
        if (!Util.isListEmpty(mDatas)) {
            mAdapter = new IndexBarRvAdapter(this, mDatas);
            mRecyclerView.setAdapter(mAdapter);

            mIndexBar.setmSourceDatas(mDatas)//设置数据
                    .invalidate();
            mDecoration.setmDatas(mDatas);
        }
    }

}
