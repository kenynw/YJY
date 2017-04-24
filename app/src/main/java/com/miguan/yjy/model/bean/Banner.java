package com.miguan.yjy.model.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Copyright (c) 2017/1/12. LiaoPeiKun Inc. All rights reserved.
 */

public class Banner implements Parcelable {

    private int id;

    private String title;

    private String img;

    private String url;

    private int type;

    private int relation_id;

    public Banner() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImg() {
        return img;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getRelation_id() {
        return relation_id;
    }

    public void setRelation_id(int relation_id) {
        this.relation_id = relation_id;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.title);
        dest.writeString(this.img);
        dest.writeString(this.url);
        dest.writeInt(this.type);
        dest.writeInt(this.relation_id);
    }

    protected Banner(Parcel in) {
        this.id = in.readInt();
        this.title = in.readString();
        this.img = in.readString();
        this.url = in.readString();
        this.type = in.readInt();
        this.relation_id = in.readInt();
    }

    public static final Creator<Banner> CREATOR = new Creator<Banner>() {
        @Override
        public Banner createFromParcel(Parcel source) {
            return new Banner(source);
        }

        @Override
        public Banner[] newArray(int size) {
            return new Banner[size];
        }
    };
}
