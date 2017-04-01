package com.miguan.yjy.model.services;

import com.dsk.chain.model.AbsModel;
import com.miguan.yjy.model.bean.User;
import com.miguan.yjy.model.local.UserPreferences;

import rx.Observable;

/**
 * Copyright (c) 2017/3/30. LiaoPeiKun Inc. All rights reserved.
 */

public class AccountModel extends AbsModel {


    public static AccountModel getInstance() {
        return getInstance(AccountModel.class);
    }

    public Observable<User> login(String mobile, String password) {
        return ServicesClient.getServices().login(mobile, password, 1)
                .doOnNext(this::saveAccount)
                .compose(new DefaultTransform<>());
    }

    public Observable<Integer> register(String mobile, String captcha, String password) {
        return ServicesClient.getServices().register(mobile, captcha, password)
                .compose(new DefaultTransform<>());
    }

    /**
     * 注册验证码
     * @param mobile 手机号
     * @return 发送结果
     */
    public Observable<Boolean> registerCaptcha(String mobile) {
        return ServicesClient.getServices().sendCaptcha(mobile, 0).compose(new DefaultTransform<>());
    }

    /**
     * 忘记密码验证码
     * @param mobile 手机号
     * @return 发送结果
     */
    public Observable<Boolean> forgotCaptcha(String mobile) {
        return ServicesClient.getServices().sendCaptcha(mobile, 2).compose(new DefaultTransform<>());
    }

    /**
     * 账号相关本地存储
     * @param user
     */
    private void saveAccount(User user) {
        UserPreferences.setUserID(user.getUser_id());
    }

}
