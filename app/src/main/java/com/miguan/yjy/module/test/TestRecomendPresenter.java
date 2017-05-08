package com.miguan.yjy.module.test;

import android.content.Context;
import android.content.Intent;

import com.dsk.chain.expansion.list.BaseListActivityPresenter;
import com.miguan.yjy.model.TestModel;
import com.miguan.yjy.model.bean.Product;
import com.miguan.yjy.model.bean.Skin;

import java.util.ArrayList;

/**
 * @作者 cjh
 * @日期 2017/4/5 19:03
 * @描述
 */

public class TestRecomendPresenter extends BaseListActivityPresenter<TestRecomendActivity, Product> {

    public static final String EXTRA_CATEGORY_LIST = "categoryList";
    public static final String EXTRA_CATEGORY_position = "position";

    public static void star(Context context, ArrayList<Skin> skin,int position) {
        Intent intent = new Intent(context, TestRecomendActivity.class);
        intent.putParcelableArrayListExtra(EXTRA_CATEGORY_LIST, skin);
        intent.putExtra(EXTRA_CATEGORY_position, position);
        context.startActivity(intent);
    }

    @Override
    protected void onCreateView(TestRecomendActivity view) {
        super.onCreateView(view);
//        TestModel.getInstantce().getTestList().subscribe(new ServicesResponse<List<Test>>() {
//            @Override
//            public void onNext(List<Test> tests) {
//                getView().setData(tests);
//            }
//        });

        onRefresh();
    }

    @Override
    public void onRefresh() {
        TestModel.getInstantce().getSkinRecommendList(getView().categoryList.get(getView().position).getId(), 1).
                unsafeSubscribe(getRefreshSubscriber());
//        TestModel.getInstantce().getTestList().unsafeSubscribe(getRefreshSubscriber());
    }

    @Override
    public void onLoadMore() {
        TestModel.getInstantce().getSkinRecommendList(getView().categoryList.get(getView().position).getId(), getCurPage()).
                unsafeSubscribe(getMoreSubscriber());
    }
}
