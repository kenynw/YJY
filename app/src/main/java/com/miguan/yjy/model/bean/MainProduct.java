package com.miguan.yjy.model.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Copyright (c) 2017/8/22. LiaoPeiKun Inc. All rights reserved.
 */

public class MainProduct implements Parcelable {

    private int sum;

    private List<Category> categoryList;

    private List<Brand> brandList;

    private List<Rank> rankingList;

    public int getSum() {
        return sum;
    }

    public void setSum(int sum) {
        this.sum = sum;
    }

    public List<Category> getCategoryList() {
        return categoryList;
    }

    public void setCategoryList(List<Category> categoryList) {
        this.categoryList = categoryList;
    }

    public List<Brand> getBrandList() {
        return brandList;
    }

    public void setBrandList(List<Brand> brandList) {
        this.brandList = brandList;
    }

    public List<Rank> getRankingList() {
        return rankingList;
    }

    public void setRankingList(List<Rank> rankingList) {
        this.rankingList = rankingList;
    }

    public MainProduct() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.sum);
        dest.writeTypedList(this.categoryList);
        dest.writeTypedList(this.brandList);
        dest.writeTypedList(this.rankingList);
    }

    protected MainProduct(Parcel in) {
        this.sum = in.readInt();
        this.categoryList = in.createTypedArrayList(Category.CREATOR);
        this.brandList = in.createTypedArrayList(Brand.CREATOR);
        this.rankingList = in.createTypedArrayList(Rank.CREATOR);
    }

    public static final Creator<MainProduct> CREATOR = new Creator<MainProduct>() {
        @Override
        public MainProduct createFromParcel(Parcel source) {
            return new MainProduct(source);
        }

        @Override
        public MainProduct[] newArray(int size) {
            return new MainProduct[size];
        }
    };
}
