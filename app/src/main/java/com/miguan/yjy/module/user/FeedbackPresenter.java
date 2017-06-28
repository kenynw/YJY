package com.miguan.yjy.module.user;

import android.content.Intent;
import android.net.Uri;

import com.dsk.chain.bijection.Presenter;
import com.jude.library.imageprovider.ImageProvider;
import com.miguan.yjy.model.ImageModel;
import com.miguan.yjy.model.UserModel;
import com.miguan.yjy.model.services.ServicesResponse;
import com.miguan.yjy.utils.LUtils;

import java.io.File;

import rx.Observable;
import rx.functions.Func1;

/**
 * Copyright (c) 2017/4/1. LiaoPeiKun Inc. All rights reserved.
 */
public class FeedbackPresenter extends Presenter<FeedbackActivity> {

    public void submit(String content, Uri uri) {
        if (uri != null) {
            getView().getExpansionDelegate().showProgressBar("正在上传图片");
            ImageModel.getInstance().uploadImageSync("feedback", new File(uri.getPath()))
                    .flatMap(new Func1<String, Observable<String>>() {
                        @Override
                        public Observable<String> call(String s) {
                            return UserModel.getInstance().addFeedback(content, s);
                        }
                    })
                    .subscribe(new ServicesResponse<String>() {
                        @Override
                        public void onNext(String s) {
                            LUtils.toast("谢谢反馈");
                            getView().finish();
                        }
                    });
        } else {
            UserModel.getInstance().addFeedback(content, "").unsafeSubscribe(new ServicesResponse<String>() {
                @Override
                public void onNext(String s) {
                    LUtils.toast("谢谢反馈");
                    getView().finish();
                }
            });
        }
    }

    @Override
    protected void onResult(int requestCode, int resultCode, Intent data) {
        super.onResult(requestCode, resultCode, data);
        ImageProvider.getInstance(getView()).onActivityResult(requestCode, resultCode, data);
    }
}
