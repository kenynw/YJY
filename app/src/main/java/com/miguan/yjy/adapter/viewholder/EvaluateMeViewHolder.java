package com.miguan.yjy.adapter.viewholder;

import android.net.Uri;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.miguan.yjy.R;
import com.miguan.yjy.model.bean.Evaluate;
import com.miguan.yjy.module.product.ProductDetailPresenter;

import butterknife.BindView;

/**
 * Copyright (c) 2017/4/1. LiaoPeiKun Inc. All rights reserved.
 */

public class EvaluateMeViewHolder extends BaseEvaluateViewHolder {

    @BindView(R.id.tv_evaluate_date)
    TextView mTvDate;

    @BindView(R.id.ll_evaluate_product)
    LinearLayout mLlProduct;

    @BindView(R.id.dv_evaluate_product_img)
    SimpleDraweeView mDvProductImg;

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
        mTvDate.setText(data.getCreated_at());
        if (data.getDetail() != null) {
            mLlProduct.setOnClickListener(v -> ProductDetailPresenter.start(getContext(), data.getDetail().getId()));
            mDvProductImg.setImageURI(Uri.parse(data.getDetail().getProduct_img()));
            mTvProductName.setText(data.getDetail().getProduct_name());
            mTvProductSpec.setText(String.format(getContext().getString(R.string.text_product_spec),
                    data.getDetail().getPrice(), data.getDetail().getForm().toUpperCase()));
        }
    }

}
