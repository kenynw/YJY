package com.miguan.yjy.module.user;

import com.dsk.chain.expansion.list.BaseListActivityPresenter;
import com.miguan.yjy.model.ProductModel;
import com.miguan.yjy.model.bean.Ask;

/**
 * Copyright (c) 2017/8/25. LiaoPeiKun Inc. All rights reserved.
 */

public class MyAskPresenter extends BaseListActivityPresenter<MyAskActivity, Ask> {

    @Override
    protected void onCreateView(MyAskActivity view) {
        super.onCreateView(view);
        onRefresh();
    }

    @Override
    public void onRefresh() {
        ProductModel.getInstance().getAskList(2, 1).unsafeSubscribe(getRefreshSubscriber());
    }

    @Override
    public void onLoadMore() {
        ProductModel.getInstance().getAskList(2, getCurPage()).unsafeSubscribe(getMoreSubscriber());
    }

}
