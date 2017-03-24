package com.miguan.yjy.model.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Copyright (c) 2017/1/12. LiaoPeiKun Inc. All rights reserved.
 */

public class Home implements Parcelable {

    private List<Banner> banner;

    private List<Category> category;

    private List<Article> article;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(this.banner);
        dest.writeTypedList(this.category);
        dest.writeTypedList(this.article);
    }

    public Home() {
    }

    protected Home(Parcel in) {
        this.banner = in.createTypedArrayList(Banner.CREATOR);
        this.category = in.createTypedArrayList(Category.CREATOR);
        this.article = in.createTypedArrayList(Article.CREATOR);
    }

    public static final Creator<Home> CREATOR = new Creator<Home>() {
        @Override
        public Home createFromParcel(Parcel source) {
            return new Home(source);
        }

        @Override
        public Home[] newArray(int size) {
            return new Home[size];
        }
    };

    public List<Banner> getBanner() {
        return banner;
    }

    public void setBanner(List<Banner> banner) {
        this.banner = banner;
    }

    public List<Category> getCategory() {
        return category;
    }

    public void setCategory(List<Category> category) {
        this.category = category;
    }

    public List<Article> getArticle() {
        return article;
    }

    public void setArticle(List<Article> article) {
        this.article = article;
    }
}
