package com.android.collect.ui.view.other_view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.android.collect.R;
import com.android.collect.bean.PhoneContactDetail;
import com.android.collect.library.util.Util;

import java.util.ArrayList;
import java.util.List;

/**
 * 自定义自动补全联系人的适配器
 *
 * @author anzai
 */
public class AutoCompleteAdapter extends BaseAdapter implements Filterable {

    private List<PhoneContactDetail> data;
    private Context context;
    private MyArrayFilter fileter;

    /**
     * 未过滤
     */
    private List<PhoneContactDetail> mUnfilteredData;

    public AutoCompleteAdapter(Context context,
                               List<PhoneContactDetail> mUnfilteredData) {
        this.context = context;
        this.mUnfilteredData = mUnfilteredData;
        data = new ArrayList<PhoneContactDetail>();
        data.addAll(mUnfilteredData);
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(
                    R.layout.item_phone_auto, null);
            holder = new ViewHolder();
            holder.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
            holder.tv_phone = (TextView) convertView.findViewById(R.id.tv_phone);
            holder.tv_email = (TextView) convertView.findViewById(R.id.tv_email);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        PhoneContactDetail pc = data.get(position);

        holder.tv_name.setText("姓名：" + pc.getName());
        holder.tv_phone.setText("电话：" + pc.getPhone());
        holder.tv_email.setText("Email：" + pc.getEmail());

        return convertView;
    }

    class ViewHolder {
        TextView tv_name, tv_phone, tv_email;
    }

    @Override
    public Filter getFilter() {
        if (fileter == null)
            fileter = new MyArrayFilter();
        return fileter;
    }

    private class MyArrayFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence prefix) {
            FilterResults results = new FilterResults();

            if (prefix == null || "".equals(prefix)) {
                results.values = mUnfilteredData;
                results.count = mUnfilteredData.size();
            } else {
                // 不区分大小写
                String prefixString = prefix.toString().toLowerCase();

                int count = mUnfilteredData.size();

                List<PhoneContactDetail> newValues = new ArrayList<PhoneContactDetail>();

                // 遍历过滤
                for (int i = 0; i < count; i++) {
                    PhoneContactDetail pc = mUnfilteredData.get(i);
                    if (pc != null) {
                        String name = pc.getName();
                        if (!Util.isStringNull(name) && name.contains(prefixString)) {
                            newValues.add(pc);
                        } else if (pc.getEmail() != null
                                && pc.getEmail().startsWith(prefixString)) {
                            newValues.add(pc);
                        }
                    }
                }

                results.values = newValues;
                results.count = newValues.size();
            }

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint,
                                      FilterResults results) {
            data = (List<PhoneContactDetail>) results.values;
            if (results.count > 0) {
                notifyDataSetChanged();
            } else {
                notifyDataSetInvalidated();
            }
        }

    }

    public List<PhoneContactDetail> getData() {
        return data;
    }

}
