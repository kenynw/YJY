package com.miguan.yjy.model.bean;

import android.os.Parcel;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Copyright (c) 2017/4/9. LiaoPeiKun Inc. All rights reserved.
 */

public class ProductList extends EntityList {

    @SerializedName(value = "data", alternate = { "list" })
    private List<Product> data;

    @SerializedName(value = "categroy", alternate = { "categories" })
    private List<Category> categroy;

    private String[] effects;

    public List<Product> getData() {
        return data;
    }

    public void setData(List<Product> data) {
        this.data = data;
    }

    public List<Category> getCategroy() {
        return categroy;
    }

    public void setCategroy(List<Category> categroy) {
        this.categroy = categroy;
    }

    public String[] getEffects() {
        return effects;
    }

    public void setEffects(String[] effects) {
        this.effects = effects;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(this.data);
        dest.writeTypedList(this.categroy);
        dest.writeStringArray(this.effects);
    }

    public ProductList() {
    }

    protected ProductList(Parcel in) {
        this.data = in.createTypedArrayList(Product.CREATOR);
        this.categroy = in.createTypedArrayList(Category.CREATOR);
        this.effects = in.createStringArray();
    }

    public static final Creator<ProductList> CREATOR = new Creator<ProductList>() {
        @Override
        public ProductList createFromParcel(Parcel source) {
            return new ProductList(source);
        }

        @Override
        public ProductList[] newArray(int size) {
            return new ProductList[size];
        }
    };
}
