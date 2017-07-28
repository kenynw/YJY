package com.miguan.yjy.model.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Copyright (c) 2017/7/27. LiaoPeiKun Inc. All rights reserved.
 */

public class Billboard implements Parcelable {

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }

    public Billboard() {
    }

    protected Billboard(Parcel in) {
    }

    public static final Creator<Billboard> CREATOR = new Creator<Billboard>() {
        @Override
        public Billboard createFromParcel(Parcel source) {
            return new Billboard(source);
        }

        @Override
        public Billboard[] newArray(int size) {
            return new Billboard[size];
        }
    };
}
