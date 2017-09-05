package com.miguan.yjy.module.test;

import com.dsk.chain.expansion.list.BaseListActivityPresenter;
import com.miguan.yjy.model.TestModel;
import com.miguan.yjy.model.bean.Wiki;

/**
 * @作者 cjh
 * @日期 2017/8/23 16:01
 * @描述
 */

public class WikiMainPresenter extends BaseListActivityPresenter<WikiMainActivity, Wiki> {

    @Override
    protected void onCreateView(WikiMainActivity view) {
        super.onCreateView(view);
        onRefresh();
    }

    @Override
    public void onRefresh() {
        TestModel.getInstantce().getBaikeList(1).unsafeSubscribe(getRefreshSubscriber());
    }

    @Override
    public void onLoadMore() {
        TestModel.getInstantce().getBaikeList(getCurPage()).unsafeSubscribe(getMoreSubscriber());
    }
}
