package com.miguan.yjy.module.user;

import android.os.Bundle;

import com.dsk.chain.bijection.ChainBaseActivity;
import com.dsk.chain.bijection.RequiresPresenter;
import com.miguan.yjy.R;

import butterknife.ButterKnife;

/**
 * Copyright (c) 2017/9/26. LiaoPeiKun Inc. All rights reserved.
 */
@RequiresPresenter(BillAddRemarkPresenter.class)
public class BillAddRemarkActivity extends ChainBaseActivity<BillAddRemarkPresenter> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_activity_add_bill_remark);
        ButterKnife.bind(this);
    }
}
