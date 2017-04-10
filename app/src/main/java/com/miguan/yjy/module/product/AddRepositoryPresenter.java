package com.miguan.yjy.module.product;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.dsk.chain.bijection.Presenter;
import com.miguan.yjy.model.ProductModel;
import com.miguan.yjy.model.bean.Product;
import com.miguan.yjy.model.services.ServicesResponse;

/**
 * Copyright (c) 2017/3/27. LiaoPeiKun Inc. All rights reserved.
 */

public class AddRepositoryPresenter extends Presenter<AddRepositoryActivity> {

    public static final String EXTRA_PRODUCT = "product";

    public static void start(Context context, Product product) {
        Intent intent = new Intent(context, AddRepositoryActivity.class);
        intent.putExtra(EXTRA_PRODUCT, product);
        context.startActivity(intent);
    }

    private Product mProduct;

    @Override
    protected void onCreate(AddRepositoryActivity view, Bundle saveState) {
        super.onCreate(view, saveState);
        mProduct = getView().getIntent().getParcelableExtra(EXTRA_PRODUCT);
    }

    @Override
    protected void onCreateView(AddRepositoryActivity view) {
        super.onCreateView(view);
        getView().setData(mProduct);
    }

    public void submit(int brandId, String brandName, String productName, int isSeal, String sealTime, int qualityTime, String overdueTime) {
        ProductModel.getInstance().addRepository(brandId, brandName, productName, isSeal, sealTime, qualityTime, overdueTime)
                .unsafeSubscribe(new ServicesResponse<String>() {
                    @Override
                    public void onNext(String s) {

                    }
                });
    }

}
