package com.miguan.yjy.module.product;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.dsk.chain.expansion.list.BaseListActivityPresenter;
import com.miguan.yjy.adapter.ProductRecommentAdapter;
import com.miguan.yjy.model.ProductModel;
import com.miguan.yjy.model.bean.Product;
import com.miguan.yjy.model.bean.ProductList;
import com.miguan.yjy.model.services.ServicesResponse;

import java.util.List;

/**
 * @作者 cjh
 * @日期 2017/3/21 11:31
 * @描述
 */

public class SearchResultPresenter extends BaseListActivityPresenter<SearchResultActivity, Product> {

    public static final String EXTRA_KEYWORDS = "keywords";

    public static final String EXTRA_CATE_ID = "cate_id";

    public static final String EXTRA_CATE_NAME = "cate_name";

    private String mKeywords;//关键词

    private int mCateId;//分类id

    private String mCateName;//分类id

    private String mEffect;//功效关键字

    public static void start(Context context, String keywords, int cateId, String cateName) {
        Intent intent = new Intent(context, SearchResultActivity.class);
        intent.putExtra(EXTRA_KEYWORDS, keywords);
        intent.putExtra(EXTRA_CATE_ID, cateId);
        intent.putExtra(EXTRA_CATE_NAME, cateName);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(SearchResultActivity view, Bundle saveState) {
        super.onCreate(view, saveState);
        mKeywords = view.getIntent().getStringExtra(EXTRA_KEYWORDS);
        mCateId = view.getIntent().getIntExtra(EXTRA_CATE_ID, 0);
        mCateName = view.getIntent().getStringExtra(EXTRA_CATE_NAME);
    }

    @Override
    protected void onCreateView(SearchResultActivity view) {
        super.onCreateView(view);
        getView().mEtKeywords.setText(mKeywords);
        onRefresh();
    }

    @Override
    public void onRefresh() {
        ProductModel.getInstance().searchQuery(getView().mEtKeywords.getText().toString(), mCateId, mEffect, 1)
                .map(product -> {
                    getView().setData(getView().mEtKeywords.getText().toString(), product, mCateName);
                    return product.getData();
                })
                .unsafeSubscribe(getRefreshSubscriber());
    }

    @Override
    public void onLoadMore() {
        ProductModel.getInstance()
                .searchQuery(getView().mEtKeywords.getText().toString(), mCateId, mEffect, getCurPage())
                .map(ProductList::getData)
                .unsafeSubscribe(getMoreSubscriber());
    }

    public void setCateId(int cateId) {
        mCateId = cateId;
    }

    public void setEffect(String effect) {
        mEffect = effect;
    }

    public void setRecommendData(String s) {
        ProductModel.getInstance().searchAssociate(s).subscribe(new ServicesResponse<List<Product>>() {
            @Override
            public void onNext(List<Product> products) {
                if (products.size() == 0) {
                    getView().mLlResultSencond.setVisibility(View.GONE);
                } else {
                    getView().mLlResultSencond.setVisibility(View.VISIBLE);
                    getView().mRecyRecommend.setLayoutManager(new LinearLayoutManager(getView(),LinearLayoutManager.VERTICAL,false));
                    getView().mRecyRecommend.setAdapter(new ProductRecommentAdapter(getView(), products));
                }
            }
        });
    }
}
