package com.miguan.yjy.model.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Copyright (c) 2017/4/11. LiaoPeiKun Inc. All rights reserved.
 */

public class EntityList implements Parcelable {

    private int pageTotal;

    private int pageSize;

    public int getPageTotal() {
        return pageTotal;
    }

    public void setPageTotal(int pageTotal) {
        this.pageTotal = pageTotal;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.pageTotal);
        dest.writeInt(this.pageSize);
    }

    public EntityList() {
    }

    protected EntityList(Parcel in) {
        this.pageTotal = in.readInt();
        this.pageSize = in.readInt();
    }

    public static final Creator<EntityList> CREATOR = new Creator<EntityList>() {
        @Override
        public EntityList createFromParcel(Parcel source) {
            return new EntityList(source);
        }

        @Override
        public EntityList[] newArray(int size) {
            return new EntityList[size];
        }
    };
}
