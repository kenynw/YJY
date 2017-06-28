package com.miguan.yjy.model;


import android.os.Build;

import com.dsk.chain.model.AbsModel;
import com.miguan.yjy.model.bean.Evaluate;
import com.miguan.yjy.model.bean.FaceScore;
import com.miguan.yjy.model.bean.Message;
import com.miguan.yjy.model.bean.ProductList;
import com.miguan.yjy.model.bean.User;
import com.miguan.yjy.model.bean.UserProduct;
import com.miguan.yjy.model.local.UserPreferences;
import com.miguan.yjy.model.services.DefaultTransform;
import com.miguan.yjy.model.services.ServicesClient;
import com.miguan.yjy.utils.LUtils;

import java.util.List;

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
        Observable<User> userObservable = ServicesClient.getServices().userInfo(UserPreferences.getUserID());
        Observable<User> unreadMsg = ServicesClient.getServices().unreadMsg(UserPreferences.getUserID());

        return Observable.zip(userObservable, unreadMsg, (user, user2) -> {
            user.setOverdueNum(user2.getOverdueNum());
            user.setUnReadNUM(user2.getUnReadNUM());
            return user;
        })
                .doOnNext(user -> UserPreferences.setAvatar(user.getImg()))
                .compose(new DefaultTransform<>());
    }

    /**
     * 颜值详情列表
     * @return
     */
    public Observable<List<FaceScore>> getFaceScoreList(int page) {
        return ServicesClient.getServices().faceScores(UserPreferences.getUserID(), page).compose(new DefaultTransform<>());
    }

    /**
     * 我在用的列表
     * @return
     */
    public Observable<List<UserProduct>> getUsedProductList(int type, int page) {
        return ServicesClient.getServices().usedProduct(UserPreferences.getUserID(), type, page, 10).compose(new DefaultTransform<>());
    }

    /**
     /**
     * 删除我在用的
     * @return
     */
    public Observable<String> deleteUsedProduct(int id, int type) {
        return ServicesClient.getServices().deleteUsedProduct(UserPreferences.getUserID(), id, type).compose(new DefaultTransform<>());
    }

    /**
     * 我长草的列表
     * @return
     */
    public Observable<ProductList> getLikeProductList(int cateId, String effect, int page) {
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
        return ServicesClient.getServices().userEvaluateList(UserPreferences.getUserID(), page)
                .compose(new DefaultTransform<>());
    }

    /**
     * 我回复的列表
     * @param page 页数
     * @return 点评列表
     */
    public Observable<List<Message>> getReplyList(int page) {
        return ServicesClient.getServices().userReplyList(UserPreferences.getUserID(), page)
                .compose(new DefaultTransform<>());
    }

    public Observable<String> modifyPwd(String mobile, String code, String newPwd) {
        return ServicesClient.getServices().modifyPwd(mobile, code, newPwd).compose(new DefaultTransform<>());
    }

    public Observable<String> modifyProfile(String key, String value) {
        return ServicesClient.getServices().modifyProfile(UserPreferences.getUserID(), key, value).compose(new DefaultTransform<>());
    }

    public Observable<String> bindMobile(String mobile, String captcha) {
        return ServicesClient.getServices().bindMobile(UserPreferences.getUserID(), mobile, captcha).compose(new DefaultTransform<>());
    }

    /**
     * 意见反馈
     * @param content 反馈内容
     * @return
     */
    public Observable<String> addFeedback(String content, String thumb) {
        return ServicesClient.getServices()
                .feedback(UserPreferences.getUserID(), UserPreferences.getUsername(), content,
                        "API " + Build.VERSION.SDK_INT + " " + Build.VERSION.RELEASE,
                        Build.BRAND + " " + Build.MODEL,
                        LUtils.getAppVersionName(),
                        thumb
                )
                .compose(new DefaultTransform<>());
    }

}
