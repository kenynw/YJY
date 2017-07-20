package com.miguan.yjy.model;

import android.text.TextUtils;

import com.dsk.chain.model.AbsModel;
import com.miguan.yjy.model.bean.User;
import com.miguan.yjy.model.local.UserPreferences;
import com.miguan.yjy.model.services.DefaultTransform;
import com.miguan.yjy.model.services.ServicesClient;
import com.miguan.yjy.utils.LUtils;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import cn.jpush.android.api.JPushInterface;
import rx.Observable;

/**
 * Copyright (c) 2017/3/30. LiaoPeiKun Inc. All rights reserved.
 */

public class AccountModel extends AbsModel {

    public static AccountModel getInstance() {
        return getInstance(AccountModel.class);
    }

    /**
     * 登录
     * @param mobile
     * @param password
     * @return
     */
    public Observable<User> login(String mobile, String password) {
        return ServicesClient.getServices().login(mobile, password, 1)
                .doOnNext(this::saveAccount)
                .compose(new DefaultTransform<>());
    }

    /**
     * 微信登录
     * @param openid 微信openid
     * @param unionId 微信unionid
     * @param nickname 微信昵称
     * @param sex－ 性别
     * @param province － 省
     * @param city－ 市
     * @param avatar － 微信头像地址
     * @return
     */
    public Observable<User> login(CharSequence openid, CharSequence unionId, CharSequence nickname,
                                  CharSequence sex, CharSequence province, CharSequence city,
                                  CharSequence avatar) {
        return ServicesClient.getServices().thirdLogin(openid, unionId, nickname, sex, province, city, avatar, "weixin")
                .doOnNext(this::saveAccount)
                .compose(new DefaultTransform<>());
    }

    /**
     * 微信登录
     * @return
     */
    public Observable<User> login(Map<String, String> map) {
        return ServicesClient.getServices().thirdLogin(map, "weixin")
                .doOnNext(this::saveAccount)
                .compose(new DefaultTransform<>());
    }

    public boolean isLogin() {
        return !TextUtils.isEmpty(UserPreferences.getToken());
    }

    public void clearToken() {
        UserPreferences.setToken("");
        UserPreferences.setUserID(0);
        UserPreferences.setUsername("");
    }

    public Observable<User> register(String mobile, String captcha, String password) {
        return ServicesClient.getServices().register(mobile, captcha, password)
                .doOnNext(this::saveAccount)
                .compose(new DefaultTransform<>());
    }

    /**
     * 注册验证码
     *
     * @param mobile 手机号
     * @return 发送结果
     */
    public Observable<Boolean> registerCaptcha(String mobile) {
        return ServicesClient.getServices().sendCaptcha(mobile, 0).compose(new DefaultTransform<>());
    }

    /**
     * 忘记密码验证码
     *
     * @param mobile 手机号
     * @return 发送结果
     */
    public Observable<Boolean> forgotCaptcha(String mobile) {
        return ServicesClient.getServices().sendCaptcha(mobile, 1).compose(new DefaultTransform<>());
    }

    /**
     * 忘记密码验证码
     * @param mobile 手机号
     * @return 发送结果
     */
    public Observable<Boolean> bindCaptcha(String mobile) {
        return ServicesClient.getServices().sendCaptcha(mobile, 4).compose(new DefaultTransform<>());
    }

    /**
     * 用户重置密码
     * action(string) － 固定值resetPassword
     * mobile(string) － 手机号码
     * captcha(int) － 验证码
     * newPassword(string) － 新密码
     */
    public Observable<User> resetPassword(String mobile,String captcha,String newPwd) {
        return ServicesClient.getServices().modifyPwd(mobile, captcha, newPwd)
                .doOnNext(this::saveAccount)
                .compose(new DefaultTransform<>());
    }

    /**
     * 账号相关本地存储
     *
     * @param user
     */
    private void saveAccount(User user) {
        if (TextUtils.isEmpty(user.getToken())) return;

        UserPreferences.setToken(user.getToken());
        UserPreferences.setUserID(user.getUser_id());
        UserPreferences.setUsername(user.getUsername());
        UserPreferences.setAvatar(user.getImg());

        // 调用 JPush 接口来设置别名。
        Set<String> set = new HashSet<>();
        set.add(LUtils.isDebug ? "Development" : "Production");
        JPushInterface.setAliasAndTags(LUtils.getAppContext(), user.getToken() + "", set, null);
    }

}
