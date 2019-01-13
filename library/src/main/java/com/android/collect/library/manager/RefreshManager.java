package com.android.collect.library.manager;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.View;

import com.android.collect.library.R;
import com.android.collect.library.callback.RefreshDataCallBack;
import com.android.collect.library.util.SnackBarUtils;
import com.android.collect.library.util.Util;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadmoreListener;

import java.util.List;


/**
 * 刷新和加载数据统一管理类
 * Created by anzai on 2017/4/20.
 */

public class RefreshManager {

    private SmartRefreshLayout mSmartRefreshLayout;
    /**
     * 0是刷新，1是加载
     */
    private int refreshFlag = 0;
    /**
     * 加载页
     */
    private int pageNo = 0; // 从零开始
    private int recordNumber = 0;

    private Activity mActivity;

    public RefreshManager(@NonNull Activity activity, @NonNull RefreshDataCallBack mRefreshDataCallBack) {
        this.mActivity = activity;
        mSmartRefreshLayout = (SmartRefreshLayout) activity.findViewById(R.id.refreshlayout);
        setOnRefreshListener(mRefreshDataCallBack);
    }

    public RefreshManager(@NonNull Activity activity, @NonNull View view, @NonNull RefreshDataCallBack mRefreshDataCallBack) {
        this.mActivity = activity;
        mSmartRefreshLayout = (SmartRefreshLayout) view.findViewById(R.id.refreshlayout);
        setOnRefreshListener(mRefreshDataCallBack);
    }

    private void setOnRefreshListener(final RefreshDataCallBack mRefreshDataCallBack) {

        mSmartRefreshLayout.setOnRefreshLoadmoreListener(new OnRefreshLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                refreshFlag = 1;
                pageNo += 1;
                if (pageNo * 10 < recordNumber) { // 有数据
                    mRefreshDataCallBack.getData(pageNo);
                } else { // 无数据500ms后关闭加载
                    mSmartRefreshLayout.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            mSmartRefreshLayout.finishLoadmore();
                            SnackBarUtils.showTip(mSmartRefreshLayout, R.string.error_no_data);
                        }
                    }, 500);
                }
            }

            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                resetData();
                mRefreshDataCallBack.getData(pageNo);
            }
        });
    }

    public void resetData() {
        refreshFlag = 0;
        pageNo = 0;
        recordNumber = 0;
    }

    public void finishGetData(String recordNumber) {
        if (refreshFlag == 0) { // 刷新
//            mTwinklingRefreshLayout.finishRefreshing();
            mSmartRefreshLayout.finishRefresh();
        } else { // 加载
//            mTwinklingRefreshLayout.finishLoadmore();
            mSmartRefreshLayout.finishLoadmore();
        }
        try {
            recordNumber = Util.isStringNull(recordNumber) ? "0" : recordNumber;
            int count = Integer.parseInt(recordNumber);
            if (count > 0)
                this.recordNumber = count;
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }

    public void startRefresh() {
        mSmartRefreshLayout.setVisibility(View.VISIBLE);
        mSmartRefreshLayout.autoRefresh();
    }

    /**
     * @return TRUE代表刷新，FALSE代表加载更多
     */
    public boolean isRefreshData() {
        return refreshFlag == 0;
    }

    public void showView(List list) {
        if (Util.isListEmpty(list)) {
            mSmartRefreshLayout.setVisibility(View.GONE);
        } else {
            mSmartRefreshLayout.setVisibility(View.VISIBLE);
        }
    }
}
