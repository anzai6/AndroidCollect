package com.android.collect.ui.view.other_view.activity;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.collect.R;
import com.android.collect.library.base.SwipeBackTitleActivity;
import com.android.collect.library.common.Constant;
import com.android.collect.library.util.SnackBarUtils;
import com.android.collect.library.util.Util;

/**
 * 六格密码控件
 */
public class SixGridPswTestActivity extends SwipeBackTitleActivity {

    private EditText edt_password;
    private Button btn_show;

    @Override
    protected void setCurrentView() {
        setContentView(R.layout.activity_six_grid_psw_test);
        initTitle(Constant.TITLE1, R.string.psw_edt_title);
    }

    @Override
    protected void initView() {
        edt_password = (EditText) findViewById(R.id.edt_password);
        btn_show = (Button) findViewById(R.id.btn_show);

        btn_show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String psw = edt_password.getText().toString().trim();
                if (!Util.isStringNull(psw)) {
                    SnackBarUtils.showTip(btn_show, psw);
                }
            }
        });

    }

    @Override
    protected void setData() {

    }

}
