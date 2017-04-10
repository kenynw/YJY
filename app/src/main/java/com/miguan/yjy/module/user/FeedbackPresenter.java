package com.miguan.yjy.module.user;

import com.dsk.chain.bijection.Presenter;
import com.miguan.yjy.model.UserModel;
import com.miguan.yjy.model.services.ServicesResponse;
import com.miguan.yjy.utils.LUtils;

/**
 * Copyright (c) 2017/4/1. LiaoPeiKun Inc. All rights reserved.
 */
public class FeedbackPresenter extends Presenter<FeedbackActivity> {

    public void submit(String content) {
        UserModel.getInstance().addFeedback(content).unsafeSubscribe(new ServicesResponse<String>() {
            @Override
            public void onNext(String s) {
                LUtils.toast("谢谢反馈");
                getView().finish();
            }
        });
    }

}
