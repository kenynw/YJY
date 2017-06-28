package com.miguan.yjy.model.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Copyright (c) 2017/6/23. LiaoPeiKun Inc. All rights reserved.
 */

public class Ask implements Parcelable {

    private int product_id;

    private String product_name;

    private String product_img;

    private int type; // 1 有提问 2 没有提问

    private int askid;

    private String subject;

    private String reply;

    private int num;

    private long add_time;

    private User user_info;

    private List<Ask> question_list;

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getProduct_img() {
        return product_img;
    }

    public void setProduct_img(String product_img) {
        this.product_img = product_img;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getAskid() {
        return askid;
    }

    public void setAskid(int askid) {
        this.askid = askid;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getReply() {
        return reply;
    }

    public void setReply(String reply) {
        this.reply = reply;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getAdd_time() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA);
        return format.format(new Date(add_time * 1000));
    }

    public void setAdd_time(long add_time) {
        this.add_time = add_time;
    }

    public List<Ask> getQuestion_list() {
        return question_list;
    }

    public void setQuestion_list(List<Ask> question_list) {
        this.question_list = question_list;
    }

    public User getUser_info() {
        return user_info;
    }

    public void setUser_info(User user_info) {
        this.user_info = user_info;
    }

    public Ask() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.product_id);
        dest.writeString(this.product_name);
        dest.writeString(this.product_img);
        dest.writeInt(this.type);
        dest.writeInt(this.askid);
        dest.writeString(this.subject);
        dest.writeString(this.reply);
        dest.writeInt(this.num);
        dest.writeLong(this.add_time);
        dest.writeParcelable(this.user_info, flags);
        dest.writeTypedList(this.question_list);
    }

    protected Ask(Parcel in) {
        this.product_id = in.readInt();
        this.product_name = in.readString();
        this.product_img = in.readString();
        this.type = in.readInt();
        this.askid = in.readInt();
        this.subject = in.readString();
        this.reply = in.readString();
        this.num = in.readInt();
        this.add_time = in.readLong();
        this.user_info = in.readParcelable(User.class.getClassLoader());
        this.question_list = in.createTypedArrayList(Ask.CREATOR);
    }

    public static final Creator<Ask> CREATOR = new Creator<Ask>() {
        @Override
        public Ask createFromParcel(Parcel source) {
            return new Ask(source);
        }

        @Override
        public Ask[] newArray(int size) {
            return new Ask[size];
        }
    };
}
