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
    private List<SelectPrice> condition;
    private String id;
    private String copy;
    private String description;
    private float valuate;
    private float score;
    private float maximum;
    private String type;

    public static String Drys[] = {"重干", "轻干", "轻油", "重油"};
    public static String tolerances[] = {"重耐", "轻耐", "轻敏", "重敏"};
    public static String Pigments[] = {"色素", "非色素"};
    public static String compacts[] = {"皱纹", "紧致"};

    public static String drySkin[] = {"重度干性皮肤","轻度干性皮肤","轻度油性皮肤","重度油性皮肤"};
    public static String toleranceSkin[] = {"重度耐受性皮肤","轻度耐受性皮肤","轻度敏感性皮肤","重度敏感性皮肤"};
    public static String pigmentSkin[] = {"色素性皮肤","非色素性皮肤"};
    public static String compactSkin[] = {"皱纹性皮肤","紧致性皮肤"};

    public String[] leftSkin = {"干性","敏感","色素","皱纹"};
    public String[] rightSkin = {"油性","耐受","非色素","紧致"};
    public String[] skinDesc = {"干性/油性","敏感程度","是否容易色素沉着","是否皱纹性肤质"};

    private String unscramble;


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<SelectPrice> getCondition() {
        return condition;
    }

    public void setCondition(List<SelectPrice> condition) {
        this.condition = condition;
    }

    public String getUnscramble() {
        return unscramble;
    }

    public void setUnscramble(String unscramble) {
        this.unscramble = unscramble;
    }

    public float getMaximum() {
        return maximum;
    }

    public void setMaximum(float maximum) {
        this.maximum = maximum;
    }

    public float getScore() {
        return score;
    }

    public void setScore(float score) {
        this.score = score;
    }

    public float getValuate() {
        return valuate;
    }

    public void setValuate(float valuate) {
        this.valuate = valuate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
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
            return "非色素性皮肤";
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


    public static String getDryOil(String value) {
        for (int i=0;i<=4;i++) {
            if (value.equals(Drys[i])) {
                return drySkin[i];
            }
        }
        return "";
    }


    public static String getTolerance(String value) {
        for (int i=0;i<=4;i++) {
            if (value.equals(tolerances[i])) {
                return toleranceSkin[i];
            }
        }
        return "";
    }


    public static String getPigment(String value) {
        for (int i=0;i<=2;i++) {
            if (value.equals(Pigments[i])) {
                return pigmentSkin[i];
            }
        }
        return "";
    }

    public static String getCompact(String value) {
        for (int i=0;i<=2;i++) {
            if (value.equals(compacts[i])) {
                return compactSkin[i];
            }
        }
        return "";
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
        dest.writeTypedList(this.condition);
        dest.writeString(this.id);
        dest.writeString(this.copy);
        dest.writeString(this.description);
        dest.writeFloat(this.valuate);
        dest.writeFloat(this.score);
        dest.writeFloat(this.maximum);
        dest.writeString(this.type);
        dest.writeStringArray(this.leftSkin);
        dest.writeStringArray(this.rightSkin);
        dest.writeStringArray(this.skinDesc);
        dest.writeString(this.unscramble);
    }

    protected Skin(Parcel in) {
        this.name = in.readString();
        this.letter = in.readString();
        this.category_id = in.readString();
        this.category_name = in.readString();
        this.data = in.createTypedArrayList(Product.CREATOR);
        this.condition = in.createTypedArrayList(SelectPrice.CREATOR);
        this.id = in.readString();
        this.copy = in.readString();
        this.description = in.readString();
        this.valuate = in.readFloat();
        this.score = in.readFloat();
        this.maximum = in.readFloat();
        this.type = in.readString();
        this.leftSkin = in.createStringArray();
        this.rightSkin = in.createStringArray();
        this.skinDesc = in.createStringArray();
        this.unscramble = in.readString();
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
}
