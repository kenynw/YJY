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

    private int comment_num;

    private int click_num;

    private String created_at;

    private String linkUrl;

    private int isGras;

    private List<Evaluate> commentList;

    private int pageTotal;

    private int pageSize;

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

    public int getComment_num() {
        return comment_num;
    }

    public void setComment_num(int comment_num) {
        this.comment_num = comment_num;
    }

    public int getClick_num() {
        return click_num;
    }

    public void setClick_num(int click_num) {
        this.click_num = click_num;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getLinkUrl() {
        return linkUrl;
    }

    public void setLinkUrl(String linkUrl) {
        this.linkUrl = linkUrl;
    }

    public int getIsGras() {
        return isGras;
    }

    public void setIsGras(int isGras) {
        this.isGras = isGras;
    }

    public List<Evaluate> getCommentList() {
        return commentList;
    }

    public void setCommentList(List<Evaluate> commentList) {
        this.commentList = commentList;
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

    public Article() {
    }

    @Override
    public String toString() {
        return "Article{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", article_img='" + article_img + '\'' +
                ", like_num=" + like_num +
                ", created_at='" + created_at + '\'' +
                ", linkUrl='" + linkUrl + '\'' +
                ", isGras=" + isGras +
                ", commentList=" + commentList +
                ", pageTotal=" + pageTotal +
                ", pageSize=" + pageSize +
                '}';
    }

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
        dest.writeInt(this.comment_num);
        dest.writeInt(this.click_num);
        dest.writeString(this.created_at);
        dest.writeString(this.linkUrl);
        dest.writeInt(this.isGras);
        dest.writeTypedList(this.commentList);
        dest.writeInt(this.pageTotal);
        dest.writeInt(this.pageSize);
    }

    protected Article(Parcel in) {
        this.id = in.readInt();
        this.title = in.readString();
        this.article_img = in.readString();
        this.like_num = in.readInt();
        this.comment_num = in.readInt();
        this.click_num = in.readInt();
        this.created_at = in.readString();
        this.linkUrl = in.readString();
        this.isGras = in.readInt();
        this.commentList = in.createTypedArrayList(Evaluate.CREATOR);
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
}
