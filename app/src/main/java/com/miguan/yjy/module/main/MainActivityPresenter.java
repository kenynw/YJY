package com.miguan.yjy.module.main;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;

import com.dsk.chain.expansion.data.BaseDataActivityPresenter;
import com.miguan.yjy.model.AccountModel;
import com.miguan.yjy.model.CommonModel;
import com.miguan.yjy.model.bean.User;
import com.miguan.yjy.model.bean.Version;
import com.miguan.yjy.model.constant.Constants;
import com.miguan.yjy.utils.LUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import q.rorbin.badgeview.Badge;
import q.rorbin.badgeview.QBadgeView;
import rx.Subscriber;

/**
 * Copyright (c) 2017/3/20. LiaoPeiKun Inc. All rights reserved.
 */

public class MainActivityPresenter extends BaseDataActivityPresenter<MainActivity, Version> {

    private Badge mBadge;

    @Override
    protected void onCreate(MainActivity view, Bundle saveState) {
        super.onCreate(view, saveState);
        EventBus.getDefault().register(this);
        CommonModel.getInstance().update(getView());
    }

    @Override
    protected void onCreateView(MainActivity view) {
        super.onCreateView(view);

        View tab = getView().getTab(3).getCustomView();
        mBadge = new QBadgeView(getView()).setBadgeGravity(Gravity.TOP | Gravity.END).bindTarget(tab);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        loadUnread();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadUnread();
    }

    private void loadUnread() {
        if (AccountModel.getInstance().isLogin()) {
            CommonModel.getInstance().getUnreadMsg()
                    .unsafeSubscribe(new Subscriber<User>() {
                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(Throwable e) {

                        }

                        @Override
                        public void onNext(User user) {
                            int count = user.getOverdueNum() + user.getUnReadNUM();
                            if (count <= 0) mBadge.hide(true);
                            else mBadge.setBadgeText("");
                        }
                    });
        } else {
            mBadge.hide(false);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @SuppressWarnings("unused")
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void setCurrentTab(Integer index) {
        getView().setCurrentTab(index);
    }

    public void saveCheckBindTime() {
        SharedPreferences.Editor edit = LUtils.getPreferences().edit();
        edit.putLong(Constants.KEY_CHECK_IS_BIND_TIME, System.currentTimeMillis());
        edit.apply();
    }

}
