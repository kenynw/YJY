package com.miguan.yjy.model.services;


import com.miguan.yjy.model.bean.Article;
import com.miguan.yjy.model.bean.Brand;
import com.miguan.yjy.model.bean.Evaluate;
import com.miguan.yjy.model.bean.Home;
import com.miguan.yjy.model.bean.ProductList;
import com.miguan.yjy.model.bean.Message;
import com.miguan.yjy.model.bean.Product;
import com.miguan.yjy.model.bean.User;
import com.miguan.yjy.model.bean.Version;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Copyright (c) 2015. LiaoPeiKun Inc. All rights reserved.
 */

public interface Services {

    String BASE_URL = "https://api.yjyapp.com/api/index/";

    String DEBUG_BASE_URL = "http://api.beta.yjyapp.com/api/index/";

    /**
     * 首页
     *
     * @return
     */
    @GET("?action=index")
    Observable<Home> home(
            @Query("user_id") int user_id
    );

    //////////////////用户相关/////////////////////
    /**
     * 用户登录
     *
     * @param mobile   用户名
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
     * @param captcha  验证码
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
     *
     * @param mobile 手机号
     * @param type   0注册1找回2登录
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
     * @param newPwd  新密码
     * @param mobile  手机
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
     * 我在用的列表
     * //TODO
     * @return
     */
    @GET("?action=userProduct")
    Observable<List<Product>> usedProduct(
            @Query("user_id") int userId
    );

    /**
     * 我长草的列表
     * @param userId 用户ID
     * @param cateId 栏目ID
     * @param effect 功效
     * @param page 当前页数
     * @return
     */
    @GET("?action=userGrass")
    Observable<ProductList> likeList(
            @Query("user_id") int userId,
            @Query("cateId") int cateId,
            @Query("effect") String effect,
            @Query("page") int page
    );

    /**
     * 我点评的列表
     * @param userId 用户ID
     * @param type  (选填)类型1产品2文章
     * @param page 当前页数
     * @return
     */
    @GET("?action=comment")
    Observable<List<Evaluate>> userEvaluateList(
            @Query("user_id") int userId,
            @Query("type") int type,
            @Query("page") int page
    );

    /**
     * 我收藏的列表
     * @param userId 用户ID
     * @param page 当前页数
     * @return
     */
    @GET("?action=userCollect")
    Observable<List<Article>> starList(
            @Query("user_id") int userId,
            @Query("page") int page
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
     * @param attribute － 需要修改的字段(username,mobile,birthday,img)
     * @param content － 修改的值
     * @return
     */
    @GET("?action=userUpdate")
    Observable<String> modifyProfile(
            @Query("user_id") int userId,
            @Query("attribute") String attribute,
            @Query("content") String content
    );

    /**
     * 消息列表
     */
    @GET("?action=userPms")
    Observable<List<Message>> getMessageList(
            @Query("user_id") int userId,
            @Query("page") Integer page
    );

    /**
     * 吐槽一下
     * @param userId 用户ID
     * @param username 用户名
     * @param content 吐槽内容
     * @return
     */
    @GET("?action=userFeedback")
    Observable<String> feedback(
            @Query("user_id") int userId,
            @Query("username") String username,
            @Query("content") String content
    );

    ////////////////////产品&&文章//////////////////////
    /**
     *  产品或文章评论列表
     * @param id 产品或文章的ID
     * @param page 当前页数
     * @param user_id 用户ID 可空
     * @param type 类型 1-产品，2-文章
     * @param orderBy 排序字段,目前有'Praise'好评,'middle'中评,'bad'差评,'likeNum'综合排序按点赞数
     * @param desc 排序方式,值为'DESC'或'ASC'
     * @return
     */
    @GET("?action=commentList")
    Observable<List<Evaluate>> evaluateList(
            @Query("id") int id,
            @Query("page") int page,
            @Query("pageSize") int pageSize,
            @Query("user_id") int user_id,
            @Query("type") int type,
            @Query("orderBy") String orderBy,
            @Query("desc") String desc
    );

    /**
     * 产品或文章添加评论
     * @param id 产品或文章的ID
     * @param user_id 用户ID 可空
     * @param type 类型 1-产品，2-文章
     * @param star 星级(文章可不传)
     * @param content 评论内容
     * @return
     */
    @GET("?action=addComment")
    Observable<String> addEvaluate(
            @Query("id") int id,
            @Query("user_id") int user_id,
            @Query("type") int type,
            @Query("star") int star,
            @Query("comment") String content
    );

    /**
     * 产品或文章添加评论
     * @param evaluateId 产品或文章的ID
     * @param user_id 用户ID 可空
     * @return
     */
    @GET("?action=addCommentLike")
    Observable<String> addEvaluateLike(
            @Query("commentId") int evaluateId,
            @Query("user_id") int user_id
    );

    /**
     * 品牌列表
     *
     */
    @GET("?action=brandList")
    Observable<Brand> brandList();

    /**
     * 批号查询
     * @param brandId 品牌ID
     * @param number 批号
     * @return
     */
    @GET("?action=queryCosmetics")
    Observable<Product> queryCode(
            @Query("id") int brandId,
            @Query("number") String number
    );

    /**
     * 添加到保质期提醒
     *
     */
    @GET("?action=addRemind")
    Observable<String> addRepository(
            @Query("user_id") int userId,
            @Query("brand_id") int brandId,
            @Query("brand_name") String brand_name,
            @Query("product") String product,
            @Query("is_seal") int is_seal,
            @Query("seal_time") String seal_time,
            @Query("quality_time") int quality_time,
            @Query("overdue_time") String overdue_time
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
     * 大家都在搜
     */
    @GET("?action=searchHot")
    Observable<List<Product>> searchHot();

    /**
     * 搜索联想接口
     */
    @GET("?action=searchAssociate")
    Observable<List<Product>> searchAssociate(
            @Query("keywords") String keywords
    );

    /**
     * 搜索结果接口
     * @param keywords
     * @param type
     * @param cate_id
     * @param effect
     * @param page
     * @return
     */
    @GET("?action=searchQuery")
    Observable<ProductList> searchQuery(
            @Query("keywords") String keywords,
            @Query("type") int type,
            @Query("cate_id") int cate_id,
            @Query("effect") String effect,
            @Query("page") int page

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
     * 收藏产品或文章
     * @param id 产品或文章ID
     * @param user_id 用户ID
     * @param type 1产品 2文章
     * @return
     */
    @GET("?action=collect")
    Observable<String> addStar(
            @Query("relation_id") int id,
            @Query("user_id") int user_id,
            @Query("type") int type
    );

    ////////////////////其他//////////////////////

    /**
     * 检测更新
     *
     * @return
     */
    @GET("?action=versionUp")
    Observable<Version> checkUpdate();

}
