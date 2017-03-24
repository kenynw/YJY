package com.miguan.yjy.module.product;

import android.os.Bundle;

import com.dsk.chain.expansion.list.BaseListActivityPresenter;
import com.miguan.yjy.model.ProductModel;
import com.miguan.yjy.model.bean.Product;

/**
 * @作者 cjh
 * @日期 2017/3/20 11:00
 * @描述 搜索框p层
 */

public class SearchActivityPresenter extends BaseListActivityPresenter<SearchActivity, Product> {

    @Override
    protected void onCreate(SearchActivity view, Bundle saveState) {
        super.onCreate(view, saveState);
    }

    @Override
    protected void onCreateView(SearchActivity view) {
        super.onCreateView(view);
        onRefresh();
    }

    @Override
    public void onRefresh() {
        getView().setData();
        ProductModel.getInstantce().getSearchList().unsafeSubscribe(getRefreshSubscriber());
    }
}
