package com.miguan.yjy.model.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * @作者 cjh
 * @日期 2017/3/30 16:03
 * @描述 肤质测试
 */

public class Test implements Parcelable {

    private int img;
    private int title;
    private int describe;
    private String testName;
    private List<Skin> desc;
    private String explain;
    private String features;
    private String elements;
    private List<Skin> skinProduct;
    private List<Article> skinArticle;
    private ArrayList<Skin> categoryList;
    private int star;

    public int getStar() {
        return star;
    }

    public void setStar(int star) {
        this.star = star;
    }

    public String getElements() {
        return elements;
    }

    public void setElements(String elements) {
        this.elements = elements;
    }

    public ArrayList<Skin> getCategoryList() {
        return categoryList;
    }

    public void setCategoryList(ArrayList<Skin> categoryList) {
        this.categoryList = categoryList;
    }

    public List<Skin> getSkinProduct() {
        return skinProduct;
    }

    public void setSkinProduct(List<Skin> skinProduct) {
        this.skinProduct = skinProduct;
    }

    public List<Article> getSkinArticle() {
        return skinArticle;
    }

    public void setSkinArticle(List<Article> skinArticle) {
        this.skinArticle = skinArticle;
    }

    public String getTestName() {
        return testName;
    }

    public void setTestName(String testName) {
        this.testName = testName;
    }

    public int getDescribe() {
        return describe;
    }

    public void setDescribe(int describe) {
        this.describe = describe;
    }

    public int getTitle() {
        return title;
    }

    public void setTitle(int title) {
        this.title = title;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public List<Skin> getDesc() {
        return desc;
    }

    public void setDesc(List<Skin> desc) {
        this.desc = desc;
    }

    public String getExplain() {
        return explain;
    }

    public void setExplain(String explain) {
        this.explain = explain;
    }

    public String getFeatures() {
        return features;
    }

    public void setFeatures(String features) {
        this.features = features;
    }

    public Test() {

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.img);
        dest.writeInt(this.title);
        dest.writeInt(this.describe);
        dest.writeString(this.testName);
        dest.writeTypedList(this.desc);
        dest.writeString(this.explain);
        dest.writeString(this.features);
        dest.writeString(this.elements);
        dest.writeTypedList(this.skinProduct);
        dest.writeTypedList(this.skinArticle);
        dest.writeTypedList(this.categoryList);
        dest.writeInt(this.star);
    }

    protected Test(Parcel in) {
        this.img = in.readInt();
        this.title = in.readInt();
        this.describe = in.readInt();
        this.testName = in.readString();
        this.desc = in.createTypedArrayList(Skin.CREATOR);
        this.explain = in.readString();
        this.features = in.readString();
        this.elements = in.readString();
        this.skinProduct = in.createTypedArrayList(Skin.CREATOR);
        this.skinArticle = in.createTypedArrayList(Article.CREATOR);
        this.categoryList = in.createTypedArrayList(Skin.CREATOR);
        this.star = in.readInt();
    }

    public static final Creator<Test> CREATOR = new Creator<Test>() {
        @Override
        public Test createFromParcel(Parcel source) {
            return new Test(source);
        }

        @Override
        public Test[] newArray(int size) {
            return new Test[size];
        }
    };
}
