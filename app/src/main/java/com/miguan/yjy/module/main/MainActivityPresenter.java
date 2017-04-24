package com.miguan.yjy.module.main;

import android.Manifest;
import android.os.Bundle;
import android.view.View;

import com.dsk.chain.expansion.data.BaseDataActivityPresenter;
import com.miguan.yjy.model.CommonModel;
import com.miguan.yjy.model.bean.Message;
import com.miguan.yjy.model.bean.Version;
import com.miguan.yjy.model.local.UserPreferences;
import com.miguan.yjy.model.services.ServicesResponse;
import com.miguan.yjy.utils.PermissionUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import q.rorbin.badgeview.QBadgeView;


/**
 * Copyright (c) 2017/3/20. LiaoPeiKun Inc. All rights reserved.
 */

public class MainActivityPresenter extends BaseDataActivityPresenter<MainActivity, Version> {

    @Override
    protected void onCreate(MainActivity view, Bundle saveState) {
        super.onCreate(view, saveState);
        EventBus.getDefault().register(this);
        requestPremission();
        CommonModel.getInstance().update(getView());
    }

    private void requestPremission() {
        PermissionUtils.requestPermission(getView(), new String[]{
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA
        }, 100);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (UserPreferences.getUserID() > 0) {
            CommonModel.getInstance().getUnreadMsg().unsafeSubscribe(new ServicesResponse<Message>() {
                @Override
                public void onNext(Message message) {
                    int count = message.getOverdueNum() + message.getUnReadNUM();
                    View view = getView().getTab(3).getCustomView();
                    new QBadgeView(getView()).bindTarget(view).setBadgeNumber(count);
                }
            });
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void setCurrentTab(Integer index) {
        getView().setCurrentTab(index);
    }

}
