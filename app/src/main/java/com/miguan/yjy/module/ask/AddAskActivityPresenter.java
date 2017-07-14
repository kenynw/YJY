package com.miguan.yjy.module.ask;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.dsk.chain.bijection.Presenter;
import com.miguan.yjy.model.AccountModel;
import com.miguan.yjy.model.ProductModel;
import com.miguan.yjy.model.bean.Ask;
import com.miguan.yjy.model.services.ServicesResponse;
import com.miguan.yjy.module.account.LoginActivity;

/**
 * Copyright (c) 2017/6/27. LiaoPeiKun Inc. All rights reserved.
 */

public class AddAskActivityPresenter extends Presenter<AddAskActivity> {

    private static final String EXTRA_PRODUCT_ID = "product_id";
    private static final String EXTRA_PRODUCT_NAME = "product_name";
    private static final String EXTRA_PRODUCT_IMG = "product_img";
    private static final String EXTRA_ASK_TYPE = "ask_type";
    private static final String EXTRA_ASK_ID = "ask_id";
    private static final String EXTRA_ASK_CONTENT = "ask_content";

    private int mProductId;

    private String mProductName;

    private String mProductImg;

    private int mAskType;

    private int mAskId;

    private String mContent;

    /**
     * 提问
     */
    public static void start(Context context, Ask ask, String content) {
        if (AccountModel.getInstance().isLogin()) {
            Intent intent = new Intent(context, AddAskActivity.class);
            intent.putExtra(EXTRA_PRODUCT_ID, ask.getProduct_id());
            intent.putExtra(EXTRA_PRODUCT_NAME, ask.getProduct_name());
            intent.putExtra(EXTRA_PRODUCT_IMG, ask.getProduct_img());
            intent.putExtra(EXTRA_ASK_CONTENT, content);
            intent.putExtra(EXTRA_ASK_TYPE, 1);
            ((Activity) context).startActivityForResult(intent, 100);
        } else {
            context.startActivity(new Intent(context, LoginActivity.class));
        }
    }

    // 回答问题
    public static void start(Context context, Ask ask, int askId) {
        if (AccountModel.getInstance().isLogin()) {
            Intent intent = new Intent(context, AddAskActivity.class);
            intent.putExtra(EXTRA_PRODUCT_ID, ask.getProduct_id());
            intent.putExtra(EXTRA_PRODUCT_NAME, ask.getProduct_name());
            intent.putExtra(EXTRA_PRODUCT_IMG, ask.getProduct_img());
            intent.putExtra(EXTRA_ASK_TYPE, 2);
            intent.putExtra(EXTRA_ASK_ID, askId);
            ((Activity) context).startActivityForResult(intent, 100);
        } else {
            context.startActivity(new Intent(context, LoginActivity.class));
        }
    }

    @Override
    protected void onCreate(AddAskActivity view, Bundle saveState) {
        super.onCreate(view, saveState);
        mProductId = getView().getIntent().getIntExtra(EXTRA_PRODUCT_ID, 0);
        mProductName = getView().getIntent().getStringExtra(EXTRA_PRODUCT_NAME);
        mProductImg = getView().getIntent().getStringExtra(EXTRA_PRODUCT_IMG);
        mAskType = getView().getIntent().getIntExtra(EXTRA_ASK_TYPE, 0);
        mAskId = getView().getIntent().getIntExtra(EXTRA_ASK_ID, 0);
        mContent = getView().getIntent().getStringExtra(EXTRA_ASK_CONTENT);
    }

    @Override
    protected void onCreateView(AddAskActivity view) {
        super.onCreateView(view);
        getView().setProduct(mProductImg, mProductName, mContent);
    }

    public void submit(String content) {
        ProductModel.getInstance().addAsk(mProductId, mProductName, mAskType, mAskId, content)
                .doOnError(throwable -> getView().getExpansionDelegate().hideProgressBar())
                .unsafeSubscribe(new ServicesResponse<String>() {
                    @Override
                    public void onNext(String s) {
                        getView().getExpansionDelegate().hideProgressBar();
                        getView().setResult(Activity.RESULT_OK);
                        getView().finish();
                    }
                });
    }

}
