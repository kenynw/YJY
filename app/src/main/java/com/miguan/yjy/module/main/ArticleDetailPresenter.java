package com.miguan.yjy.module.main;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.dsk.chain.expansion.list.BaseListActivityPresenter;
import com.miguan.yjy.model.ArticleModel;
import com.miguan.yjy.model.bean.Article;
import com.miguan.yjy.model.bean.Evaluate;
import com.miguan.yjy.model.local.UserPreferences;
import com.miguan.yjy.model.services.ServicesResponse;
import com.miguan.yjy.module.account.LoginActivity;
import com.miguan.yjy.utils.LUtils;
import com.miguan.yjy.widget.SharePopupWindow;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.media.UMImage;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

/**
 * Copyright (c) 2017/3/23. LiaoPeiKun Inc. All rights reserved.
 */

public class ArticleDetailPresenter extends BaseListActivityPresenter<ArticleDetailActivity, Evaluate> {

    public static final String EXTRA_ARTICLE_ID = "article_id";

    public static void start(Context context, int articleId) {
        Intent intent = new Intent(context, ArticleDetailActivity.class);
        intent.putExtra(EXTRA_ARTICLE_ID, articleId);
        intent.setFlags(FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    private int mArticleId;

    private Article mArticle;

    @Override
    protected void onCreate(ArticleDetailActivity view, Bundle saveState) {
        super.onCreate(view, saveState);
        mArticleId = getView().getIntent().getIntExtra(EXTRA_ARTICLE_ID, 0);
    }

    @Override
    protected void onCreateView(ArticleDetailActivity view) {
        super.onCreateView(view);
        onRefresh();
    }

    @Override
    public void onRefresh() {
        ArticleModel.getInstance().getArticleDetail(mArticleId)
                .map(article -> {
                    mArticle = article;
                    getView().setData(article);
                    return article.getCommentList();
                })
                .doOnError(throwable -> getView().getExpansionDelegate().hideProgressBar())
                .unsafeSubscribe(getRefreshSubscriber());
    }

    @Override
    public void onLoadMore() {
        ArticleModel.getInstance().getEvaluateList(mArticleId, getCurPage(), "").unsafeSubscribe(getMoreSubscriber());
    }

    @Override
    protected void onResult(int requestCode, int resultCode, Intent data) {
        super.onResult(requestCode, resultCode, data);
        UMShareAPI.get(getView()).onActivityResult(requestCode,resultCode,data);
        if (requestCode == 100 && resultCode == Activity.RESULT_OK) {
            onRefresh();
        }
    }

    public void star(boolean isStar) {
        if (UserPreferences.getUserID() > 0) {
            ArticleModel.getInstance().star(mArticleId).unsafeSubscribe(new ServicesResponse<String>() {
                @Override
                public void onNext(String result) {
                    getView().setStar(!isStar);
                    LUtils.toast(isStar ? "取消成功" : "收藏成功");
                }
            });
        } else {
            getView().startActivity(new Intent(getView(), LoginActivity.class));
        }
    }

    /**
     * 分享
     */
    public void share() {
        if (mArticle != null) {
            new SharePopupWindow.Builder(getView())
                    .setTitle(mArticle.getTitle())
                    .setUrl(mArticle.getLinkUrl())
                    .setContent(mArticle.getTitle())
                    .setImage(new UMImage(getView(), mArticle.getArticle_img()))
                    .show(getView().getToolbar());
        }
    }

}
