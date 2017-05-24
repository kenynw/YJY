package com.miguan.yjy.model.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Copyright (c) 2017/4/11. LiaoPeiKun Inc. All rights reserved.
 */

public class EntityRoot<T> implements Parcelable {

    private int status;

    private int pageTotal;

    private int pageSize;

    private T msg;

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

    public T getData() {
        return msg;
    }

    public void setData(T list) {
        this.msg = list;
    }

    public EntityRoot() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.pageTotal);
        dest.writeInt(this.pageSize);
        dest.writeString(this.msg.getClass().getName());
    }

    protected EntityRoot(Parcel in) {
        this.pageTotal = in.readInt();
        this.pageSize = in.readInt();
        String className = in.readString();
        try {
            this.msg = in.readParcelable(Class.forName(className).getClassLoader());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static final Creator<EntityRoot> CREATOR = new Creator<EntityRoot>() {
        @Override
        public EntityRoot createFromParcel(Parcel source) {
            return new EntityRoot(source);
        }

        @Override
        public EntityRoot[] newArray(int size) {
            return new EntityRoot[size];
        }
    };
}
