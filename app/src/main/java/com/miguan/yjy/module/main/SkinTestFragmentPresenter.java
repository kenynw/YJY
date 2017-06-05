package com.miguan.yjy.module.main;

import com.dsk.chain.expansion.data.BaseDataFragmentPresenter;
import com.miguan.yjy.model.TestModel;
import com.miguan.yjy.model.bean.Test;
import com.miguan.yjy.model.local.UserPreferences;

/**
 * @作者 cjh
 * @日期 2017/6/5 17:30
 * @描述
 */

public class SkinTestFragmentPresenter extends BaseDataFragmentPresenter<SkinTestFragment, Test> {


    @Override
    protected void onResume() {
        super.onResume();
        loadData();
    }


    public void loadData() {
        if (UserPreferences.getUserID() > 0) {
            TestModel.getInstantce().getSkinRecommend().unsafeSubscribe(getSubscriber());
        }
    }

}
