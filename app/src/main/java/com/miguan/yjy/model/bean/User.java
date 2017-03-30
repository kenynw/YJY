package com.miguan.yjy.model.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Copyright (c) 2015. LiaoPeiKun Inc. All rights reserved.
 */

public class User implements Parcelable {

    private String token;

    @SerializedName("userId")
    private int user_id;

    private String usernName;

    private String userImg;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getUsernName() {
        return usernName;
    }

    public void setUsernName(String usernName) {
        this.usernName = usernName;
    }

    public String getUserImg() {
        return userImg;
    }

    public void setUserImg(String userImg) {
        this.userImg = userImg;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.token);
        dest.writeInt(this.user_id);
        dest.writeString(this.usernName);
        dest.writeString(this.userImg);
    }

    public User() {
    }

    protected User(Parcel in) {
        this.token = in.readString();
        this.user_id = in.readInt();
        this.usernName = in.readString();
        this.userImg = in.readString();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel source) {
            return new User(source);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };
}
