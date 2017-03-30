package com.miguan.yjy.module.account;

import com.dsk.chain.bijection.Presenter;
import com.miguan.yjy.model.bean.User;
import com.miguan.yjy.model.services.AccountModel;
import com.miguan.yjy.model.services.ServicesResponse;

/**
 * Copyright (c) 2017/3/30. LiaoPeiKun Inc. All rights reserved.
 */

public class LoginPresenter extends Presenter<LoginActivity> {

    public void login(String username, String pwd) {
        AccountModel.getInstance().login(username, pwd).unsafeSubscribe(new ServicesResponse<User>() {
            @Override
            public void onNext(User user) {
                getView().finish();
            }
        });
    }

}
