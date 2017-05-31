package com.miguan.yjy.model.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * @作者 cjh
 * @日期 2017/5/19 14:18
 * @描述
 */

public class BrandAll implements Parcelable {

    private Brand brandInfo;
    private List<Brand> otherBrand;

    public Brand getBrandInfo() {
        return brandInfo;
    }

    public void setBrandInfo(Brand brandInfo) {
        this.brandInfo = brandInfo;
    }

    public List<Brand> getOtherBrand() {
        return otherBrand;
    }

    public void setOtherBrand(List<Brand> otherBrand) {
        this.otherBrand = otherBrand;
    }

    public BrandAll() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.brandInfo, flags);
        dest.writeTypedList(this.otherBrand);
    }

    protected BrandAll(Parcel in) {
        this.brandInfo = in.readParcelable(Brand.class.getClassLoader());
        this.otherBrand = in.createTypedArrayList(Brand.CREATOR);
    }

    public static final Creator<BrandAll> CREATOR = new Creator<BrandAll>() {
        @Override
        public BrandAll createFromParcel(Parcel source) {
            return new BrandAll(source);
        }

        @Override
        public BrandAll[] newArray(int size) {
            return new BrandAll[size];
        }
    };
}
