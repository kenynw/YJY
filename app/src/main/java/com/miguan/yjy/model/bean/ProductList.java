package com.miguan.yjy.model.bean;

import android.os.Parcel;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Copyright (c) 2017/4/9. LiaoPeiKun Inc. All rights reserved.
 */

public class ProductList extends EntityList {

    private List<Product> data;

    @SerializedName(value = "categories", alternate = { "categroy" })
    private List<Category> categories;

    private String[] effects;

    public List<Product> getData() {
        return data;
    }

    public void setData(List<Product> data) {
        this.data = data;
    }

    public List<Category> getCategroy() {
        return categories;
    }

    public void setCategroy(List<Category> categroy) {
        this.categories = categroy;
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
        dest.writeTypedList(this.categories);
        dest.writeStringArray(this.effects);
    }

    public ProductList() {
    }

    protected ProductList(Parcel in) {
        this.data = in.createTypedArrayList(Product.CREATOR);
        this.categories = in.createTypedArrayList(Category.CREATOR);
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
