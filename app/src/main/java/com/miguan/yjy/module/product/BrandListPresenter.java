package com.miguan.yjy.module.product;

import com.dsk.chain.expansion.list.BaseListActivityPresenter;
import com.miguan.yjy.model.ProductModel;
import com.miguan.yjy.model.bean.Brand;
import com.miguan.yjy.model.bean.BrandList;

/**
 * Copyright (c) 2017/3/28. LiaoPeiKun Inc. All rights reserved.
 */

public class BrandListPresenter extends BaseListActivityPresenter<BrandListActivity, Brand> {

    private BrandList mBrandList;

    @Override
    protected void onCreateView(BrandListActivity view) {
        super.onCreateView(view);
        onRefresh();
    }

    @Override
    public void onRefresh() {
        ProductModel.getInstance().getBrandList()
                .map(brandList -> {
                    mBrandList = brandList;
                    getAdapter().addHeader(new BrandHeader(getView(), brandList.getHotCosmetics()));
                    getView().setBrandList(brandList);
                    return brandList.getOtherCosmetics();
                })
                .unsafeSubscribe(getRefreshSubscriber());
    }

    public void insertBrand(Brand brand) {
        ProductModel.getInstance().insertBrand(brand);
    }

    public BrandList getBrandList() {
        return mBrandList;
    }
}
