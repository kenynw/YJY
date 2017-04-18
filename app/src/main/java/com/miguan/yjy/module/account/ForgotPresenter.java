package com.miguan.yjy.module.account;

import android.os.Bundle;
import android.text.TextUtils;

import com.dsk.chain.bijection.Presenter;
import com.miguan.yjy.model.AccountModel;
import com.miguan.yjy.model.services.ServicesResponse;
import com.miguan.yjy.utils.LUtils;

/**
 * Copyright (c) 2017/3/30. LiaoPeiKun Inc. All rights reserved.
 */

public class ForgotPresenter extends Presenter<ForgotActivity> {
    @Override
    protected void onCreate(ForgotActivity view, Bundle saveState) {
        super.onCreate(view, saveState);
    }

    @Override
    protected void onCreateView(ForgotActivity view) {
        super.onCreateView(view);

    }

    private boolean isGetCode() {
        String userName = getView().mEtUsername.getText().toString();
        if (TextUtils.isEmpty(userName)) {
            return false;
        }
        return true;
    }

    private boolean isCanSubmit() {
        String userName = getView().mEtUsername.getText().toString();
        String captcha = getView().mEtCaptcha.getText().toString();
        String pwd = getView().mEtPassword.getText().toString();
        if (TextUtils.isEmpty(userName)) {
            LUtils.toast("用户名或手机号不能为空");
            return false;
        }
        if (TextUtils.isEmpty(captcha)) {
            LUtils.toast("验证码为空");
            return false;
        }
        if (TextUtils.isEmpty(pwd)) {
            LUtils.toast("密码为空");
            return false;
        }

        return true;
    }

    public void getCode() {
        if (isGetCode()) {
            String userName = getView().mEtUsername.getText().toString();
            AccountModel.getInstance().forgotCaptcha(userName).unsafeSubscribe(new ServicesResponse<Boolean>() {
                @Override
                public void onNext(Boolean aBoolean) {
                    LUtils.toast("发送成功");
                    getView().mBtnCaptcha.startTickWork();
                }
            });
        }
    }

    public void updatePwd() {
        if (isCanSubmit()) {
            String userName = getView().mEtUsername.getText().toString();
            String captcha = getView().mEtCaptcha.getText().toString();
            String pwd = getView().mEtPassword.getText().toString();
            AccountModel.getInstance().resetPassword(userName, captcha, pwd).unsafeSubscribe(new ServicesResponse<String>() {
                public void onNext(String s) {
                    getView().finish();
                }
            });
        }
    }

}
