package com.miguan.yjy.module.article;

import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.miguan.yjy.R;
import com.miguan.yjy.model.bean.Evaluate;
import com.miguan.yjy.module.product.ProductDetailPresenter;
import com.miguan.yjy.utils.StringUtils;
import com.miguan.yjy.widget.RatingBar;

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

    @BindView(R.id.tv_evaluate_product_spec)
    TextView mTvProductSpec;

    @BindView(R.id.ll_evaluate_article)
    LinearLayout mLlArticle;

    @BindView(R.id.dv_evaluate_article_thumb)
    SimpleDraweeView mDvArticleThumb;

    @BindView(R.id.tv_evaluate_article_title)
    TextView mTvArticleTitle;

    @BindView(R.id.rating_evaluate_me_stars)
    RatingBar mEvaluateRating;

    @BindView(R.id.tv_evaluate_me_desc)
    TextView mTvEvaluateDesc;

    @BindView(R.id.fl_evaluate_me_star)
    FrameLayout mFlStar;

    public EvaluateMeViewHolder(ViewGroup parent) {
        super(parent, R.layout.item_list_evaluate_me);
    }

    @Override
    public void setData(Evaluate data) {
        super.setData(data);
        mTvDate.setText(data.getCreated_at());
        mEvaluateRating.setStar(data.getStar());
        String ratingText = "中评";
        if (data.getStar() < 3) ratingText = "差评";
        else if (data.getStar() > 3) ratingText = "好评";
        mTvEvaluateDesc.setText(ratingText);
        if (data.getDetail() != null) {
            if (data.getType() == 1) {
                mLlArticle.setVisibility(View.GONE);
                mLlProduct.setVisibility(View.VISIBLE);
                mFlStar.setVisibility(View.VISIBLE);
                mDvProductImg.setImageURI(Uri.parse(data.getDetail().getImg()));
                mTvProductName.setText(data.getDetail().getName());
                mTvProductSpec.setText(StringUtils.getFormatSpec(getContext(), data.getDetail().getPrice(), data.getDetail().getForm()));
                mLlProduct.setOnClickListener(v -> ProductDetailPresenter.start(getContext(), data.getDetail().getId()));
            } else {
                mLlProduct.setVisibility(View.GONE);
                mLlArticle.setVisibility(View.VISIBLE);
                mFlStar.setVisibility(View.GONE);
                mDvArticleThumb.setImageURI(Uri.parse(data.getDetail().getImg()));
                mTvArticleTitle.setText(data.getDetail().getName());
                mLlArticle.setOnClickListener(v -> ArticleDetailPresenter.start(getContext(), data.getDetail().getId()));
            }
        }
    }

}
