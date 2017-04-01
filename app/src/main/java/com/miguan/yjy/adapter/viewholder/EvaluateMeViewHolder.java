package com.miguan.yjy.adapter.viewholder;

import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.miguan.yjy.R;
import com.miguan.yjy.model.bean.Evaluate;

import butterknife.BindView;

/**
 * Copyright (c) 2017/4/1. LiaoPeiKun Inc. All rights reserved.
 */

public class EvaluateMeViewHolder extends BaseEvaluateViewHolder {

    @BindView(R.id.tv_evaluate_date)
    TextView mTvDate;

    @BindView(R.id.iv_evaluate_product_img)
    ImageView mIvProductImg;

    @BindView(R.id.tv_evaluate_product_name)
    TextView mTvProductName;

    @BindView(R.id.tv_evaluate_product_rating)
    RatingBar mTvProductRating;

    @BindView(R.id.tv_evaluate_product_spec)
    TextView mTvProductSpec;

    public EvaluateMeViewHolder(ViewGroup parent) {
        super(parent, R.layout.item_list_evaluate_me);
    }

    @Override
    public void setData(Evaluate data) {
        super.setData(data);
    }

}
