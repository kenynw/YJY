package com.miguan.yjy.module.article;

import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.miguan.yjy.R;
import com.miguan.yjy.model.bean.Evaluate;
import com.miguan.yjy.module.product.ProductDetailPresenter;
import com.miguan.yjy.utils.LUtils;

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

    @BindView(R.id.ll_evaluate_article)
    LinearLayout mLlArticle;

    @BindView(R.id.dv_evaluate_article_thumb)
    SimpleDraweeView mDvArticleThumb;

    @BindView(R.id.tv_evaluate_article_title)
    TextView mTvArticleTitle;

    public EvaluateMeViewHolder(ViewGroup parent) {
        super(parent, R.layout.item_list_evaluate_me);
    }

    @Override
    public void setData(Evaluate data) {
        super.setData(data);
        mTvDate.setText(data.getCreated_at());
        if (data.getDetail() != null) {
            if (data.getType() == 1) {
                mLlArticle.setVisibility(View.GONE);
                mLlProduct.setVisibility(View.VISIBLE);
                mDvProductImg.setImageURI(Uri.parse(data.getDetail().getImg()));
                mTvProductName.setText(data.getDetail().getName());
                mTvProductSpec.setText(String.format(getContext().getString(R.string.text_product_spec), data.getDetail().getPrice(),
                        data.getDetail().getForm()));
                mLlProduct.setOnClickListener(v -> ProductDetailPresenter.start(getContext(), data.getDetail().getId()));
            } else {
                mLlProduct.setVisibility(View.GONE);
                mLlArticle.setVisibility(View.VISIBLE);
                mDvArticleThumb.setImageURI(Uri.parse(data.getDetail().getImg()));
                mTvArticleTitle.setText(data.getDetail().getName());
                mLlArticle.setOnClickListener(v -> ArticleDetailPresenter.start(getContext(), data.getDetail().getId()));
            }
        }
    }

    @Override
    public int getContentTextWidth() {
        return (int) (LUtils.getScreenWidth() - getDimen(R.dimen.spacing_large));
    }

}
