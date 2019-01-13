package com.android.collect.ui.view.other_view.adapter;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.collect.R;
import com.android.collect.bean.IndexBarDetail;
import com.android.collect.library.util.Util;

import java.util.List;

/**
 * 选择页面列表适配器
 * Created by anzai on 2017/4/19.
 */
public class IndexBarRvAdapter extends RecyclerView.Adapter<IndexBarRvAdapter.MyViewHolder> {

    private List<IndexBarDetail> data;
    private LayoutInflater inflater;
    private Activity mActivity;

    public IndexBarRvAdapter(Activity activity, List<IndexBarDetail> data) {
        this.mActivity = activity;
        this.data = data;
        inflater = LayoutInflater.from(activity);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mActivity).inflate(R.layout.item_index_bar_rv, parent, false);
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

    public void refreshData(List<IndexBarDetail> list) {
        if (!Util.isListEmpty(list) && data != list) {
            data.clear();
            data.addAll(list);
        }
        notifyDataSetChanged();

    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tv_circle, tv_name;
        LinearLayout ll_item;

        public MyViewHolder(View view) {
            super(view);

            tv_name = (TextView) view.findViewById(R.id.tv_name);
            tv_circle = (TextView) view.findViewById(R.id.tv_circle);
            ll_item = (LinearLayout) view;
        }

        public void setItemData(int position) {
            IndexBarDetail detail = data.get(position);
            tv_name.setText(detail.getName());
            tv_circle.setText(detail.getName().substring(0, 1));

            GradientDrawable myGrad = (GradientDrawable) tv_circle
                    .getBackground();
            // 随机颜色
            try {
                int i = (int) ((Math.random() * 16777215 + 1));
                myGrad.setColor(Color.parseColor("#"
                        + Integer.toHexString(i)));
            } catch (Exception e) {
                e.printStackTrace();
                myGrad.setColor(ContextCompat.getColor(mActivity, R.color.text_blue));
            }

        }


    }

}
