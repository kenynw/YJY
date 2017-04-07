package com.miguan.yjy.module.test;

import android.content.Context;
import android.content.Intent;

import com.dsk.chain.expansion.list.BaseListActivityPresenter;
import com.miguan.yjy.model.TestModel;
import com.miguan.yjy.model.bean.Test;
import com.miguan.yjy.model.services.ServicesResponse;

import java.util.List;

/**
 * @作者 cjh
 * @日期 2017/4/5 19:03
 * @描述
 */

public class TestRecomendPresenter extends BaseListActivityPresenter<TestRecomendActivity, Test> {

    public static void star(Context context) {
        Intent intent = new Intent(context, TestRecomendActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreateView(TestRecomendActivity view) {
        super.onCreateView(view);
        TestModel.getInstantce().getTestList().subscribe(new ServicesResponse<List<Test>>() {
            @Override
            public void onNext(List<Test> tests) {
                getView().setData(tests);
            }
        });
        onRefresh();
    }

    @Override
    public void onRefresh() {
        TestModel.getInstantce().getTestList().unsafeSubscribe(getRefreshSubscriber());
    }
}
