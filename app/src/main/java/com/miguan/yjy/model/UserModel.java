package com.miguan.yjy.model;


import com.dsk.chain.model.AbsModel;
import com.miguan.yjy.model.bean.Evaluate;
import com.miguan.yjy.model.bean.LikeProductList;
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
//        List<Product> list = new ArrayList<>();
//        for (int i = 0; i < 10; i++) {
//            Product product = new Product();
//            product.setProduct_name("adfasdf");
//            product.setProduct_date("2033-2-2");
//            list.add(product);
//        }
//        return Observable.just(list);
        return ServicesClient.getServices().usedProduct(UserPreferences.getUserID()).compose(new DefaultTransform<>());
    }

    /**
     * 我长草的列表
     * @return
     */
    public Observable<LikeProductList> getLikeProductList(int cateId, String effect, int page) {
        return ServicesClient.getServices().likeList(UserPreferences.getUserID(), cateId, effect, page).compose(new DefaultTransform<>());
    }

    /**
     * 消息列表
     */
    public Observable<List<Message>> getMessageList(Integer page) {
        return ServicesClient.getServices().getMessageList(UserPreferences.getUserID(), page).compose(new DefaultTransform<>());
    }

    /**
     * 我点评的列表
     * @param page 页数
     * @return 点评列表
     */
    public Observable<List<Evaluate>> getEvaluateList(int page) {
        return ServicesClient.getServices().userEvaluateList(UserPreferences.getUserID(), 1, page)
                .compose(new DefaultTransform<>());
    }

    public Observable<Boolean> modifyPwd(String mobile, String code, String newPwd) {
        return ServicesClient.getServices().modifyPwd(mobile, code, newPwd).compose(new DefaultTransform<>());
    }

    public Observable<Boolean> setProfile(Map<String, String> map) {
//        map.put("token", UserPreferences.getToken());
        return ServicesClient.getServices().modifyProfile(map).compose(new DefaultTransform<>());
    }

    public Observable<Boolean> setProfile(String key, String value) {
        Map<String, String> map = new HashMap<>();
//        map.put("token", UserPreferences.getToken());
        map.put(key, value);
        return ServicesClient.getServices().modifyProfile(map).compose(new DefaultTransform<>());
    }

    /**
     * 意见反馈
     * @param content 反馈内容
     * @return
     */
    public Observable<String> addFeedback(String content) {
        return ServicesClient.getServices()
                .feedback(UserPreferences.getUserID(), UserPreferences.getUsername(), content)
                .compose(new DefaultTransform<>());
    }

}
