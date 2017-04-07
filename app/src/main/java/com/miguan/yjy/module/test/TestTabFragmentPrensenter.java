package com.miguan.yjy.module.test;

import com.dsk.chain.expansion.list.BaseListFragmentPresenter;
import com.miguan.yjy.model.TestModel;
import com.miguan.yjy.model.bean.Test;

/**
 * @作者 cjh
 * @日期 2017/4/6 14:32
 * @描述
 */

public class TestTabFragmentPrensenter extends BaseListFragmentPresenter<TestTabFragment, Test> {

    @Override
    protected void onCreateView(TestTabFragment view) {
        super.onCreateView(view);
        onRefresh();
        TestModel.getInstantce().getTestList().unsafeSubscribe(getRefreshSubscriber());

    }

    @Override
    public void onRefresh() {
        TestModel.getInstantce().getTestList().unsafeSubscribe(getRefreshSubscriber());
    }
}
