package com.miguan.yjy.module.user;

import android.content.Context;
import android.content.Intent;

import com.dsk.chain.expansion.list.BaseListActivityPresenter;
import com.miguan.yjy.model.UserModel;
import com.miguan.yjy.model.bean.FaceScore;

/**
 * Copyright (c) 2017/4/1. LiaoPeiKun Inc. All rights reserved.
 */

public class FaceScorePresenter extends BaseListActivityPresenter<FaceScoreActivity, FaceScore> {

    public static String EXTRA_SCORE = "score";

    public static void start(Context context, String score) {
        Intent intent = new Intent(context, FaceScoreActivity.class);
        intent.putExtra(EXTRA_SCORE, score);
        context.startActivity(intent);
    }

    @Override
    protected void onCreateView(FaceScoreActivity view) {
        super.onCreateView(view);
        onRefresh();
    }

    @Override
    public void onRefresh() {
        UserModel.getInstance().getFaceScoreList(1).unsafeSubscribe(getRefreshSubscriber());
    }

    @Override
    public void onLoadMore() {
        UserModel.getInstance().getFaceScoreList(getCurPage()).unsafeSubscribe(getMoreSubscriber());
    }

}
