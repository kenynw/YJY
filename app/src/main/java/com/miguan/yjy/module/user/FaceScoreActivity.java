package com.miguan.yjy.module.user;

import android.graphics.Color;
import android.os.Bundle;

import com.dsk.chain.bijection.RequiresPresenter;
import com.dsk.chain.expansion.data.BaseDataActivity;
import com.miguan.yjy.R;
import com.miguan.yjy.model.bean.User;

import butterknife.ButterKnife;

/**
 * Copyright (c) 2017/4/1. LiaoPeiKun Inc. All rights reserved.
 */
@RequiresPresenter(FaceScorePresenter.class)
public class FaceScoreActivity extends BaseDataActivity<FaceScorePresenter, User> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_activity_face_score);
        setToolbarTitle(R.string.text_face_score);
        getToolbar().setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        getTitleView().setTextColor(Color.WHITE);
        ButterKnife.bind(this);
    }

}
