package com.miguan.yjy.module.article;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.ViewGroup;

import com.dsk.chain.expansion.data.BaseDataActivityPresenter;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.miguan.yjy.R;
import com.miguan.yjy.model.AccountModel;
import com.miguan.yjy.model.ArticleModel;
import com.miguan.yjy.model.bean.Article;
import com.miguan.yjy.model.bean.Evaluate;
import com.miguan.yjy.model.services.ServicesResponse;
import com.miguan.yjy.module.account.LoginActivity;
import com.miguan.yjy.utils.LUtils;
import com.miguan.yjy.widget.ShareBottomDialog;
import com.umeng.socialize.UMShareAPI;

import java.util.List;

import rx.Subscriber;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;
import static com.miguan.yjy.module.article.AddEvaluatePresenter.REQUEST_CODE;

/**
 * Copyright (c) 2017/3/23. LiaoPeiKun Inc. All rights reserved.
 */

public class ArticleDetailPresenter extends BaseDataActivityPresenter<ArticleDetailActivity, Article>
        implements RecyclerArrayAdapter.OnMoreListener, android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener {

    public static final String EXTRA_ARTICLE_ID = "article_id";

    private RecyclerArrayAdapter<Evaluate> mAdapter;

    public static void start(Context context, int articleId) {
        Intent intent = new Intent(context, ArticleDetailActivity.class);
        intent.putExtra(EXTRA_ARTICLE_ID, articleId);
        intent.setFlags(FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    private int mArticleId;

    private int mPage;

    @Override
    protected void onCreate(ArticleDetailActivity view, Bundle saveState) {
        super.onCreate(view, saveState);
        getView().getExpansionDelegate().showProgressBar();
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
                .doOnCompleted(() -> mPage = 2)
                .unsafeSubscribe(getDataSubscriber());
    }

    @Override
    public void onMoreShow() {
        ArticleModel.getInstance().getEvaluateList(mArticleId, "", mPage)
                .unsafeSubscribe(new Subscriber<List<Evaluate>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(List<Evaluate> evaluates) {
                        mAdapter.addAll(evaluates);
                        mPage++;
                    }
                });
    }

    @Override
    public void onMoreClick() {

    }

    @Override
    protected void onResult(int requestCode, int resultCode, Intent data) {
        super.onResult(requestCode, resultCode, data);
        UMShareAPI.get(getView()).onActivityResult(requestCode, resultCode, data);
        LUtils.log("request code: " + requestCode + ", result: " + resultCode);
        if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            onRefresh();
        }
    }

    public void star(boolean isStar) {
        if (AccountModel.getInstance().isLogin()) {
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
        if (getData() != null) {
            new ShareBottomDialog.Builder(getView())
                    .setTitle(getData().getTitle())
                    .setUrl(getData().getLinkUrl())
                    .setContent("护肤不交智商税，颜究院帮你科学高效护肤。")
                    .setWxCircleTitle(getData().getTitle() + "(来自颜究院APP)")
                    .setWbContent(getData().getTitle() + " 分享来自#颜究院APP# " + getData().getLinkUrl())
                    .setImageUrl(getData().getArticle_img())
                    .setId(getData().getId())
                    .setType(2)
                    .show();
        }
    }

    public RecyclerArrayAdapter<Evaluate> getAdapter() {
        if (mAdapter == null) {
            mAdapter = new RecyclerArrayAdapter<Evaluate>(getView()) {
                @Override
                public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
                    return new EvaluateViewHolder(parent);
                }
            };
            mAdapter.setMore(R.layout.default_footer_load_more, this);
            mAdapter.setNoMore(R.layout.include_default_footer);
        }
        return mAdapter;
    }

}
