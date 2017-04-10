package com.miguan.yjy.model.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Copyright (c) 2017/4/9. LiaoPeiKun Inc. All rights reserved.
 */

public class LikeProductList implements Parcelable {
    private List<Product> data;

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

    public LikeProductList() {
    }

    protected LikeProductList(Parcel in) {
        this.data = in.createTypedArrayList(Product.CREATOR);
        this.categroy = in.createTypedArrayList(Category.CREATOR);
        this.effects = in.createStringArray();
    }

    public static final Creator<LikeProductList> CREATOR = new Creator<LikeProductList>() {
        @Override
        public LikeProductList createFromParcel(Parcel source) {
            return new LikeProductList(source);
        }

        @Override
        public LikeProductList[] newArray(int size) {
            return new LikeProductList[size];
        }
    };
}
