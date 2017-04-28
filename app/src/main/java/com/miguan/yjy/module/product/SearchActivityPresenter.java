package com.miguan.yjy.module.product;

import android.os.Bundle;
import android.view.View;

import com.dsk.chain.expansion.list.BaseListActivityPresenter;
import com.miguan.yjy.model.ProductModel;
import com.miguan.yjy.model.bean.Product;
import com.miguan.yjy.model.services.ServicesResponse;

import java.util.List;

import rx.functions.Action1;

/**
 * @作者 cjh
 * @日期 2017/3/20 11:00
 * @描述 搜索框p层
 */

public class SearchActivityPresenter extends BaseListActivityPresenter<SearchActivity, Product> {

    @Override
    protected void onCreate(SearchActivity view, Bundle saveState) {
        super.onCreate(view, saveState);
    }

    @Override
    protected void onCreateView(SearchActivity view) {
        super.onCreateView(view);
        onRefresh();
        ProductModel.getInstance().searchHot().subscribe(new ServicesResponse<List<Product>>() {
            @Override
            public void onNext(List<Product> products) {
                getView().setData(products);
            }
        });

    }

    @Override
    public void onRefresh() {
        ProductModel.getInstance().searchAssociate(getView().edtSearch.getText().toString()).doOnNext(new Action1<List<Product>>() {
            @Override
            public void call(List<Product> products) {
                if (products.size() == 0) {
                    getView().llSearchSencond.setVisibility(View.GONE);
                } else {
                    getView().llSearchSencond.setVisibility(View.VISIBLE);
                }
            }
        }).unsafeSubscribe(getRefreshSubscriber());
    }
}
