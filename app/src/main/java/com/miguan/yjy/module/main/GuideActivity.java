package com.miguan.yjy.module.main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;

import com.dsk.chain.bijection.ChainBaseActivity;
import com.dsk.chain.bijection.RequiresPresenter;
import com.miguan.yjy.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Copyright (c) 2017/4/21. LiaoPeiKun Inc. All rights reserved.
 */
@RequiresPresenter(GuideActivityPresenter.class)
public class GuideActivity extends ChainBaseActivity<GuideActivityPresenter> {

    @BindView(R.id.pager_main_guide)
    ViewPager mPagerGuide;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity_guide);
        ButterKnife.bind(this);

        mPagerGuide.setAdapter(getPresenter().getAdapter());
    }

}
