package com.miguan.yjy.model.services;

import android.content.Intent;

import com.miguan.yjy.model.AccountModel;
import com.miguan.yjy.utils.LUtils;

import rx.Subscriber;

/**
 * Copyright (c) 2015. LiaoPeiKun Inc. All rights reserved.
 */

public class ServicesResponse<T> extends Subscriber<T> {

    @Override
    public void onCompleted() {

    }

    @Override
    public void onError(Throwable e) {
        if (e instanceof ServiceException) {
            serviceError((ServiceException) e);
        } else {
            serviceError(new ServiceException(-1, "网络错误"));
        }
    }

    @Override
    public void onNext(T t) {

    }

    private void serviceError(ServiceException e) {
        if (e.getCode() == 3 || e.getCode() == -6) { // 未登录
            AccountModel.getInstance().clearToken();
            Intent intent = new Intent();
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            intent.setAction("com.miguan.yjy.login");
            LUtils.toast("登录信息过期");
            LUtils.getAppContext().startActivity(intent);
            return;
        }
        LUtils.toast(e.getMsg());
    }

}
