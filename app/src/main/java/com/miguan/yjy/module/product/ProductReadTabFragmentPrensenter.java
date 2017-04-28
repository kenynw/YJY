package com.miguan.yjy.module.product;

import android.os.Bundle;

import com.dsk.chain.expansion.list.BaseListFragmentPresenter;
import com.miguan.yjy.model.bean.Component;

import java.util.ArrayList;

import rx.Observable;

/**
 * @作者 cjh
 * @日期 2017/4/6 14:32
 * @描述
 */

public class ProductReadTabFragmentPrensenter extends BaseListFragmentPresenter<ProductReadTabFragment, Component> {

    public static final String EXTRA_COMPONET = "componet";

    public static ProductReadTabFragment getInstance(ArrayList<Component> component) {
        ProductReadTabFragment fragment = new ProductReadTabFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(EXTRA_COMPONET, component);
        fragment.setArguments(bundle);
        return fragment;
    }

    private ArrayList<Component> mComponent;

    @Override
    protected void onCreate(ProductReadTabFragment view, Bundle saveState) {
        super.onCreate(view, saveState);
        if (getView().getArguments() != null) {
            mComponent = getView().getArguments().getParcelableArrayList(EXTRA_COMPONET);
        }
    }

    @Override
    protected void onCreateView(ProductReadTabFragment view) {
        super.onCreateView(view);
        onRefresh();
    }

    @Override
    public void onRefresh() {

        Observable.just(mComponent).unsafeSubscribe(getRefreshSubscriber());
//        TestModel.getInstantce().getTestList().unsafeSubscribe(getRefreshSubscriber());
    }
}
