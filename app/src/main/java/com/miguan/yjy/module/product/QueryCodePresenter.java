package com.miguan.yjy.module.product;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.dsk.chain.bijection.Presenter;
import com.miguan.yjy.model.ProductModel;
import com.miguan.yjy.model.bean.Brand;
import com.miguan.yjy.model.bean.UserProduct;
import com.miguan.yjy.model.services.ServicesResponse;

/**
 * Copyright (c) 2017/3/24. LiaoPeiKun Inc. All rights reserved.
 */

public class QueryCodePresenter extends Presenter<QueryCodeActivity> {

    public static final String EXTRA_PRODUCT = "mProduct";

    public static void start(Context context, UserProduct product) {
        Intent intent = new Intent(context, QueryCodeActivity.class);
        intent.putExtra(EXTRA_PRODUCT, product);
        context.startActivity(intent);
    }

    private UserProduct mProduct;

    @Override
    protected void onCreate(QueryCodeActivity view, Bundle saveState) {
        super.onCreate(view, saveState);
        mProduct = getView().getIntent().getParcelableExtra(EXTRA_PRODUCT);
    }

    @Override
    protected void onCreateView(QueryCodeActivity view) {
        super.onCreateView(view);
        if (mProduct != null) getView().setBrand(mProduct.getBrand_name(), (long) mProduct.getBrand_id());
    }

    @Override
    protected void onResult(int requestCode, int resultCode, Intent data) {
        super.onResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && requestCode == 100) {
            Brand brand = data.getParcelableExtra("brand");
            getView().setBrand(brand.getName(), brand.getId());
        }
    }

    public void query(Long brandId, String number) {
        ProductModel.getInstance().queryCode(brandId, number).subscribe(new ServicesResponse<UserProduct>() {
            @Override
            public void onNext(UserProduct product) {
                getView().showQueryDialog(product, brandId);
            }
        });
    }

}
