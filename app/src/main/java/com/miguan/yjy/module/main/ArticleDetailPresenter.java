package com.miguan.yjy.module.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.dsk.chain.expansion.list.BaseListActivityPresenter;
import com.miguan.yjy.model.ArticleModel;
import com.miguan.yjy.model.bean.Article;
import com.miguan.yjy.model.bean.Evaluate;
import com.miguan.yjy.model.services.ServicesResponse;
import com.miguan.yjy.utils.LUtils;

/**
 * Copyright (c) 2017/3/23. LiaoPeiKun Inc. All rights reserved.
 */

public class ArticleDetailPresenter extends BaseListActivityPresenter<ArticleDetailActivity, Evaluate> {

    public static final String EXTRA_ARTICLE = "article";

    public static void start(Context context, Article article) {
        Intent intent = new Intent(context, ArticleDetailActivity.class);
        intent.putExtra(EXTRA_ARTICLE, article);
        context.startActivity(intent);
    }

    private Article mArticle;

    @Override
    protected void onCreate(ArticleDetailActivity view, Bundle saveState) {
        super.onCreate(view, saveState);
        mArticle = getView().getIntent().getParcelableExtra(EXTRA_ARTICLE);
    }

    @Override
    protected void onCreateView(ArticleDetailActivity view) {
        super.onCreateView(view);
        onRefresh();
    }

    @Override
    public void onRefresh() {
        getView().setData(mArticle);
        ArticleModel.getInstance().getEvaluateList(mArticle.getId(), 1, "", "desc").unsafeSubscribe(getRefreshSubscriber());
    }

    @Override
    public void onLoadMore() {
        ArticleModel.getInstance().getEvaluateList(mArticle.getId(), getCurPage(), "", "desc").unsafeSubscribe(getMoreSubscriber());
    }

    public void star() {
        ArticleModel.getInstance().star(mArticle.getId()).unsafeSubscribe(new ServicesResponse<String>() {
            @Override
            public void onNext(String result) {
                getView().setStar(true);
                LUtils.toast("收藏成功");
            }
        });
    }

    public Article getArticle() {
        return mArticle;
    }

}
