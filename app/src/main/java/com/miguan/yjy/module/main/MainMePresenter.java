package com.miguan.yjy.module.main;

import com.dsk.chain.expansion.data.BaseDataFragmentPresenter;
import com.miguan.yjy.model.UserModel;
import com.miguan.yjy.model.bean.User;

/**
 * Copyright (c) 2017/3/30. LiaoPeiKun Inc. All rights reserved.
 */

public class MainMePresenter extends BaseDataFragmentPresenter<MeFragment, User> {

    @Override
    protected void onCreateView(MeFragment view) {
        super.onCreateView(view);
        loadData();
    }

    public void loadData() {
        UserModel.getInstance().getUserInfo().unsafeSubscribe(getSubscriber());
    }

}
