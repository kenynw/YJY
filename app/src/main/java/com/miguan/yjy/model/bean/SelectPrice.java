package com.miguan.yjy.model.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @作者 cjh
 * @日期 2017/6/23 14:08
 * @描述
 */

public class SelectPrice implements Parcelable {

    private float min;
    private float max;

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

    private boolean isSelect=false;

    public SelectPrice() {
    }

    public float getMin() {
        return min;
    }

    public void setMin(float min) {
        this.min = min;
    }

    public float getMax() {
        return max;
    }

    public void setMax(float max) {
        this.max = max;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeFloat(this.min);
        dest.writeFloat(this.max);
        dest.writeByte(this.isSelect ? (byte) 1 : (byte) 0);
    }

    protected SelectPrice(Parcel in) {
        this.min = in.readFloat();
        this.max = in.readFloat();
        this.isSelect = in.readByte() != 0;
    }

    public static final Creator<SelectPrice> CREATOR = new Creator<SelectPrice>() {
        @Override
        public SelectPrice createFromParcel(Parcel source) {
            return new SelectPrice(source);
        }

        @Override
        public SelectPrice[] newArray(int size) {
            return new SelectPrice[size];
        }
    };
}
