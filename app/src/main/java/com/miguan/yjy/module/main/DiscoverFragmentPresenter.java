package com.miguan.yjy.module.main;

import com.dsk.chain.expansion.data.BaseDataFragmentPresenter;
import com.miguan.yjy.model.bean.Discover;

/**
 * Copyright (c) 2017/8/22. LiaoPeiKun Inc. All rights reserved.
 */

public class DiscoverFragmentPresenter extends BaseDataFragmentPresenter<DiscoverFragment, Discover> {

    @Override
    protected void onCreateView(DiscoverFragment view) {
        super.onCreateView(view);
        loadData();
    }

    private void loadData() {
        getView().setData(null);
    }

}
