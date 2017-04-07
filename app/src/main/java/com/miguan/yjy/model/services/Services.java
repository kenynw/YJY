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

import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import rx.Observable;

/**
 * Copyright (c) 2015. LiaoPeiKun Inc. All rights reserved.
 */

public interface Services {

    String BASE_URL = "https://api.yjyapp.com/api/index/";

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
    Observable<Integer> register(
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
     * 我在用的列表
     * //TODO
     * @return
     */
    @GET("?action=userProduct")
    Observable<List<Product>> usedProduct(
            @Query("userId") int userId
    );

    /**
     * 我长草的列表
     * //TODO
     * @return
     */
    @GET("?action=userProduct")
    Observable<List<Product>> likeProduct(
            @Query("userId") int userId
    );

    /**
     * 个人中心/个人资料
     *
     * @param user_id 用户ID
     * @return
     */
    @GET("?action=userInfo")
    Observable<User> userInfo(
            @Query("user_id") int user_id
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
    @GET("user/message/edit")
    Observable<Boolean> modifyProfile(
            @QueryMap Map<String, String> request
    );

    /**
     * 消息列表
     */
    @FormUrlEncoded
    @GET("user/systemmessage/system-message")
    Observable<List<Message>> getMessageList(
            @Query("token") String token,
            @Query("type") Integer type,
            @Query("page") Integer page
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
    @GET("user/feedback/feedback")
    Observable<Feedback> feedback(
            @Query("token") String token,
            @Query("type") Integer type,
            @Query("contact") String contact,
            @Query("content") String content,
            @Query("img") String img,
            @Query("source") int source
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

    /**
     * 产品详情
     *
     * @return
     */
    @GET("?action=productInfo")
    Observable<Product> productDetail(
            @Query("id") int id,
            @Query("uid") int uid
    );

    /**
     * 产品长草
     * @param type － 类型(1产品 2文章)
     * @return
     */
    @GET("?action=productInfo")
    Observable<Product> productLike(
            @Query("id") int productId,
            @Query("userId") int uid,
            @Query("type") int type
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
