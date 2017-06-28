package com.miguan.yjy.module.article;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.annotation.LayoutRes;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.miguan.yjy.R;
import com.miguan.yjy.model.ArticleModel;
import com.miguan.yjy.model.bean.Evaluate;
import com.miguan.yjy.model.local.UserPreferences;
import com.miguan.yjy.model.services.ServicesResponse;
import com.miguan.yjy.module.account.LoginActivity;
import com.miguan.yjy.module.product.ProductDetailPresenter;
import com.miguan.yjy.module.product.ProductRemarkPresenter;

import butterknife.BindView;

/**
 * Copyright (c) 2017/6/15. LiaoPeiKun Inc. All rights reserved.
 */

public class EvaluateCommendVH extends BaseEvaluateViewHolder {

    @BindView(R.id.dv_evaluate_avatar)
    SimpleDraweeView mDvAvatar;

    @BindView(R.id.tv_evaluate_user_name)
    TextView mTvUserName;

    @BindView(R.id.tv_evaluate_user_age)
    TextView mTvUserAge;

    @BindView(R.id.ll_evaluate_like)
    LinearLayout mLlEvaluateLike;

    @BindView(R.id.ll_evaluate_user_info)
    LinearLayout mLlUserInfo;

    @BindView(R.id.tv_evaluate_label)
    TextView mTvLabel;

    @BindView(R.id.tv_evaluate_essence)
    TextView mTvEvaluateEssence;

    @BindView(R.id.dv_evaluate_product_img)
    SimpleDraweeView mDvProductImg;

    @BindView(R.id.tv_evaluate_product_name)
    TextView mTvProductName;

    @BindView(R.id.tv_evaluate_product_rating)
    RatingBar mTvProductRating;

    @BindView(R.id.tv_evaluate_product_spec)
    TextView mTvProductSpec;

    @BindView(R.id.ll_evaluate_product)
    LinearLayout mLlEvaluateProduct;

    @BindView(R.id.tv_evaluate_commend_like)
    TextView mTvEvaluateLike;

    @BindView(R.id.tv_evaluate_commend_comment)
    TextView mTvEvaluateComment;

    protected int mIsLike;

    public EvaluateCommendVH(ViewGroup parent) {
        this(parent, R.layout.item_list_evaluate_commend);
    }

    public EvaluateCommendVH(ViewGroup parent, @LayoutRes int res) {
        super(parent, res);
    }

    @Override
    public void setData(Evaluate data) {
        super.setData(data);
        if (data.getUser() != null && data.getUser().getUser_id() > 0) {
            mDvAvatar.setImageURI(Uri.parse(data.getUser().getImg()));
            mTvUserName.setText(data.getUser().getUsername());
            mTvUserAge.setText(data.getUser().getAge() > 0 ? data.getUser().getAge() + "岁" : "");
            if (TextUtils.isEmpty(data.getUser().getSkin())) {
                mTvLabel.setVisibility(View.GONE);
                mLlUserInfo.setGravity(Gravity.CENTER_VERTICAL);
            } else {
                mTvLabel.setText(data.getUser().getSkin());
            }
        }

        if (data.getProduct() != null && data.getProduct().getId() > 0) {
            mDvProductImg.setImageURI(data.getProduct().getProduct_img());
            mTvProductName.setText(data.getProduct().getProduct_name());
            mTvProductRating.setRating(data.getProduct().getStar());
            mTvProductSpec.setText(data.getProduct().getSpec(getContext()));
            mLlEvaluateProduct.setOnClickListener(v -> ProductDetailPresenter.start(getContext(), data.getProduct().getId()));
            mTvEvaluateComment.setOnClickListener(v -> ProductRemarkPresenter.start(getContext(), data.getProduct()));
        } else {
            mLlEvaluateProduct.setVisibility(View.GONE);
            mTvEvaluateComment.setVisibility(View.GONE);
        }

        mTvEvaluateEssence.setVisibility(View.GONE);
        mIsLike = data.getIsLike();
        setLike(data);
    }

    protected void setLike(Evaluate data) {
        mIsLike = data.getIsLike();
        mLlEvaluateLike.setVisibility(View.GONE);
        mTvEvaluateLike.setText(data.getLike_num() > 0 ? data.getLike_num() + "" : "");
        setLikeIcon(data.getIsLike() == 1);
        mTvEvaluateLike.setOnClickListener(v -> {
            if (UserPreferences.getUserID() > 0) {
                ArticleModel.getInstance().addEvaluateLike(data.getId())
                        .subscribe(new ServicesResponse<String>() {
                            @Override
                            public void onNext(String s) {
                                int curLikeCount = TextUtils.isEmpty(mTvEvaluateLike.getText()) ? 0 :
                                        Integer.valueOf(mTvEvaluateLike.getText().toString());
                                int likeCount = curLikeCount + (mIsLike == 0 ? 1 : -1);
                                mTvEvaluateLike.setText(likeCount > 0 ? likeCount + "" : "");
                                mIsLike = mIsLike == 0 ? 1 : 0;
                                setLikeIcon(mIsLike == 1);
                            }
                        });
            } else {
                getContext().startActivity(new Intent(getContext(), LoginActivity.class));
            }
        });
    }

    private void setLikeIcon(boolean isLike) {
        Drawable drawable = getContext().getResources().getDrawable(isLike ? R.mipmap.ic_like_pressed : R.mipmap.ic_like_normal);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        mTvEvaluateLike.setCompoundDrawables(drawable, null, null, null);
    }

}
