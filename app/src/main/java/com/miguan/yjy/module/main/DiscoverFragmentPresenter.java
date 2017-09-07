package com.miguan.yjy.module.main;

import android.os.Bundle;

import com.dsk.chain.expansion.data.BaseDataFragmentPresenter;
import com.miguan.yjy.model.CommonModel;
import com.miguan.yjy.model.bean.Discover;
import com.miguan.yjy.model.bean.Skin;
import com.miguan.yjy.model.bean.User;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

/**
 * Copyright (c) 2017/8/22. LiaoPeiKun Inc. All rights reserved.
 */

public class DiscoverFragmentPresenter extends BaseDataFragmentPresenter<DiscoverFragment, Discover> {

    @Override
    protected void onCreate(DiscoverFragment view, Bundle saveState) {
        super.onCreate(view, saveState);
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onCreateView(DiscoverFragment view) {
        super.onCreateView(view);
        loadData();
    }

    public void loadData() {
        CommonModel.getInstance().getDiscover().unsafeSubscribe(getSubscriber());
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    // 接收登录通知
    @SuppressWarnings("unused")
    @Subscribe(threadMode = ThreadMode.POSTING)
    public void onLogin(User user) {
        loadData();
    }

    // 接收肤质测试完成通知
    @SuppressWarnings("unused")
    @Subscribe(threadMode = ThreadMode.POSTING)
    public void onTestComplete(List<Skin> list) {
        getView().getSkinAdapter().addAll(list);
    }

}
