package com.android.collect.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.android.collect.data.BaseData;

/**
 * Created by qinshunan on 2017/9/28.
 */

public class LearnDataDetail implements Parcelable {


    private String address, target, content, title;
    private BaseData mBaseData;

    public LearnDataDetail() {
    }

    public LearnDataDetail(String title, BaseData baseData) {
        this.title = title;
        mBaseData = baseData;
    }

    public LearnDataDetail(String title, String content, String address) {
        this.title = title;
        this.content = content;
        this.address = address;
    }

    public LearnDataDetail(String title, String content, String target, String address) {
        this.title = title;
        this.content = content;
        this.target = target;
        this.address = address;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public BaseData getBaseData() {
        return mBaseData;
    }

    public void setBaseData(BaseData baseData) {
        mBaseData = baseData;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(address);
        dest.writeString(target);
        dest.writeString(content);
        dest.writeString(title);
    }

    public static final Parcelable.Creator<LearnDataDetail> CREATOR = new Parcelable.Creator<LearnDataDetail>() {

        @Override
        public LearnDataDetail[] newArray(int size) {
            return null;
        }

        @Override
        public LearnDataDetail createFromParcel(Parcel source) {
            LearnDataDetail detail = new LearnDataDetail();
            detail.address = source.readString();
            detail.target = source.readString();
            detail.content = source.readString();
            detail.title = source.readString();
            return detail;
        }
    };

}
