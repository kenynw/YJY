package com.miguan.yjy.module.test;

import android.os.Bundle;

import com.dsk.chain.expansion.list.BaseListFragmentPresenter;
import com.miguan.yjy.model.TestModel;
import com.miguan.yjy.model.bean.Skin;
import com.miguan.yjy.model.bean.Test;

import java.util.List;

import rx.functions.Func1;

/**
 * @作者 cjh
 * @日期 2017/6/16 15:51
 * @描述
 */

public class SkinRecomFragmentPresenter extends BaseListFragmentPresenter<SkinRecomFragment, Skin> {
    public Test mTest;

    @Override
    protected void onCreate(SkinRecomFragment view, Bundle saveState) {
        super.onCreate(view, saveState);
    }

    @Override
    protected void onCreateView(SkinRecomFragment view) {
        super.onCreateView(view);
        onRefresh();
    }

    @Override
    public void onRefresh() {
        super.onRefresh();
        TestModel.getInstantce().getSkinRecommend().map(new Func1<Test, List<Skin>>() {
            @Override
            public List<Skin> call(Test test) {
                mTest = test;
                return test.getSkinProduct();
            }
        }).unsafeSubscribe(getRefreshSubscriber());


    }
}
