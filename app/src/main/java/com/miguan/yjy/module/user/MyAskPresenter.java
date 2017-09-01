package com.miguan.yjy.module.user;

import com.dsk.chain.expansion.list.BaseListActivityPresenter;
import com.miguan.yjy.model.ProductModel;
import com.miguan.yjy.model.bean.Ask;

/**
 * Copyright (c) 2017/8/25. LiaoPeiKun Inc. All rights reserved.
 */

public class MyAskPresenter extends BaseListActivityPresenter<MyAskActivity, Ask> {

    public static final int TYPE_ASK = 2;

    public static final int TYPE_ANSWER = 3;

    private int mType = TYPE_ASK;

    @Override
    protected void onCreateView(MyAskActivity view) {
        super.onCreateView(view);
        onRefresh();
    }

    @Override
    public void onRefresh() {
        ProductModel.getInstance().getAskList(mType, 1).unsafeSubscribe(getRefreshSubscriber());
    }

    @Override
    public void onLoadMore() {
        ProductModel.getInstance().getAskList(mType, getCurPage()).unsafeSubscribe(getMoreSubscriber());
    }

    public void setType(int type) {
        mType = type;
    }

}
