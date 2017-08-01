package com.miguan.yjy.model.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Copyright (c) 2017/4/11. LiaoPeiKun Inc. All rights reserved.
 */

public class EntityList<T> implements Parcelable {

    private int pageTotal;

    private int pageSize;

    private T list;

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

    public EntityList() {
    }

    public T getList() {
        return list;
    }

    public void setList(T list) {
        this.list = list;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.pageTotal);
        dest.writeInt(this.pageSize);
        dest.writeString(this.list.getClass().getName());
    }

    protected EntityList(Parcel in) {
        this.pageTotal = in.readInt();
        this.pageSize = in.readInt();
        String className = in.readString();
        try {
            this.list = in.readParcelable(Class.forName(className).getClassLoader());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

}
