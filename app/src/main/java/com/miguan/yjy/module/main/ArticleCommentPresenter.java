package com.miguan.yjy.module.main;

import android.content.Context;
import android.content.Intent;

import com.dsk.chain.bijection.Presenter;

/**
 * Copyright (c) 2017/3/23. LiaoPeiKun Inc. All rights reserved.
 */

public class ArticleCommentPresenter extends Presenter<ArticleCommentActivity> {

    public static final String EXTRA_ARTICLE_ID = "article_id";

    public static void start(Context context, int articleId) {
        Intent intent = new Intent(context, ArticleCommentActivity.class);
        intent.putExtra(EXTRA_ARTICLE_ID, articleId);
        context.startActivity(intent);
    }

}
