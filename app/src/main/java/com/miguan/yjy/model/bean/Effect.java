package com.miguan.yjy.model.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @作者 cjh
 * @日期 2017/5/18 19:02
 * @描述
 */

public class Effect implements Parcelable {
    public int getEffect_id() {
        return effect_id;
    }

    private int effect_id;
    private String effect_name;

    public Effect() {
    }

    public String getEffect_name() {
        return effect_name;
    }

    public void setEffect_name(String effect_name) {
        this.effect_name = effect_name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.effect_id);
        dest.writeString(this.effect_name);
    }

    protected Effect(Parcel in) {
        this.effect_id = in.readInt();
        this.effect_name = in.readString();
    }

    public static final Creator<Effect> CREATOR = new Creator<Effect>() {
        @Override
        public Effect createFromParcel(Parcel source) {
            return new Effect(source);
        }

        @Override
        public Effect[] newArray(int size) {
            return new Effect[size];
        }
    };

    public void setEffect_id(int effect_id) {
        this.effect_id = effect_id;
    }
}
