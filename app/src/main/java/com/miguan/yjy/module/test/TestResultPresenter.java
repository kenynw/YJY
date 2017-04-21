package com.miguan.yjy.module.test;

import com.dsk.chain.expansion.list.BaseListActivityPresenter;
import com.miguan.yjy.model.TestModel;
import com.miguan.yjy.model.bean.Article;
import com.miguan.yjy.model.bean.Test;
import com.miguan.yjy.model.services.ServicesResponse;

import java.util.List;

import rx.functions.Func1;

/**
 * @作者 cjh
 * @日期 2017/4/1 16:43
 * @描述
 */

public class TestResultPresenter extends BaseListActivityPresenter<TestResultActivity, Article> {

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
//                getView().setData(tests);
            }
        });
        TestModel.getInstantce().userSkin().subscribe(new ServicesResponse<Test>() {
            @Override
            public void onNext(Test test) {
                setView(test);
            }
        });
        TestModel.getInstantce().getSkinRecommend().map(new Func1<Test, List<Article>>() {
            @Override
            public List<Article> call(Test test) {
                getView().setData(test.getSkinProduct(),test.getCategoryList());
                return test.getSkinArticle();
            }
        }).unsafeSubscribe(getRefreshSubscriber());


    }


    private void setView(Test test) {
        getView().mTvFirstLetter.setText(test.getDesc().get(0).getLetter());
        getView().mTvSecondLetter.setText(test.getDesc().get(1).getLetter());
        getView().mTvThirdLetter.setText(test.getDesc().get(2).getLetter());
        getView().mTvFourLetter.setText(test.getDesc().get(3).getLetter());
        getView().mTvFirstName.setText(test.getDesc().get(0).getName());
        getView().mTvSecondName.setText(test.getDesc().get(1).getName());
        getView().mTvThirdName.setText(test.getDesc().get(2).getName());
        getView().mTvFourName.setText(test.getDesc().get(3).getName());
        getView().mTvResultDescirbe.setText(test.getFeatures());
        getView().mTvResultDescirbeSecond.setText(test.getExplain());

    }

}
