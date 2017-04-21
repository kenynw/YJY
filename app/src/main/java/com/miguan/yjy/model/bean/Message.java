package com.miguan.yjy.model.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * Copyright (c) 2016/12/26. LiaoPeiKun Inc. All rights reserved.
 */

public class Message implements Parcelable {

    private int id;

    private String user_name;

    private String content;

    private long created_at;

    private String img;

    private int type;

    /**
     * 过期产品数
     */
    private int overdueNum;

    /**
     * 未读消息数
     */
    private int unReadNUM;

    public Message() {
    }

    public int getOverdueNum() {
        return overdueNum;
    }

    public void setOverdueNum(int overdueNum) {
        this.overdueNum = overdueNum;
    }

    public int getUnReadNUM() {
        return unReadNUM;
    }

    public void setUnReadNUM(int unReadNUM) {
        this.unReadNUM = unReadNUM;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreated_at() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
        return format.format(created_at * 1000);
    }

    public void setCreated_at(long created_at) {
        this.created_at = created_at;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.user_name);
        dest.writeString(this.content);
        dest.writeLong(this.created_at);
        dest.writeString(this.img);
        dest.writeInt(this.type);
        dest.writeInt(this.overdueNum);
        dest.writeInt(this.unReadNUM);
    }

    protected Message(Parcel in) {
        this.id = in.readInt();
        this.user_name = in.readString();
        this.content = in.readString();
        this.created_at = in.readLong();
        this.img = in.readString();
        this.type = in.readInt();
        this.overdueNum = in.readInt();
        this.unReadNUM = in.readInt();
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
}
