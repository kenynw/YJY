package com.miguan.yjy.model.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * @作者 cjh
 * @日期 2017/7/25 9:42
 * @描述
 *    id(int) － 百科ID
 *    question(string) － 问题
 *    content(string) － 答案
 *    share_url(string) － 分享链接
 *    relation_info(array) － 关联百科信息
 *    id(int) － 关联百科ID
 *    question(string) － 关联百科问题
 *    shortcontent(string) － 短答案
 *    picture(string) － 图片
 *    is_like(string) － 是否点赞（1已点2未点）
 *    like_num(int) － 点赞数量
 */
public class Wiki implements Parcelable {

    private int id;

    private String question;

    private String content;

    private ArrayList<Wiki> relation_info;

    private String share_url;

    private String shortcontent;

    private String picture;

    private String is_like;

    private int like_num;

    public int getLike_num() {
        return like_num;
    }

    public void setLike_num(int like_num) {
        this.like_num = like_num;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getIs_like() {
        return is_like;
    }

    public void setIs_like(String is_like) {
        this.is_like = is_like;
    }

    public String getShare_url() {
        return share_url;
    }

    public void setShare_url(String share_url) {
        this.share_url = share_url;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getShortcontent() {
        return shortcontent;
    }

    public void setShortcontent(String shortcontent) {
        this.shortcontent = shortcontent;
    }

    public ArrayList<Wiki> getRelation_info() {
        return relation_info;
    }

    public void setRelation_info(ArrayList<Wiki> relation_info) {
        this.relation_info = relation_info;
    }

    public Wiki() {

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.question);
        dest.writeString(this.content);
        dest.writeString(this.shortcontent);
        dest.writeTypedList(this.relation_info);
        dest.writeString(this.share_url);
        dest.writeString(this.shortcontent);
        dest.writeString(this.picture);
        dest.writeString(this.is_like);
        dest.writeInt(this.like_num);
    }

    protected Wiki(Parcel in) {
        this.id = in.readInt();
        this.question = in.readString();
        this.content = in.readString();
        this.shortcontent = in.readString();
        this.relation_info = in.createTypedArrayList(Wiki.CREATOR);
        this.share_url = in.readString();
        this.shortcontent = in.readString();
        this.picture = in.readString();
        this.is_like = in.readString();
        this.like_num = in.readInt();
    }

    public static final Creator<Wiki> CREATOR = new Creator<Wiki>() {
        @Override
        public Wiki createFromParcel(Parcel source) {
            return new Wiki(source);
        }

        @Override
        public Wiki[] newArray(int size) {
            return new Wiki[size];
        }
    };

}
