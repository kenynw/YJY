package com.miguan.yjy.model.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

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

    @SerializedName(value = "rank_id", alternate = {"id"})
    private int rank_id;

    @SerializedName(value = "rank_name", alternate = {"title"})
    private String rank_name;

    @SerializedName(value = "rank_banner", alternate = {"banner"})
    private String rank_banner;

    private int num;

    private ArrayList<String> img_list;

    private int category_id;

    private int brand_id;

    private long add_time;

    private String share_url;

    private Rank rankingInfo;

    private List<Product> productList;

    private List<Rank> relation_info;

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

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public ArrayList<String> getImg_list() {
        return img_list;
    }

    public void setImg_list(ArrayList<String> img_list) {
        this.img_list = img_list;
    }

    public int getCategory_id() {
        return category_id;
    }

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
    }

    public int getBrand_id() {
        return brand_id;
    }

    public void setBrand_id(int brand_id) {
        this.brand_id = brand_id;
    }

    public long getAdd_time() {
        return add_time;
    }

    public void setAdd_time(long add_time) {
        this.add_time = add_time;
    }

    public String getShare_url() {
        return share_url;
    }

    public void setShare_url(String share_url) {
        this.share_url = share_url;
    }

    public Rank getRankingInfo() {
        return rankingInfo;
    }

    public void setRankingInfo(Rank rankingInfo) {
        this.rankingInfo = rankingInfo;
    }

    public List<Product> getProductList() {
        return productList;
    }

    public void setProductList(List<Product> productList) {
        this.productList = productList;
    }

    public List<Rank> getRelation_info() {
        return relation_info;
    }

    public void setRelation_info(List<Rank> relation_info) {
        this.relation_info = relation_info;
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
        dest.writeInt(this.num);
        dest.writeStringList(this.img_list);
        dest.writeInt(this.category_id);
        dest.writeInt(this.brand_id);
        dest.writeLong(this.add_time);
        dest.writeString(this.share_url);
        dest.writeParcelable(this.rankingInfo, flags);
        dest.writeTypedList(this.productList);
        dest.writeTypedList(this.relation_info);
    }

    protected Rank(Parcel in) {
        this.rank_id = in.readInt();
        this.rank_name = in.readString();
        this.rank_banner = in.readString();
        this.num = in.readInt();
        this.img_list = in.createStringArrayList();
        this.category_id = in.readInt();
        this.brand_id = in.readInt();
        this.add_time = in.readLong();
        this.share_url = in.readString();
        this.rankingInfo = in.readParcelable(Rank.class.getClassLoader());
        this.productList = in.createTypedArrayList(Product.CREATOR);
        this.relation_info = in.createTypedArrayList(Rank.CREATOR);
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
