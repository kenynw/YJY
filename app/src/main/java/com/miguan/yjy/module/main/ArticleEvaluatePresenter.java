package com.miguan.yjy.module.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.dsk.chain.bijection.Presenter;
import com.miguan.yjy.model.ArticleModel;
import com.miguan.yjy.model.services.ServicesResponse;

/**
 * Copyright (c) 2017/3/23. LiaoPeiKun Inc. All rights reserved.
 */

public class ArticleEvaluatePresenter extends Presenter<ArticleEvaluateActivity> {

    public static final String EXTRA_ARTICLE_ID = "article_id";

    public static void start(Context context, int articleId) {
        Intent intent = new Intent(context, ArticleEvaluateActivity.class);
        intent.putExtra(EXTRA_ARTICLE_ID, articleId);
        context.startActivity(intent);
    }

    private int mArticleId;

    @Override
    protected void onCreate(ArticleEvaluateActivity view, Bundle saveState) {
        super.onCreate(view, saveState);
        mArticleId = getView().getIntent().getIntExtra(EXTRA_ARTICLE_ID, 0);
    }

    public void submit(String content) {
        ArticleModel.getInstance().addEvaluate(mArticleId, content).subscribe(new ServicesResponse<String>() {
            @Override
            public void onNext(String evaluate) {
                getView().finish();
            }
        });
    }

}
