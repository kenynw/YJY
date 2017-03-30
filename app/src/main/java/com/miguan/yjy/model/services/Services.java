package com.miguan.yjy.model.services;


import com.miguan.yjy.model.bean.Article;
import com.miguan.yjy.model.bean.Feedback;
import com.miguan.yjy.model.bean.Home;
import com.miguan.yjy.model.bean.Message;
import com.miguan.yjy.model.bean.Product;
import com.miguan.yjy.model.bean.User;
import com.miguan.yjy.model.bean.Version;

import java.util.List;
import java.util.Map;

import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Copyright (c) 2015. LiaoPeiKun Inc. All rights reserved.
 */

public interface Services {

    String BASE_URL = "http://api.yjyapp.com/api/index/";

    /**
     * 首页
     * @return
     */
    @GET("?action=index")
    Observable<Home> home();

    //////////////////用户相关/////////////////////
    /**
     * 用户登录
     * @param mobile 用户名
     * @param password 密码
     * @return 是否成功
     */
    @GET("?action=login")
    Observable<User> login(
            @Query("mobile") CharSequence mobile,
            @Query("password") CharSequence password,
            @Query("type") int type
    );

    /**
     * 用户注册
     *
     * @param password 密码
     * @param captcha 验证码
     * @return 结果
     */
    @GET("?action=register")
    Observable<User> register(
            @Query("mobile") CharSequence mobile,
            @Query("captcha") CharSequence captcha,
            @Query("password") CharSequence password
    );

    /**
     * 发送注册验证码
     * @param mobile 手机号
     * @param type 0注册1找回2登录
     * @return 是否成功
     */
    @GET("?action=message")
    Observable<Boolean> sendCaptcha(
            @Query("mobile") CharSequence mobile,
            @Query("type") int type
    );

    /**
     * 忘记密码
     *
     * @param newPwd 新密码
     * @param mobile 手机
     * @param captcha 验证码
     * @return
     */
    @GET("user/user/reset-password")
    Observable<Boolean> modifyPwd(
            @Query("mobile") CharSequence mobile,
            @Query("captcha") CharSequence captcha,
            @Query("password") CharSequence newPwd
    );

    /**
     * 个人中心
     *
     * @param token 登录令牌
     * @return
     */
    @FormUrlEncoded
    @POST("user/center/index")
    Observable<User> userInfo(
            @Field("token") CharSequence token
    );

    /**
     * 用户收藏、长草列表
     * @param userId 用户ID
     * @param type 类型(1产品 2文章)
     * @return
     */
    @GET("?action=collect")
    Observable<List<Article>> starList(
            @Query("userId") int userId,
            @Query("productId") int productId,
            @Query("type") int type
    );

    /**
     * 个人资料
     *
     * @param token 登录令牌
     * @return
     */
    @FormUrlEncoded
    @POST("user/message/index")
    Observable<User> userProfile(
            @Field("token") CharSequence token
    );

    /**
     * 个人资料修改
     * token
     * photo
     * qq
     * email
     * actuality
     * birthday
     * province
     * city
     * sign
     * @return
     */
    @FormUrlEncoded
    @POST("user/message/edit")
    Observable<Boolean> modifyProfile(
            @FieldMap Map<String, String> request
    );

    /**
     * 消息列表
     */
    @FormUrlEncoded
    @POST("user/systemmessage/system-message")
    Observable<List<Message>> getMessageList(
            @Field("token") String token,
            @Field("type") Integer type,
            @Field("page") Integer page
    );

    /**
     * 吐槽一下
     * @param token 登录令牌
     * @param type  类型（0:产品建议、1:发现BUG、2:举报作弊、3:其他）
     * @param contact 联系方式
     * @param content 吐槽内容
     * @param img 上传照片
     * @param source 信息来源（来源 0:安卓 1：IOS）
     * @return
     */
    @FormUrlEncoded
    @POST("user/feedback/feedback")
    Observable<Feedback> feedback(
            @Field("token") String token,
            @Field("type") Integer type,
            @Field("contact") String contact,
            @Field("content") String content,
            @Field("img") String img,
            @Field("source") int source
    );

    ////////////////////产品//////////////////////
    /**
     * 批号查询
     * //TODO
     * @return
     */
    @GET("?action=queryCode")
    Observable<Product> queryCode(
            @Query("brand") String brand,
            @Query("name") String name
    );


    ////////////////////文章//////////////////////
    /**
     * 文章列表
     *
     * @param page 当前页数
     * @return
     */
    @GET("?action=articleList")
    Observable<List<Article>> articleList(
            @Query("page") int page,
            @Query("pageSize") int pageSize
    );

    /**
     * 收藏文章
     * //TODO
     * @param articleId 文章ID
     * @return
     */
    @GET("?action=articleStar")
    Observable<Article> articleStar(
            @Query("article_id") int articleId
    );

    ////////////////////其他//////////////////////
    /**
     * 检测更新
     *
     * @param version 当前版本号
     * @return
     */
    @GET("system/system/version-upgrade")
    Observable<Version> checkUpdate(
            @Query("version") String version
    );

}
