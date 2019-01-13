package com.android.collect.ui.view.other_view.praise;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.android.collect.R;
import com.android.collect.library.base.SwipeBackTitleActivity;
import com.android.collect.library.common.Constant;
import com.android.collect.library.util.SnackBarUtils;
import com.android.collect.library.util.Util;
import com.android.collect.widget.praise.PraiseListView;

import java.util.ArrayList;
import java.util.List;

/**
 * 点赞
 */
public class PraiseListViewTestActivity extends SwipeBackTitleActivity {

    private LinearLayout ll_praiseListView;
    private PraiseListView mPraiseListView;
    private List<String> mList;

    private EditText edt1, edt2;
    private Button btn_add, btn_remove;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void setCurrentView() {
        isSwipeBackTitle = false;
        setContentView(R.layout.activity_praise_listview_test);
        initTitle(Constant.TITLE1, R.string.praise_title);
    }

    @Override
    protected void initView() {
        ll_praiseListView = (LinearLayout) findViewById(R.id.ll_praiseListView);
        mPraiseListView = (PraiseListView) findViewById(R.id.praiseListView);

        edt1 = (EditText) findViewById(R.id.edt1);
        edt2 = (EditText) findViewById(R.id.edt2);
        btn_add = (Button) findViewById(R.id.btn_add);
        btn_remove = (Button) findViewById(R.id.btn_remove);

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String add = edt1.getText().toString().trim();
                if (!Util.isStringNull(add)) {
                    mList.add(add);
                    mPraiseListView.setDatas(mList);
                }
            }
        });

        btn_remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String remove = edt2.getText().toString().trim();
                if (!Util.isStringNull(remove)) {
                    int removePosition = Integer.parseInt(remove);
                    if (removePosition < mList.size()) {
                        mList.remove(removePosition);
                        mPraiseListView.setDatas(mList);
                    }
                }
            }
        });

        ll_praiseListView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    protected void setData() {
        mList = new ArrayList<>();
        mList.add("网友");

        // 文字点赞
        mPraiseListView.setDatas(mList);
        mPraiseListView.setOnItemClickListener(new PraiseListView.OnItemClickListener() {
            @Override
            public void onClick(int position) {
                String item = mList.get(position);
                SnackBarUtils.showTip(mPraiseListView, item);
            }
        });

    }

}
