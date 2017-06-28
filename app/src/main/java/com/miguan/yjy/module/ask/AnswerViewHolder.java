package com.miguan.yjy.module.ask;

import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.miguan.yjy.R;
import com.miguan.yjy.model.bean.Ask;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Copyright (c) 2017/6/27. LiaoPeiKun Inc. All rights reserved.
 */

public class AnswerViewHolder extends BaseViewHolder<Ask> {

    @BindView(R.id.dv_evaluate_avatar)
    SimpleDraweeView mDvAvatar;

    @BindView(R.id.tv_evaluate_user_name)
    TextView mTvUserName;

    @BindView(R.id.tv_evaluate_user_age)
    TextView mTvUserAge;

    @BindView(R.id.tv_evaluate_essence)
    TextView mTvEssence;

    @BindView(R.id.ll_evaluate_like)
    LinearLayout mLlLike;

    @BindView(R.id.tv_evaluate_label)
    TextView mTvLabel;

    @BindView(R.id.tv_evaluate_content)
    TextView mTvContent;

    @BindView(R.id.tv_evaluate_date)
    TextView mTvDate;

    public AnswerViewHolder(ViewGroup parent) {
        super(parent, R.layout.item_list_evaluate_reply);
        ButterKnife.bind(this, itemView);
        mLlLike.setVisibility(View.GONE);
        mTvEssence.setVisibility(View.GONE);
        itemView.setBackgroundColor(getContext().getResources().getColor(R.color.white));
    }

    @Override
    public void setData(Ask data) {
        mDvAvatar.setImageURI(data.getUser_info().getImg());
        mTvUserName.setText(data.getUser_info().getUsername());
        mTvUserAge.setText(data.getUser_info().getAge() > 0 ? data.getUser_info().getAge() + "岁" : "");
        if (TextUtils.isEmpty(data.getUser_info().getSkin())) {
            mTvLabel.setVisibility(View.GONE);
        } else {
            mTvLabel.setVisibility(View.VISIBLE);
            mTvLabel.setText(data.getUser_info().getSkin());
        }
        mTvContent.setText(data.getReply());
        mTvDate.setText(data.getAdd_time());
    }

}
