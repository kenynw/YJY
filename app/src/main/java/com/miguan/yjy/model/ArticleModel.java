package com.miguan.yjy.model;

import com.dsk.chain.model.AbsModel;
import com.miguan.yjy.model.bean.Article;
import com.miguan.yjy.model.bean.Banner;
import com.miguan.yjy.model.bean.Category;
import com.miguan.yjy.model.bean.Home;
import com.miguan.yjy.model.services.DefaultTransform;
import com.miguan.yjy.model.services.ServicesClient;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;

/**
 * Copyright (c) 2017/1/10. LiaoPeiKun Inc. All rights reserved.
 */

public class ArticleModel extends AbsModel {

    public static ArticleModel getInstance() {
        return getInstance(ArticleModel.class);
    }

    public Observable<Home> getHomeList() {
        List<Article> list = new ArrayList<>();
        for (int i=0; i<10; i++) {
            Article article = new Article();
            article.setTitle("答疑解惑|面膜到底要不要每天敷");
            article.setCreated_at("2天前");
            article.setLike_num(4);
            list.add(article);
        }
        List<Banner> bannerList = new ArrayList<>();
        for (int i=0; i<3; i++) {
            Banner banner = new Banner();
            banner.setImg("http://p4.music.126.net/0bI3ZYeUuj0HpWCqaTQNkg==/879609302221060.jpg?param=640y300");
            bannerList.add(banner);
        }
        List<Category> categories = new ArrayList<>();
        for (int i=0; i<8; i++) {
            Category category = new Category();
            category.setCate_name("洁面");
            categories.add(category);
        }
        Home home = new Home();
        home.setArticle(list);
        home.setBanner(bannerList);
        home.setCategory(categories);
        return Observable.just(home);
//        return ServicesClient.getServices().home("index").compose(new DefaultTransform<>());
    }

    public Observable<List<Article>> getArticleList(int page) {
        return ServicesClient.getServices().articleList(page).compose(new DefaultTransform<>());
    }

}
