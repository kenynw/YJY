package com.miguan.yjy.model.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Copyright (c) 2017/4/9. LiaoPeiKun Inc. All rights reserved.
 */

public class BrandList implements Parcelable {

    private String[] letterArr;

    private List<Brand> cosmeticsList;

    public String[] getLetterArr() {
        return letterArr;
    }

    public void setLetterArr(String[] letterArr) {
        this.letterArr = letterArr;
    }

    public List<Brand> getCosmeticsList() {
        return cosmeticsList;
    }

    public void setCosmeticsList(List<Brand> cosmeticsList) {
        this.cosmeticsList = cosmeticsList;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringArray(this.letterArr);
        dest.writeTypedList(this.cosmeticsList);
    }

    public BrandList() {
    }

    protected BrandList(Parcel in) {
        this.letterArr = in.createStringArray();
        this.cosmeticsList = in.createTypedArrayList(Brand.CREATOR);
    }

    public static final Creator<BrandList> CREATOR = new Creator<BrandList>() {
        @Override
        public BrandList createFromParcel(Parcel source) {
            return new BrandList(source);
        }

        @Override
        public BrandList[] newArray(int size) {
            return new BrandList[size];
        }
    };
}
