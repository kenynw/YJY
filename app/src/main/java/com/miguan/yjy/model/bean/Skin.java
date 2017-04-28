package com.miguan.yjy.model.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * @作者 cjh
 * @日期 2017/4/20 14:21
 * @描述
 */

public class Skin implements Parcelable {
    private String name;
    private String letter;
    private String category_id;
    private String category_name;
    private List<Product> data;
    private String id;
    private String copy;

//      "category_id": "7",
//              "category_name": "化妆水",
//              "data":


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCopy() {
        return copy;
    }

    public void setCopy(String copy) {
        this.copy = copy;
    }

    public String getCategory_id() {
        return category_id;
    }

    public void setCategory_id(String category_id) {
        this.category_id = category_id;
    }

    public String getCategory_name() {
        return category_name;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }

    public List<Product> getData() {
        return data;
    }

    public void setData(List<Product> data) {
        this.data = data;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLetter() {
        return letter;
    }

    public void setLetter(String letter) {
        this.letter = letter;
    }

    public Skin() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.letter);
        dest.writeString(this.category_id);
        dest.writeString(this.category_name);
        dest.writeTypedList(this.data);
        dest.writeString(this.id);
        dest.writeString(this.copy);
    }

    protected Skin(Parcel in) {
        this.name = in.readString();
        this.letter = in.readString();
        this.category_id = in.readString();
        this.category_name = in.readString();
        this.data = in.createTypedArrayList(Product.CREATOR);
        this.id = in.readString();
        this.copy = in.readString();
    }

    public static final Creator<Skin> CREATOR = new Creator<Skin>() {
        @Override
        public Skin createFromParcel(Parcel source) {
            return new Skin(source);
        }

        @Override
        public Skin[] newArray(int size) {
            return new Skin[size];
        }
    };

    public static boolean matchValue(int value, int star, int end) {
        for (int i = star; i < end; i++) {
            if (value == i) {
                return true;
            }
        }
        return false;
    }


    public static String getDryOil(int value) {
        if (matchValue(value, 11, 16)) {
            return "重度干性皮肤";
        }
        if (matchValue(value, 17, 26)) {
            return "轻度干性皮肤";
        }
        if (matchValue(value, 27, 33)) {
            return "轻度油性皮肤";
        }
        if (matchValue(value, 34, 44)) {
            return "重度油性皮肤";
        }
        return "";
    }


    public static String getTolerance(int value) {
        if (matchValue(value, 17, 24)) {
            return "重度耐受性皮肤";
        }
        if (matchValue(value, 25, 29)) {
            return "轻度耐受性皮肤";
        }
        if (matchValue(value, 30, 33)) {
            return "轻度敏感性皮肤";
        }
        if (matchValue(value, 34, 72)) {
            return "重度敏感性皮肤";
        }
        return "";
    }


    public static String getPigment(int value) {
        if (matchValue(value, 10, 30)) {
            return "非色素敏感性皮肤";
        }
        if (matchValue(value, 31, 45)) {
            return "色素性皮肤";
        }
        return "";
    }

    public static String getCompact(int value) {
        if (matchValue(value, 20, 40)) {
            return "紧致性皮肤";
        }
        if (matchValue(value, 41, 85)) {
            return "皱纹性皮肤";
        }
        return "";
    }

}
