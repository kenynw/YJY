package com.miguan.yjy.model.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.miguan.yjy.adapter.viewholder.ArticleCate;

import java.util.ArrayList;
import java.util.List;

/**
 * Copyright (c) 2017/1/12. LiaoPeiKun Inc. All rights reserved.
 */

public class Home implements Parcelable {

    private int num;

    private List<Banner> banner;

    private List<Category> category;

    private ArrayList<ArticleCate> articleGory;

    private List<Evaluate> essence;

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

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

    public ArrayList<ArticleCate> getArticleGory() {
        return articleGory;
    }

    public void setArticleGory(ArrayList<ArticleCate> articleGory) {
        this.articleGory = articleGory;
    }

    public List<Evaluate> getEvaluateList() {
        return essence;
    }

    public void setEvaluateList(List<Evaluate> recommendComment) {
        this.essence = recommendComment;
    }

    public Home() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.num);
        dest.writeTypedList(this.banner);
        dest.writeTypedList(this.category);
        dest.writeTypedList(this.articleGory);
        dest.writeTypedList(this.essence);
    }

    protected Home(Parcel in) {
        this.num = in.readInt();
        this.banner = in.createTypedArrayList(Banner.CREATOR);
        this.category = in.createTypedArrayList(Category.CREATOR);
        this.articleGory = in.createTypedArrayList(ArticleCate.CREATOR);
        this.essence = in.createTypedArrayList(Evaluate.CREATOR);
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
}
