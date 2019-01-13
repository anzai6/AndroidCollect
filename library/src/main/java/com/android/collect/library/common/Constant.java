package com.android.collect.library.common;

import com.android.collect.library.http.CommDictAction;

/**
 * Created by WD on 2016/11/30.
 */
public class Constant {

    /**
     *  是否是调试模式
     */
    public static boolean isDebug = true;
    /**
     *  是否打印日志
     */
    public static boolean isPrintMsg = true;
    /**
     *  是否捕获未知异常
     */
    public static final boolean isNeedCaughtExeption = true;
    /**
     *  是否从本地读取HTML
     */
    public static boolean isReedHtmlFromLocal = true;
    /**
     * 读取本地HTML默认地址
     */
    public static String LocalIndexHtml = CommDictAction.Url + "indexForApp.html";

    public static final String spu = "dfadfpoasdjfoajsiodfoaisdjfoiasdoifaojsdfasdfas2345345";

    public static int APPVERSION_CODE = 0;

    public static final int DIALOG_TIMEOUT = 45000;// 加载中时：转圈时间

    // 首页
    public static final int TITLE0 = 1000;// 显示图标
    public static final int TITLE1 = 1001;// 显示文字

    // 回到首页选择的页面
    public final static String CHECK_HOMEPAGE = "1";// 首页
    public final static String CHECK_LOAN_PRODUCT = "2";// 贷款产品
    public final static String CHECK_MYSELF = "3";// 我的

    // 跳转目的地标识
    public final static int TARGET_FEED_BACK = 1;// 意见反馈

    // 登录
    public static final int LOGIN = 0;
    public static final int LOGOUT = 1;
    public static final String LOGIN_APP_ACTION = "com.android.collect.login";

}
