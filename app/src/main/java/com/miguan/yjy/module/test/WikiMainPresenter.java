package com.miguan.yjy.module.test;

import com.dsk.chain.expansion.list.BaseListActivityPresenter;
import com.miguan.yjy.model.TestModel;
import com.miguan.yjy.model.bean.Wiki;

import java.util.List;

import rx.functions.Action1;

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

        TestModel.getInstantce().getBaikeList().doOnNext(new Action1<List<Wiki>>() {
            @Override
            public void call(List<Wiki> wikis) {
                getAdapter().setOnItemClickListener(v -> WikiAskActivityPresenter.start(getView(), wikis.get(v).getId() + ""));
            }
        }).unsafeSubscribe(getRefreshSubscriber());
    }


}
