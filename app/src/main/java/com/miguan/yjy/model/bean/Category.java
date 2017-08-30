package com.miguan.yjy.model.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Copyright (c) 2017/3/20. LiaoPeiKun Inc. All rights reserved.
 */

public class Category implements Parcelable {


//    id(int) － 一级分类ID
//    parent_id(int) － 父分类ID
//    cate_name(string) － 分类名
//    cate_app_img(string) － app分类图标
//    cate_h5_img(string) － h5分类图标
//    sub(array) － 二级分类信息

    private int id;
    private int parent_id;
    private String cate_name;
    @SerializedName(value = "cate_img", alternate = {"cate_app_img"})
    private String cate_img;
    private boolean select;

    public boolean isSelect() {
        return select;
    }

    public void setSelect(boolean select) {
        this.select = select;
    }

    public String getCate_h5_img() {
        return cate_h5_img;
    }

    public void setCate_h5_img(String cate_h5_img) {
        this.cate_h5_img = cate_h5_img;
    }

    private String cate_h5_img;
    private List<Category> sub;


    public String getCate_name() {
        return cate_name;
    }

    public int getParent_id() {
        return parent_id;
    }

    public void setParent_id(int parent_id) {
        this.parent_id = parent_id;
    }


    public List<Category> getSub() {
        return sub;
    }

    public void setSub(List<Category> sub) {
        this.sub = sub;
    }

    public Category() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getCate_img() {
        return cate_img;
    }

    public void setCate_img(String cate_img) {
        this.cate_img = cate_img;
    }

    public void setCate_name(String cate_name) {
        this.cate_name = cate_name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeInt(this.parent_id);
        dest.writeString(this.cate_name);
        dest.writeString(this.cate_img);
        dest.writeByte(this.select ? (byte) 1 : (byte) 0);
        dest.writeString(this.cate_h5_img);
        dest.writeTypedList(this.sub);
    }

    protected Category(Parcel in) {
        this.id = in.readInt();
        this.parent_id = in.readInt();
        this.cate_name = in.readString();
        this.cate_img = in.readString();
        this.select = in.readByte() != 0;
        this.cate_h5_img = in.readString();
        this.sub = in.createTypedArrayList(Category.CREATOR);
    }

    public static final Creator<Category> CREATOR = new Creator<Category>() {
        @Override
        public Category createFromParcel(Parcel source) {
            return new Category(source);
        }

        @Override
        public Category[] newArray(int size) {
            return new Category[size];
        }
    };
}
