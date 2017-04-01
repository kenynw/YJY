package com.miguan.yjy.module.user;

import com.dsk.chain.expansion.list.BaseListActivityPresenter;
import com.miguan.yjy.model.UserModel;
import com.miguan.yjy.model.bean.Evaluate;

/**
 * Copyright (c) 2017/4/1. LiaoPeiKun Inc. All rights reserved.
 */

public class EvaluateListPresenter extends BaseListActivityPresenter<EvaluateListActivity, Evaluate> {

    @Override
    protected void onCreateView(EvaluateListActivity view) {
        super.onCreateView(view);
        onRefresh();
    }

    @Override
    public void onRefresh() {
        UserModel.getInstance().getEvaluateList().unsafeSubscribe(getRefreshSubscriber());
    }
}
