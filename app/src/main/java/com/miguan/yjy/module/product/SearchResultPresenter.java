package com.miguan.yjy.module.product;

import android.os.Bundle;

import com.dsk.chain.expansion.list.BaseListActivityPresenter;
import com.miguan.yjy.model.ProductModel;
import com.miguan.yjy.model.bean.Product;
import com.miguan.yjy.model.bean.ProductList;

/**
 * @作者 cjh
 * @日期 2017/3/21 11:31
 * @描述
 */

public class SearchResultPresenter extends BaseListActivityPresenter<SearchResultActivity, Product> {

    private String mKeywords;//关键词

    private int mCateId;//分类id

    private String mEffect;//功效关键字

    @Override
    protected void onCreate(SearchResultActivity view, Bundle saveState) {
        super.onCreate(view, saveState);
        mKeywords = getView().getIntent().getStringExtra("keywords");
    }

    @Override
    protected void onCreateView(SearchResultActivity view) {
        super.onCreateView(view);
        onRefresh();
    }

    @Override
    public void onRefresh() {
        ProductModel.getInstance().searchQuery(mKeywords, mCateId, mEffect, 1)
                .map(product -> {
                    getView().setData(mKeywords, product);
                    return product.getData();
                })
                .unsafeSubscribe(getRefreshSubscriber());
    }

    @Override
    public void onLoadMore() {
        ProductModel.getInstance()
                .searchQuery(mKeywords, mCateId, mEffect, getCurPage())
                .map(ProductList::getData)
                .unsafeSubscribe(getMoreSubscriber());
    }

    public void setCateId(int cateId) {
        mCateId = cateId;
    }

    public void setEffect(String effect) {
        mEffect = effect;
    }
}
