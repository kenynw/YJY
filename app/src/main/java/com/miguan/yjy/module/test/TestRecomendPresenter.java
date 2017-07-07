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

import java.util.ArrayList;
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

    public float mFirstMinPrice;
    public float mSecondMinPrice;
    public float mThirdMinPrice;
    public float mFirstMaxPrice;
    public float mSecondMaxPrice;
    public float mThirdMaxPrice;
    public int mFirstPosition;
    public int mSecondPosition;
    public int mThirdPosition;
    public List<SelectPrice> selectPrices = new ArrayList<>();
    public SkinPriceAdapter skinPriceAdapter;

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
                selectPrices = getView().mTest.getCategoryList().get(getView().position).getCondition();
                if (selectPrices.size() != 0) {
                    SelectPrice selectPrice = new SelectPrice();
                    selectPrice.setMin(0);
                    selectPrice.setMax(0);
                    selectPrice.setSelect(true);
                    selectPrices.add(0, selectPrice);

                }
                skinPriceAdapter = new SkinPriceAdapter(getView(), selectPrices);
                mFlowtagSkinPrice.setAdapter(skinPriceAdapter);
                skinPriceAdapter.onlyAddAll(selectPrices);
                skinPriceAdapter.setSetOnTagClickListener(new SkinPriceAdapter.SetOnTagClickListener() {
                    @Override
                    public void itemClick(View v, int position, float min, float max, List<SelectPrice> list) {
                        if (min == 0f) {
                            min = 0.1f;
                        }
                        switch (getView().position) {
                            case 0:
                                mFirstMinPrice = min;
                                mFirstMaxPrice = max;
                                mFirstPosition = position;
                                break;
                            case 1:
                                mSecondMinPrice = min;
                                mSecondMaxPrice = max;
                                mSecondPosition = position;
                                break;
                            case 2:
                                mThirdMinPrice = min;
                                mThirdMaxPrice = max;
                                mThirdPosition = position;
                                break;
                        }
//                        minPrice = min;
//                        maxPrice = max;

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
        setMaxMinPrice();
        TestModel.getInstantce().getSkinRecommendList(getView().categoryList.get(getView().position).getId(), minPrice, maxPrice, 1).
                unsafeSubscribe(getRefreshSubscriber());
//        TestModel.getInstantce().getTestList().unsafeSubscribe(getRefreshSubscriber());
    }

    @Override
    public void onLoadMore() {
        setMaxMinPrice();
        TestModel.getInstantce().getSkinRecommendList(getView().categoryList.get(getView().position).getId(), minPrice, maxPrice, getCurPage()).
                unsafeSubscribe(getMoreSubscriber());
    }

    private void setMaxMinPrice() {
        switch (getView().position) {
            case 0:
                minPrice = mFirstMinPrice;
                maxPrice = mFirstMaxPrice;
                break;
            case 1:
                minPrice = mSecondMinPrice;
                maxPrice = mSecondMaxPrice;
                break;
            case 2:
                minPrice = mThirdMinPrice;
                maxPrice = mThirdMaxPrice;
                break;
        }
    }

}
