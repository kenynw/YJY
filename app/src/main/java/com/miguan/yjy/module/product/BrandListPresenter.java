package com.miguan.yjy.module.product;

import com.dsk.chain.expansion.list.BaseListActivityPresenter;
import com.miguan.yjy.model.ProductModel;
import com.miguan.yjy.model.bean.Brand;

/**
 * Copyright (c) 2017/3/28. LiaoPeiKun Inc. All rights reserved.
 */

public class BrandListPresenter extends BaseListActivityPresenter<BrandListActivity, Brand> {

    @Override
    protected void onCreateView(BrandListActivity view) {
        super.onCreateView(view);
        onRefresh();
    }

    @Override
    public void onRefresh() {
        ProductModel.getInstance().getBrandList()
                .map(brandList -> {
                    getAdapter().addHeader(new BrandHeader(getView(), brandList.getHotCosmetics()));
                    getView().setBrandList(brandList);
                    return brandList.getOtherCosmetics();
                })
                .unsafeSubscribe(getRefreshSubscriber());
    }


}
