package com.miguan.yjy.module.user;

import android.os.Bundle;
import android.support.design.widget.TabLayout;

import com.dsk.chain.expansion.list.BaseListActivityPresenter;
import com.miguan.yjy.model.UserModel;
import com.miguan.yjy.model.bean.UserProduct;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Copyright (c) 2017/3/30. LiaoPeiKun Inc. All rights reserved.
 */

public class UsedListPresenter extends BaseListActivityPresenter<UsedListActivity, UserProduct> implements TabLayout.OnTabSelectedListener {

    private int mType = 0;

    @Override
    protected void onCreate(UsedListActivity view, Bundle saveState) {
        super.onCreate(view, saveState);
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        onRefresh();
    }

    @Subscribe(threadMode = ThreadMode.POSTING)
    public void refreshEvent(UserProduct product) {
        onRefresh();
    }

    @Override
    public void onRefresh() {
        UserModel.getInstance().getUsedProductList(mType, 1)
                .unsafeSubscribe(getRefreshSubscriber());
    }

    @Override
    public void onLoadMore() {
        UserModel.getInstance().getUsedProductList(mType, getCurPage()).unsafeSubscribe(getMoreSubscriber());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        if (mType != tab.getPosition()) {
            mType = tab.getPosition();
            onRefresh();
        }
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }

}
