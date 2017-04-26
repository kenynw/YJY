package com.miguan.yjy.module.user;

import android.view.ViewGroup;
import android.widget.TextView;

import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.miguan.yjy.R;
import com.miguan.yjy.model.bean.FaceScore;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Copyright (c) 2017/4/25. LiaoPeiKun Inc. All rights reserved.
 */

public class FaceScoreViewHolder extends BaseViewHolder<FaceScore> {

    @BindView(R.id.tv_face_score_title)
    TextView mTvTitle;

    @BindView(R.id.tv_face_score_time)
    TextView mTvTime;

    @BindView(R.id.tv_face_score_value)
    TextView mTvValue;

    public FaceScoreViewHolder(ViewGroup parent) {
        super(parent, R.layout.item_list_face_score);
        ButterKnife.bind(this, itemView);
    }

    @Override
    public void setData(FaceScore data) {
        mTvTitle.setText(data.getContent());
        mTvTime.setText(data.getCreated_at());
        mTvValue.setText("+" + data.getMoney());
    }
}
