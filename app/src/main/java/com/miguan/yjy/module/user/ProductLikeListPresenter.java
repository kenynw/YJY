package com.miguan.yjy.module.user;

import com.dsk.chain.expansion.list.BaseListActivityPresenter;
import com.miguan.yjy.model.UserModel;
import com.miguan.yjy.model.bean.Product;

/**
 * Copyright (c) 2017/3/31. LiaoPeiKun Inc. All rights reserved.
 */

public class ProductLikeListPresenter extends BaseListActivityPresenter<ProductLikeListActivity, Product> {

    private int mCateId;//分类id

    private String mEffect;//功效关键字

    @Override
    protected void onCreateView(ProductLikeListActivity view) {
        super.onCreateView(view);
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

    public void setCateId(int cateId) {
        mCateId = cateId;
    }

    public void setEffect(String effect) {
        mEffect = effect;
    }

}
