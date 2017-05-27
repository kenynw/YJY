package com.miguan.yjy.module.template;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.SparseArray;

import java.util.List;

/**
 * Copyright (c) 2017/5/5. LiaoPeiKun Inc. All rights reserved.
 */

public class Template implements Parcelable {

    private String mTitle;

    private String mDesc;

    private List<Item> mItems;

    private int mType;

    public Template() {
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getDesc() {
        return mDesc;
    }

    public void setDesc(String desc) {
        mDesc = desc;
    }

    public List<Item> getItems() {
        return mItems;
    }

    public void setItems(List<Item> items) {
        mItems = items;
    }

    public int getType() {
        return mType;
    }

    public void setType(int type) {
        mType = type;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.mTitle);
        dest.writeString(this.mDesc);
        dest.writeTypedList(this.mItems);
        dest.writeInt(this.mType);
    }

    protected Template(Parcel in) {
        this.mTitle = in.readString();
        this.mDesc = in.readString();
        this.mItems = in.createTypedArrayList(Item.CREATOR);
        this.mType = in.readInt();
    }

    public static final Creator<Template> CREATOR = new Creator<Template>() {
        @Override
        public Template createFromParcel(Parcel source) {
            return new Template(source);
        }

        @Override
        public Template[] newArray(int size) {
            return new Template[size];
        }
    };

    public static class Item implements Parcelable {

        private SparseArray<Uri> mUris;

        private SparseArray<String> mEtContents;

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeSparseArray((SparseArray) this.mUris);
            dest.writeSparseArray((SparseArray) this.mEtContents);
        }

        public Item() {
        }

        protected Item(Parcel in) {
            this.mUris = in.readSparseArray(Uri.class.getClassLoader());
            this.mEtContents = in.readSparseArray(String.class.getClassLoader());
        }

        public static final Creator<Item> CREATOR = new Creator<Item>() {
            @Override
            public Item createFromParcel(Parcel source) {
                return new Item(source);
            }

            @Override
            public Item[] newArray(int size) {
                return new Item[size];
            }
        };

        public SparseArray<Uri> getUris() {
            return mUris;
        }

        public void setUris(SparseArray<Uri> uris) {
            mUris = uris;
        }

        public SparseArray<String> getEtContents() {
            return mEtContents;
        }

        public void setEtContents(SparseArray<String> etContents) {
            mEtContents = etContents;
        }
    }

}
