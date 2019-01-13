package com.android.collect.ui.view.other_view.activity;

import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AutoCompleteTextView;

import com.android.collect.R;
import com.android.collect.bean.PhoneContactDetail;
import com.android.collect.library.base.SwipeBackTitleActivity;
import com.android.collect.library.common.Constant;
import com.android.collect.ui.view.other_view.adapter.AutoCompleteAdapter;

import java.util.ArrayList;

/**
 * 自动补全输入框
 */
public class AutoCompleteTvTestActivity extends SwipeBackTitleActivity implements OnItemClickListener {

    private AutoCompleteTextView mAutoCompleteTextView;
    private ArrayList<PhoneContactDetail> mList;
    private AutoCompleteAdapter mAutoCompleteAdapter;

    @Override
    protected void setCurrentView() {
        setContentView(R.layout.activity_auto_complete_tv_test);
        initTitle(Constant.TITLE1, R.string.auto_complete_title);
    }

    @Override
    protected void initView() {
        mAutoCompleteTextView = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextView);
        mAutoCompleteTextView.setOnItemClickListener(this);
    }

    @Override
    protected void setData() {
        String[] names = {"abc", "allen", "bird", "bike", "book", "cray",
                "david", "demon", "eclipse", "felling", "frank", "google",
                "green", "hill", "hook", "jin zhiwen", "jack", "jay", "king",
                "kevin", "kobe", "lily", "lucy", "mike", "nike", "nail",
                "open", "open cv", "panda", "pp", "queue", "ray allen", "risk",
                "tim cook", "T-MAC", "tony allen", "x man", "x phone", "yy",
                "world", "w3c", "zoom", "zhu ziqing"};

        mList = new ArrayList<PhoneContactDetail>();

        for (int i = 0; i < names.length; i++) {
            PhoneContactDetail pc = new PhoneContactDetail(names[i], 1511112744
                    + i + "", names[i].concat("@gmail.com"));
            mList.add(pc);
        }

        mAutoCompleteAdapter = new AutoCompleteAdapter(this, mList);
        mAutoCompleteTextView.setAdapter(mAutoCompleteAdapter);
        mAutoCompleteTextView.setThreshold(1); // 设置输入一个字符 提示，默认为2
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,
                            long id) {
        PhoneContactDetail pc = mAutoCompleteAdapter.getData().get(position);
        mAutoCompleteTextView.setText(pc.getName() + " " + pc.getPhone());
        mAutoCompleteTextView.setSelection(pc.getName().length() + pc.getPhone().length() + 1);
    }
}
