package com.android.collect.ui.other.keyboard;

import android.os.Bundle;

import com.android.collect.R;
import com.android.collect.library.base.SwipeBackTitleActivity;
import com.android.collect.library.common.Constant;
import com.android.collect.library.listener.KeyboardChangeListener;
import com.android.collect.library.util.LogUtils;
import com.android.collect.library.util.ToastUtils;
import com.rengwuxian.materialedittext.MaterialEditText;

/**
 * 键盘
 */
public class KeyBoardTestActivity extends SwipeBackTitleActivity {

    private MaterialEditText materialEditText1;

    private KeyboardChangeListener mKeyboardChangeListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void setCurrentView() {
        isSwipeBackTitle = false;
        setContentView(R.layout.activity_key_board_test);
        initTitle(Constant.TITLE1, R.string.key_board_title);
    }

    @Override
    protected void initView() {
        materialEditText1 = (MaterialEditText) findViewById(R.id.materialEditText1);

        //  StatusBarUtil.setColorForSwipeBack(this, mColor);会使监听失效
        mKeyboardChangeListener = new KeyboardChangeListener(this);
        mKeyboardChangeListener.setKeyBoardListener(new KeyboardChangeListener.KeyBoardListener() {
            @Override
            public void onKeyboardChange(boolean isShow, int keyboardHeight) {
                LogUtils.i(TAG, "isKeyBoardShow = [" + isShow + "], keyboardHeight = [" + keyboardHeight + "]");
                ToastUtils.Toast(KeyBoardTestActivity.this, isShow ? "弹出" : "收起");
            }
        });
    }

    @Override
    protected void setData() {

    }

    @Override
    protected void onDestroy() {
        mKeyboardChangeListener.destroy();
        super.onDestroy();
    }
}
