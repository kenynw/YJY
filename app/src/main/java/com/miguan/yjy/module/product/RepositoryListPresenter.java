package com.miguan.yjy.module.product;

import android.os.Bundle;
import android.text.TextUtils;

import com.dsk.chain.expansion.list.BaseListActivityPresenter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.miguan.yjy.model.ProductModel;
import com.miguan.yjy.model.bean.EntityRoot;
import com.miguan.yjy.model.bean.Product;
import com.miguan.yjy.utils.LUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Copyright (c) 2017/5/23. LiaoPeiKun Inc. All rights reserved.
 */

public class RepositoryListPresenter extends BaseListActivityPresenter<RepositoryListActivity, Product> {

    public static final String EXTRA_REPOSITORY_PRODUCT = "repository_product";

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

    public void insertLocalProduct(Product product) {
        Gson gson = new Gson();
        String productsStr = LUtils.getPreferences().getString(EXTRA_REPOSITORY_PRODUCT, "");
        List<Product> list = new ArrayList<>();
        if (!TextUtils.isEmpty(productsStr)) {
            list = gson.fromJson(productsStr, new TypeToken<List<Product>>(){}.getType());
        }
        list.add(product);
        LUtils.getPreferences().edit().putString(EXTRA_REPOSITORY_PRODUCT, gson.toJson(list)).apply();
    }

}
