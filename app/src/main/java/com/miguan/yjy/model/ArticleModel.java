package com.miguan.yjy.model;

import com.dsk.chain.model.AbsModel;
import com.miguan.yjy.model.bean.Article;
import com.miguan.yjy.model.bean.Evaluate;
import com.miguan.yjy.model.bean.Home;
import com.miguan.yjy.model.local.UserPreferences;
import com.miguan.yjy.model.services.DefaultTransform;
import com.miguan.yjy.model.services.ServicesClient;

import java.util.List;

import rx.Observable;

/**
 * Copyright (c) 2017/1/10. LiaoPeiKun Inc. All rights reserved.
 */

public class ArticleModel extends AbsModel {

    private final int TYPE_ARTICLE = 2;

    public static ArticleModel getInstance() {
        return getInstance(ArticleModel.class);
    }

    public Observable<Home> getHomeList() {
        return ServicesClient.getServices().home(UserPreferences.getToken()).compose(new DefaultTransform<>());
    }

    /**
     * 文章列表
     * @param page
     * @return
     */
    public Observable<List<Article>> getArticleList(long brandId, int cateId, int page) {
        return ServicesClient.getServices()
                .articleList(UserPreferences.getToken(), brandId, cateId, page)
                .compose(new DefaultTransform<>());
    }

    /**
     * 文章列表
     * @param articleId
     * @return
     */
    public Observable<Article> getArticleDetail(int articleId) {
        return ServicesClient.getServices().articleDetail(articleId, UserPreferences.getToken()).compose(new DefaultTransform<>());
    }

    /**
     * 评论列表
     */
    public Observable<List<Evaluate>> getReplyList(int id, int page) {
        return ServicesClient.getServices()
                .replyList(id, UserPreferences.getToken(), page)
                .compose(new DefaultTransform<>());
    }

    /**
     * 评论列表
     */
    public Observable<List<Evaluate>> getEvaluateList(int id, String orderBy, int page) {
        return ServicesClient.getServices()
                .evaluateList(id, UserPreferences.getToken(), TYPE_ARTICLE, orderBy,"", page)
                .compose(new DefaultTransform<>());
    }

    /**
     * 评论详情
     */
    public Observable<Evaluate> getEvaluateDetail(int id) {
        return ServicesClient.getServices()
                .evaluateDetail(id, UserPreferences.getToken())
                .compose(new DefaultTransform<>());
    }

    /**
     * 精华点评列表
     */
    public Observable<List<Evaluate>> getEssenceList(int page) {
        return ServicesClient.getServices()
                .essenceList(UserPreferences.getToken(), page)
                .compose(new DefaultTransform<>());
    }

    /**
     * 添加评论
     */
    public Observable<String> addEvaluate(int articleId, int type, int parentId, String image, String content) {
        return ServicesClient.getServices()
                .addEvaluate(articleId, UserPreferences.getToken(), type, 0, parentId, image, content)
                .compose(new DefaultTransform<>());
    }

    /**
     * 评论点赞
     * @param evaluateId
     * @return
     */
    public Observable<String> addEvaluateLike(int evaluateId) {
        return ServicesClient.getServices().addEvaluateLike(evaluateId, UserPreferences.getToken()).compose(new DefaultTransform<>());
    }

    /**
     * 收藏文章
     * @param articleId
     * @return
     */
    public Observable<String> star(int articleId) {
        return ServicesClient.getServices().addStar(articleId, UserPreferences.getToken(), TYPE_ARTICLE).compose(new DefaultTransform<>());
    }

    /**
     * 我的收藏列表
     * @param page
     * @return
     */
    public Observable<List<Article>> getStarList(int page) {
        return ServicesClient.getServices().starList(UserPreferences.getToken(), page).compose(new DefaultTransform<>());
    }

}
