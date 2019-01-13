package com.android.collect.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.android.collect.library.util.GsonUtil;
import com.android.collect.library.util.JSONUtil;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

/**
 * 广告详情
 */
public class AdvertDetail implements Parcelable {

//    "AdvertUrl": "http://baidu.com",
//    "AdvertName": "start0724",
//    "StopDate": "2017-08-30 00:00:00",
//    "StartDate": "2017-07-21 00:00:00",
//    "AdvertContent": "测试",
//    "AdvertImage": "http://8.1.1.108/directad/welcome1.png",
//    "Order": null,
//    "Location": "0"
//    "ShopAdvertSeq": "0"

    private String AdvertUrl, AdvertName, StopDate,
            StartDate, AdvertContent, AdvertImage, Order, Location, ShopAdvertSeq;

    public String getShopAdvertSeq() {
        return ShopAdvertSeq;
    }

    public void setShopAdvertSeq(String shopAdvertSeq) {
        ShopAdvertSeq = shopAdvertSeq;
    }

    public String getAdvertUrl() {
        return AdvertUrl;
    }

    public void setAdvertUrl(String advertUrl) {
        AdvertUrl = advertUrl;
    }

    public String getAdvertName() {
        return AdvertName;
    }

    public void setAdvertName(String advertName) {
        AdvertName = advertName;
    }

    public String getStopDate() {
        return StopDate;
    }

    public void setStopDate(String stopDate) {
        StopDate = stopDate;
    }

    public String getStartDate() {
        return StartDate;
    }

    public void setStartDate(String startDate) {
        StartDate = startDate;
    }

    public String getAdvertContent() {
        return AdvertContent;
    }

    public void setAdvertContent(String advertContent) {
        AdvertContent = advertContent;
    }

    public String getAdvertImage() {
        return AdvertImage;
    }

    public void setAdvertImage(String advertImage) {
        AdvertImage = advertImage;
    }

    public String getOrder() {
        return Order;
    }

    public void setOrder(String order) {
        Order = order;
    }

    public String getLocation() {
        return Location;
    }

    public void setLocation(String location) {
        Location = location;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(AdvertUrl);
        dest.writeString(AdvertName);
        dest.writeString(StopDate);
        dest.writeString(StartDate);
        dest.writeString(AdvertContent);
        dest.writeString(AdvertImage);
        dest.writeString(Order);
        dest.writeString(Location);
        dest.writeString(ShopAdvertSeq);
    }

    public static final Creator<AdvertDetail> CREATOR = new Creator<AdvertDetail>() {

        @Override
        public AdvertDetail[] newArray(int size) {
            return null;
        }

        @Override
        public AdvertDetail createFromParcel(Parcel source) {
            AdvertDetail detail = new AdvertDetail();
            detail.AdvertUrl = source.readString();
            detail.AdvertName = source.readString();
            detail.StopDate = source.readString();
            detail.StartDate = source.readString();
            detail.AdvertContent = source.readString();
            detail.AdvertImage = source.readString();
            detail.Order = source.readString();
            detail.Location = source.readString();
            detail.ShopAdvertSeq = source.readString();
            return detail;
        }
    };

    public static List<AdvertDetail> getList(JSONObject jsonObject) {
        JSONArray array = JSONUtil.getJSONArray(jsonObject, "LocList1");
        Class<AdvertDetail[]> itemList = AdvertDetail[].class;
        List<AdvertDetail> list = GsonUtil.getListFromJSON(array.toString(), itemList);
        return list;
    }
}
