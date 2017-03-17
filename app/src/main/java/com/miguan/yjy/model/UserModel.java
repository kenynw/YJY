package com.miguan.yjy.model;


import com.dsk.chain.model.AbsModel;
import com.miguan.yjy.model.bean.Feedback;
import com.miguan.yjy.model.bean.Message;
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
     * 注册验证码
     * @param mobile 手机号
     * @return 发送结果
     */
    public Observable<Boolean> registerCaptcha(String mobile) {
        return ServicesClient.getServices().sendCaptchaRegister(mobile).compose(new DefaultTransform<>());
    }

    /**
     * 忘记密码验证码
     * @param mobile 手机号
     * @return 发送结果
     */
    public Observable<Boolean> forgotCaptcha(String mobile) {
        return ServicesClient.getServices().sendCaptchaReset(mobile).compose(new DefaultTransform<>());
    }

    /**
     * 忘记密码验证码
     * @param mobile 手机号
     * @return 发送结果
     */
    public Observable<Boolean> updateCaptcha(String mobile) {
        return ServicesClient.getServices().sendCaptchaUpdate(mobile, UserPreferences.getToken()).compose(new DefaultTransform<>());
    }

    public Observable<User> login(String mobile, String password) {
        return ServicesClient.getServices().login(mobile, password)
                .doOnNext(this::saveAccount)
                .compose(new DefaultTransform<>());
    }

    public Observable<User> register(String username, String mobile, String code, String password) {
        return ServicesClient.getServices().register(username, mobile, code, password)
                .doOnNext(this::saveAccount)
                .compose(new DefaultTransform<>());
    }

    public Observable<User> userInfo() {
        return ServicesClient.getServices().userInfo(UserPreferences.getToken())
                .compose(new DefaultTransform<>());
    }

    public Observable<Boolean> modifyPwd(String mobile, String code, String newPwd) {
        return ServicesClient.getServices().modifyPwd(mobile, code, newPwd).compose(new DefaultTransform<>());
    }

    public Observable<User> getProfile() {
        return ServicesClient.getServices().userProfile(UserPreferences.getToken()).compose(new DefaultTransform<>());
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

    /**
     * 账号相关本地存储
     * @param user
     */
    private void saveAccount(User user) {
        UserPreferences.setUserID(user.getUser_id() + "");
        UserPreferences.setToken(user.getToken());
    }

}
