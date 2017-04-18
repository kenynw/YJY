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

    private String jd;
    private String taobao;
    private String amazon;

    protected Buy(Parcel in) {
        jd = in.readString();
        taobao = in.readString();
        amazon = in.readString();
    }

    public static final Creator<Buy> CREATOR = new Creator<Buy>() {
        @Override
        public Buy createFromParcel(Parcel in) {
            return new Buy(in);
        }

        @Override
        public Buy[] newArray(int size) {
            return new Buy[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(jd);
        dest.writeString(taobao);
        dest.writeString(amazon);
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
}
