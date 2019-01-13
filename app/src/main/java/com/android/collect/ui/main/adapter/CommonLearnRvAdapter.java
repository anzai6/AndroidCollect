package com.android.collect.ui.main.adapter;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.collect.R;
import com.android.collect.bean.LearnDataDetail;
import com.android.collect.data.BaseData;
import com.android.collect.library.common.FileConstant;
import com.android.collect.library.common.KeyHelper;
import com.android.collect.library.ui.web.CommonWebViewActivity;
import com.android.collect.library.util.ActUtil;
import com.android.collect.library.util.Util;
import com.android.collect.ui.main.activity.LearnListActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by anzai on 2017/4/19.
 */
public class CommonLearnRvAdapter extends RecyclerView.Adapter<CommonLearnRvAdapter.MyViewHolder> {

    private List<LearnDataDetail> data;
    private LayoutInflater inflater;
    private Activity mActivity;

    public CommonLearnRvAdapter(Activity activity, List<LearnDataDetail> data) {
        this.mActivity = activity;
        this.data = data;
        inflater = LayoutInflater.from(activity);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mActivity).inflate(R.layout.item_learn_rv, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.setItemData(position);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void refreshData(List<LearnDataDetail> list) {
        if (!Util.isListEmpty(list) && data != list) {
            data.clear();
            data.addAll(list);
        }
        notifyDataSetChanged();

    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        LinearLayout ll_item;
        TextView tv_title, tv_content;

        public MyViewHolder(View view) {
            super(view);

            tv_title = (TextView) view.findViewById(R.id.tv_title);
            tv_content = (TextView) view.findViewById(R.id.tv_content);
            ll_item = (LinearLayout) view.findViewById(R.id.ll_item);
        }

        public void setItemData(int position) {
            final LearnDataDetail detail = data.get(position);
            tv_title.setText(detail.getTitle());
            String content = detail.getContent();
            if (Util.isStringNull(content))
                tv_content.setVisibility(View.GONE);
            else {
                tv_content.setVisibility(View.VISIBLE);
                tv_content.setText(content);
            }
            ll_item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    toTarget(detail);
                }
            });
        }

        private void toTarget(LearnDataDetail detail) {
            BaseData baseData = detail.getBaseData();
            String target = detail.getTarget();
            String address = detail.getAddress();
            if (baseData != null) {
                ArrayList<LearnDataDetail> data = baseData.getData();
                Bundle bundle = new Bundle();
                bundle.putString(KeyHelper.Title, detail.getTitle());
                bundle.putParcelableArrayList(KeyHelper.List, data);
                ActUtil.startMyActivity(mActivity, LearnListActivity.class, bundle);
            } else if (!Util.isStringNull(target)) {
                ActUtil.startMyActivity(mActivity, target);
            } else if (!Util.isStringNull(address)) {
                Bundle bundle = new Bundle();
                bundle.putString(KeyHelper.WebTitle, detail.getTitle());
                bundle.putString(KeyHelper.WebUrl, address);
                if (address.contains(FileConstant.APP_ASSET_DIR)) {
                    bundle.putInt(KeyHelper.WebMode, CommonWebViewActivity.MODE_DEFAULT);
                } else {
                    bundle.putInt(KeyHelper.WebMode, CommonWebViewActivity.MODE_SONIC);
                }
                ActUtil.startMyActivity(mActivity, CommonWebViewActivity.class, bundle);
            } else {

            }
        }


    }

}
