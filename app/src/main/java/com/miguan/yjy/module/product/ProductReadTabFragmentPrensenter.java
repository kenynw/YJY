package com.miguan.yjy.module.product;

import com.dsk.chain.expansion.list.BaseListFragmentPresenter;
import com.miguan.yjy.model.bean.Component;

/**
 * @作者 cjh
 * @日期 2017/4/6 14:32
 * @描述
 */

public class ProductReadTabFragmentPrensenter extends BaseListFragmentPresenter<ProductReadTabFragment, Component> {

    @Override
    protected void onCreateView(ProductReadTabFragment view) {
        super.onCreateView(view);
        onRefresh();
    }

    @Override
    public void onRefresh() {
//        TestModel.getInstantce().getTestList().unsafeSubscribe(getRefreshSubscriber());
    }
}
