package com.miguan.yjy.module.main;

import com.dsk.chain.expansion.data.BaseDataFragmentPresenter;
import com.miguan.yjy.model.ProductModel;
import com.miguan.yjy.model.bean.MainProduct;

/**
 * Copyright (c) 2017/8/22. LiaoPeiKun Inc. All rights reserved.
 */

public class MainProductPresenter extends BaseDataFragmentPresenter<MainProductFragment, MainProduct> {

    @Override
    protected void onCreateView(MainProductFragment view) {
        super.onCreateView(view);
        loadData();
    }

    private void loadData() {
        ProductModel.getInstance().getMainProduct().unsafeSubscribe(getSubscriber());
    }



}
