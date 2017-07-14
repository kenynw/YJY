package com.miguan.yjy.module.test;

import com.dsk.chain.expansion.data.BaseDataFragmentPresenter;
import com.miguan.yjy.model.AccountModel;
import com.miguan.yjy.model.TestModel;
import com.miguan.yjy.model.bean.Test;

/**
 * @作者 cjh
 * @日期 2017/4/1 16:43
 * @描述
 */

public class TestResultPresenter extends BaseDataFragmentPresenter<TestResultFragment, Test> {


    @Override
    protected void onResume() {
        super.onResume();
        loadData();
    }


    public void loadData() {
        if (AccountModel.getInstance().isLogin()) {
            TestModel.getInstantce().getSkinRecommend().unsafeSubscribe(getSubscriber());
        }
    }

}
