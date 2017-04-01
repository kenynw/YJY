package com.miguan.yjy.model.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Copyright (c) 2016/12/26. LiaoPeiKun Inc. All rights reserved.
 */

public class Message implements Parcelable {

    private String id;

    private String message;

    private String create_id;

    public Message() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.message);
        dest.writeString(this.create_id);
    }

    protected Message(Parcel in) {
        this.id = in.readString();
        this.message = in.readString();
        this.create_id = in.readString();
    }

    public static final Creator<Message> CREATOR = new Creator<Message>() {
        @Override
        public Message createFromParcel(Parcel source) {
            return new Message(source);
        }

        @Override
        public Message[] newArray(int size) {
            return new Message[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCreate_id() {
        return create_id;
    }

    public void setCreate_id(String create_id) {
        this.create_id = create_id;
    }
}
