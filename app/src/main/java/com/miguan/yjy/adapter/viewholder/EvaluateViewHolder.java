package com.miguan.yjy.adapter.viewholder;

import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.miguan.yjy.R;
import com.miguan.yjy.model.bean.Evaluate;
import com.miguan.yjy.utils.GlideCircleTransform;

import butterknife.BindView;

/**
 * Copyright (c) 2017/3/24. LiaoPeiKun Inc. All rights reserved.
 */

public class EvaluateViewHolder extends BaseEvaluateViewHolder {

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

    public EvaluateViewHolder(ViewGroup parent) {
        super(parent, R.layout.item_list_evaluate);
    }

    @Override
    public void setData(Evaluate data) {
        super.setData(data);
        Glide.with(getContext())
                .load(data.getImg())
                .transform(new GlideCircleTransform(getContext()))
                .placeholder(R.mipmap.def_image_loading)
                .error(R.mipmap.def_image_loading)
                .into(mIvAvatar);

        mTvUserName.setText(data.getUsername());
        mTvUserAge.setText(data.getUsername());
        mTvLike.setText(String.valueOf((data.getLike_num())));
        mTvLabel.setText(data.getUsername());
    }
}
