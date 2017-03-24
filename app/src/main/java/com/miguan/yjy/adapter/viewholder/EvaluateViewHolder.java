package com.miguan.yjy.adapter.viewholder;

import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.miguan.yjy.R;
import com.miguan.yjy.model.bean.Evaluate;
import com.miguan.yjy.utils.GlideCircleTransform;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Copyright (c) 2017/3/24. LiaoPeiKun Inc. All rights reserved.
 */

public class EvaluateViewHolder extends BaseViewHolder<Evaluate> {

    @BindView(R.id.img_evaluate_avatar)
    ImageView mIvAvatar;
    @BindView(R.id.tv_evaluate_user_name)
    TextView mTvUserName;
    @BindView(R.id.tv_evaluate_user_age)
    TextView mTvUserAge;
    @BindView(R.id.tv_evaluate_like)
    TextView mTvLike;
    @BindView(R.id.ll_evaluate_user_info)
    LinearLayout mLlUserInfo;
    @BindView(R.id.tv_evaluate_label)
    TextView mTvLabel;
    @BindView(R.id.tv_evaluate_content)
    TextView mTvContent;

    public EvaluateViewHolder(ViewGroup parent) {
        super(parent, R.layout.item_list_evaluate);
        ButterKnife.bind(this, itemView);
    }

    @Override
    public void setData(Evaluate data) {
        Glide.with(getContext())
                .load(data.getImg())
                .transform(new GlideCircleTransform(getContext()))
                .placeholder(R.mipmap.def_image_loading)
                .error(R.mipmap.def_image_loading)
                .into(mIvAvatar);

        mTvContent.setText(data.getComment());
        mTvUserName.setText(data.getUsername());
        mTvUserAge.setText(data.getUsername());
        mTvLike.setText(String.valueOf((data.getLike_num())));
        mTvLabel.setText(data.getUsername());
    }
}
