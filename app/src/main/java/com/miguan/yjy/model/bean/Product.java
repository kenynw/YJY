package com.miguan.yjy.model.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * @作者 cjh
 * @日期 2017/3/21 9:38
 * @描述 产品相关（搜索）
 */

public class Product implements Parcelable {

    private int id;

    private String name;

    private int cate_id;

    private String product_name;

    private String price;

    private String form;

    private String alias;

    private int star;

    private int is_top;
    private int rule;
    private String product_explain;
    private List<String> tagList;


    private String standard_number;

    private String product_country;

    private String product_date;

    private String remark;

    private String product_img;

    private String product_company;

    private Long brand_id;

    private String brand_name;

    private String en_product_company;

    private String copy;

    private int effectNum;

    private int isGras;
    private int Praise;
    private int middle;
    private int bad;
    private int total;

    private String linkUrl;

    private ArrayList<Component> componentList;

    private ArrayList<ComponentTag> effect;

    private List<String> recommend;

    private List<String> notRecommend;

    private ArrayList<ComponentTag> security;

    public ArrayList<ComponentTag> getEffect() {
        return effect;
    }

    private Buy buy;

    public int getIs_top() {
        return is_top;
    }

    public void setIs_top(int is_top) {
        this.is_top = is_top;
    }

    public int getRule() {
        return rule;
    }

    public void setRule(int rule) {
        this.rule = rule;
    }

    public String getProduct_explain() {
        return product_explain;
    }

    public void setProduct_explain(String product_explain) {
        this.product_explain = product_explain;
    }

    public List<String> getTagList() {
        return tagList;
    }

    public void setTagList(List<String> tagList) {
        this.tagList = tagList;
    }
    private boolean isLocal;

    public int getEffectNum() {
        return effectNum;
    }

    public void setEffectNum(int effectNum) {
        this.effectNum = effectNum;
    }

    public String getCopy() {
        return copy;
    }

    public void setCopy(String copy) {
        this.copy = copy;
    }

    public int getIsGras() {
        return isGras;
    }

    public String getLinkUrl() {
        return linkUrl;
    }

    public void setLinkUrl(String linkUrl) {
        this.linkUrl = linkUrl;
    }

    public void setIsGras(int isGras) {
        this.isGras = isGras;
    }

    public int getPraise() {
        return Praise;
    }

    public void setPraise(int praise) {
        Praise = praise;
    }

    public int getMiddle() {
        return middle;
    }

    public void setMiddle(int middle) {
        this.middle = middle;
    }

    public int getBad() {
        return bad;
    }

    public void setBad(int bad) {
        this.bad = bad;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public ArrayList<Component> getComponentList() {
        return componentList;
    }

    public void setComponentList(ArrayList<Component> componentList) {
        this.componentList = componentList;
    }

    public ArrayList<ComponentTag> getSecurity() {
        return security;
    }

    public void setSecurity(ArrayList<ComponentTag> security) {
        this.security = security;
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

    public int getStar() {
        return star;
    }

    public void setStar(int star) {
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

    public String getEn_product_company() {
        return en_product_company;
    }

    public void setEn_product_company(String en_product_company) {
        this.en_product_company = en_product_company;
    }

    public Long getBrand_id() {
        return brand_id;
    }

    public void setBrand_id(Long brand_id) {
        this.brand_id = brand_id;
    }

    public String getBrand_name() {
        return brand_name;
    }

    public void setBrand_name(String brand_name) {
        this.brand_name = brand_name;
    }

    public Product() {
    }

    public Buy getBuy() {
        return buy;
    }

    public List<String> getNotRecommend() {
        return notRecommend;
    }

    public void setNotRecommend(List<String> notRecommend) {
        this.notRecommend = notRecommend;
    }
    public List<String> getRecommend() {
        return recommend;
    }

    public void setRecommend(List<String> recommend) {
        this.recommend = recommend;
    }

    public boolean isLocal() {
        return isLocal;
    }

    public void setLocal(boolean local) {
        isLocal = local;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.name);
        dest.writeInt(this.cate_id);
        dest.writeString(this.product_name);
        dest.writeString(this.price);
        dest.writeString(this.form);
        dest.writeString(this.alias);
        dest.writeInt(this.star);
        dest.writeInt(this.is_top);
        dest.writeInt(this.rule);
        dest.writeString(this.product_explain);
        dest.writeStringList(this.tagList);
        dest.writeString(this.standard_number);
        dest.writeString(this.product_country);
        dest.writeString(this.product_date);
        dest.writeString(this.remark);
        dest.writeString(this.product_img);
        dest.writeString(this.product_company);
        dest.writeValue(this.brand_id);
        dest.writeString(this.brand_name);
        dest.writeString(this.en_product_company);
        dest.writeString(this.copy);
        dest.writeInt(this.effectNum);
        dest.writeInt(this.isGras);
        dest.writeInt(this.Praise);
        dest.writeInt(this.middle);
        dest.writeInt(this.bad);
        dest.writeInt(this.total);
        dest.writeString(this.linkUrl);
        dest.writeTypedList(this.componentList);
        dest.writeTypedList(this.effect);
        dest.writeStringList(this.recommend);
        dest.writeStringList(this.notRecommend);
        dest.writeTypedList(this.security);
        dest.writeParcelable(this.buy, flags);
        dest.writeByte(this.isLocal ? (byte) 1 : (byte) 0);
    }

    protected Product(Parcel in) {
        this.id = in.readInt();
        this.name = in.readString();
        this.cate_id = in.readInt();
        this.product_name = in.readString();
        this.price = in.readString();
        this.form = in.readString();
        this.alias = in.readString();
        this.star = in.readInt();
        this.is_top = in.readInt();
        this.rule = in.readInt();
        this.product_explain = in.readString();
        this.tagList = in.createStringArrayList();
        this.standard_number = in.readString();
        this.product_country = in.readString();
        this.product_date = in.readString();
        this.remark = in.readString();
        this.product_img = in.readString();
        this.product_company = in.readString();
        this.brand_id = (Long) in.readValue(Long.class.getClassLoader());
        this.brand_name = in.readString();
        this.en_product_company = in.readString();
        this.copy = in.readString();
        this.effectNum = in.readInt();
        this.isGras = in.readInt();
        this.Praise = in.readInt();
        this.middle = in.readInt();
        this.bad = in.readInt();
        this.total = in.readInt();
        this.linkUrl = in.readString();
        this.componentList = in.createTypedArrayList(Component.CREATOR);
        this.effect = in.createTypedArrayList(ComponentTag.CREATOR);
        this.recommend = in.createStringArrayList();
        this.notRecommend = in.createStringArrayList();
        this.security = in.createTypedArrayList(ComponentTag.CREATOR);
        this.buy = in.readParcelable(Buy.class.getClassLoader());
        this.isLocal = in.readByte() != 0;
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
