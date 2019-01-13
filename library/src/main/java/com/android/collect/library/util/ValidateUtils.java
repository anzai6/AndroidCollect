package com.android.collect.library.util;

public class ValidateUtils {

    /**
     * 登录密码校验
     *
     * @param context
     * @param pswEditText
     * @return
     *//*
    public static boolean validateLoginPsw(Context context, PEEditText pswEditText) {

//        0：合法
//        -1：内容为空
//        -2：输入小于最小长度
//        -3：输入字符不可接受
        int v_check = pswEditText.validity_check();
//        0 内容为空
//        1 内容只含有数字
//        2 内容只含有字母
//        3 内容包含数字和字母
//        4 内容只含有符号
//        5 内容含有符号和数字
//        6 内容含有符号和字母
//        7 内容含有符号，数字和字母
        int content_type = pswEditText.getContentType();

        if (v_check == -1) {
            DialogUtil.showError(context, R.string.error_login_psw_null);
            pswEditText.requestFocus();
            pswEditText.setSelected(true);
            return false;
        }

        if (v_check == -2) {
            DialogUtil.showError(context, R.string.error_login_psw_length);
            pswEditText.requestFocus();
            pswEditText.setSelected(true);
            return false;
        }

        if (content_type != 3) {
            DialogUtil.showError(context, R.string.error_login_psw_format);
            pswEditText.requestFocus();
            return false;
        }

        return true;
    }


    *//**
     * 交易密码校验
     *
     * @param context
     * @param pswEditText
     * @return
     *//*
    public static boolean validateTradePsw(Context context, PEEditText pswEditText) {

        int v_check = pswEditText.validity_check();

        if (v_check == -1) {
            DialogUtil.showError(context, R.string.error_psw_null);
            pswEditText.requestFocus();
            pswEditText.setSelected(true);
            return false;
        }

        if (v_check == -2) {
            DialogUtil.showError(context, R.string.error_psw_length);
            pswEditText.requestFocus();
            pswEditText.setSelected(true);
            return false;
        }

//        if (Util.isSimplePassword(pswEditText)) {
//            DialogUtil.showError(context, R.string.error_tran_psw_simple);
//            pswEditText.requestFocus();
//            return false;
//        }

        return true;
    }

    *//**
     * 登录密码设置校验
     *
     * @param context
     * @param newPswEditText
     * @param pswEditTextConfirm
     * @return
     *//*
    public static boolean validateLoginPswModify(Context context, PEEditText oldPswEditText, PEEditText newPswEditText, PEEditText pswEditTextConfirm) {


        int v_check_old = oldPswEditText.validity_check();
        int content_type_old = oldPswEditText.getContentType();

        if (v_check_old == -1) {
            DialogUtil.showError(context, R.string.error_old_login_psw_null);
            oldPswEditText.requestFocus();
            oldPswEditText.setSelected(true);
            return false;
        }

        if (v_check_old == -2) {
            DialogUtil.showError(context, R.string.error_old_login_psw_length);
            oldPswEditText.requestFocus();
            oldPswEditText.setSelected(true);
            return false;
        }

        if (content_type_old != 3) {
            DialogUtil.showError(context, R.string.error_login_psw_format);
            oldPswEditText.requestFocus();
            return false;
        }

        int v_check = newPswEditText.validity_check();
        int content_type = newPswEditText.getContentType();

        if (v_check == -1) {
            DialogUtil.showError(context, R.string.error_new_login_psw_null);
            newPswEditText.requestFocus();
            newPswEditText.setSelected(true);
            return false;
        }

        if (v_check == -2) {
            DialogUtil.showError(context,
                    R.string.error_new_login_psw_length);
            newPswEditText.requestFocus();
            newPswEditText.setSelected(true);
            return false;
        }

        if (content_type != 3) {
            DialogUtil.showError(context, R.string.error_new_login_psw_format);
            newPswEditText.requestFocus();
            return false;
        }

        if (oldPswEditText.getHash().equals(newPswEditText.getHash())) {
            DialogUtil.showError(context, R.string.error_modify_login_psw_same);
            return false;
        }

        return validateLoginPswConfirm(context, newPswEditText, pswEditTextConfirm);
    }

    *//**
     * 登录密码设置校验
     *
     * @param context
     * @param newPswEditText
     * @param pswEditTextConfirm
     * @return
     *//*
    public static boolean validateLoginPswSet(Context context, PEEditText newPswEditText, PEEditText pswEditTextConfirm) {

        int v_check = newPswEditText.validity_check();
        int content_type = newPswEditText.getContentType();

        if (v_check == -1) {
            DialogUtil.showError(context, R.string.error_new_login_psw_null);
            newPswEditText.requestFocus();
            newPswEditText.setSelected(true);
            return false;
        }

        if (v_check == -2) {
            DialogUtil.showError(context,
                    R.string.error_new_login_psw_length);
            newPswEditText.requestFocus();
            newPswEditText.setSelected(true);
            return false;
        }

        if (content_type != 3) {
            DialogUtil.showError(context, R.string.error_new_login_psw_format);
            newPswEditText.requestFocus();
            return false;
        }

        return validateLoginPswConfirm(context, newPswEditText, pswEditTextConfirm);
    }

    *//**
     * 确认登录密码校验
     *
     * @param context
     * @param pswEditTextConfirm
     * @return
     *//*
    public static boolean validateLoginPswConfirm(Context context, PEEditText newPswEditText, PEEditText pswEditTextConfirm) {

        int v_check = pswEditTextConfirm.validity_check();

        if (v_check == -1) {
            DialogUtil.showError(context, R.string.error_confirm_psw_null);
            pswEditTextConfirm.requestFocus();
            pswEditTextConfirm.setSelected(true);
            return false;
        }

        if (v_check == -2) {
            DialogUtil.showError(context,
                    R.string.error_confirm_login_psw_length);
            pswEditTextConfirm.requestFocus();
            pswEditTextConfirm.setSelected(true);
            return false;
        }

        if (!newPswEditText.getHash().equals(pswEditTextConfirm.getHash())) {
            DialogUtil.showError(context, R.string.error_psw_not_same);
            return false;
        }

        return true;
    }

    *//**
     * 交易密码设置校验
     *
     * @param context
     * @param newPswEditText
     * @param pswEditTextConfirm
     * @return
     *//*
    public static boolean validateTradePswSet(Context context, PEEditText newPswEditText, PEEditText pswEditTextConfirm) {

        int v_check = newPswEditText.validity_check();

        if (v_check == -1) {
            DialogUtil.showError(context, R.string.error_new_trade_psw_null);
            newPswEditText.requestFocus();
            newPswEditText.setSelected(true);
            return false;
        }

        if (v_check == -2) {
            DialogUtil.showError(context,
                    R.string.error_new_trade_psw_length);
            newPswEditText.requestFocus();
            newPswEditText.setSelected(true);
            return false;
        }

        return validateTradePswConfirm(context, newPswEditText, pswEditTextConfirm);
    }

    *//**
     * 确认交易密码校验
     *
     * @param context
     * @param pswEditTextConfirm
     * @return
     *//*
    public static boolean validateTradePswConfirm(Context context, PEEditText newPswEditText, PEEditText pswEditTextConfirm) {

        int v_check = pswEditTextConfirm.validity_check();

        if (v_check == -1) {
            DialogUtil.showError(context, R.string.error_confirm_psw_null);
            pswEditTextConfirm.requestFocus();
            pswEditTextConfirm.setSelected(true);
            return false;
        }

        if (v_check == -2) {
            DialogUtil.showError(context,
                    R.string.error_confirm_trade_psw_length);
            pswEditTextConfirm.requestFocus();
            pswEditTextConfirm.setSelected(true);
            return false;
        }

        if (!newPswEditText.getHash().equals(pswEditTextConfirm.getHash())) {
            DialogUtil.showError(context, R.string.error_psw_not_same);
            pswEditTextConfirm.requestFocus();
            pswEditTextConfirm.setSelected(true);
            return false;
        }

        return true;
    }

    *//**
     * 手机号统一校验
     *
     * @param context
     * @param editText
     * @return
     *//*
    public static boolean validatePhone(Context context, EditText editText) {
        String phone = editText.getText().toString().trim().replace(" ", "");
        // 判断密码是否为空
        if (Util.isStringNull(phone)) {
            DialogUtil.showError(context, R.string.error_phone_null);
            editText.requestFocus();
            editText.setSelected(true);
            return false;
        }

        if (phone.length() != 11) {
            DialogUtil.showError(context, R.string.error_phone_length);
            editText.requestFocus();
            editText.setSelected(true);
            return false;
        }

        return true;
    }

    *//**
     * 验证码校验
     *
     * @param context
     * @param et_capture
     * @return
     *//*
    public static boolean validateMsgCode(Context context, EditText et_capture) {
        String code = et_capture.getText().toString().trim();
        // 是否为空
        if (Util.isStringNull(code)) {
            DialogUtil.showError(context, R.string.error_code_null);
            et_capture.requestFocus();
            et_capture.setSelected(true);
            return false;
        }

        if (code.length() != 6) {
            DialogUtil.showError(context, R.string.error_code_length);
            et_capture.requestFocus();
            et_capture.setSelected(true);
            return false;
        }

        return true;
    }

    *//**
     * 图形验证码校验
     *
     * @param context
     * @param edt
     * @return
     *//*
    public static boolean validateImageCode(Context context, EditText edt) {
        String code = edt.getText().toString().trim();
        // 是否为空
        if (Util.isStringNull(code)) {
            DialogUtil.showError(context, R.string.error_image_code_null);
            edt.requestFocus();
            edt.setSelected(true);
            return false;
        }

        if (code.length() != 4) {
            DialogUtil.showError(context, R.string.error_image_code_length);
            edt.requestFocus();
            edt.setSelected(true);
            return false;
        }

        return true;
    }

    *//**
     * 身份证校验
     *
     * @param context
     * @param id_edt
     * @return
     *//*
    public static boolean validateIdNo(Context context, EditText id_edt) {
        String id_num = id_edt.getText().toString().trim();
        if (Util.isStringNull(id_num)) {
            DialogUtil.showError(context, R.string.error_idno_verify_null);
            id_edt.requestFocus();
            id_edt.setSelected(true);
            return false;
        }

        if (id_num.length() != 18) {
            DialogUtil.showError(context, R.string.error_idno_lenght);
            id_edt.requestFocus();
            id_edt.setSelected(true);
            return false;
        }

        if (!id_num.matches("^[a-zA-Z0-9][a-zA-Z0-9_@.]{4,30}$")) {
            DialogUtil.showError(context, R.string.error_idno_verify_format);
            id_edt.requestFocus();
            id_edt.setSelected(true);
            return false;
        }
        return true;
    }

    *//**
     * 校验姓名
     **//*
    public static boolean validateName(Context context, EditText name_edt) {
        String name = name_edt.getText().toString().trim();
        if (Util.isStringNull(name)) {
            DialogUtil.showError(context, R.string.error_login_username_null);
            name_edt.requestFocus();
            name_edt.setSelected(true);
            return false;
        }
        return true;
    }

    *//**
     * 校验银行卡号
     **//*
    public static boolean validateBankCard(Context context,
                                           EditText edt_CardNo) {
        String bankCard = edt_CardNo.getText().toString().trim().replace(" ", "");
        if (Util.isStringNull(bankCard)) {
            DialogUtil.showError(context, R.string.error_bank_card_null);
            edt_CardNo.requestFocus();
            edt_CardNo.setSelected(true);
            return false;
        }
        if (bankCard.length() < 16) {
            DialogUtil.showError(context, R.string.error_bank_card_length);
            edt_CardNo.requestFocus();
            edt_CardNo.setSelected(true);
            return false;
        }
        return true;
    }

    *//**
     * 校验金额
     **//*
    public static boolean validateAmount(Context context,
                                         EditText edt_amount) {
        String amount = edt_amount.getText().toString().trim();
        if (Util.isStringNull(amount)) {
            DialogUtil.showError(context, R.string.error_amount_format);
            edt_amount.requestFocus();
            edt_amount.setSelected(true);
            return false;
        }
        if (!BigDecimalUtil.BigThan(amount, "0")) {
            DialogUtil.showError(context, R.string.error_amount_format);
            edt_amount.requestFocus();
            edt_amount.setSelected(true);
            return false;
        }
        // 金额格式
        if (!PatternUtil.isNumber(amount.replace(".", "").replace(",", ""))) {
            DialogUtil.showError(context, R.string.error_amount_format);
            edt_amount.requestFocus();
            edt_amount.setSelected(true);
            return false;
        }
        return true;
    }*/
}
