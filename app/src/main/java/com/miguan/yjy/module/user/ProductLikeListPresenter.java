package com.miguan.yjy.module.user;

import com.dsk.chain.expansion.list.BaseListActivityPresenter;
import com.miguan.yjy.model.UserModel;
import com.miguan.yjy.model.bean.Product;
import com.miguan.yjy.model.bean.ProductList;

/**
 * Copyright (c) 2017/3/31. LiaoPeiKun Inc. All rights reserved.
 */

public class ProductLikeListPresenter extends BaseListActivityPresenter<ProductLikeListActivity, Product> {

    private int mCateId;//分类id

    private String mEffect;//功效关键字

    @Override
    protected void onResume() {
        super.onResume();
        onRefresh();
    }

    @Override
    public void onRefresh() {
        UserModel.getInstance().getLikeProductList(mCateId, mEffect, 1)
                .map(productList -> {
                    getView().setData(productList);
                    return productList.getData();
                })
                .unsafeSubscribe(getRefreshSubscriber());
    }

    @Override
    public void onLoadMore() {
        UserModel.getInstance().getLikeProductList(mCateId, mEffect, getCurPage())
                .map(ProductList::getData)
                .unsafeSubscribe(getMoreSubscriber());
    }

    public void setCateId(int cateId) {
        mCateId = cateId;
    }

    public void setEffect(String effect) {
        mEffect = effect;
    }

}
