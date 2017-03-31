package com.miguan.yjy.module.user;

import android.view.ViewGroup;

import com.dsk.chain.bijection.RequiresPresenter;
import com.dsk.chain.expansion.list.BaseListActivity;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.miguan.yjy.adapter.viewholder.ProductLikeViewHolder;

/**
 * Copyright (c) 2017/3/31. LiaoPeiKun Inc. All rights reserved.
 */
@RequiresPresenter(ProductLikeListPresenter.class)
public class ProductLikeListActivity extends BaseListActivity<ProductLikeListPresenter> {

    @Override
    protected BaseViewHolder createViewHolder(ViewGroup parent, int viewType) {
        return new ProductLikeViewHolder(parent);
    }

}
