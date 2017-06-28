package com.miguan.yjy.module.ask;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.dsk.chain.expansion.list.BaseListActivityPresenter;
import com.miguan.yjy.model.ProductModel;
import com.miguan.yjy.model.bean.Ask;
import com.miguan.yjy.model.local.UserPreferences;
import com.miguan.yjy.model.services.ServicesResponse;
import com.miguan.yjy.module.account.LoginActivity;

/**
 * Copyright (c) 2017/6/26. LiaoPeiKun Inc. All rights reserved.
 */

public class AskDetailActivityPresenter extends BaseListActivityPresenter<AskDetailActivity, Ask> {

    public static final String EXTRA_PRODUCT_ID = "product_id";

    public static final String EXTRA_ASK_ID = "ask_id";

    private int mAskId;

    private int mProductId;

    public static void start(Context context, int productId, int askId) {
        Intent intent = new Intent(context, AskDetailActivity.class);
        intent.putExtra(EXTRA_PRODUCT_ID, productId);
        intent.putExtra(EXTRA_ASK_ID, askId);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(AskDetailActivity view, Bundle saveState) {
        super.onCreate(view, saveState);
        mAskId = getView().getIntent().getIntExtra(EXTRA_ASK_ID, 0);
        mProductId = getView().getIntent().getIntExtra(EXTRA_PRODUCT_ID, 0);
    }

    @Override
    protected void onCreateView(AskDetailActivity view) {
        super.onCreateView(view);
        onRefresh();
    }

    @Override
    public void onRefresh() {
        ProductModel.getInstance().getAskDetail(mProductId, mAskId, 1)
                .map(ask -> {
                    getView().setData(ask);
                    return ask.getQuestion_list();
                })
                .unsafeSubscribe(getRefreshSubscriber());
    }

    @Override
    public void onLoadMore() {
        ProductModel.getInstance().getAskDetail(mProductId, mAskId, getCurPage())
                .map(Ask::getQuestion_list)
                .unsafeSubscribe(getMoreSubscriber());
    }

    public void send(String productName, String content) {
        if (UserPreferences.getUserID() > 0) {
            ProductModel.getInstance().addAsk(mProductId, productName, 2, mAskId, content)
                    .subscribe(new ServicesResponse<String>() {
                        @Override
                        public void onNext(String s) {
                            getView().clearInput();
                            onRefresh();
                        }
                    });
        } else {
            getView().startActivity(new Intent(getView(), LoginActivity.class));
        }
    }

}
