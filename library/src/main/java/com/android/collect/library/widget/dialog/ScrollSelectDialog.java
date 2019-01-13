package com.android.collect.library.widget.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager.LayoutParams;
import android.widget.TextView;

import com.android.collect.library.R;
import com.android.collect.library.callback.OnSelectDialogCallBack;
import com.android.collect.library.widget.WheelView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ScrollSelectDialog {

    private WheelView wv;
    private Dialog dialog;
    private Context mContext;
    private String[] datas;
    private View outerView;
    protected int currentIndex;
    protected String currentItem;
    private OnSelectDialogCallBack mOnSelectDialogCallBack;
    private ArrayList objs;
    private TextView mTextView;
    private int style = 0;

    public ScrollSelectDialog(Context context, String[] datas, TextView tv) {
        this.mContext = context;
        this.datas = datas;
        this.mTextView = tv;
        init();
    }

    public ScrollSelectDialog(Context context, ArrayList objs, TextView tv) {
        this.mContext = context;
        this.objs = objs;
        this.mTextView = tv;
        datas = new String[objs.size()];

        for (int i = 0; i < objs.size(); i++) {
            Object obj = objs.get(i);
            datas[i] = obj.toString();
        }

        init();
    }

    public void initData(String[] datas) {

    }

    public void initData(List<Object> objs) {

    }

    private void init() {
        outerView = LayoutInflater.from(mContext).inflate(R.layout.dialog_scroll_select,
                null);
        wv = (WheelView) outerView.findViewById(R.id.wheel_view_wv);
        wv.setOffset(1);
        wv.setItems(Arrays.asList(datas));

        dialog = new Dialog(mContext, R.style.dialog_scroll_select);
        dialog.setContentView(outerView);

        Display display = ((Activity) mContext).getWindowManager()
                .getDefaultDisplay();

        Window window = dialog.getWindow();
        LayoutParams params = new LayoutParams();
        params.width = display.getWidth();

        outerView.measure(0, 0);
        params.height = outerView.getMeasuredHeight();
        params.y = display.getHeight() - params.height;

        window.setGravity(Gravity.BOTTOM);
        window.setAttributes(params);

        window.setWindowAnimations(R.style.ScrollSelectDialogWindowAnim);

        dialog.setCanceledOnTouchOutside(false);

        wv.setOnWheelViewListener(new WheelView.OnWheelViewListener() {

            @Override
            public void onSelected(int selectedIndex, String item) {
                currentIndex = selectedIndex;
                currentItem = item;
            }
        });

        outerView.findViewById(R.id.btn_confirm).setOnClickListener(
                new OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        if (!TextUtils.isEmpty(currentItem)) {
                            if (mTextView != null)
                                mTextView.setText(currentItem);
                            mOnSelectDialogCallBack.selectConfirm(currentIndex,
                                    currentItem, mTextView);
                            dialog.dismiss();
                        }
                    }
                });
        ;

        outerView.findViewById(R.id.btn_cancel).setOnClickListener(
                new OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
    }

    public void setOnSelectDialogConfirm(
            OnSelectDialogCallBack onSelectDialogCallBack) {
        this.mOnSelectDialogCallBack = onSelectDialogCallBack;
    }

    public void showSelectDialog(int position) {
        wv.setSeletion(position);

        if (position >= datas.length)
            return;
        currentItem = datas[position];
        currentIndex = position;
        dialog.show();
    }

}