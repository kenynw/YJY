package com.miguan.yjy.module.user;

import com.dsk.chain.expansion.list.BaseListActivityPresenter;
import com.miguan.yjy.model.UserModel;
import com.miguan.yjy.model.bean.Product;

/**
 * Copyright (c) 2017/3/30. LiaoPeiKun Inc. All rights reserved.
 */

public class UsedListPresenter extends BaseListActivityPresenter<UsedListActivity, Product> {

    @Override
    protected void onCreateView(UsedListActivity view) {
        super.onCreateView(view);
        onRefresh();
    }

    @Override
    public void onRefresh() {
        UserModel.getInstance().getUsedProductList().unsafeSubscribe(getRefreshSubscriber());
    }
}
