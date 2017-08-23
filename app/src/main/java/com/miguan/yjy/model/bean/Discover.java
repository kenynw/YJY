package com.miguan.yjy.model.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Copyright (c) 2017/8/22. LiaoPeiKun Inc. All rights reserved.
 */

public class Discover implements Parcelable {

    protected Discover(Parcel in) {
    }

    public static final Creator<Discover> CREATOR = new Creator<Discover>() {
        @Override
        public Discover createFromParcel(Parcel in) {
            return new Discover(in);
        }

        @Override
        public Discover[] newArray(int size) {
            return new Discover[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }

}
