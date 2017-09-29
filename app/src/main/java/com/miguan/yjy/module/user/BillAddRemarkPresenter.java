package com.miguan.yjy.module.user;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.dsk.chain.bijection.Presenter;
import com.miguan.yjy.model.UserModel;
import com.miguan.yjy.model.services.ServicesResponse;
import com.miguan.yjy.utils.LUtils;

/**
 * Copyright (c) 2017/9/26. LiaoPeiKun Inc. All rights reserved.
 */

public class BillAddRemarkPresenter extends Presenter<BillAddRemarkActivity> {

    public static final int REQUEST_CODE_ADD_REMARK = 0x33;

    public static final String EXTRA_BILL_ID = "bill_id";

    public static final String EXTRA_BILL_POSITION = "bill_position";

    public static final String EXTRA_BILL_REMARK = "bill_remark";

    public static void start(Activity context, int billId, int position) {
        Intent intent = new Intent(context, BillAddRemarkActivity.class);
        intent.putExtra(EXTRA_BILL_ID, billId);
        intent.putExtra(EXTRA_BILL_POSITION, position);
        context.startActivityForResult(intent, REQUEST_CODE_ADD_REMARK);
    }

    private int mBillId;

    private int mPosition;

    @Override
    protected void onCreate(BillAddRemarkActivity view, Bundle saveState) {
        super.onCreate(view, saveState);
        mBillId = getView().getIntent().getIntExtra(EXTRA_BILL_ID, 0);
        mPosition = getView().getIntent().getIntExtra(EXTRA_BILL_POSITION, 0);
    }

    public void submit(String content) {
        UserModel.getInstance().addBillRemark(mBillId, content)
                .unsafeSubscribe(new ServicesResponse<String>() {
                    @Override
                    public void onNext(String s) {
                        LUtils.toast("添加成功");
                        Intent intent = new Intent();
                        intent.putExtra(EXTRA_BILL_POSITION, mPosition);
                        intent.putExtra(EXTRA_BILL_REMARK, content);
                        getView().setResult(Activity.RESULT_OK, intent);
                        getView().finish();
                    }
                });
    }

}
