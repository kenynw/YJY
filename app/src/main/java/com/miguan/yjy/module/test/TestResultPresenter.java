package com.miguan.yjy.module.test;

import com.dsk.chain.expansion.list.BaseListActivityPresenter;
import com.miguan.yjy.model.TestModel;
import com.miguan.yjy.model.bean.Test;
import com.miguan.yjy.model.services.ServicesResponse;

import java.util.List;

/**
 * @作者 cjh
 * @日期 2017/4/1 16:43
 * @描述
 */

public class TestResultPresenter extends BaseListActivityPresenter<TestResultActivity, Test> {

    @Override
    protected void onCreateView(TestResultActivity view) {
        super.onCreateView(view);
        onRefresh();
    }

    @Override
    public void onRefresh() {
        super.onRefresh();
        TestModel.getInstantce().getTestList().subscribe(new ServicesResponse<List<Test>>() {
            @Override
            public void onNext(List<Test> tests) {
                getView().setData(tests);
            }
        });

    }


}
