package com.miguan.yjy.module.billboard;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.dsk.chain.expansion.data.BaseDataActivityPresenter;
import com.miguan.yjy.model.ProductModel;
import com.miguan.yjy.model.bean.Rank;

/**
 * Copyright (c) 2017/7/27. LiaoPeiKun Inc. All rights reserved.
 */

public class BillboardActivityPresenter extends BaseDataActivityPresenter<BillboardActivity, Rank> {

    public static final String EXTRA_BILLBOARD_ID = "billboard_id";

    private int mBillboardId;

    public static void start(Context context, int billboardId) {
        Intent intent = new Intent(context, BillboardActivity.class);
        intent.putExtra(EXTRA_BILLBOARD_ID, billboardId);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(BillboardActivity view, Bundle saveState) {
        super.onCreate(view, saveState);
        if (saveState != null && saveState.containsKey(EXTRA_BILLBOARD_ID)) {
            mBillboardId = saveState.getInt(EXTRA_BILLBOARD_ID);
        } else {
            mBillboardId = getView().getIntent().getIntExtra(EXTRA_BILLBOARD_ID, 0);
        }
    }

    @Override
    protected void onCreateView(BillboardActivity view) {
        super.onCreateView(view);
        loadData();
    }

    private void loadData() {
        ProductModel.getInstance().getBillboardDetail(mBillboardId).unsafeSubscribe(getDataSubscriber());
    }

}
