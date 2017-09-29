package com.miguan.yjy.model.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Copyright (c) 2017/9/21. LiaoPeiKun Inc. All rights reserved.
 */

public class Bill implements Parcelable {

    private int id;

    private int invent_id;

    private String title;

    private String desc;

    private String picture;

    private String add_time;

    private int num;

    private int order;

    private int product_id;

    private String product_name;

    private String product_img;

    private String price;

    private String form;

    private int star;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getInvent_id() {
        return invent_id;
    }

    public void setInvent_id(int invent_id) {
        this.invent_id = invent_id;
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

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getAdd_time() {
        return add_time;
    }

    public void setAdd_time(String add_time) {
        this.add_time = add_time;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getProduct_img() {
        return product_img;
    }

    public void setProduct_img(String product_img) {
        this.product_img = product_img;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getForm() {
        return form;
    }

    public void setForm(String form) {
        this.form = form;
    }

    public int getStar() {
        return star;
    }

    public void setStar(int star) {
        this.star = star;
    }

    public Bill() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeInt(this.invent_id);
        dest.writeString(this.title);
        dest.writeString(this.desc);
        dest.writeString(this.picture);
        dest.writeString(this.add_time);
        dest.writeInt(this.num);
        dest.writeInt(this.order);
        dest.writeInt(this.product_id);
        dest.writeString(this.product_name);
        dest.writeString(this.product_img);
        dest.writeString(this.price);
        dest.writeString(this.form);
        dest.writeInt(this.star);
    }

    protected Bill(Parcel in) {
        this.id = in.readInt();
        this.invent_id = in.readInt();
        this.title = in.readString();
        this.desc = in.readString();
        this.picture = in.readString();
        this.add_time = in.readString();
        this.num = in.readInt();
        this.order = in.readInt();
        this.product_id = in.readInt();
        this.product_name = in.readString();
        this.product_img = in.readString();
        this.price = in.readString();
        this.form = in.readString();
        this.star = in.readInt();
    }

    public static final Creator<Bill> CREATOR = new Creator<Bill>() {
        @Override
        public Bill createFromParcel(Parcel source) {
            return new Bill(source);
        }

        @Override
        public Bill[] newArray(int size) {
            return new Bill[size];
        }
    };
}
