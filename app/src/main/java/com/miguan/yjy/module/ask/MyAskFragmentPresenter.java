package com.miguan.yjy.module.ask;

import com.dsk.chain.expansion.list.BaseListFragmentPresenter;
import com.miguan.yjy.model.ProductModel;
import com.miguan.yjy.model.bean.Ask;

/**
 * Copyright (c) 2017/9/5. LiaoPeiKun Inc. All rights reserved.
 */

public class MyAskFragmentPresenter extends BaseListFragmentPresenter<MyAskFragment, Ask> {

    @Override
    protected void onCreateView(MyAskFragment view) {
        super.onCreateView(view);
        onRefresh();
    }

    @Override
    public void onRefresh() {
        ProductModel.getInstance().getAskList(getView().getType(), 1).unsafeSubscribe(getRefreshSubscriber());
    }

    @Override
    public void onLoadMore() {
        ProductModel.getInstance().getAskList(getView().getType(), getCurPage()).unsafeSubscribe(getMoreSubscriber());
    }
    
}
