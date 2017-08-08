package com.miguan.yjy.model.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @作者 cjh
 * @日期 2017/4/12 11:33
 * @描述
 */

public class Buy implements Parcelable {
//    jd(string) － 京东
//    taobao(string) － 淘宝
//    amazon(int) － 亚马逊
//        "type": "3",
//                "url": "http://www.baidu.com",
//                "link_price": "0",
//                "brand_name": "芙丽芳丝",
//                "brand_img": "http://oss.yjyapp.com/cs/uploads/brand_img/20170609/7009201706099.jpg"


    private String jd;
    private String taobao;
    private String amazon;
    private int type;
    private String url;
    private String link_price;
    private String brand_name;
    private String brand_img;


    public String getBrand_name() {
        return brand_name;
    }

    public void setBrand_name(String brand_name) {
        this.brand_name = brand_name;
    }

    public String getBrand_img() {
        return brand_img;
    }

    public void setBrand_img(String brand_img) {
        this.brand_img = brand_img;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getLink_price() {
        return link_price;
    }

    public void setLink_price(String link_price) {
        this.link_price = link_price;
    }

    public String getJd() {
        return jd;
    }

    public String getTaobao() {
        return taobao;
    }

    public String getAmazon() {
        return amazon;
    }

    public Buy() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.jd);
        dest.writeString(this.taobao);
        dest.writeString(this.amazon);
        dest.writeInt(this.type);
        dest.writeString(this.url);
        dest.writeString(this.link_price);
        dest.writeString(this.brand_name);
        dest.writeString(this.brand_img);
    }

    protected Buy(Parcel in) {
        this.jd = in.readString();
        this.taobao = in.readString();
        this.amazon = in.readString();
        this.type = in.readInt();
        this.url = in.readString();
        this.link_price = in.readString();
        this.brand_name = in.readString();
        this.brand_img = in.readString();
    }

    public static final Creator<Buy> CREATOR = new Creator<Buy>() {
        @Override
        public Buy createFromParcel(Parcel source) {
            return new Buy(source);
        }

        @Override
        public Buy[] newArray(int size) {
            return new Buy[size];
        }
    };
}
