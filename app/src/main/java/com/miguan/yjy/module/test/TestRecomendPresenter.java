package com.miguan.yjy.module.test;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;

import com.dsk.chain.expansion.list.BaseListActivityPresenter;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.miguan.yjy.R;
import com.miguan.yjy.adapter.SkinPriceAdapter;
import com.miguan.yjy.model.TestModel;
import com.miguan.yjy.model.bean.Product;
import com.miguan.yjy.model.bean.SelectPrice;
import com.miguan.yjy.model.bean.Test;
import com.miguan.yjy.widget.FlowTagLayout;

import java.util.List;


/**
 * @作者 cjh
 * @日期 2017/4/5 19:03
 * @描述
 */

public class TestRecomendPresenter extends BaseListActivityPresenter<TestRecomendActivity, Product> {

    public static final String EXTRA_CATEGORY_LIST = "categoryList";
    public static final String EXTRA_TEST = "test";
    public static final String EXTRA_CATEGORY_position = "position";
    public static final String EXTRA_CATEGORY_NAME = "name";

    private FlowTagLayout mFlowtagSkinPrice;
    public float minPrice;
    public float maxPrice;

    public static void star(Context context, Test tests, int position, String name) {
        Intent intent = new Intent(context, TestRecomendActivity.class);
        intent.putExtra(EXTRA_TEST, tests);
        intent.putExtra(EXTRA_CATEGORY_position, position);
        intent.putExtra(EXTRA_CATEGORY_NAME, name);
        context.startActivity(intent);
    }

    @Override
    protected void onCreateView(TestRecomendActivity view) {
        super.onCreateView(view);
        getAdapter().addHeader(new RecyclerArrayAdapter.ItemView() {
            @Override
            public View onCreateView(ViewGroup parent) {
                View skinPrice = View.inflate(getView(), R.layout.include_head_skin_price, null);
                mFlowtagSkinPrice = (FlowTagLayout) skinPrice.findViewById(R.id.flowtag_skin_price);
                mFlowtagSkinPrice.setTagCheckedMode(FlowTagLayout.FLOW_TAG_CHECKED_SINGLE);
                List<SelectPrice> selectPrices = getView().mTest.getCategoryList().get(getView().position).getCondition();
                if (selectPrices.size() != 0) {
                    SelectPrice selectPrice = new SelectPrice();
                    selectPrice.setMin(0);
                    selectPrice.setMax(0);
                    selectPrices.add(0, selectPrice);
                }
                SkinPriceAdapter skinPriceAdapter = new SkinPriceAdapter(getView(), selectPrices);
                mFlowtagSkinPrice.setAdapter(skinPriceAdapter);
                skinPriceAdapter.onlyAddAll(selectPrices);
                skinPriceAdapter.setSetOnTagClickListener(new SkinPriceAdapter.SetOnTagClickListener() {
                    @Override
                    public void itemClick(View v, int position, float min, float max) {
                        minPrice = min;
                        maxPrice = max;
                        onRefresh();
                    }

                });
                return skinPrice;
            }

            @Override
            public void onBindView(View headerView) {

            }
        });

        onRefresh();
    }

    @Override
    public void onRefresh() {
        TestModel.getInstantce().getSkinRecommendList(getView().categoryList.get(getView().position).getId(), minPrice, maxPrice, 1).
                unsafeSubscribe(getRefreshSubscriber());
//        TestModel.getInstantce().getTestList().unsafeSubscribe(getRefreshSubscriber());
    }

    @Override
    public void onLoadMore() {
        TestModel.getInstantce().getSkinRecommendList(getView().categoryList.get(getView().position).getId(), minPrice, maxPrice, getCurPage()).
                unsafeSubscribe(getMoreSubscriber());
    }
}
