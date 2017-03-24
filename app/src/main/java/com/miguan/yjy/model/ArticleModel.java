package com.miguan.yjy.model;

import com.dsk.chain.model.AbsModel;
import com.miguan.yjy.model.bean.Article;
import com.miguan.yjy.model.bean.Banner;
import com.miguan.yjy.model.bean.Category;
import com.miguan.yjy.model.bean.Evaluate;
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

    public Observable<List<Evaluate>> getEvaluateList(int articleId) {

        List<Evaluate> list = new ArrayList<>();
        if (articleId < 2) {
            for (int i = 0; i < 10; i++) {
                Evaluate evaluate = new Evaluate();
                evaluate.setComment("我是敏感混合容易过敏长痘痘的肌肤，以前都是用雅漾，除了不过敏，还是爱长痘，尤其嘴巴旁边和额头，今年在柜台入手了k乳和特安洁面和修护乳，小痘痘真的统统不见了，对产品表示好感。这款修护霜回购必买，讲真，效果我并没觉得有多好，纯属心理");
                evaluate.setLike_num(i);
                evaluate.setUsername("用户" + i);
                evaluate.setImg("http://p4.music.126.net/JNc_qNwKuUt5-mS-1us0bw==/109951162863818081.jpg?param=130y130");
                list.add(evaluate);
            }
        }

        return Observable.just(list).compose(new DefaultTransform<>());
    }

    public Observable<Article> star(int articleId) {
        return ServicesClient.getServices().articleStar(articleId).compose(new DefaultTransform<>());
    }

}
