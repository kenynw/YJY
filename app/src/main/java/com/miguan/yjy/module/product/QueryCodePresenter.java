package com.miguan.yjy.module.product;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.dsk.chain.bijection.Presenter;
import com.miguan.yjy.model.ProductModel;
import com.miguan.yjy.model.bean.Brand;
import com.miguan.yjy.model.bean.Product;
import com.miguan.yjy.model.bean.UserProduct;
import com.miguan.yjy.model.services.ServicesResponse;
import com.miguan.yjy.utils.LUtils;

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

    private Brand mBrand = new Brand();

    @Override
    protected void onCreate(QueryCodeActivity view, Bundle saveState) {
        super.onCreate(view, saveState);
        mProduct = getView().getIntent().getParcelableExtra(EXTRA_PRODUCT);
    }

    @Override
    protected void onCreateView(QueryCodeActivity view) {
        super.onCreateView(view);
        if (mProduct != null) {
            mBrand.setId(mProduct.getBrand_id());
            mBrand.setName(mProduct.getBrand_name());
            mBrand.setRule(mProduct.getRule());
            getView().setBrand(mBrand.getName());
        }
    }

    @Override
    protected void onResult(int requestCode, int resultCode, Intent data) {
        super.onResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && requestCode == 100) {
            mBrand = data.getParcelableExtra("brand");
            getView().setBrand(mBrand.getName());
        }
    }

    public void query(String number) {
        if (mBrand.getId() <= 0) {
            LUtils.toast("暂不提供该品牌查询哦~");
            return;
        }

        ProductModel.getInstance().queryCode(mBrand.getId(), number)
                .subscribe(new ServicesResponse<UserProduct>() {
                    @Override
                    public void onNext(UserProduct product) {
                        getView().showQueryDialog(product, mBrand);
                    }
                });
    }

}
