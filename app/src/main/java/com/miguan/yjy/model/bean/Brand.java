package com.miguan.yjy.model.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Copyright (c) 2017/3/28. LiaoPeiKun Inc. All rights reserved.
 */

public class Brand implements Parcelable {

    private int id;

    private String name;

    private String letter;

    private List<Brand> hot;

    private List<Brand> other;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.name);
        dest.writeString(this.letter);
        dest.writeTypedList(this.hot);
        dest.writeTypedList(this.other);
    }

    public Brand() {
    }

    protected Brand(Parcel in) {
        this.id = in.readInt();
        this.name = in.readString();
        this.letter = in.readString();
        this.hot = in.createTypedArrayList(Brand.CREATOR);
        this.other = in.createTypedArrayList(Brand.CREATOR);
    }

    public static final Creator<Brand> CREATOR = new Creator<Brand>() {
        @Override
        public Brand createFromParcel(Parcel source) {
            return new Brand(source);
        }

        @Override
        public Brand[] newArray(int size) {
            return new Brand[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLetter() {
        return letter;
    }

    public void setLetter(String letter) {
        this.letter = letter;
    }

    public List<Brand> getHot() {
        return hot;
    }

    public void setHot(List<Brand> hot) {
        this.hot = hot;
    }

    public List<Brand> getOther() {
        return other;
    }

    public void setOther(List<Brand> other) {
        this.other = other;
    }
}