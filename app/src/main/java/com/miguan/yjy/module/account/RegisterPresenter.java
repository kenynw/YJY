package com.miguan.yjy.module.account;

import android.app.Activity;

import com.dsk.chain.bijection.Presenter;
import com.miguan.yjy.model.AccountModel;
import com.miguan.yjy.model.bean.User;
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
                getView().mBtnCaptcha.startTickWork();
            }
        });
    }

    public void register(String mobile, String captcha, String password) {
        AccountModel.getInstance().register(mobile, captcha, password).unsafeSubscribe(new ServicesResponse<User>() {
            @Override
            public void onNext(User user) {
                getView().getExpansionDelegate().hideProgressBar();
                getView().setResult(Activity.RESULT_OK);
                getView().finish();
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                getView().getExpansionDelegate().hideProgressBar();
            }
        });
    }

}
