package com.miguan.yjy.model.bean;

import android.os.Parcel;
import android.os.Parcelable;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Unique;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Copyright (c) 2017/3/28. LiaoPeiKun Inc. All rights reserved.
 */
@Entity
public class Brand implements Parcelable {

    @Id(autoincrement = true)
    @Unique
    private Long id;

    private String name;

    private String letter;

    private boolean isLocal;

    private String img;

    private int hot;

    private String description;
    private String relevantArticle;


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLetter() {
        return letter;
    }

    public void setLetter(String letter) {
        this.letter = letter;
    }

    public boolean isLocal() {
        return isLocal;
    }

    public void setLocal(boolean local) {
        isLocal = local;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public int getHot() {
        return hot;
    }

    public void setHot(int hot) {
        this.hot = hot;
    }

    public boolean getIsLocal() {
        return this.isLocal;
    }

    public void setIsLocal(boolean isLocal) {
        this.isLocal = isLocal;
    }

    public Brand() {
    }

    @Generated(hash = 1202386878)
    public Brand(Long id, String name, String letter, boolean isLocal, String img,
            int hot, String description, String relevantArticle) {
        this.id = id;
        this.name = name;
        this.letter = letter;
        this.isLocal = isLocal;
        this.img = img;
        this.hot = hot;
        this.description = description;
        this.relevantArticle = relevantArticle;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.id);
        dest.writeString(this.name);
        dest.writeString(this.letter);
        dest.writeByte(this.isLocal ? (byte) 1 : (byte) 0);
        dest.writeString(this.img);
        dest.writeInt(this.hot);
        dest.writeString(this.description);
        dest.writeString(this.relevantArticle);
    }

    public String getRelevantArticle() {
        return this.relevantArticle;
    }

    public void setRelevantArticle(String relevantArticle) {
        this.relevantArticle = relevantArticle;
    }

    protected Brand(Parcel in) {
        this.id = (Long) in.readValue(Long.class.getClassLoader());
        this.name = in.readString();
        this.letter = in.readString();
        this.isLocal = in.readByte() != 0;
        this.img = in.readString();
        this.hot = in.readInt();
        this.description = in.readString();
        this.relevantArticle = in.readString();
    }

    public static final Creator<Brand> CREATOR = new Creator<Brand>() {
        @Override
        public Brand createFromParcel(Parcel source) {
            return new Brand(source);
        }

        @Override
        public Brand[] newArray(int size) {
            return new Brand[size];
        }
    };
}