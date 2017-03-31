package com.miguan.yjy.model;


import com.dsk.chain.model.AbsModel;
import com.miguan.yjy.model.bean.Feedback;
import com.miguan.yjy.model.bean.Message;
import com.miguan.yjy.model.bean.Product;
import com.miguan.yjy.model.bean.User;
import com.miguan.yjy.model.local.UserPreferences;
import com.miguan.yjy.model.services.DefaultTransform;
import com.miguan.yjy.model.services.ServicesClient;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.Observable;

/**
 * Copyright (c) 2015. LiaoPeiKun Inc. All rights reserved.
 */
public class UserModel extends AbsModel {

    public static UserModel getInstance() {
        return getInstance(UserModel.class);
    }

    /**
     * 获取用户资料/个人中心
     * @return
     */
    public Observable<User> getUserInfo() {
        return ServicesClient.getServices().userInfo(UserPreferences.getUserID()).compose(new DefaultTransform<>());
    }

    /**
     * 我在用的列表
     * @return
     */
    public Observable<List<Product>> getUsedProductList() {
        return ServicesClient.getServices().usedProduct(UserPreferences.getUserID()).compose(new DefaultTransform<>());
    }

    /**
     * 我长草的列表
     * @return
     */
    public Observable<List<Product>> getLikeProductList() {
        return ServicesClient.getServices().usedProduct(UserPreferences.getUserID())
                .compose(new DefaultTransform<>());
    }

    public Observable<Boolean> modifyPwd(String mobile, String code, String newPwd) {
        return ServicesClient.getServices().modifyPwd(mobile, code, newPwd).compose(new DefaultTransform<>());
    }

    public Observable<Boolean> setProfile(Map<String, String> map) {
        map.put("token", UserPreferences.getToken());
        return ServicesClient.getServices().modifyProfile(map).compose(new DefaultTransform<>());
    }

    public Observable<Boolean> setProfile(String key, String value) {
        Map<String, String> map = new HashMap<>();
        map.put("token", UserPreferences.getToken());
        map.put(key, value);
        return ServicesClient.getServices().modifyProfile(map).compose(new DefaultTransform<>());
    }

    /**
     * 消息列表
     */
    public Observable<List<Message>> getMessageList(Integer type, Integer page) {
        return ServicesClient.getServices().getMessageList(UserPreferences.getToken(), type, page).compose(new DefaultTransform<>());
    }

    /**
     * 意见反馈
     * @param type
     * @return
     */
    public Observable<Feedback> saveFeedback(Integer type, String contact, String content, String img) {
        return ServicesClient.getServices()
                .feedback(UserPreferences.getToken(), type, contact, content, img, 0)
                .compose(new DefaultTransform<>());
    }

}
