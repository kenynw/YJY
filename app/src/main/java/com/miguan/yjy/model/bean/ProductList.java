package com.miguan.yjy.model.bean;

import android.os.Parcel;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Copyright (c) 2017/4/9. LiaoPeiKun Inc. All rights reserved.
 */

public class ProductList extends EntityList {
    private List<Rank> rank;

    @SerializedName(value = "data", alternate = {"product"})
    private List<Product> product;

    @SerializedName(value = "categroy", alternate = {"categoryList"})
    private List<Category> categoryList;

    @SerializedName(value = "effects", alternate = {"effectList"})
    private List<Effect> effectList;

    private Brand brand;

    public List<Rank> getRank() {
        return rank;
    }

    public void setRank(List<Rank> rank) {
        this.rank = rank;
    }

    public Brand getBrand() {
        return brand;
    }

    public void setBrand(Brand brand) {
        this.brand = brand;
    }

    public List<Category> getCategoryList() {
        return categoryList;
    }

    public void setCategoryList(List<Category> categoryList) {
        this.categoryList = categoryList;
    }

    public List<Effect> getEffectList() {
        return effectList;
    }

    public void setEffectList(List<Effect> effectList) {
        this.effectList = effectList;
    }

    public List<Product> getProduct() {
        return product;

    }

    public void setProduct(List<Product> product) {
        this.product = product;
    }


    public ProductList() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeTypedList(this.rank);
        dest.writeTypedList(this.product);
        dest.writeTypedList(this.categoryList);
        dest.writeTypedList(this.effectList);
        dest.writeParcelable(this.brand, flags);
    }

    protected ProductList(Parcel in) {
        super(in);
        this.rank = in.createTypedArrayList(Rank.CREATOR);
        this.product = in.createTypedArrayList(Product.CREATOR);
        this.categoryList = in.createTypedArrayList(Category.CREATOR);
        this.effectList = in.createTypedArrayList(Effect.CREATOR);
        this.brand = in.readParcelable(Brand.class.getClassLoader());
    }

    public static final Creator<ProductList> CREATOR = new Creator<ProductList>() {
        @Override
        public ProductList createFromParcel(Parcel source) {
            return new ProductList(source);
        }

        @Override
        public ProductList[] newArray(int size) {
            return new ProductList[size];
        }
    };
}
