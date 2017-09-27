package com.miguan.yjy.module.user;

import com.dsk.chain.expansion.list.BaseListActivityPresenter;
import com.miguan.yjy.model.UserModel;
import com.miguan.yjy.model.bean.Bill;
import com.miguan.yjy.model.services.ServicesResponse;
import com.miguan.yjy.utils.LUtils;

/**
 * Copyright (c) 2017/9/21. LiaoPeiKun Inc. All rights reserved.
 */

public class BillListPresenter extends BaseListActivityPresenter<BillListActivity, Bill> {

    @Override
    protected void onCreateView(BillListActivity view) {
        super.onCreateView(view);
        onRefresh();
    }

    @Override
    public void onRefresh() {
        UserModel.getInstance().getBillList(1).unsafeSubscribe(getRefreshSubscriber());
    }

    @Override
    public void onLoadMore() {
        UserModel.getInstance().getBillList(getCurPage()).unsafeSubscribe(getMoreSubscriber());
    }

    public void newBill(String name) {
        UserModel.getInstance().addBill(name, 0).unsafeSubscribe(new ServicesResponse<String>() {
            @Override
            public void onNext(String result) {
                LUtils.toast("创建成功");
                onRefresh();
            }
        });
    }

}
