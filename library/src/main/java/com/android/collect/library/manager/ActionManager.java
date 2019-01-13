package com.android.collect.library.manager;

/**
 * Created by anzai on 2017/7/17.
 */

/**
 * 公共交易管理
 */
public class ActionManager {
    /**
     * 获取Token
     */
    /*public static void getToken(Context context,
                                final OnGetTokenListener onGetTokenListener) {
        new DataRepository(context, HttpHelper.GenToken, new JSONObject(),
                new DataResultCallBack() {

                    @Override
                    public void onSucceed(JSONObject jsonObject) {
                        String token = JSONUtil.getString(jsonObject, KeyHelper._rTokenName);
                        if (onGetTokenListener != null)
                            onGetTokenListener.onGetToken(token);
                    }

                    @Override
                    public void onFailed(String statusCode, String msg) {

                    }
                }).postRequest();
    }

    *//**
     * 获取时间戳
     *//*
    public static void getTimestamp(Context context,
                                    final OnGetTimestampListener onGetTimestampListener) {
        JSONObject jsonObject = new JSONObject();
        JSONUtil.setString(jsonObject, KeyHelper.Type, "0");
        new DataRepository(context, HttpHelper.GenTimeStamp, jsonObject,
                new DataResultCallBack() {

                    @Override
                    public void onSucceed(JSONObject jsonObject) {
                        String timestamp = JSONUtil.getString(jsonObject, KeyHelper.Timestamp);
                        onGetTimestampListener.onGetTimeStamp(timestamp);
                    }

                    @Override
                    public void onFailed(String statusCode, String msg) {

                    }
                }).showDialog().postRequest();
    }

    *//**
     * 未登陆 获取短信验证码
     *
     * @param mobilePhone 模板
     * @param transCode   下一步要验证的交易码
     *//*
    public static void getPhoneTokenNoLogin(final Context context, final String mobilePhone, final String confirmEvent, final String cancelEvent,
                                            final String transCode, final SMSAuthCode btn_msgCode, final EditText edt_msgCode) {

        DialogUtil.showSendMsgTipDialog(context, mobilePhone, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!Util.isStringNull(confirmEvent))
                    //友盟统计
                    MobclickAgent.onEvent(context, confirmEvent);
                JSONObject obj = new JSONObject();
                JSONUtil.setString(obj, KeyHelper.TransCode, transCode);
                JSONUtil.setString(obj, KeyHelper.MobilePhone, mobilePhone);
                new DataRepository(context, HttpHelper.GenPhoneToken, obj,
                        new DataResultCallBack() {

                            @Override
                            public void onSucceed(JSONObject jsonObject) {
                                btn_msgCode.doTimeStart();
                                edt_msgCode.setText("");
                            }

                            @Override
                            public void onFailed(String statusCode, String msg) {
                                btn_msgCode.doTimeEnd();
                            }
                        }).showDialog().postRequest();
            }
        }, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!Util.isStringNull(cancelEvent)) {
                    //友盟统计
                    MobclickAgent.onEvent(context, cancelEvent);
                }
            }
        });
    }

    *//**
     * 未登陆 获取短信验证码
     *
     * @param mobilePhone 模板
     * @param transCode   下一步要验证的交易码
     *//*
    public static void getPhoneTokenNoLogin(final Context context, final String mobilePhone, final String confirmEvent, final String cancelEvent,
                                            final String transCode, final DataResultCallBack dataResultCallBack) {
        DialogUtil.showSendMsgTipDialog(context, mobilePhone, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!Util.isStringNull(confirmEvent))
                    //友盟统计
                    MobclickAgent.onEvent(context, confirmEvent);
                JSONObject obj = new JSONObject();
                JSONUtil.setString(obj, KeyHelper.TransCode, transCode);
                JSONUtil.setString(obj, KeyHelper.MobilePhone, mobilePhone);
                new DataRepository(context, HttpHelper.GenPhoneToken, obj, dataResultCallBack).showDialog().postRequest();
            }
        }, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!Util.isStringNull(cancelEvent)) {
                    //友盟统计
                    MobclickAgent.onEvent(context, cancelEvent);
                }
            }
        });

    }

    *//**
     * 登陆 获取短信验证码
     *
     * @param mobilePhone 模板
     * @param transCode   下一步要验证的交易码
     *//*
    public static void getPhoneTokenForLogin(final Context context, final String mobilePhone, final String confirmEvent, final String cancelEvent,
                                             final String transCode, final SMSAuthCode btn_msgCode,
                                             final EditText edt_msgCode) {
        DialogUtil.showSendMsgTipDialog(context, mobilePhone, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!Util.isStringNull(confirmEvent))
                    //友盟统计
                    MobclickAgent.onEvent(context, confirmEvent);
                JSONObject obj = new JSONObject();
                JSONUtil.setString(obj, KeyHelper.TransCode, transCode);
                JSONUtil.setString(obj, KeyHelper.MobilePhone, mobilePhone);
                new DataRepository(context, HttpHelper.GenPhoneTokenFLogin, obj, new DataResultCallBack() {
                    @Override
                    public void onSucceed(JSONObject jsonObject) {
                        btn_msgCode.doTimeStart();
                        edt_msgCode.setText("");
                    }

                    @Override
                    public void onFailed(String statusCode, String msg) {
                        btn_msgCode.doTimeEnd();
                    }
                }).showDialog().postRequest();
            }
        }, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!Util.isStringNull(cancelEvent)) {
                    //友盟统计
                    MobclickAgent.onEvent(context, cancelEvent);
                }
            }
        });

    }

    *//**
     * 登陆 获取短信验证码
     *
     * @param mobilePhone 模板
     * @param transCode   下一步要验证的交易码
     *//*
    public static void getPhoneTokenForLogin(final Context context, final String mobilePhone, final String confirmEvent, final String cancelEvent,
                                             final String transCode, final DataResultCallBack dataResultCallBack) {
        DialogUtil.showSendMsgTipDialog(context, mobilePhone, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!Util.isStringNull(confirmEvent))
                    //友盟统计
                    MobclickAgent.onEvent(context, confirmEvent);
                JSONObject obj = new JSONObject();
                JSONUtil.setString(obj, KeyHelper.TransCode, transCode);
                JSONUtil.setString(obj, KeyHelper.MobilePhone, mobilePhone);
                new DataRepository(context, HttpHelper.GenPhoneTokenFLogin, obj, dataResultCallBack).showDialog().postRequest();
            }
        }, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!Util.isStringNull(cancelEvent)) {
                    //友盟统计
                    MobclickAgent.onEvent(context, cancelEvent);
                }
            }
        });
    }

    *//**
     * 获取图形验证码
     *//*
    public static void getImageCode(Context context, final ImageView imageView) {

        JSONObject obj = new JSONObject();
        new DataRepository(context, HttpHelper.GenTokenImg, obj, new DataResultCallBack() {
            @Override
            public void onSucceed(JSONObject jsonObject) {
                String content = JSONUtil.getString(jsonObject, "Content");
                imageView.setImageBitmap(BitmapUtil.strToBitmap(content));
            }

            @Override
            public void onFailed(String statusCode, String msg) {
            }
        }).showDialog().postRequest();
    }

    *//**
     * 获取电子账户信息
     *
     * @param context
     *//*
    public static void ShowBindCard(Context context) {
        if (!UserManager.isUserOpen(context))
            return;
        new DataRepository(context, HttpHelper.ShowBindCard, new JSONObject(), new DataResultCallBack() {
            @Override
            public void onSucceed(JSONObject jsonObject) {
                String AcNo = JSONUtil.getString(jsonObject, KeyHelper.AcNo, "");
                String AcctNo = JSONUtil.getString(jsonObject, KeyHelper.AcctNo, "");
                String CardBankName = JSONUtil.getString(jsonObject, KeyHelper.CardBankName, "");
                UserManager.getInstance().getUser().setAcctNo(AcctNo);
                UserManager.getInstance().getUser().setCardBankName(CardBankName);
                UserManager.getInstance().getUser().setAcNo(AcNo);
            }

            @Override
            public void onFailed(String statusCode, String msg) {

            }
        }).postRequest();
    }*/
}
