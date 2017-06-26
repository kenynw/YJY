package com.miguan.yjy.module.question;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.dsk.chain.expansion.list.BaseListActivityPresenter;
import com.miguan.yjy.model.ProductModel;
import com.miguan.yjy.model.bean.Ask;

/**
 * Copyright (c) 2017/6/23. LiaoPeiKun Inc. All rights reserved.
 */

public class AskListActivityPresenter extends BaseListActivityPresenter<AskListActivity, Ask> {

    public static final String EXTRA_PRODUCT_ID = "product_id";

    private int mProductId;

    public static void start(Context context, int productId) {
        Intent intent = new Intent(context, AskListActivity.class);
        intent.putExtra(EXTRA_PRODUCT_ID, productId);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(AskListActivity view, Bundle saveState) {
        super.onCreate(view, saveState);
        mProductId = getView().getIntent().getIntExtra(EXTRA_PRODUCT_ID, 0);
    }

    @Override
    protected void onCreateView(AskListActivity view) {
        super.onCreateView(view);
        onRefresh();
    }

    @Override
    public void onRefresh() {
        ProductModel.getInstance().getAskList(mProductId, 1)
                .map(ask -> {
                    getView().setData(ask);
                    return ask.getQuestion_list();
                })
                .unsafeSubscribe(getRefreshSubscriber());
    }

    @Override
    public void onLoadMore() {
        ProductModel.getInstance().getAskList(mProductId, getCurPage())
                .map(Ask::getQuestion_list)
                .unsafeSubscribe(getMoreSubscriber());
    }

}
