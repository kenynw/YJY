package com.miguan.yjy.model.services;


import com.miguan.yjy.model.bean.Article;
import com.miguan.yjy.model.bean.Ask;
import com.miguan.yjy.model.bean.BrandAll;
import com.miguan.yjy.model.bean.BrandList;
import com.miguan.yjy.model.bean.Component;
import com.miguan.yjy.model.bean.EntityRoot;
import com.miguan.yjy.model.bean.Evaluate;
import com.miguan.yjy.model.bean.FaceScore;
import com.miguan.yjy.model.bean.Home;
import com.miguan.yjy.model.bean.Message;
import com.miguan.yjy.model.bean.Product;
import com.miguan.yjy.model.bean.ProductList;
import com.miguan.yjy.model.bean.Test;
import com.miguan.yjy.model.bean.User;
import com.miguan.yjy.model.bean.UserProduct;
import com.miguan.yjy.model.bean.Version;
import com.miguan.yjy.model.bean.Wiki;
import com.miguan.yjy.utils.LUtils;

import java.util.List;
import java.util.Map;

import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import rx.Observable;

/**
 * Copyright (c) 2015. LiaoPeiKun Inc. All rights reserved.
 */

public interface Services {

    String BASE_BETA_URL = LUtils.isDebug ? "http://m.beta.yjyapp.com/" : "http://m.yjyapp.com/";

    String BASE_URL = "https://api.yjyapp.com/api/index/";

    String DEBUG_BASE_URL = "http://api.beta.yjyapp.com/api/index/";

    /**
     * 首页
     *
     * @return
     */
    @GET("?action=index")
    Observable<Home> home(
            @Query("token") String token
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
     * 第三方登录
     *
     * @param type － 类型（例：weixin）
     * @return
     */
    @GET("?action=thirdLogin")
    Observable<User> thirdLogin(
            @QueryMap Map<String, String> map,
            @Query("type") CharSequence type
    );

    /**
     * 第三方登录
     *
     * @param openid   微信openid
     * @param unionId  微信unionid
     * @param nickname 微信昵称
     * @param sex      － 性别
     * @param province － 省
     * @param city－    市
     * @param avatar   － 微信头像地址
     * @param type     － 类型（例：weixin）
     * @return
     */
    @GET("?action=thirdLogin")
    Observable<User> thirdLogin(
            @Query("openid") CharSequence openid,
            @Query("unionid") CharSequence unionId,
            @Query("nickname") CharSequence nickname,
            @Query("sex") CharSequence sex,
            @Query("province") CharSequence province,
            @Query("city") CharSequence city,
            @Query("headimgurl") CharSequence avatar,
            @Query("type") CharSequence type
    );

    /**
     * 用户注册
     *
     * @param password 密码
     * @param captcha  验证码
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
    @GET("?action=resetPassword")
    Observable<User> modifyPwd(
            @Query("mobile") CharSequence mobile,
            @Query("captcha") CharSequence captcha,
            @Query("newPassword") CharSequence newPwd
    );

    /**
     * 是否绑定手机
     *
     * @param token
     * @return
     */
    @GET("?action=isBind")
    Observable<Integer> isBind(
            @Query("token") String token
    );

    /**
     * 绑定手机
     *
     * @param token
     * @param mobile
     * @param captcha
     * @return
     */
    @GET("?action=mobileBind")
    Observable<String> bindMobile(
            @Query("token") String token,
            @Query("mobile") String mobile,
            @Query("captcha") String captcha
    );

    /**
     * 我在用的列表
     *
     * @return
     */
    @GET("?action=userProduct")
    Observable<List<UserProduct>> usedProduct(
            @Query("token") String token,
            @Query("type") int type,
            @Query("page") int page,
            @Query("pageSize") int pageSize
    );

    /**
     * 我在用的列表
     *
     * @return
     */
    @GET("?action=operateUserproduct")
    Observable<String> deleteUsedProduct(
            @Query("token") String token,
            @Query("id") int id,
            @Query("type") int type
    );

    /**
     * 我长草的列表
     *
     * @param token 用户ID
     * @param cateId 栏目ID
     * @param effect 功效
     * @param page   当前页数
     * @return
     */
    @GET("?action=userGrass")
    Observable<ProductList> likeList(
            @Query("token") String token,
            @Query("cate_id") int cateId,
            @Query("effect") String effect,
            @Query("page") int page
    );

    /**
     * 我点评的列表
     *
     * @param token 用户ID
     * @param page   当前页数
     * @return
     */
    @GET("?action=userComment")
    Observable<List<Evaluate>> userEvaluateList(
            @Query("token") String token,
            @Query("page") int page
    );

    /**
     * 我回复的列表
     *
     * @param token 用户ID
     * @param page   当前页数
     * @return
     */
    @GET("?action=userReply")
    Observable<List<Message>> userReplyList(
            @Query("token") String token,
            @Query("page") int page
    );

    /**
     * 我收藏的列表
     *
     * @param token 用户ID
     * @param page   当前页数
     * @return
     */
    @GET("?action=userCollect")
    Observable<List<Article>> starList(
            @Query("token") String token,
            @Query("page") int page
    );

    /**
     * 个人中心/个人资料
     *
     * @param token 用户ID
     * @return
     */
    @GET("?action=userInfo")
    Observable<User> userInfo(
            @Query("token") String token
    );

    /**
     * 个人资料修改
     *
     * @param attribute － 需要修改的字段(username,mobile,birthday,img)
     * @param content   － 修改的值
     * @return
     */
    @GET("?action=userUpdate")
    Observable<String> modifyProfile(
            @Query("token") String token,
            @Query("attribute") String attribute,
            @Query("content") String content
    );

    /**
     * 颜值记录
     */
    @GET("?action=faceList")
    Observable<List<FaceScore>> faceScores(
            @Query("token") String token,
            @Query("page") Integer page
    );

    /**
     * 消息列表
     */
    @GET("?action=userPms")
    Observable<List<Message>> getMessageList(
            @Query("token") String token,
            @Query("page") Integer page
    );

    /**
     * 吐槽一下
     *
     * @param token   用户ID
     * @param username 用户名
     * @param content  吐槽内容
     * @return
     */
    @GET("?action=userFeedback")
    Observable<String> feedback(
            @Query("token") String token,
            @Query("username") String username,
            @Query("content") String content,
            @Query("system") String system,
            @Query("model") String device,
            @Query("number") String versionName,
            @Query("attachment") String thumb
    );

    ////////////////////产品&&文章//////////////////////

    /**
     * 精华点评列表
     *
     * @param token   用户ID 可空
     * @param page      当前页数
     * @return
     */
    @GET("?action=essenceList")
    Observable<List<Evaluate>> essenceList(
            @Query("token") String token,
            @Query("page") int page
    );

    /**
     * 产品或文章评论列表
     *
     * @param id        产品或文章的ID
     * @param page      当前页数
     * @param token   用户ID 可空
     * @param type      类型 1-产品，2-文章
     * @param orderBy   － 排序方式-默认default综合排序，skin 肤质排序
     * @param condition 筛选星级,目前有'Praise'好评,'middle'中评,'bad'差评
     * @return
     */
    @GET("?action=commentList")
    Observable<List<Evaluate>> evaluateList(
            @Query("id") int id,
            @Query("token") String token,
            @Query("type") int type,
            @Query("orderBy") String orderBy,
            @Query("condition") String condition,
            @Query("page") int page
    );
    /**
     * 产品或文章评论列表
     *
     * @param id        产品或文章的ID
     * @param page      当前页数
     * @param token   用户ID 可空
     * @param type      类型 1-产品，2-文章
     * @param orderBy   － 排序方式-默认default综合排序，skin 肤质排序
     * @param condition 筛选星级,目前有'Praise'好评,'middle'中评,'bad'差评
     * @return
     */
    @GET("?action=commentList")
    Observable<EntityRoot<List<Evaluate>>> evaluateListSecond(
            @Query("id") int id,
            @Query("token") String token,
            @Query("type") int type,
            @Query("orderBy") String orderBy,
            @Query("condition") String condition,
            @Query("page") int page
    );

    /**
     * 回复评论列表
     *
     * @param id        产品或文章的ID
     * @param page      当前页数
     * @param token   用户ID 可空
     * @return
     */
    @GET("?action=commentReplyList")
    Observable<List<Evaluate>> replyList(
            @Query("id") int id,
            @Query("token") String token,
            @Query("page") int page
    );

    /**
     * 评论详情
     *
     * @param id        评论的ID
     * @param token   用户ID 可空
     * @return
     */
    @GET("?action=commentInfo")
    Observable<Evaluate> evaluateDetail(
            @Query("id") int id,
            @Query("token") String token
    );

    /**
     * 产品或文章添加评论
     *
     * @param id      产品或文章的ID
     * @param token 用户ID 可空
     * @param type    类型 1-产品，2-文章
     * @param star    星级(文章可不传)
     * @param image 图片地址（路径attachment)
     * @param content 评论内容
     * @return
     */
    @GET("?action=addComment")
    Observable<String> addEvaluate(
            @Query("id") int id,
            @Query("token") String token,
            @Query("type") int type,
            @Query("star") int star,
            @Query("parent_id") int parentId,
            @Query("attachment") String image,
            @Query("comment") String content
    );

    /**
     * 产品或文章添加评论
     *
     * @param evaluateId 产品或文章的ID
     * @param token    用户ID 可空
     * @return
     */
    @GET("?action=addCommentLike")
    Observable<String> addEvaluateLike(
            @Query("commentId") int evaluateId,
            @Query("token") String token
    );

    /**
     * 品牌详情
     */
    @GET("?action=brandInfo")
    Observable<BrandAll> brandInfo(
            @Query("id") long id
    );

    /**
     * 品牌列表
     */
    @GET("?action=brandList")
    Observable<BrandList> brandList(
            @Query("model") int type
    );

    /**
     * 批号查询
     *
     * @param brandId 品牌ID
     * @param number  批号
     * @return
     */
    @GET("?action=queryCosmetics")
    Observable<UserProduct> queryCode(
            @Query("id") int brandId,
            @Query("number") String number
    );

    /**
     * 添加到保质期提醒
     */
    @GET("?action=addRemind")
    Observable<String> addRepository(
            @Query("token") String token,
            @Query("brand_id") int brandId,
            @Query("brand_name") String brand_name,
            @Query("product") String product,
            @Query("product_img") String img,
            @Query("is_seal") int is_seal,
            @Query("seal_time") String seal_time,
            @Query("quality_time") int quality_time,
            @Query("overdue_time") String overdue_time
    );

    /**
     * 产品列表接口
     * action(string) － 固定值productList
     * brand_id(int) － 品牌ID
     * is_top(int) － 明星产品,1为是,0为不传为所有
     * page(int) － 当前页数
     * pageSize(int) － 每页多少条
     */

    @GET("?action=productList")
    Observable<List<Product>> productList(
            @Query("brand_id") long brand_id,
            @Query("is_top") int is_top,
            @Query("page") int page,
            @Query("pageSize") int pageSize

    );

    /**
     * 产品详情
     *
     * @return
     */
    @GET("?action=productInfo")
    Observable<Product> productDetail(
            @Query("id") int id,
            @Query("token") String token
    );

    /**
     * 成份详情接口
     *
     * @return
     */
    @GET("?action=componentInfo")
    Observable<Component> componentInfo(
            @Query("id") int id
    );

    /**
     * 成份产品接口
     *componentProductIist
     * @return
     */
    @GET("?action=componentProductIist")
    Observable<EntityRoot<List<Product>>> componentProductList(
            @Query("id") int id,
            @Query("page") int page,
            @Query("pageSize") int pageSize
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
    Observable<ProductList> searchAssociate(
            @Query("keywords") String keywords
    );

    /**
     * 搜索结果接口
     *
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


    /**
     * 搜索结果接口
     *
     * @param keywords(string) － 搜索内容
     * @param brandId(int)     － 品牌ID
     * @param isTop(int)       － 明星产品,1为是,0为不传为所有
     * @param page(int)        － 当前页数
     * @return
     */
    @GET("?action=productList")
    Observable<EntityRoot<List<Product>>> productList(
            @Query("search") String keywords,
            @Query("brand_id") int brandId,
            @Query("is_top") int isTop,
            @Query("page") int page
    );

    /**
     * 文章列表
     *
     * @param page 当前页数
     * @return
     */
    @GET("?action=articleList")
    Observable<List<Article>> articleList(
            @Query("token") String token,
            @Query("brand_id") long brand_id,
            @Query("category_id") long category_id,
            @Query("page") int page
    );

    /**
     * 文章详情
     *
     * @return
     */
    @GET("?action=articleInfo")
    Observable<Article> articleDetail(
            @Query("id") int articleId,
            @Query("token") String token
    );

    /**
     * 收藏产品或文章
     *
     * @param id      产品或文章ID
     * @param token 用户ID
     * @param type    1产品 2文章
     * @return
     */
    @GET("?action=collect")
    Observable<String> addStar(
            @Query("relation_id") int id,
            @Query("token") String token,
            @Query("type") int type
    );

    ////////////////////提问//////////////////////

    /**
     * 提问列表
     *
     * @param productId      产品ID
     * @return
     */
    @GET("?action=askList")
    Observable<Ask> askList(
            @Query("product_id") int productId,
            @Query("page") int page
    );

    /**
     * 提问详情
     *
     * @param productId      产品ID
     * @return
     */
    @GET("?action=questionList")
    Observable<Ask> askDetail(
            @Query("product_id") int productId,
            @Query("askid") int askId,
            @Query("page") int page
    );

    /**
     * 添加提问
     *
     * @param token 用户ID
     * @param username 用户名
     * @param type 类型  1 发布问题 2 提交问题回复
     * @param productId 产品ID
     * @param product_name 产品名
     * @param askId 问题ID（type为2必需传）
     * @param content 提交内容
     */
    @GET("?action=subAsk")
    Observable<String> addAsk(
            @Query("token") String token,
            @Query("username") String username,
            @Query("product_id") int productId,
            @Query("product_name") String product_name,
            @Query("type") int type,
            @Query("askid") int askId,
            @Query("content") String content
    );


    ////////////////////测试//////////////////////

    /**
     * 肤质提交接口
     * action(string) － 固定值saveSkin
     * user_id(int) － 用户ID
     * type(string) － dry,tolerance,pigment,compact四种类型
     * value(int) － 对应值
     */
    @GET("?action=saveSkin")
    Observable<String> saveSkin(
            @Query("token") String token,
            @Query("type") String type,
            @Query("value") int value
    );

    /**
     * 用户肤质接口
     * action(string) － 固定值userSkin
     * user_id(int) － 用户ID
     */
    @GET("?action=userSkin")
    Observable<Test> userSkin(
            @Query("token") String token
    );

    /**
     * 肤质推荐列表接口
     * action(string) － 固定值getSkinRecommendList
     * user_id(int) － 用户ID
     * cate_id(string) － 栏目ID
     * page(int) － 当前页数
     * pageSize(int) － 每页多少条
     * user_id(int) － 用户ID
     * min(float) － 最小价格
     * max(float) － 最大价格
     */
    @GET("?action=getSkinRecommendList")
    Observable<List<Product>> getSkinRecommendList(
            @Query("token") String token,
            @Query("cate_id") String cateId,
            @Query("min") float min,
            @Query("max") float max,
            @Query("page") int page,
            @Query("pageSize") int pageSize
    );

    /**
     * 肤质推荐接口
     * action(string) － 固定值getSkinRecommend
     * user_id(int) － 用户ID
     */
    @GET("?action=getSkinRecommend")
    Observable<Test> getSkinRecommend(
            @Query("token") String token
    );
    /**
     * 护肤百科详情页
     * action(string) － 固定值BaikeInfo
     * user_id(int) － 用户ID
     */
    @GET("?action=BaikeInfo")
    Observable<Wiki> getBaikeInfo(
            @Query("baike_id") String baikeId
    );

    ////////////////////其他//////////////////////

    /**
     * 检测更新
     *
     * @return
     */
    @GET("?action=versionUp")
    Observable<Version> checkUpdate();

    /**
     * 未读消息数
     *
     * @return
     */
    @GET("?action=noticeUnread")
    Observable<User> unreadMsg(
            @Query("token") String token
    );

    /**
     * 设置消息为已读
     *
     * @return
     */
    @GET("?action=readNotice")
    Observable<String> setMsgRead(
            @Query("token") String token,
            @Query("id") int msgId,
            @Query("type") String type
    );

    /**
     * 统计Banner点击
     * user_id(int) － 用户ID
     * id(int) － 对应ID
     * type(string) － 1为产品，2为文章
     *
     * @return
     */
    @GET("?action=bannerLog")
    Observable<String> analyticsBanner(
            @Query("token") String token,
            @Query("id") int bannerId
    );

    /**
     * 统计分享
     * user_id(int) － 用户ID
     * id(int) － 对应ID
     * type(string) － 1为产品，2为文章
     *
     * @return
     */
    @GET("?action=addShare")
    Observable<String> analyticsShare(
            @Query("token") String token,
            @Query("id") int id,
            @Query("type") int type
    );

    /**
     * 统计功课模板
     * user_id(int) － 用户ID
     * id(int) － 对应ID
     * type(string) － 1为产品，2为文章
     *
     * @return
     */
    @GET("?action=addLessons")
    Observable<String> analyticsTemplate(
            @Query("token") String token
    );

}
