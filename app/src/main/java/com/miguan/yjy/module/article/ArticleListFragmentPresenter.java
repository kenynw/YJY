package com.miguan.yjy.module.article;

import android.os.Bundle;

import com.dsk.chain.expansion.list.BaseListFragmentPresenter;
import com.miguan.yjy.model.ArticleModel;
import com.miguan.yjy.model.bean.Article;

/**
 * Copyright (c) 2017/6/15. LiaoPeiKun Inc. All rights reserved.
 */

public class ArticleListFragmentPresenter extends BaseListFragmentPresenter<ArticleListFragment, Article> {

    private static final String EXTRA_CATE_ID = "cate_id";

    private int mCateId;

    public static ArticleListFragment get(int cateId) {
        ArticleListFragment fragment = new ArticleListFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(EXTRA_CATE_ID, cateId);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected void onCreate(ArticleListFragment view, Bundle saveState) {
        super.onCreate(view, saveState);
        if (getView().getArguments() != null) {
            mCateId = getView().getArguments().getInt(EXTRA_CATE_ID, 0);
        }
    }

    @Override
    protected void onCreateView(ArticleListFragment view) {
        super.onCreateView(view);
        onRefresh();
    }

    @Override
    public void onRefresh() {
        ArticleModel.getInstance().getArticleList(0, mCateId, 1).unsafeSubscribe(getRefreshSubscriber());
    }

    @Override
    public void onLoadMore() {
        ArticleModel.getInstance().getArticleList(0, mCateId, getCurPage()).unsafeSubscribe(getMoreSubscriber());
    }

}
