package com.miguan.yjy.model.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * @作者 cjh
 * @日期 2017/3/21 9:38
 * @描述 产品相关（搜索）
 */

public class Product implements Parcelable {

    //  list(array) － 产品列表详情
//    id(int) － 文章ID
//    product_name(string) － 标题
//    product_img(string) － 配图
//    price(int) － 价格
//    form(string) － 规格
//    star(int) － 星级
//  categories(array) － 产品分类列表
//    id(int) － 分类ID
//    cate_name(string) － 分类名称
//    effects(array) － 功效列表（一维数组）
//    pageTotal(int) － 总数
//    pageSize(int) － 当前页数
//
    private ArrayList<ProductInfo> list;

    private ArrayList<Sort> categories;

    private ArrayList<String> effects;

    private int pageTotal;

    private int pageSize;

    private int id;

    private String name;

    private int cate_id;

    private String product_name;

    private String price;

    private String form;

    private String alias;

    private String star;

    private String standard_number;

    private String product_country;

    private String product_date;

    private String remark;

    private String product_img;

    private String product_company;

    private String brand;

    private String en_product_company;

    private String startDay;

    private String endDay;

    static class Sort implements Parcelable {
        private int id;
        private String cate_name;

        protected Sort(Parcel in) {
            id = in.readInt();
            cate_name = in.readString();
        }

        public static final Creator<Sort> CREATOR = new Creator<Sort>() {
            @Override
            public Sort createFromParcel(Parcel in) {
                return new Sort(in);
            }

            @Override
            public Sort[] newArray(int size) {
                return new Sort[size];
            }
        };

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(id);
            dest.writeString(cate_name);
        }
    }

    public ArrayList<ProductInfo> getList() {
        return list;
    }

    public void setList(ArrayList<ProductInfo> list) {
        this.list = list;
    }

    public ArrayList<Sort> getCategories() {
        return categories;
    }

    public void setCategories(ArrayList<Sort> categories) {
        this.categories = categories;
    }

    public ArrayList<String> getEffects() {
        return effects;
    }

    public void setEffects(ArrayList<String> effects) {
        this.effects = effects;
    }

    public int getPageTotal() {
        return pageTotal;
    }

    public void setPageTotal(int pageTotal) {
        this.pageTotal = pageTotal;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCate_id() {
        return cate_id;
    }

    public void setCate_id(int cate_id) {
        this.cate_id = cate_id;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getForm() {
        return form;
    }

    public void setForm(String form) {
        this.form = form;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getStar() {
        return star;
    }

    public void setStar(String star) {
        this.star = star;
    }

    public String getStandard_number() {
        return standard_number;
    }

    public void setStandard_number(String standard_number) {
        this.standard_number = standard_number;
    }

    public String getProduct_country() {
        return product_country;
    }

    public void setProduct_country(String product_country) {
        this.product_country = product_country;
    }

    public String getProduct_date() {
        return product_date;
    }

    public void setProduct_date(String product_date) {
        this.product_date = product_date;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getProduct_img() {
        return product_img;
    }

    public void setProduct_img(String product_img) {
        this.product_img = product_img;
    }

    public String getProduct_company() {
        return product_company;
    }

    public void setProduct_company(String product_company) {
        this.product_company = product_company;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getEn_product_company() {
        return en_product_company;
    }

    public void setEn_product_company(String en_product_company) {
        this.en_product_company = en_product_company;
    }

    public String getStartDay() {
        return startDay;
    }

    public void setStartDay(String startDay) {
        this.startDay = startDay;
    }

    public String getEndDay() {
        return endDay;
    }

    public void setEndDay(String endDay) {
        this.endDay = endDay;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(this.list);
        dest.writeTypedList(this.categories);
        dest.writeStringList(this.effects);
        dest.writeInt(this.pageTotal);
        dest.writeInt(this.pageSize);
        dest.writeInt(this.id);
        dest.writeString(this.name);
        dest.writeInt(this.cate_id);
        dest.writeString(this.product_name);
        dest.writeString(this.price);
        dest.writeString(this.form);
        dest.writeString(this.alias);
        dest.writeString(this.star);
        dest.writeString(this.standard_number);
        dest.writeString(this.product_country);
        dest.writeString(this.product_date);
        dest.writeString(this.remark);
        dest.writeString(this.product_img);
        dest.writeString(this.product_company);
        dest.writeString(this.brand);
        dest.writeString(this.en_product_company);
        dest.writeString(this.startDay);
        dest.writeString(this.endDay);
    }

    public Product() {
    }

    protected Product(Parcel in) {
        this.list = in.createTypedArrayList(ProductInfo.CREATOR);
        this.categories = in.createTypedArrayList(Sort.CREATOR);
        this.effects = in.createStringArrayList();
        this.pageTotal = in.readInt();
        this.pageSize = in.readInt();
        this.id = in.readInt();
        this.name = in.readString();
        this.cate_id = in.readInt();
        this.product_name = in.readString();
        this.price = in.readString();
        this.form = in.readString();
        this.alias = in.readString();
        this.star = in.readString();
        this.standard_number = in.readString();
        this.product_country = in.readString();
        this.product_date = in.readString();
        this.remark = in.readString();
        this.product_img = in.readString();
        this.product_company = in.readString();
        this.brand = in.readString();
        this.en_product_company = in.readString();
        this.startDay = in.readString();
        this.endDay = in.readString();
    }

    public static final Creator<Product> CREATOR = new Creator<Product>() {
        @Override
        public Product createFromParcel(Parcel source) {
            return new Product(source);
        }

        @Override
        public Product[] newArray(int size) {
            return new Product[size];
        }
    };
}
