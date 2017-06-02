package com.miguan.yjy.module.product;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;

import com.dsk.chain.bijection.Presenter;
import com.miguan.yjy.model.ImageModel;
import com.miguan.yjy.model.ProductModel;
import com.miguan.yjy.model.bean.Brand;
import com.miguan.yjy.model.bean.Product;
import com.miguan.yjy.model.local.UserPreferences;
import com.miguan.yjy.model.services.ServicesResponse;
import com.miguan.yjy.module.account.LoginActivity;

import java.io.File;

/**
 * Copyright (c) 2017/3/27. LiaoPeiKun Inc. All rights reserved.
 */

public class AddRepositoryPresenter extends Presenter<AddRepositoryActivity> {

    public static final String EXTRA_BRAND = "brand";
    public static final String EXTRA_OVERTIME = "overtime";
    public static final String EXTRA_PRODUCT = "product";

    public static final int REQUEST_CODE_BRAND = 0x200;
    public static final int REQUEST_CODE_PRODUCT = 0x201;

    public static void start(Context context, Brand brand, String overtime, Product product) {
        Intent intent = new Intent(context, UserPreferences.getUserID() > 0 ? AddRepositoryActivity.class : LoginActivity.class);
        if (brand != null) intent.putExtra(EXTRA_BRAND, brand);
        if (!TextUtils.isEmpty(overtime)) intent.putExtra(EXTRA_OVERTIME, overtime);
        if (product != null) intent.putExtra(EXTRA_PRODUCT, product);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        context.startActivity(intent);
    }

    private Brand mBrand;

    private String mImagePath = "";

    private String mOvertime;

    private Product mProduct;

    @Override
    protected void onCreate(AddRepositoryActivity view, Bundle saveState) {
        super.onCreate(view, saveState);
        mBrand = getView().getIntent().getParcelableExtra(EXTRA_BRAND);
        mOvertime = getView().getIntent().getStringExtra(EXTRA_OVERTIME);
        mProduct = getView().getIntent().getParcelableExtra(EXTRA_PRODUCT);
    }

    @Override
    protected void onCreateView(AddRepositoryActivity view) {
        super.onCreateView(view);
        if (mBrand != null) getView().setBrand(mBrand, mOvertime);
        if (mProduct != null) getView().setProduct(mProduct);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        mBrand = intent.getParcelableExtra(EXTRA_BRAND);
        mOvertime = intent.getStringExtra(EXTRA_OVERTIME);
        getView().setBrand(mBrand, mOvertime);
    }

    public void submit(String brandName, String productName, int isSeal, String sealTime, int qualityTime, String overdueTime) {
        ProductModel.getInstance().addRepository(mBrand.getId(), brandName, productName, mImagePath, isSeal,
                sealTime, qualityTime, overdueTime)
                .unsafeSubscribe(new ServicesResponse<String>() {
                    @Override
                    public void onNext(String s) {
                        getView().finish();
                    }
                });
    }

    @Override
    protected void onResult(int requestCode, int resultCode, Intent data) {
        super.onResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case REQUEST_CODE_BRAND:
                    mBrand = data.getParcelableExtra("brand");
                    getView().setBrand(mBrand, mOvertime);
                    break;
                case REQUEST_CODE_PRODUCT:
                    Product product = data.getParcelableExtra("product");
                    mImagePath = product.getProduct_img();
                    getView().setProduct(product);
                    break;
            }
        }
    }

    public void uploadImage(Uri uri) {
        ImageModel.getInstance().uploadImageAsync(ImageModel.OSS_PATH_REPOSITORY, new File(uri.getPath()).getPath())
                .unsafeSubscribe(new ServicesResponse<String>() {
                    @Override
                    public void onNext(String s) {
                        getView().setImage(uri);
                        mImagePath = s;
                    }
                });
    }

    public int getBrandId() {
        return mBrand.getId();
    }
}