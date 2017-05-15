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
        return ServicesClient.getServices().home(UserPreferences.getUserID()).compose(new DefaultTransform<>());
    }

    /**
     * 文章列表
     * @param page
     * @return
     */
    public Observable<List<Article>> getArticleList(int page) {
        return ServicesClient.getServices().articleList(page).compose(new DefaultTransform<>());
    }

    /**
     * 文章列表
     * @param articleId
     * @return
     */
    public Observable<Article> getArticleDetail(int articleId) {
        return ServicesClient.getServices().articleDetail(articleId, UserPreferences.getUserID()).compose(new DefaultTransform<>());
    }

    /**
     * 评论列表
     * @param articleId
     * @param page
     * @param orderBy
     * @return
     */
    public Observable<List<Evaluate>> getEvaluateList(int articleId, int page, String orderBy) {
        return ServicesClient.getServices().evaluateList(
                articleId, page, 10, UserPreferences.getUserID(), TYPE_ARTICLE, orderBy,""
        ).compose(new DefaultTransform<>());
    }

    /**
     * 添加评论
     * @param articleId
     * @param content
     * @return
     */
    public Observable<String> addEvaluate(int articleId, String content) {
        return ServicesClient.getServices()
                .addEvaluate(articleId, UserPreferences.getUserID(), TYPE_ARTICLE, 0, content)
                .compose(new DefaultTransform<>());
    }

    /**
     * 评论点赞
     * @param evaluateId
     * @return
     */
    public Observable<String> addEvaluateLike(int evaluateId) {
        return ServicesClient.getServices().addEvaluateLike(evaluateId, UserPreferences.getUserID()).compose(new DefaultTransform<>());
    }

    /**
     * 收藏文章
     * @param articleId
     * @return
     */
    public Observable<String> star(int articleId) {
        return ServicesClient.getServices().addStar(articleId, UserPreferences.getUserID(), TYPE_ARTICLE).compose(new DefaultTransform<>());
    }

    /**
     * 我的收藏列表
     * @param page
     * @return
     */
    public Observable<List<Article>> getStarList(int page) {
        return ServicesClient.getServices().starList(UserPreferences.getUserID(), page).compose(new DefaultTransform<>());
    }

}
