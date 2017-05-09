package com.miguan.yjy.module.test;

import com.dsk.chain.expansion.list.BaseListFragmentPresenter;
import com.miguan.yjy.model.TestModel;
import com.miguan.yjy.model.bean.Article;
import com.miguan.yjy.model.local.UserPreferences;

/**
 * @作者 cjh
 * @日期 2017/4/1 16:43
 * @描述
 */

public class TestResultPresenter extends BaseListFragmentPresenter<TestResultFragment, Article> {

    @Override
    protected void onResume() {
        super.onResume();
        onRefresh();
    }

    @Override
    public void onRefresh() {
        if (UserPreferences.getUserID() > 0) {
            TestModel.getInstantce().getSkinRecommend()
                    .map(test -> {
//                        getView().setData(test);
                        return test.getSkinArticle();
                    }).unsafeSubscribe(getRefreshSubscriber());
        }
    }

}
