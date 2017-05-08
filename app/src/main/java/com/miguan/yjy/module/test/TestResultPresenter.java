package com.miguan.yjy.module.test;

import com.dsk.chain.expansion.list.BaseListFragmentPresenter;
import com.miguan.yjy.model.TestModel;
import com.miguan.yjy.model.bean.Article;
import com.miguan.yjy.model.bean.Test;
import com.miguan.yjy.model.local.UserPreferences;

import rx.Observable;
import rx.functions.Func1;

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
            TestModel.getInstantce().userSkin()
                    .flatMap(new Func1<Test, Observable<Test>>() {
                        @Override
                        public Observable<Test> call(Test test) {
                            getView().setSkinResult(test);
                            return TestModel.getInstantce().getSkinRecommend();
                        }
                    })
                    .map(test -> {
                        getView().setData(test.getSkinProduct(), test.getCategoryList());
                        getView().setFocusable();
                        return test.getSkinArticle();
                    }).unsafeSubscribe(getRefreshSubscriber());
        }
    }

}
