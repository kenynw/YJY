package com.miguan.yjy.model.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Copyright (c) 2017/8/22. LiaoPeiKun Inc. All rights reserved.
 */

public class Discover implements Parcelable {

    private List<Banner> banner;

    private List<Skin> userSkin;

    private List<Ask> ask;

    private List<Wiki> baike;

    public List<Banner> getBanner() {
        return banner;
    }

    public void setBanner(List<Banner> banner) {
        this.banner = banner;
    }

    public List<Skin> getUserSkin() {
        return userSkin;
    }

    public void setUserSkin(List<Skin> userSkin) {
        this.userSkin = userSkin;
    }

    public List<Ask> getAsk() {
        return ask;
    }

    public void setAsk(List<Ask> ask) {
        this.ask = ask;
    }

    public List<Wiki> getBaike() {
        return baike;
    }

    public void setBaike(List<Wiki> baike) {
        this.baike = baike;
    }

    public Discover() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(this.banner);
        dest.writeTypedList(this.userSkin);
        dest.writeTypedList(this.ask);
        dest.writeTypedList(this.baike);
    }

    protected Discover(Parcel in) {
        this.banner = in.createTypedArrayList(Banner.CREATOR);
        this.userSkin = in.createTypedArrayList(Skin.CREATOR);
        this.ask = in.createTypedArrayList(Ask.CREATOR);
        this.baike = in.createTypedArrayList(Wiki.CREATOR);
    }

    public static final Creator<Discover> CREATOR = new Creator<Discover>() {
        @Override
        public Discover createFromParcel(Parcel source) {
            return new Discover(source);
        }

        @Override
        public Discover[] newArray(int size) {
            return new Discover[size];
        }
    };
}
