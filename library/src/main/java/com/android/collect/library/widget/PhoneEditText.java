package com.android.collect.library.widget;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.text.InputFilter;
import android.text.InputType;
import android.util.AttributeSet;
import android.widget.EditText;

import com.android.collect.library.util.EditTextUtil;

/**
 * 手机号输入框
 * Created by qinshunan on 2017/10/19.
 */

public class PhoneEditText extends EditText {

    public PhoneEditText(Context context) {
        super(context);
    }

    public PhoneEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PhoneEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public PhoneEditText(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public void setPhoneInput() {
        setFilters(new InputFilter[]{new InputFilter.LengthFilter(13)});
        setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_VARIATION_NORMAL);
        EditTextUtil.setPhoneEditText(this);
    }

    public String getPhoneText() {
        return getText().toString().replace(" ", "");
    }


}
