package com.miguan.yjy.module.user;

import com.dsk.chain.expansion.data.BaseDataActivityPresenter;
import com.miguan.yjy.model.CommonModel;
import com.miguan.yjy.model.bean.Version;

/**
 * Copyright (c) 2017/8/17. LiaoPeiKun Inc. All rights reserved.
 */

public class AboutActivityPresenter extends BaseDataActivityPresenter<AboutActivity, Version> {

    @Override
    protected void onCreateView(AboutActivity view) {
        super.onCreateView(view);
        loadData();
    }

    private void loadData() {
        CommonModel.getInstance().checkCurVersion(getView()).subscribe(getDataSubscriber());
    }

}
