package com.miguan.yjy.module.account;

import android.content.Intent;

import com.dsk.chain.bijection.Presenter;
import com.miguan.yjy.model.AccountModel;
import com.miguan.yjy.model.bean.TestStart;
import com.miguan.yjy.model.bean.User;
import com.miguan.yjy.model.services.ServicesResponse;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;

import org.greenrobot.eventbus.EventBus;

import java.util.Map;

/**
 * Copyright (c) 2017/3/30. LiaoPeiKun Inc. All rights reserved.
 */

public class LoginPresenter extends Presenter<LoginActivity> {

    public void login(String username, String pwd) {
        AccountModel.getInstance().login(username, pwd).unsafeSubscribe(new ServicesResponse<User>() {
            @Override
            public void onNext(User user) {
                EventBus.getDefault().post(new TestStart());
                getView().closeKeyboard();
                getView().finish();
            }
        });
    }

    public void  wxLogin() {
        getView().getExpansionDelegate().showProgressBar("等待微信授权");
        UMShareAPI.get(getView()).getPlatformInfo(getView(), SHARE_MEDIA.WEIXIN, new UMAuthListener() {
            @Override
            public void onStart(SHARE_MEDIA share_media) {

            }

            @Override
            public void onComplete(SHARE_MEDIA share_media, int i, Map<String, String> map) {
                map.put("nickname", map.get("screen_name"));
                int sex;
                if (map.get("gender").equals("女")) {
                    sex = 0;
                } else if (map.get("gender").equals("男")) {
                    sex = 1;
                } else {
                    sex = 2;
                }
                map.put("sex", sex + "");
                map.put("headimgurl", map.get("iconurl"));
                AccountModel.getInstance().login(map).unsafeSubscribe(new ServicesResponse<User>() {
                    @Override
                    public void onNext(User user) {
                        EventBus.getDefault().post(new TestStart());
                        getView().finish();

                    }
                });
                getView().getExpansionDelegate().hideProgressBar();
            }

            @Override
            public void onError(SHARE_MEDIA share_media, int i, Throwable throwable) {
                getView().getExpansionDelegate().hideProgressBar();
            }

            @Override
            public void onCancel(SHARE_MEDIA share_media, int i) {
                getView().getExpansionDelegate().hideProgressBar();
            }
        });
    }

    @Override
    protected void onResult(int requestCode, int resultCode, Intent data) {
        super.onResult(requestCode, resultCode, data);
        UMShareAPI.get(getView()).onActivityResult(requestCode, resultCode, data);
    }

}
