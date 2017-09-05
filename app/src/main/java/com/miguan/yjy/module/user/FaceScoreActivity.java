package com.miguan.yjy.module.user;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dsk.chain.bijection.RequiresPresenter;
import com.dsk.chain.expansion.list.BaseListActivity;
import com.dsk.chain.expansion.list.ListConfig;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.miguan.yjy.R;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.miguan.yjy.module.user.FaceScorePresenter.EXTRA_SCORE;

/**
 * Copyright (c) 2017/4/1. LiaoPeiKun Inc. All rights reserved.
 */
@RequiresPresenter(FaceScorePresenter.class)
public class FaceScoreActivity extends BaseListActivity<FaceScorePresenter> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setToolbarTitle(R.string.text_face_score);
        getToolbar().setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        getTitleView().setTextColor(Color.WHITE);
        ButterKnife.bind(this);

        getPresenter().getAdapter().addHeader(new Header());
    }

    @Override
    protected BaseViewHolder createViewHolder(ViewGroup parent, int viewType) {
        return new FaceScoreViewHolder(parent);
    }

    @Override
    public ListConfig getListConfig() {
        return super.getListConfig()
                .setContainerLayoutRes(R.layout.user_activity_face_score)
                .setFooterNoMoreRes(R.layout.include_default_footer);
    }

    public class Header implements RecyclerArrayAdapter.ItemView {

        @BindView(R.id.tv_face_score_value)
        TextView mTvValue;

        @Override
        public View onCreateView(ViewGroup parent) {
            View view = LayoutInflater.from(FaceScoreActivity.this).inflate(R.layout.header_face_score, null);
            ButterKnife.bind(this, view);

            mTvValue.setText(getIntent().getStringExtra(EXTRA_SCORE));

            return view;
        }

        @Override
        public void onBindView(View headerView) {

        }

    }

}
