package com.miguan.yjy.model.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * @作者 cjh
 * @日期 2017/4/12 10:53
 * @描述
 */

public class ComponentTag implements Parcelable {

    private String name;
    private List<String> id;

    public List<String> getId() {
        return id;
    }

    public void setId(List<String> id) {
        this.id = id;
    }

    public String getName() {

        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ComponentTag() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeStringList(this.id);
    }

    protected ComponentTag(Parcel in) {
        this.name = in.readString();
        this.id = in.createStringArrayList();
    }

    public static final Creator<ComponentTag> CREATOR = new Creator<ComponentTag>() {
        @Override
        public ComponentTag createFromParcel(Parcel source) {
            return new ComponentTag(source);
        }

        @Override
        public ComponentTag[] newArray(int size) {
            return new ComponentTag[size];
        }
    };
}
