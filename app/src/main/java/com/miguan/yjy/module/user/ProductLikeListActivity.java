package com.miguan.yjy.module.user;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.ViewGroup;

import com.dsk.chain.bijection.RequiresPresenter;
import com.dsk.chain.expansion.list.BaseListActivity;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.miguan.yjy.R;
import com.miguan.yjy.adapter.viewholder.ProductLikeViewHolder;
import com.miguan.yjy.model.bean.ProductList;
import com.miguan.yjy.module.product.FilterPanel;

/**
 * Copyright (c) 2017/3/31. LiaoPeiKun Inc. All rights reserved.
 */
@RequiresPresenter(ProductLikeListPresenter.class)
public class ProductLikeListActivity extends BaseListActivity<ProductLikeListPresenter> {

    private boolean mIsInit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setToolbarTitle(R.string.btn_me_like);
    }

    @Override
    protected BaseViewHolder createViewHolder(ViewGroup parent, int viewType) {
        return new ProductLikeViewHolder(parent);
    }

    @Override
    protected int getLayout() {
        return R.layout.user_activity_like;
    }

    public void setData(ProductList productList) {
        if (mIsInit) return;
        FilterPanel filterPanel = new FilterPanel(this, productList.getCategroy(), productList.getEffects());
        filterPanel.setOnItemSelectedListener((cateId, text) -> {
            filterPanel.dismissMenu();
            if (cateId > 0) {
                getPresenter().setCateId(cateId);
            }
            if (!TextUtils.isEmpty(text)) {
                getPresenter().setEffect("");
            }
            if (cateId <= 0 && text.isEmpty()){
                getPresenter().setEffect("");
                getPresenter().setCateId(0);
            }
            getPresenter().onRefresh();
        });
        mIsInit = true;
    }

}
