package com.miguan.yjy.module.product;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.dsk.chain.bijection.Presenter;
import com.miguan.yjy.model.ProductModel;
import com.miguan.yjy.model.bean.Product;
import com.miguan.yjy.model.services.ServicesResponse;

/**
 * Copyright (c) 2017/3/24. LiaoPeiKun Inc. All rights reserved.
 */

public class QueryCodePresenter extends Presenter<QueryCodeActivity> {

    public static final String EXTRA_PRODUCT = "product";

    public static void start(Context context, Product product) {
        Intent intent = new Intent(context, QueryCodeActivity.class);
        intent.putExtra(EXTRA_PRODUCT, product);
        context.startActivity(intent);
    }

    private Product mProduct;

    @Override
    protected void onCreate(QueryCodeActivity view, Bundle saveState) {
        super.onCreate(view, saveState);
        mProduct = getView().getIntent().getParcelableExtra(EXTRA_PRODUCT);
    }

    @Override
    protected void onCreateView(QueryCodeActivity view) {
        super.onCreateView(view);
        getView().setData(mProduct);
    }

    public void query(int brandId, String number) {
        getView().getExpansionDelegate().showProgressBar();
        ProductModel.getInstance().queryCode(brandId, number)
                .doAfterTerminate(() -> getView().getExpansionDelegate().hideProgressBar())
                .subscribe(new ServicesResponse<Product>() {
            @Override
            public void onNext(Product product) {
                mProduct.setStartDay(product.getStartDay());
                mProduct.setEndDay(product.getEndDay());
                getView().showQueryDialog(mProduct);
            }
        });
    }

    public Product getProduct() {
        return mProduct;
    }

}
