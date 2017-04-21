package com.miguan.yjy.module.main;

import android.Manifest;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.dsk.chain.expansion.data.BaseDataActivityPresenter;
import com.miguan.yjy.model.CommonModel;
import com.miguan.yjy.model.bean.Message;
import com.miguan.yjy.model.bean.Version;
import com.miguan.yjy.model.services.ServicesResponse;
import com.miguan.yjy.utils.LUtils;
import com.miguan.yjy.utils.PermissionUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import cn.nekocode.badge.BadgeDrawable;

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
        CommonModel.getInstance().getUnreadMsg().unsafeSubscribe(new ServicesResponse<Message>() {
            @Override
            public void onNext(Message message) {
                int count = message.getOverdueNum() + message.getUnReadNUM();
                LUtils.log("count: " + count);
                if (count >= 0) {
                    final BadgeDrawable drawable = new BadgeDrawable.Builder()
                                    .type(BadgeDrawable.TYPE_NUMBER)
                                    .number(count)
                                    .build();
                    SpannableString spannableString = new SpannableString(TextUtils.concat("我的", drawable.toSpannable()));

                    View view = getView().getTab(3).getCustomView();
                    TextView tv = (TextView) view.findViewById(android.R.id.text1);
                    tv.setText(spannableString);
                }
            }
        });
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
