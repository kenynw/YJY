package com.miguan.yjy.model.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Copyright (c) 2015. LiaoPeiKun Inc. All rights reserved.
 */

public class Article implements Parcelable {

    private int id;

    private String title;

    private String article_img;

    private int like_num;

    private String created_at;

    private List<Evaluate> evaluates;

    private int pageTotal;

    private int pageSize;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.title);
        dest.writeString(this.article_img);
        dest.writeInt(this.like_num);
        dest.writeString(this.created_at);
        dest.writeTypedList(this.evaluates);
        dest.writeInt(this.pageTotal);
        dest.writeInt(this.pageSize);
    }

    public Article() {
    }

    protected Article(Parcel in) {
        this.id = in.readInt();
        this.title = in.readString();
        this.article_img = in.readString();
        this.like_num = in.readInt();
        this.created_at = in.readString();
        this.evaluates = in.createTypedArrayList(Evaluate.CREATOR);
        this.pageTotal = in.readInt();
        this.pageSize = in.readInt();
    }

    public static final Creator<Article> CREATOR = new Creator<Article>() {
        @Override
        public Article createFromParcel(Parcel source) {
            return new Article(source);
        }

        @Override
        public Article[] newArray(int size) {
            return new Article[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getArticle_img() {
        return article_img;
    }

    public void setArticle_img(String article_img) {
        this.article_img = article_img;
    }

    public int getLike_num() {
        return like_num;
    }

    public void setLike_num(int like_num) {
        this.like_num = like_num;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public List<Evaluate> getEvaluates() {
        return evaluates;
    }

    public void setEvaluates(List<Evaluate> evaluates) {
        this.evaluates = evaluates;
    }

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
}
