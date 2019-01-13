package com.android.collect.library.util;

import android.text.Editable;
import android.text.Selection;
import android.text.Spannable;
import android.text.TextWatcher;
import android.widget.EditText;

import static com.android.collect.library.util.Util.isStringNull;

/**
 * Created by anzai on 2017/4/13.
 */

public class EditTextUtil {

    /**
     * 使edittext只能输入两位小数
     */
    public static void setAmountEditText(final EditText editText) {
        editText.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                if (s.toString().contains(".")) {
                    if (s.length() - 1 - s.toString().indexOf(".") > 2) {
                        s = s.toString().subSequence(0,
                                s.toString().indexOf(".") + 3);
                        editText.setText(s);
                        editText.setSelection(s.length());
                    }
                }
                if (s.toString().trim().substring(0).equals(".")) {
                    s = "0" + s;
                    editText.setText(s);
                    editText.setSelection(2);
                }

                if (s.toString().startsWith("0")
                        && s.toString().trim().length() > 1) {
                    if (!s.toString().substring(1, 2).equals(".")) {
                        editText.setText(s.subSequence(0, 1));
                        editText.setSelection(1);
                        return;
                    }
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    /**
     * 格式化手机输入
     *
     * @param phoneEdt
     */
    public static void setPhoneEditText(final EditText phoneEdt) {
        phoneEdt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                phoneEdt.removeTextChangedListener(this);
                String value = phoneEdt.getText().toString().replace(" ", "");
                if (!Util.isStringNull(value)) {
                    phoneEdt.setText(FormatUtil.formatPhone(value));
                    Spannable editable = phoneEdt.getText();
                    Selection.setSelection(editable, editable.length());

                }
                phoneEdt.addTextChangedListener(this);
            }
        });
    }

    /**
     * 监听输入的银行卡号并每隔4位加一个空格
     */
    public static void setCardNumEditText(final EditText cardNumEdt) {
        cardNumEdt.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                cardNumEdt.removeTextChangedListener(this);
                String value = cardNumEdt.getText().toString().replace(" ", "");
                if (!isStringNull(value)) {
                    cardNumEdt.setText(FormatUtil.formatAcNO(value));
                    Spannable editable = cardNumEdt.getText();
                    Selection.setSelection(editable, editable.length());

                }
                cardNumEdt.addTextChangedListener(this);
            }
        });
    }


}
