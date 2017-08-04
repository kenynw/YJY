package com.miguan.yjy.module.product;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.dsk.chain.expansion.list.BaseListActivityPresenter;
import com.miguan.yjy.model.ProductModel;
import com.miguan.yjy.model.bean.EntityRoot;
import com.miguan.yjy.model.bean.Product;

/**
 * Copyright (c) 2017/5/23. LiaoPeiKun Inc. All rights reserved.
 */

public class RepositoryListPresenter extends BaseListActivityPresenter<RepositoryListActivity, Product> implements RepositoryViewHolder.OnDeleteListener {

    public static final String EXTRA_BRAND_ID = "brand_id";

    private int mBrandId;

    public static void startForResult(Context context, int requestCode, int brandId) {
        Intent intent = new Intent(context, RepositoryListActivity.class);
        intent.putExtra(EXTRA_BRAND_ID, brandId);
        ((Activity) context).startActivityForResult(intent, requestCode);
    }

    @Override
    protected void onCreate(RepositoryListActivity view, Bundle saveState) {
        super.onCreate(view, saveState);
        mBrandId = getView().getIntent().getIntExtra(EXTRA_BRAND_ID, 0);
    }

    @Override
    protected void onCreateView(RepositoryListActivity view) {
        super.onCreateView(view);
        onRefresh();
    }

    @Override
    public void onRefresh() {
        if (mBrandId > 0) {
            ProductModel.getInstance().getProductList(getView().getInputPanel().getInputText(), mBrandId, 1)
                    .map(listEntityList -> {
                        getView().setCount(listEntityList.getPageTotal());
                        return listEntityList.getData();
                    })
                    .unsafeSubscribe(getRefreshSubscriber());
        } else {
            getView().getListView().showEmpty();
            getView().setCount(0);
        }
    }

    @Override
    public void onLoadMore() {
        ProductModel.getInstance().getProductList(getView().getInputPanel().getInputText(), mBrandId, getCurPage())
                .map(EntityRoot::getData)
                .unsafeSubscribe(getMoreSubscriber());
    }

    public void insertLocalProduct(Product product) {
        ProductModel.getInstance().insertProduct(product);
    }

    @Override
    public void onDelete(Product product) {
        ProductModel.getInstance().deleteProduct(product);
        getAdapter().remove(product);
    }

}
