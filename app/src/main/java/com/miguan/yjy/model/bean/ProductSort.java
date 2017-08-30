package com.miguan.yjy.model.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * @作者 cjh
 * @日期 2017/8/28 10:22
 * @描述 产品分类
 */

public class ProductSort implements Parcelable {

//    id(int) － 二级分类ID
//    parent_id(int) － 父分类ID
//    cate_name(string) － 分类名
//    cate_app_img(string) － app分类图标
//    cate_h5_img(string) － h5分类图标
//    sub(array) － 下一级分类信息

    private int id;
    private int parent_id;
    private int cate_name;
    private int cate_app_img;
    private int cate_h5_img;
    private List<ProductSort> sub;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getParent_id() {
        return parent_id;
    }

    public void setParent_id(int parent_id) {
        this.parent_id = parent_id;
    }

    public int getCate_name() {
        return cate_name;
    }

    public void setCate_name(int cate_name) {
        this.cate_name = cate_name;
    }

    public int getCate_app_img() {
        return cate_app_img;
    }

    public void setCate_app_img(int cate_app_img) {
        this.cate_app_img = cate_app_img;
    }

    public int getCate_h5_img() {
        return cate_h5_img;
    }

    public void setCate_h5_img(int cate_h5_img) {
        this.cate_h5_img = cate_h5_img;
    }

    public List<ProductSort> getSub() {
        return sub;
    }

    public void setSub(List<ProductSort> sub) {
        this.sub = sub;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeInt(this.parent_id);
        dest.writeInt(this.cate_name);
        dest.writeInt(this.cate_app_img);
        dest.writeInt(this.cate_h5_img);
        dest.writeTypedList(this.sub);
    }

    public ProductSort() {
    }

    protected ProductSort(Parcel in) {
        this.id = in.readInt();
        this.parent_id = in.readInt();
        this.cate_name = in.readInt();
        this.cate_app_img = in.readInt();
        this.cate_h5_img = in.readInt();
        this.sub = in.createTypedArrayList(ProductSort.CREATOR);
    }

    public static final Creator<ProductSort> CREATOR = new Creator<ProductSort>() {
        @Override
        public ProductSort createFromParcel(Parcel source) {
            return new ProductSort(source);
        }

        @Override
        public ProductSort[] newArray(int size) {
            return new ProductSort[size];
        }
    };
}
