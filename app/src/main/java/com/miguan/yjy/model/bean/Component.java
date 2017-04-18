package com.miguan.yjy.model.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @作者 cjh
 * @日期 2017/3/29 10:49
 * @描述 产品成分
 */

public class Component  implements Parcelable {

//    id(int) － ID
//    name(string) － 成分名
//    risk_grade(int) － 风险等级
//    is_active(int) － 活性成分 1-是，0-否
//    is_pox(int) － 致痘 1-是，0-否
//    component_action(string) － 使用目的
//    description(string) － 简介

    private String  id;
    private String name;
    private String risk_grade;
    private int is_active;
    private int is_pox;
    private String component_action;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    public String getRisk_grade() {
        return risk_grade;
    }

    public void setRisk_grade(String risk_grade) {
        this.risk_grade = risk_grade;
    }

    public int getIs_active() {
        return is_active;
    }

    public void setIs_active(int is_active) {
        this.is_active = is_active;
    }

    public int getIs_pox() {
        return is_pox;
    }

    public void setIs_pox(int is_pox) {
        this.is_pox = is_pox;
    }

    public String getComponent_action() {
        return component_action;
    }

    public void setComponent_action(String component_action) {
        this.component_action = component_action;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    private String description;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Component() {

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.name);
        dest.writeString(this.risk_grade);
        dest.writeInt(this.is_active);
        dest.writeInt(this.is_pox);
        dest.writeString(this.component_action);
        dest.writeString(this.description);
    }

    protected Component(Parcel in) {
        this.id = in.readString();
        this.name = in.readString();
        this.risk_grade = in.readString();
        this.is_active = in.readInt();
        this.is_pox = in.readInt();
        this.component_action = in.readString();
        this.description = in.readString();
    }

    public static final Creator<Component> CREATOR = new Creator<Component>() {
        @Override
        public Component createFromParcel(Parcel source) {
            return new Component(source);
        }

        @Override
        public Component[] newArray(int size) {
            return new Component[size];
        }
    };
}
