package com.miguan.yjy.module.product;

import com.dsk.chain.expansion.data.BaseDataActivityPresenter;
import com.miguan.yjy.model.bean.Product;

/**
 * Copyright (c) 2017/7/27. LiaoPeiKun Inc. All rights reserved.
 */

public class BillboardActivityPresenter extends BaseDataActivityPresenter<BillboardActivity, Product> {

    @Override
    protected void onCreateView(BillboardActivity view) {
        super.onCreateView(view);
        loadData();
    }

    private void loadData() {
    }

}
