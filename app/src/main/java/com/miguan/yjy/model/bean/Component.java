package com.miguan.yjy.model.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @作者 cjh
 * @日期 2017/3/29 10:49
 * @描述 产品成分
 */

public class Component implements Parcelable {

    private String id;

    private String name; // 成分名

    private String ename; // 成份英文名

    private String alias; // 成份别名

    private String risk_grade; // 风险等级

    private int is_active; // 活性成分 1-是，0-否

    private int is_pox; // 致痘 1-是，0-否

    private String component_action; // 使用目的(成份功效)

    private String description; // 简介(用户说明)

    private int num; // 含有该成分的产品数量

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEname() {
        return ename;
    }

    public void setEname(String ename) {
        this.ename = ename;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
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
        dest.writeString(this.ename);
        dest.writeString(this.alias);
        dest.writeString(this.risk_grade);
        dest.writeInt(this.is_active);
        dest.writeInt(this.is_pox);
        dest.writeString(this.component_action);
        dest.writeString(this.description);
        dest.writeInt(this.num);
    }

    protected Component(Parcel in) {
        this.id = in.readString();
        this.name = in.readString();
        this.ename = in.readString();
        this.alias = in.readString();
        this.risk_grade = in.readString();
        this.is_active = in.readInt();
        this.is_pox = in.readInt();
        this.component_action = in.readString();
        this.description = in.readString();
        this.num = in.readInt();
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
