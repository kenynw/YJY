package com.miguan.yjy.module.main;

import com.dsk.chain.expansion.list.BaseListFragmentPresenter;
import com.miguan.yjy.adapter.viewholder.ArticleCate;
import com.miguan.yjy.model.AccountModel;
import com.miguan.yjy.model.ArticleModel;
import com.miguan.yjy.model.CommonModel;
import com.miguan.yjy.model.bean.Evaluate;

import java.util.ArrayList;

/**
 * Copyright (c) 2017/3/20. LiaoPeiKun Inc. All rights reserved.
 */

public class HomeFragmentPresenter extends BaseListFragmentPresenter<HomeFragment, Evaluate> implements
        EvaluateArticleViewHolder.OnLoadDataListener {

    private boolean mIsInit = false;

    private boolean mIsLogin;

    private ArrayList<ArticleCate> mArticleCates;

    @Override
    protected void onCreateView(HomeFragment view) {
        super.onCreateView(view);

        ArrayList<Evaluate> evaluates = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            evaluates.add(new Evaluate());
        }
        getAdapter().addAll(evaluates);
        onRefresh();
    }

    @Override
    public void onRefresh() {
        CommonModel.getInstance().getHomeList()
                .map(home -> {
                    mIsInit = true;
                    mIsLogin = AccountModel.getInstance().isLogin();

                    mArticleCates = home.getArticleGory();
                    getView().setSearchHint(home.getNum());
                    getView().setHeader(home);
                    return home.getEvaluateList();
                })
                .doOnCompleted(() -> getView().setScrollListener())
                .unsafeSubscribe(getRefreshSubscriber());
    }

    @Override
    public void onLoadMore() {
        if (mIsInit) {
            ArticleModel.getInstance().getEssenceList(getCurPage())
                    .map(Evaluate::getEssence)
                    .unsafeSubscribe(getMoreSubscriber());
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mIsLogin && !AccountModel.getInstance().isLogin()) {
            onRefresh();
        }
    }

    @Override
    public ArrayList<ArticleCate> getCates() {
        return mArticleCates;
    }

}
