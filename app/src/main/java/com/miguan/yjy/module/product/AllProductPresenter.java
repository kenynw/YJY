package com.miguan.yjy.module.product;

import android.os.Bundle;

import com.dsk.chain.expansion.list.BaseListFragmentPresenter;
import com.miguan.yjy.adapter.BrandPagerAdapter;
import com.miguan.yjy.model.ProductModel;
import com.miguan.yjy.model.bean.Product;

/**
 * @作者 cjh
 * @日期 2017/5/19 17:50
 * @描述
 */

public class AllProductPresenter extends BaseListFragmentPresenter<AllProductFragment, Product> {

    long brandId;

    @Override
    protected void onCreate(AllProductFragment view, Bundle saveState) {
        super.onCreate(view, saveState);
        brandId = getView().getArguments().getLong(BrandPagerAdapter.EXTRA_BRAND_ID);
        onRefresh();
    }

    @Override
    protected void onCreateView(AllProductFragment view) {
        super.onCreateView(view);
    }

    @Override
    public void onRefresh() {
        super.onRefresh();
        ProductModel.getInstance().getProductList(brandId, 0, 1).unsafeSubscribe(getRefreshSubscriber());

    }

    @Override
    public void onLoadMore() {
        super.onLoadMore();
        ProductModel.getInstance().getProductList(brandId, 0, getCurPage()).unsafeSubscribe(getMoreSubscriber());
    }
}
