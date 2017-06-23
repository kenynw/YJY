package com.miguan.yjy.module.article;

import android.view.ViewGroup;

import com.dsk.chain.bijection.RequiresPresenter;
import com.dsk.chain.expansion.list.BaseListFragment;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.miguan.yjy.adapter.viewholder.ArticleViewHolder;
import com.miguan.yjy.model.bean.Article;

/**
 * Copyright (c) 2017/6/15. LiaoPeiKun Inc. All rights reserved.
 */
@RequiresPresenter(ArticleListFragmentPresenter.class)
public class ArticleListFragment extends BaseListFragment<ArticleListFragmentPresenter, Article> {

    @Override
    public BaseViewHolder<Article> createViewHolder(ViewGroup parent, int viewType) {
        return new ArticleViewHolder(parent);
    }

}
