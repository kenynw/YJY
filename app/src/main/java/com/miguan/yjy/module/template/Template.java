package com.miguan.yjy.module.template;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Copyright (c) 2017/5/5. LiaoPeiKun Inc. All rights reserved.
 */

public class Template implements Parcelable {

    private String product;

    private String brand;

    private String cate;

    private String title;

    private String desc;

    private String content;

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getCate() {
        return cate;
    }

    public void setCate(String cate) {
        this.cate = cate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.product);
        dest.writeString(this.brand);
        dest.writeString(this.cate);
        dest.writeString(this.title);
        dest.writeString(this.desc);
        dest.writeString(this.content);
    }

    public Template() {
    }

    protected Template(Parcel in) {
        this.product = in.readString();
        this.brand = in.readString();
        this.cate = in.readString();
        this.title = in.readString();
        this.desc = in.readString();
        this.content = in.readString();
    }

    public static final Creator<Template> CREATOR = new Creator<Template>() {
        @Override
        public Template createFromParcel(Parcel source) {
            return new Template(source);
        }

        @Override
        public Template[] newArray(int size) {
            return new Template[size];
        }
    };

//    public static Template getTemplate(int type) {
//        switch (type) {
//            case 0 :
//                break;
//            case 1:
//                break;
//            case 2:
//
//                break;
//        }
//    }

}
