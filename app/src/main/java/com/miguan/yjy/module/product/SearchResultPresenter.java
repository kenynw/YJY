package com.miguan.yjy.module.product;

import com.dsk.chain.expansion.list.BaseListActivityPresenter;
import com.miguan.yjy.model.ProductModel;

/**
 * @作者 cjh
 * @日期 2017/3/21 11:31
 * @描述
 */

public class SearchResultPresenter extends BaseListActivityPresenter {

    @Override
    protected void onCreateView(Object view) {
        super.onCreateView(view);
        onRefresh();
    }

    @Override
    public void onRefresh() {
        super.onRefresh();

        ProductModel.getInstantce().getSearchList().unsafeSubscribe(getRefreshSubscriber());

    }
}
