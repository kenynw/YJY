package com.miguan.yjy.module.main;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;

import com.dsk.chain.expansion.data.BaseDataActivityPresenter;
import com.miguan.yjy.model.AccountModel;
import com.miguan.yjy.model.CommonModel;
import com.miguan.yjy.model.bean.User;
import com.miguan.yjy.model.bean.Version;
import com.miguan.yjy.model.services.ServicesResponse;
import com.miguan.yjy.utils.PermissionUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import q.rorbin.badgeview.Badge;
import q.rorbin.badgeview.QBadgeView;


/**
 * Copyright (c) 2017/3/20. LiaoPeiKun Inc. All rights reserved.
 */

public class MainActivityPresenter extends BaseDataActivityPresenter<MainActivity, Version> {

    private Badge mBadge;
    private Badge mBadgeTest;

    @Override
    protected void onCreate(MainActivity view, Bundle saveState) {
        super.onCreate(view, saveState);
        EventBus.getDefault().register(this);
        requestPermission();
        CommonModel.getInstance().update(getView());
    }

    @Override
    protected void onCreateView(MainActivity view) {
        super.onCreateView(view);

        View tab = getView().getTab(3).getCustomView();
        mBadge = new QBadgeView(getView()).setBadgeGravity(Gravity.TOP | Gravity.END).bindTarget(tab);

        View tabTest = getView().getTab(2).getCustomView();
        mBadgeTest = new QBadgeView(getView()).setBadgeGravity(Gravity.TOP | Gravity.END).bindTarget(tabTest);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        loadUnread();
    }

    private void requestPermission() {
        PermissionUtils.requestPermission(getView(), new String[]{
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA
        }, 100);
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadUnread();
    }

    private void loadUnread() {
        if (AccountModel.getInstance().isLogin()) {
            CommonModel.getInstance().getUnreadMsg().unsafeSubscribe(new ServicesResponse<User>() {
                @Override
                public void onNext(User user) {
                    int count = user.getOverdueNum() + user.getUnReadNUM();
                    if (count <= 0) mBadge.hide(true);
                    else mBadge.setBadgeText("");

                    if (user.getIsComplete() == 1) mBadgeTest.hide(false);
                    else mBadgeTest.setBadgeText("");
                }
            });
        } else {
            mBadgeTest.hide(false);
            mBadge.hide(false);
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
