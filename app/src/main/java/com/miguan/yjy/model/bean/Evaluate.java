package com.miguan.yjy.model.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Copyright (c) 2017/3/24. LiaoPeiKun Inc. All rights reserved.
 */

public class Evaluate implements Parcelable {

    private int id;

    private int user_id;

    private int type;

    private String comment;

    private int like_num;

    private String username;

    private String img;

    private String birth_year;

    private String skin;

    private long created_at;

    private Item detail;

    private int age;

    private int isLike;

    private int pageTotal;

    private int pageSize;

    public Evaluate() {
    }

    public int getId() {
        return id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getCreated_at() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(new Date(created_at * 1000));
    }

    public void setCreated_at(long created_at) {
        this.created_at = created_at;
    }

    public Item getDetail() {
        return detail;
    }

    public void setDetail(Item detail) {
        this.detail = detail;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getLike_num() {
        return like_num;
    }

    public void setLike_num(int like_num) {
        this.like_num = like_num;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getBirth_year() {
        return birth_year;
    }

    public void setBirth_year(String birth_year) {
        this.birth_year = birth_year;
    }

    public int getPageTotal() {
        return pageTotal;
    }

    public void setPageTotal(int pageTotal) {
        this.pageTotal = pageTotal;
    }

    public String getSkin() {
        return skin;
    }

    public void setSkin(String skin) {
        this.skin = skin;
    }

    public int getIsLike() {
        return isLike;
    }

    public void setIsLike(int isLike) {
        this.isLike = isLike;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public static class Item implements Parcelable {
        private int id;

        private String name;

        private String img;

        private String price;

        private String form;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public int getId() {
            return id;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getForm() {
            return form.toUpperCase();
        }

        public void setForm(String form) {
            this.form = form;
        }

        public void setId(int id) {
            this.id = id;
        }

        public Item() {
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.id);
            dest.writeString(this.name);
            dest.writeString(this.img);
            dest.writeString(this.price);
            dest.writeString(this.form);
        }

        protected Item(Parcel in) {
            this.id = in.readInt();
            this.name = in.readString();
            this.img = in.readString();
            this.price = in.readString();
            this.form = in.readString();
        }

        public static final Creator<Item> CREATOR = new Creator<Item>() {
            @Override
            public Item createFromParcel(Parcel source) {
                return new Item(source);
            }

            @Override
            public Item[] newArray(int size) {
                return new Item[size];
            }
        };
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeInt(this.user_id);
        dest.writeInt(this.type);
        dest.writeString(this.comment);
        dest.writeInt(this.like_num);
        dest.writeString(this.username);
        dest.writeString(this.img);
        dest.writeString(this.birth_year);
        dest.writeString(this.skin);
        dest.writeLong(this.created_at);
        dest.writeParcelable(this.detail, flags);
        dest.writeInt(this.age);
        dest.writeInt(this.isLike);
        dest.writeInt(this.pageTotal);
        dest.writeInt(this.pageSize);
    }

    protected Evaluate(Parcel in) {
        this.id = in.readInt();
        this.user_id = in.readInt();
        this.type = in.readInt();
        this.comment = in.readString();
        this.like_num = in.readInt();
        this.username = in.readString();
        this.img = in.readString();
        this.birth_year = in.readString();
        this.skin = in.readString();
        this.created_at = in.readLong();
        this.detail = in.readParcelable(Item.class.getClassLoader());
        this.age = in.readInt();
        this.isLike = in.readInt();
        this.pageTotal = in.readInt();
        this.pageSize = in.readInt();
    }

    public static final Creator<Evaluate> CREATOR = new Creator<Evaluate>() {
        @Override
        public Evaluate createFromParcel(Parcel source) {
            return new Evaluate(source);
        }

        @Override
        public Evaluate[] newArray(int size) {
            return new Evaluate[size];
        }
    };
}
