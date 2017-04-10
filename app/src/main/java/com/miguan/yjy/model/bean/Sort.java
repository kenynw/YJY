package com.miguan.yjy.model.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @作者 cjh
 * @日期 2017/4/10 14:54
 * @描述
 */
public class Sort implements Parcelable {
    private int id;
    private String cate_name;

    protected Sort(Parcel in) {
        id = in.readInt();
        cate_name = in.readString();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCate_name() {
        return cate_name;
    }

    public void setCate_name(String cate_name) {
        this.cate_name = cate_name;
    }


    public static final Creator<Sort> CREATOR = new Creator<Sort>() {
        @Override
        public Sort createFromParcel(Parcel in) {
            return new Sort(in);
        }

        @Override
        public Sort[] newArray(int size) {
            return new Sort[size];
        }
    };


    @Override

    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(cate_name);
    }
}