package com.miguan.yjy.model.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @作者 cjh
 * @日期 2017/7/25 9:42
 * @描述
 */

public class Wiki implements Parcelable {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
    }

    public Wiki() {
    }

    protected Wiki(Parcel in) {
        this.name = in.readString();
    }

    public static final Creator<Wiki> CREATOR = new Creator<Wiki>() {
        @Override
        public Wiki createFromParcel(Parcel source) {
            return new Wiki(source);
        }

        @Override
        public Wiki[] newArray(int size) {
            return new Wiki[size];
        }
    };
}
