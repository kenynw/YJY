package com.miguan.yjy.module.user;

import android.graphics.Color;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dsk.chain.bijection.RequiresPresenter;
import com.dsk.chain.expansion.list.BaseListActivity;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.miguan.yjy.R;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.miguan.yjy.module.user.FaceScorePresenter.EXTRA_SCORE;

/**
 * Copyright (c) 2017/4/1. LiaoPeiKun Inc. All rights reserved.
 */
@RequiresPresenter(FaceScorePresenter.class)
public class FaceScoreActivity extends BaseListActivity<FaceScorePresenter> {

    @BindView(R.id.tv_face_score_value)
    TextView mTvValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setToolbarTitle(R.string.text_face_score);
        getToolbar().setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        getTitleView().setTextColor(Color.WHITE);
        ButterKnife.bind(this);

        mTvValue.setText(getIntent().getStringExtra(EXTRA_SCORE));
    }

    @Override
    protected int getLayout() {
        return R.layout.user_activity_face_score;
    }

    @Override
    protected BaseViewHolder createViewHolder(ViewGroup parent, int viewType) {
        return new FaceScoreViewHolder(parent);
    }

}
