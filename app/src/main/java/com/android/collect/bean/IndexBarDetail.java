package com.android.collect.bean;

import com.android.collect.library.util.GsonUtil;
import com.android.collect.library.util.JSONUtil;
import com.mcxtzhang.indexlib.IndexBar.bean.BaseIndexPinyinBean;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by anzai on 2017/7/27.
 * 选择项
 */
public class IndexBarDetail extends BaseIndexPinyinBean {

    private String Name, Value;
    private boolean isTop;//是否是最上面的 不需要被转化成拼音的

    public IndexBarDetail() {
    }

    public IndexBarDetail(String name) {
        this.Name = name;
    }

    public IndexBarDetail(String name, String value) {
        this.Name = name;
        this.Value = value;
    }

    public String getValue() {
        return Value;
    }

    public void setValue(String value) {
        this.Value = value;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        this.Name = name;
    }

    public boolean isTop() {
        return isTop;
    }

    public void setTop(boolean top) {
        isTop = top;
    }

    @Override
    public String getTarget() {
        return Name;
    }

    @Override
    public boolean isNeedToPinyin() {
        return !isTop;
    }


    @Override
    public boolean isShowSuspension() {
        return !isTop;
    }

    public static List<IndexBarDetail> getList(JSONObject jsonObject) {
        JSONArray array = JSONUtil.getJSONArray(jsonObject, "SupportBank");
        Class<IndexBarDetail[]> itemList = IndexBarDetail[].class;
        List<IndexBarDetail> list = GsonUtil.getListFromJSON(array.toString(), itemList);
        return list;
    }

}
