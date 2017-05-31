package com.miguan.yjy.module.product;

import android.os.Bundle;

import com.dsk.chain.expansion.list.BaseListFragmentPresenter;
import com.miguan.yjy.adapter.BrandPagerAdapter;
import com.miguan.yjy.model.ProductModel;
import com.miguan.yjy.model.bean.Product;

/**
 * @作者 cjh
 * @日期 2017/5/19 15:57
 * @描述
 */

public class StarProductPresenter extends BaseListFragmentPresenter<StarProductFragment, Product> {
    long brandId;

    @Override
    protected void onCreate(StarProductFragment view, Bundle saveState) {
        super.onCreate(view, saveState);
        brandId = getView().getArguments().getLong(BrandPagerAdapter.EXTRA_BRAND_ID);
        onRefresh();
    }

    @Override
    protected void onCreateView(StarProductFragment view) {
        super.onCreateView(view);
    }

    @Override
    public void onRefresh() {
        super.onRefresh();
        ProductModel.getInstance().getProductList(brandId, 1, 1).unsafeSubscribe(getRefreshSubscriber());

    }
}
