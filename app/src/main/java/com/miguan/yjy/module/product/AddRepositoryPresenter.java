package com.miguan.yjy.module.product;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.dsk.chain.bijection.Presenter;
import com.miguan.yjy.model.ImageModel;
import com.miguan.yjy.model.ProductModel;
import com.miguan.yjy.model.bean.Brand;
import com.miguan.yjy.model.services.ServicesResponse;

import java.io.File;

/**
 * Copyright (c) 2017/3/27. LiaoPeiKun Inc. All rights reserved.
 */

public class AddRepositoryPresenter extends Presenter<AddRepositoryActivity> {

    public static final String EXTRA_BRAND_NAME = "brand_name";
    public static final String EXTRA_BRAND_ID = "brand_id";
    public static final String EXTRA_OVERTIME = "overtime";

    public static void start(Context context, String brandName, Long brandId, String overtime) {
        Intent intent = new Intent(context, AddRepositoryActivity.class);
        intent.putExtra(EXTRA_BRAND_NAME, brandName);
        intent.putExtra(EXTRA_BRAND_ID, brandId);
        intent.putExtra(EXTRA_OVERTIME, overtime);
        context.startActivity(intent);
    }

    private String mBrandName;

    private Long mBrandId;

    private String mImagePath;

    private String mOvertime;

    @Override
    protected void onCreate(AddRepositoryActivity view, Bundle saveState) {
        super.onCreate(view, saveState);
        mBrandName = getView().getIntent().getStringExtra(EXTRA_BRAND_NAME);
        mBrandId = getView().getIntent().getLongExtra(EXTRA_BRAND_ID, 0);
        mOvertime = getView().getIntent().getStringExtra(EXTRA_OVERTIME);
    }

    @Override
    protected void onCreateView(AddRepositoryActivity view) {
        super.onCreateView(view);
        getView().setData(mBrandName, mOvertime);
    }

    public void submit(String brandName, String productName, int isSeal, String sealTime, int qualityTime, String overdueTime) {
        ProductModel.getInstance().addRepository(mBrandId, brandName, productName, mImagePath, isSeal,
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
        if (requestCode == 100 && resultCode == Activity.RESULT_OK) {
            Brand brand = data.getParcelableExtra("brand");
            mBrandId = brand.getId();
            mBrandName = brand.getName();
            getView().setData(brand.getName(), mOvertime);
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

}
