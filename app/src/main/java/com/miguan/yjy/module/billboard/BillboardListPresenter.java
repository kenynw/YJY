package com.miguan.yjy.module.billboard;

import com.dsk.chain.expansion.list.BaseListActivityPresenter;
import com.miguan.yjy.model.ProductModel;
import com.miguan.yjy.model.bean.Rank;

/**
 * Copyright (c) 2017/8/23. LiaoPeiKun Inc. All rights reserved.
 */

public class BillboardListPresenter extends BaseListActivityPresenter<BillboardListActivity, Rank> {

    @Override
    protected void onCreateView(BillboardListActivity view) {
        super.onCreateView(view);
        onRefresh();
    }

    @Override
    public void onRefresh() {
        ProductModel.getInstance().getBillboardList(1).unsafeSubscribe(getRefreshSubscriber());
    }

    @Override
    public void onLoadMore() {
        ProductModel.getInstance().getBillboardList(getCurPage()).unsafeSubscribe(getMoreSubscriber());
    }

}
