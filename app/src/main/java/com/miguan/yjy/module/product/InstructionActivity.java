package com.miguan.yjy.module.product;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.dsk.chain.bijection.ChainBaseActivity;
import com.dsk.chain.bijection.RequiresPresenter;
import com.miguan.yjy.R;

/**
 * Copyright (c) 2017/3/24. LiaoPeiKun Inc. All rights reserved.
 */

@RequiresPresenter(InstructionActivityPresenter.class)
public class InstructionActivity extends ChainBaseActivity<InstructionActivityPresenter> {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_activity_instructions);
        setToolbarTitle(R.string.text_query_instructions);
    }

}
