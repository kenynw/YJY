package com.miguan.yjy.module.product;

import com.dsk.chain.expansion.list.BaseListActivityPresenter;
import com.miguan.yjy.adapter.viewholder.BrandViewHolder;
import com.miguan.yjy.model.ProductModel;
import com.miguan.yjy.model.bean.Brand;

import java.util.List;

/**
 * Copyright (c) 2017/3/28. LiaoPeiKun Inc. All rights reserved.
 */

public class BrandListPresenter extends BaseListActivityPresenter<BrandListActivity, Brand> implements BrandViewHolder.OnBrandDeleteListener {

    private List<Brand> mBrandList;

    @Override
    protected void onCreateView(BrandListActivity view) {
        super.onCreateView(view);
        getAdapter().setOnItemClickListener(getView());
        onRefresh();
    }

    @Override
    public void onRefresh() {
        ProductModel.getInstance().getBrandList()
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
