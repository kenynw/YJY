package com.miguan.yjy.model.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * @作者 cjh
 * @日期 2017/7/25 9:42
 * @描述
 */

public class Wiki implements Parcelable {

    //    id(int) － 百科ID
//    question(string) － 问题
//    content(string) － 答案
//    share_url(string) － 分享链接
//    relation_info(array) － 关联百科信息
//    id(int) － 关联百科ID
//    question(string) － 关联百科问题
    private int id;
    private String question;
    private String content;
    private ArrayList<Wiki> relation_info;
    private String share_url;

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
        dest.writeTypedList(this.relation_info);
        dest.writeString(this.share_url);
    }

    protected Wiki(Parcel in) {
        this.id = in.readInt();
        this.question = in.readString();
        this.content = in.readString();
        this.relation_info = in.createTypedArrayList(Wiki.CREATOR);
        this.share_url = in.readString();
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
