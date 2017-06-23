package com.miguan.yjy.adapter.viewholder;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Copyright (c) 2017/6/16. LiaoPeiKun Inc. All rights reserved.
 */

public class ArticleCate implements Parcelable {

    private int id;

    private String cate_name;

    private String cate_img;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCate_name() {
        return cate_name;
    }

    public void setCate_name(String cate_name) {
        this.cate_name = cate_name;
    }

    public String getCate_img() {
        return cate_img;
    }

    public void setCate_img(String cate_img) {
        this.cate_img = cate_img;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.cate_name);
        dest.writeString(this.cate_img);
    }

    public ArticleCate() {
    }

    protected ArticleCate(Parcel in) {
        this.id = in.readInt();
        this.cate_name = in.readString();
        this.cate_img = in.readString();
    }

    public static final Creator<ArticleCate> CREATOR = new Creator<ArticleCate>() {
        @Override
        public ArticleCate createFromParcel(Parcel source) {
            return new ArticleCate(source);
        }

        @Override
        public ArticleCate[] newArray(int size) {
            return new ArticleCate[size];
        }
    };
}
