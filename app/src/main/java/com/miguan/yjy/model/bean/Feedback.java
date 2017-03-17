package com.miguan.yjy.model.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Copyright (c) 2017/1/16. LiaoPeiKun Inc. All rights reserved.
 */

public class Feedback implements Parcelable {

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }

    public Feedback() {
    }

    protected Feedback(Parcel in) {
    }

    public static final Creator<Feedback> CREATOR = new Creator<Feedback>() {
        @Override
        public Feedback createFromParcel(Parcel source) {
            return new Feedback(source);
        }

        @Override
        public Feedback[] newArray(int size) {
            return new Feedback[size];
        }
    };

}
