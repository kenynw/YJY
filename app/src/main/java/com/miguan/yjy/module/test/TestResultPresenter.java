package com.miguan.yjy.module.test;

import com.dsk.chain.expansion.data.BaseDataFragmentPresenter;
import com.miguan.yjy.model.TestModel;
import com.miguan.yjy.model.bean.Test;
import com.miguan.yjy.model.local.UserPreferences;

/**
 * @作者 cjh
 * @日期 2017/4/1 16:43
 * @描述
 */

public class TestResultPresenter extends BaseDataFragmentPresenter<TestResultFragment, Test> {

    @Override
    protected void onCreateView(TestResultFragment view) {
        super.onCreateView(view);
        loadData();
    }

    public void loadData() {
        if (UserPreferences.getUserID() > 0) {
            TestModel.getInstantce().getSkinRecommend().unsafeSubscribe(getSubscriber());
        }
    }

}
