package com.miguan.yjy.model.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Copyright (c) 2017/8/28. LiaoPeiKun Inc. All rights reserved.
 */

public class Benefit implements Parcelable {

    private int id;

    private String picture;

    private int type;

    private String prize_num;

    private String prize;

    private String relation;

    private int starttime;

    private int endtime;

    private int current_time;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getPrize_num() {
        return prize_num;
    }

    public void setPrize_num(String prize_num) {
        this.prize_num = prize_num;
    }

    public String getPrize() {
        return prize;
    }

    public void setPrize(String prize) {
        this.prize = prize;
    }

    public String getRelation() {
        return relation;
    }

    public void setRelation(String relation) {
        this.relation = relation;
    }

    public int getStarttime() {
        return starttime;
    }

    public void setStarttime(int starttime) {
        this.starttime = starttime;
    }

    public int getEndtime() {
        return endtime;
    }

    public void setEndtime(int endtime) {
        this.endtime = endtime;
    }

    public int getCurrent_time() {
        return current_time;
    }

    public void setCurrent_time(int current_time) {
        this.current_time = current_time;
    }

    public Benefit() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.picture);
        dest.writeInt(this.type);
        dest.writeString(this.prize_num);
        dest.writeString(this.prize);
        dest.writeString(this.relation);
        dest.writeInt(this.starttime);
        dest.writeInt(this.endtime);
        dest.writeInt(this.current_time);
    }

    protected Benefit(Parcel in) {
        this.id = in.readInt();
        this.picture = in.readString();
        this.type = in.readInt();
        this.prize_num = in.readString();
        this.prize = in.readString();
        this.relation = in.readString();
        this.starttime = in.readInt();
        this.endtime = in.readInt();
        this.current_time = in.readInt();
    }

    public static final Creator<Benefit> CREATOR = new Creator<Benefit>() {
        @Override
        public Benefit createFromParcel(Parcel source) {
            return new Benefit(source);
        }

        @Override
        public Benefit[] newArray(int size) {
            return new Benefit[size];
        }
    };

}
