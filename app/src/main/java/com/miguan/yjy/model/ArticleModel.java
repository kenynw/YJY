package com.miguan.yjy.model;

import com.dsk.chain.model.AbsModel;
import com.miguan.yjy.model.bean.Article;
import com.miguan.yjy.model.services.DefaultTransform;
import com.miguan.yjy.model.services.ServicesClient;

import java.util.List;

import rx.Observable;

/**
 * Copyright (c) 2017/1/10. LiaoPeiKun Inc. All rights reserved.
 */

public class ArticleModel extends AbsModel {

    public static ArticleModel getInstance() {
        return getInstance(ArticleModel.class);
    }

    public Observable<List<Article>> getArticleList(int type, int page) {
        return ServicesClient.getServices().articleList(type, page).compose(new DefaultTransform<>());
    }

}
