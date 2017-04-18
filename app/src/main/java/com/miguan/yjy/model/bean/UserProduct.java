package com.miguan.yjy.model.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * Copyright (c) 2017/4/12. LiaoPeiKun Inc. All rights reserved.
 */

public class UserProduct implements Parcelable {

    private int id;

    private int user_id;

    private int brand_id;

    private String brand_name;

    private String product;

    private int is_seal;

    private long seal_time;

    private int quality_time;

    private long overdue_time;

    private long add_time;

    private String startDay;

    private String endDay;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeInt(this.user_id);
        dest.writeInt(this.brand_id);
        dest.writeString(this.brand_name);
        dest.writeString(this.product);
        dest.writeInt(this.is_seal);
        dest.writeLong(this.seal_time);
        dest.writeInt(this.quality_time);
        dest.writeLong(this.overdue_time);
        dest.writeLong(this.add_time);
        dest.writeString(this.startDay);
        dest.writeString(this.endDay);
    }

    public UserProduct() {
    }

    protected UserProduct(Parcel in) {
        this.id = in.readInt();
        this.user_id = in.readInt();
        this.brand_id = in.readInt();
        this.brand_name = in.readString();
        this.product = in.readString();
        this.is_seal = in.readInt();
        this.seal_time = in.readLong();
        this.quality_time = in.readInt();
        this.overdue_time = in.readLong();
        this.add_time = in.readLong();
        this.startDay = in.readString();
        this.endDay = in.readString();
    }

    public static final Creator<UserProduct> CREATOR = new Creator<UserProduct>() {
        @Override
        public UserProduct createFromParcel(Parcel source) {
            return new UserProduct(source);
        }

        @Override
        public UserProduct[] newArray(int size) {
            return new UserProduct[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getBrand_id() {
        return brand_id;
    }

    public void setBrand_id(int brand_id) {
        this.brand_id = brand_id;
    }

    public String getBrand_name() {
        return brand_name;
    }

    public void setBrand_name(String brand_name) {
        this.brand_name = brand_name;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public int getIs_seal() {
        return is_seal;
    }

    public void setIs_seal(int is_seal) {
        this.is_seal = is_seal;
    }

    public String getSeal_time() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
        return format.format(seal_time * 1000);
    }

    public void setSeal_time(long seal_time) {
        this.seal_time = seal_time;
    }

    public int getQuality_time() {
        return quality_time;
    }

    public void setQuality_time(int quality_time) {
        this.quality_time = quality_time;
    }

    public long getOverdue_time() {
        return overdue_time;
    }

    public void setOverdue_time(long overdue_time) {
        this.overdue_time = overdue_time;
    }

    public long getAdd_time() {
        return add_time;
    }

    public void setAdd_time(long add_time) {
        this.add_time = add_time;
    }

    public String getStartDay() {
        return startDay;
    }

    public void setStartDay(String startDay) {
        this.startDay = startDay;
    }

    public String getEndDay() {
        return endDay;
    }

    public void setEndDay(String endDay) {
        this.endDay = endDay;
    }

}
