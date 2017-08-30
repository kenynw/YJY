package com.miguan.yjy.module.ask;

import com.dsk.chain.expansion.list.BaseListActivityPresenter;
import com.miguan.yjy.model.ProductModel;
import com.miguan.yjy.model.bean.Ask;

/**
 * Copyright (c) 2017/8/22. LiaoPeiKun Inc. All rights reserved.
 */

public class AnswerListPresenter extends BaseListActivityPresenter<AnswerListActivity, Ask> {

    public static final int EXTRA_TYPE = 1;

    @Override
    protected void onCreateView(AnswerListActivity view) {
        super.onCreateView(view);
        onRefresh();
    }

    @Override
    public void onRefresh() {
        ProductModel.getInstance().getAskList(1, 1).unsafeSubscribe(getRefreshSubscriber());
    }

    @Override
    public void onLoadMore() {
        ProductModel.getInstance().getAskList(1, getCurPage()).unsafeSubscribe(getMoreSubscriber());
    }

}
