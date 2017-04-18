package com.miguan.yjy.module.user;

import android.os.Bundle;

import com.dsk.chain.expansion.list.BaseListActivityPresenter;
import com.miguan.yjy.model.UserModel;
import com.miguan.yjy.model.bean.UserProduct;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Copyright (c) 2017/3/30. LiaoPeiKun Inc. All rights reserved.
 */

public class UsedListPresenter extends BaseListActivityPresenter<UsedListActivity, UserProduct> {

    @Override
    protected void onCreate(UsedListActivity view, Bundle saveState) {
        super.onCreate(view, saveState);
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onCreateView(UsedListActivity view) {
        super.onCreateView(view);
        onRefresh();
    }


    @Subscribe(threadMode = ThreadMode.POSTING)
    public void refreshEvent(UserProduct product) {
        onRefresh();
    }

    @Override
    public void onRefresh() {
        UserModel.getInstance().getUsedProductList(1).unsafeSubscribe(getRefreshSubscriber());
    }

    @Override
    public void onLoadMore() {
        UserModel.getInstance().getUsedProductList(getCurPage()).unsafeSubscribe(getMoreSubscriber());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
