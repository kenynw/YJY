package com.miguan.yjy.module.main;

import android.content.Context;
import android.content.Intent;

import com.dsk.chain.expansion.data.BaseDataActivityPresenter;
import com.miguan.yjy.model.bean.Article;

/**
 * Copyright (c) 2017/3/23. LiaoPeiKun Inc. All rights reserved.
 */

public class ArticleDetailPresenter extends BaseDataActivityPresenter<ArticleDetailActivity, Article> {

    public static final String EXTRA_ARTICLE = "article";

    public static void start(Context context, Article article) {
        Intent intent = new Intent(context, ArticleDetailActivity.class);
        intent.putExtra(EXTRA_ARTICLE, article);
        context.startActivity(intent);
    }

    @Override
    protected void onCreateView(ArticleDetailActivity view) {
        super.onCreateView(view);
    }

    public void loadData() {

    }

    public void star() {

    }

}
