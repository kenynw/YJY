package com.miguan.yjy.module.main;

import com.dsk.chain.expansion.data.BaseDataFragmentPresenter;
import com.miguan.yjy.model.CommonModel;
import com.miguan.yjy.model.bean.Discover;

/**
 * Copyright (c) 2017/8/22. LiaoPeiKun Inc. All rights reserved.
 */

public class DiscoverFragmentPresenter extends BaseDataFragmentPresenter<DiscoverFragment, Discover> {

    @Override
    protected void onCreateView(DiscoverFragment view) {
        super.onCreateView(view);
    }

    private void loadData() {
        CommonModel.getInstance().getDiscover()
                .unsafeSubscribe(getSubscriber());
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadData();
    }

}
