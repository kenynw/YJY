package com.miguan.yjy.model.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * Copyright (c) 2017/4/25. LiaoPeiKun Inc. All rights reserved.
 */

public class FaceScore implements Parcelable {

    private String money;

    private String content;

    private long created_at;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.money);
        dest.writeString(this.content);
        dest.writeLong(this.created_at);
    }

    public FaceScore() {
    }

    protected FaceScore(Parcel in) {
        this.money = in.readString();
        this.content = in.readString();
        this.created_at = in.readLong();
    }

    public static final Creator<FaceScore> CREATOR = new Creator<FaceScore>() {
        @Override
        public FaceScore createFromParcel(Parcel source) {
            return new FaceScore(source);
        }

        @Override
        public FaceScore[] newArray(int size) {
            return new FaceScore[size];
        }
    };

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreated_at() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
        return format.format(created_at);
    }

    public void setCreated_at(long created_at) {
        this.created_at = created_at;
    }
}
