package com.miguan.yjy.model.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * @作者 cjh
 * @日期 2017/7/31 11:01
 * @描述 排行榜
 */

public class Rank implements Parcelable {

//    rank(array) － 排行榜分类信息
//    rank_id(int) － 排行榜ID
//    rank_name(string) － 标题
//    rank_banner(string) － 背景图
//    img_list(array) － 产品图列表

    private int rank_id;
    private String rank_name;
    private String rank_banner;
    private ArrayList<String> img_list;

    public int getRank_id() {
        return rank_id;
    }

    public void setRank_id(int rank_id) {
        this.rank_id = rank_id;
    }

    public String getRank_name() {
        return rank_name;
    }

    public void setRank_name(String rank_name) {
        this.rank_name = rank_name;
    }

    public String getRank_banner() {
        return rank_banner;
    }

    public void setRank_banner(String rank_banner) {
        this.rank_banner = rank_banner;
    }

    public ArrayList<String> getImg_list() {
        return img_list;
    }

    public void setImg_list(ArrayList<String> img_list) {
        this.img_list = img_list;
    }

    public Rank() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.rank_id);
        dest.writeString(this.rank_name);
        dest.writeString(this.rank_banner);
        dest.writeStringList(this.img_list);
    }

    protected Rank(Parcel in) {
        this.rank_id = in.readInt();
        this.rank_name = in.readString();
        this.rank_banner = in.readString();
        this.img_list = in.createStringArrayList();
    }

    public static final Creator<Rank> CREATOR = new Creator<Rank>() {
        @Override
        public Rank createFromParcel(Parcel source) {
            return new Rank(source);
        }

        @Override
        public Rank[] newArray(int size) {
            return new Rank[size];
        }
    };
}
