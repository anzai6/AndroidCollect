package com.android.collect.library.util;

import static com.android.collect.library.util.Util.isStringNull;

/**
 * Created by qinshunan on 2017/8/21.
 */
public class FormatUtil {

    /**
     * 姓名加密 张小三 ----> *小三
     */
    public static String getEncryptName(String username) {
        if (isStringNull(username))
            return "";
        int length = username.length();
        if (length >= 2) {
            return "*" + username.substring(1,
                    length);
        } else {
            return username;
        }
    }

    /**
     * 加密手机号 ("186****2345")
     */
    public static String getEncryptPhone(String phone) {
        if (isStringNull(phone))
            return "";
        int length = phone.length();
        if (length > 3) {
            return phone.substring(0, 3) + " **** "
                    + phone.substring(length - 4, length);
        } else {
            return phone;
        }
    }

    /**
     * 加密卡号 (6228 **** **** 2345)
     */
    public static String getEncryptAcNO(String acNo) {
        if (isStringNull(acNo))
            return "";
        int length = acNo.length();
        if (length > 8) {
            return acNo.substring(0, 4) + " **** **** "
                    + acNo.substring(length - 4, length);
        } else {
            return acNo;
        }
    }

    /**
     * 加密电子账号 (6228 **** **** 2345)
     */
    public static String getEncryptElectAccount(String acNo) {
        if (isStringNull(acNo))
            return "";
        int length = acNo.length();
        if (length > 8) {
            return acNo.substring(0, 4) + " **** **** **** "
                    + acNo.substring(length - 3, length);
        } else {
            return acNo;
        }
    }

    /**
     * 加密身份证号码(2+"****")
     */
    public static String getEncryptIdNo(String IDCode) {

        if (isStringNull(IDCode))
            return "";
        if (IDCode.length() > 4) {
            return IDCode.substring(0, 2) + " **** "
                    + IDCode.substring(IDCode.length() - 2, IDCode.length());
        } else {
            return IDCode;
        }
    }

    /**
     * 每隔4位加空格
     */
    private static int SEPARATOR_NUMBER = 4;

    /**
     * 格式化卡号 (6228 2222 1111 2345)
     */
    public static String formatAcNO(String acNo) {
        if (isStringNull(acNo))
            return "";
        int length = acNo.length();
        int tab_size = (float) length / (float) SEPARATOR_NUMBER - length
                / SEPARATOR_NUMBER > 0 ? length / SEPARATOR_NUMBER : length
                / SEPARATOR_NUMBER - 1;
        StringBuilder sb = new StringBuilder(acNo);
        for (int i = 1; i <= tab_size; i++) {
            if (i * SEPARATOR_NUMBER <= length) {
                sb.insert(i * SEPARATOR_NUMBER + i - 1, " ");
            }
        }
        return sb.toString();
    }

    /**
     * 格式化手机号 ("186 1234 2345")
     */
    public static String formatPhone(String phone) {
        if (isStringNull(phone))
            return "";
        int length = phone.length();

        if (3 < length && length <= 7) {
            return phone.substring(0, 3) + " " + phone.substring(3, length);
        } else if (7 < length) {
            return phone.substring(0, 3) + " " + phone.substring(3, 7) + " "
                    + phone.substring(7, length);
        } else {
            return phone;
        }
    }

}
