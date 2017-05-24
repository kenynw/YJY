package com.miguan.yjy.module.product;

import android.os.Bundle;

import com.dsk.chain.expansion.list.BaseListActivityPresenter;
import com.miguan.yjy.model.ProductModel;
import com.miguan.yjy.model.bean.EntityRoot;
import com.miguan.yjy.model.bean.Product;

/**
 * Copyright (c) 2017/5/23. LiaoPeiKun Inc. All rights reserved.
 */

public class RepositoryListPresenter extends BaseListActivityPresenter<RepositoryListActivity, Product> {

    private int mBrandId;

    @Override
    protected void onCreate(RepositoryListActivity view, Bundle saveState) {
        super.onCreate(view, saveState);
        mBrandId = getView().getIntent().getIntExtra("brand_id", 0);
    }

    @Override
    protected void onCreateView(RepositoryListActivity view) {
        super.onCreateView(view);
        onRefresh();
    }

    @Override
    public void onRefresh() {
        ProductModel.getInstance().getProductList(getView().getInputPanel().getInputText(), mBrandId, 1)
                .map(listEntityList -> {
                    getView().setCount(listEntityList.getPageTotal());
                    return listEntityList.getData();
                })
                .unsafeSubscribe(getRefreshSubscriber());
    }

    @Override
    public void onLoadMore() {
        ProductModel.getInstance().getProductList(getView().getInputPanel().getInputText(), mBrandId, getCurPage())
                .map(EntityRoot::getData)
                .unsafeSubscribe(getMoreSubscriber());
    }

    public void insertProduct(Product product) {

    }

}
