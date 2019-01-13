package com.android.collect.library.common;

import java.util.HashMap;
import java.util.Map.Entry;

public class PutDataMap {

    public static final HashMap<String, String> receiveAcStateMap = new HashMap<String, String>();
    public static final HashMap<String, String> receiveRepayModeMap = new HashMap<String, String>();
    public static final HashMap<String, String> receiveProfessionMap = new HashMap<String, String>();

    static {

        // 账户状态
        receiveAcStateMap.put("0", "正常");

        receiveRepayModeMap.put("1", "等额本息");
        receiveRepayModeMap.put("2", "等额本金");
        receiveRepayModeMap.put("3", "按频率付息，任意本金计划");
        receiveRepayModeMap.put("4", "一次还本付息/前收息");
        receiveRepayModeMap.put("5", "按频率付息，一次还本");

        // 职业
        receiveProfessionMap.put("0", "国家机关、党群组织、企业、事业单位负责人");
        receiveProfessionMap.put("1", "专业技术人员");
        receiveProfessionMap.put("3", "办事人员和有关人员");
        receiveProfessionMap.put("4", "商业、服务业人员");
        receiveProfessionMap.put("5", "农、林、牧、渔、水利业生产人员");
        receiveProfessionMap.put("6", "生产、运输设备操作人员及有关人员");
        receiveProfessionMap.put("X", "军人");
        receiveProfessionMap.put("Y", "不便分类的其他从业人员");
        receiveProfessionMap.put("Z", "未知");
    }

    public static final HashMap<String, String> sendAcStateMap = new HashMap<String, String>();
    public static final HashMap<String, String> sendProfessionMap = new HashMap<String, String>();

    static {
        for (Entry<String, String> entry : receiveAcStateMap.entrySet()) {
            sendAcStateMap.put(entry.getValue(), entry.getKey());
        }
        for (Entry<String, String> entry : receiveProfessionMap.entrySet()) {
            sendProfessionMap.put(entry.getValue(), entry.getKey());
        }
    }
}
