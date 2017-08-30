package com.miguan.yjy.model.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Copyright (c) 2017/3/24. LiaoPeiKun Inc. All rights reserved.
 */
public class Evaluate implements Parcelable {

    @SerializedName(value = "id", alternate = {"comment_id"})
    private int id;

    private int post_id;

    private int type;

    private String comment;

    private int like_num;

    private long created_at;

    private int isLike;

    private int is_digest;

    private String username;

    private String author;

    private String img;

    private String skin;

    private int age;

    private String attachment;

    private int level;

    private User user;

    private Product product;

    private Item detail;

    private int star;

    private Evaluate reply;

    private List<Evaluate> replyList;

    private String replyUserName;

    private List<Evaluate> essence;

    private Ask ask;

    private int addMoney;



    public int getIs_digest() {
        return is_digest;
    }

    public void setIs_digest(int is_digest) {
        this.is_digest = is_digest;
    }

    public Evaluate() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPost_id() {
        return post_id;
    }

    public void setPost_id(int post_id) {
        this.post_id = post_id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getCreated_at() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return format.format(new Date(created_at * 1000));
    }

    public void setCreated_at(long created_at) {
        this.created_at = created_at;
    }

    public int getStar() {
        return star;
    }

    public void setStar(int star) {
        this.star = star;
    }

    public Item getDetail() {
        return detail;
    }

    public void setDetail(Item detail) {
        this.detail = detail;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getLike_num() {
        return like_num;
    }

    public void setLike_num(int like_num) {
        this.like_num = like_num;
    }

    public int getIsLike() {
        return isLike;
    }

    public void setIsLike(int isLike) {
        this.isLike = isLike;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getSkin() {
        return skin;
    }

    public void setSkin(String skin) {
        this.skin = skin;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getAttachment() {
        return attachment;
    }

    public void setAttachment(String attachment) {
        this.attachment = attachment;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Evaluate getReply() {
        return reply;
    }

    public void setReply(Evaluate reply) {
        this.reply = reply;
    }

    public List<Evaluate> getReplyList() {
        return replyList;
    }

    public void setReplyList(List<Evaluate> replyList) {
        this.replyList = replyList;
    }

    public String getReplyUserName() {
        return replyUserName;
    }

    public void setReplyUserName(String replyUserName) {
        this.replyUserName = replyUserName;
    }

    public List<Evaluate> getEssence() {
        return essence;
    }

    public void setEssence(List<Evaluate> essence) {
        this.essence = essence;
    }

    public Ask getAsk() {
        return ask;
    }

    public void setAsk(Ask ask) {
        this.ask = ask;
    }

    public int getAddMoney() {
        return addMoney;
    }

    public void setAddMoney(int addMoney) {
        this.addMoney = addMoney;
    }

    public static class Item implements Parcelable {

        private int id;

        private String name;

        private String img;

        private String price;

        private String form;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public int getId() {
            return id;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getForm() {
            return form.toUpperCase();
        }

        public void setForm(String form) {
            this.form = form;
        }

        public void setId(int id) {
            this.id = id;
        }

        public Item() {
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.id);
            dest.writeString(this.name);
            dest.writeString(this.img);
            dest.writeString(this.price);
            dest.writeString(this.form);
        }

        protected Item(Parcel in) {
            this.id = in.readInt();
            this.name = in.readString();
            this.img = in.readString();
            this.price = in.readString();
            this.form = in.readString();
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
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeInt(this.post_id);
        dest.writeInt(this.type);
        dest.writeString(this.comment);
        dest.writeInt(this.like_num);
        dest.writeLong(this.created_at);
        dest.writeInt(this.star);
        dest.writeInt(this.isLike);
        dest.writeInt(this.is_digest);
        dest.writeString(this.username);
        dest.writeString(this.author);
        dest.writeString(this.img);
        dest.writeString(this.skin);
        dest.writeInt(this.age);
        dest.writeString(this.attachment);
        dest.writeInt(this.level);
        dest.writeParcelable(this.user, flags);
        dest.writeParcelable(this.product, flags);
        dest.writeParcelable(this.detail, flags);
        dest.writeInt(this.star);
        dest.writeParcelable(this.reply, flags);
        dest.writeTypedList(this.replyList);
        dest.writeString(this.replyUserName);
        dest.writeTypedList(this.essence);
        dest.writeParcelable(this.ask, flags);
        dest.writeInt(this.addMoney);
    }

    protected Evaluate(Parcel in) {
        this.id = in.readInt();
        this.post_id = in.readInt();
        this.type = in.readInt();
        this.comment = in.readString();
        this.like_num = in.readInt();
        this.created_at = in.readLong();
        this.star = in.readInt();
        this.isLike = in.readInt();
        this.is_digest = in.readInt();
        this.username = in.readString();
        this.author = in.readString();
        this.img = in.readString();
        this.skin = in.readString();
        this.age = in.readInt();
        this.attachment = in.readString();
        this.level = in.readInt();
        this.user = in.readParcelable(User.class.getClassLoader());
        this.product = in.readParcelable(Product.class.getClassLoader());
        this.detail = in.readParcelable(Item.class.getClassLoader());
        this.star = in.readInt();
        this.reply = in.readParcelable(Evaluate.class.getClassLoader());
        this.replyList = in.createTypedArrayList(Evaluate.CREATOR);
        this.replyUserName = in.readString();
        this.essence = in.createTypedArrayList(Evaluate.CREATOR);
        this.ask = in.readParcelable(Ask.class.getClassLoader());
        this.addMoney = in.readInt();
    }

    public static final Creator<Evaluate> CREATOR = new Creator<Evaluate>() {
        @Override
        public Evaluate createFromParcel(Parcel source) {
            return new Evaluate(source);
        }

        @Override
        public Evaluate[] newArray(int size) {
            return new Evaluate[size];
        }
    };
}
