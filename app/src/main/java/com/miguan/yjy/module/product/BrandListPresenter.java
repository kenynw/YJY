package com.miguan.yjy.module.product;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.dsk.chain.expansion.list.BaseListActivityPresenter;
import com.miguan.yjy.adapter.viewholder.BrandViewHolder;
import com.miguan.yjy.model.ProductModel;
import com.miguan.yjy.model.bean.Brand;

import java.util.List;

/**
 * Copyright (c) 2017/3/28. LiaoPeiKun Inc. All rights reserved.
 */

public class BrandListPresenter extends BaseListActivityPresenter<BrandListActivity, Brand> implements BrandViewHolder.OnBrandDeleteListener {

    public static final String EXTRA_TYPE = "type";

    public static final String EXTRA_EMPTY_TYPE = "empty_type";

    private List<Brand> mBrandList;

    private int mType;

    public static void start(Activity context, int requestCode, boolean showAll, boolean canAdd) {
        Intent intent = new Intent(context, BrandListActivity.class);
        intent.putExtra(EXTRA_TYPE, showAll);
        intent.putExtra(EXTRA_EMPTY_TYPE, canAdd);
        context.startActivityForResult(intent, requestCode);
    }

    @Override
    protected void onCreate(BrandListActivity view, Bundle saveState) {
        super.onCreate(view, saveState);
        mType = getView().getIntent().getBooleanExtra(EXTRA_TYPE, true) ? 1 : 0;
    }

    @Override
    protected void onCreateView(BrandListActivity view) {
        super.onCreateView(view);
        getAdapter().setOnItemClickListener(getView());
        onRefresh();
    }

    @Override
    public void onRefresh() {
        ProductModel.getInstance().getBrandList(mType)
                .map(brandList -> {
                    mBrandList = brandList.getCosmeticsList();
                    return brandList.getCosmeticsList();
                })
                .unsafeSubscribe(getRefreshSubscriber());
    }

    public void insertBrand(Brand brand) {
        ProductModel.getInstance().insertBrand(brand);
    }

    public List<Brand> getBrandList() {
        return mBrandList;
    }

    @Override
    public void onBrandDelete(Brand brand) {
        ProductModel.getInstance().deleteBrand(brand);
        getAdapter().remove(brand);
    }

}
