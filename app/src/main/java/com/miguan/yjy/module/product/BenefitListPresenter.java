package com.miguan.yjy.module.product;

import com.dsk.chain.expansion.list.BaseListActivityPresenter;
import com.miguan.yjy.model.ProductModel;
import com.miguan.yjy.model.bean.Benefit;

/**
 * Copyright (c) 2017/8/28. LiaoPeiKun Inc. All rights reserved.
 */

public class BenefitListPresenter extends BaseListActivityPresenter<BenefitListActivity, Benefit> {

    @Override
    protected void onCreateView(BenefitListActivity view) {
        super.onCreateView(view);
        onRefresh();
    }

    @Override
    public void onRefresh() {
        ProductModel.getInstance().getBenefitList(1).unsafeSubscribe(getRefreshSubscriber());
    }

    @Override
    public void onLoadMore() {
        ProductModel.getInstance().getBenefitList(getCurPage()).unsafeSubscribe(getMoreSubscriber());
    }

}
