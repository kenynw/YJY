package com.miguan.yjy.module.account;

import com.dsk.chain.bijection.Presenter;
import com.miguan.yjy.model.services.AccountModel;
import com.miguan.yjy.model.services.ServicesResponse;
import com.miguan.yjy.utils.LUtils;

/**
 * Copyright (c) 2017/3/30. LiaoPeiKun Inc. All rights reserved.
 */

public class RegisterPresenter extends Presenter<RegisterActivity> {

    public void sendCaptcha(String mobile) {
        AccountModel.getInstance().registerCaptcha(mobile).unsafeSubscribe(new ServicesResponse<Boolean>() {
            @Override
            public void onNext(Boolean result) {
                LUtils.toast("发送成功");
            }
        });
    }

    public void register(String mobile, String captcha, String password) {
        AccountModel.getInstance().register(mobile, captcha, password).unsafeSubscribe(new ServicesResponse<Integer>() {
            @Override
            public void onNext(Integer integer) {
                getView().finish();
            }
        });
    }

}
