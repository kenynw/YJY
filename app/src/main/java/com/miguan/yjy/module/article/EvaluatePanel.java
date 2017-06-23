package com.miguan.yjy.module.article;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.miguan.yjy.R;
import com.miguan.yjy.model.ArticleModel;
import com.miguan.yjy.model.bean.Evaluate;
import com.miguan.yjy.model.local.UserPreferences;
import com.miguan.yjy.model.services.ServicesResponse;
import com.miguan.yjy.module.account.LoginActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Copyright (c) 2017/6/20. LiaoPeiKun Inc. All rights reserved.
 */

public class EvaluatePanel {

    private AppCompatActivity mActivity;

    @BindView(R.id.dv_evaluate_avatar)
    SimpleDraweeView mDvAvatar;

    @BindView(R.id.tv_evaluate_user_name)
    TextView mTvUserName;

    @BindView(R.id.tv_evaluate_user_age)
    TextView mTvUserAge;

    @BindView(R.id.ll_evaluate_like)
    LinearLayout mLlEvaluateLike;

    @BindView(R.id.iv_evaluate_like)
    ImageView mIvLike;

    @BindView(R.id.tv_evaluate_like)
    TextView mTvLike;

    @BindView(R.id.ll_evaluate_user_info)
    LinearLayout mLlUserInfo;

    @BindView(R.id.tv_evaluate_label)
    TextView mTvLabel;

    @BindView(R.id.tv_evaluate_essence)
    TextView mTvEvaluateEssence;

    public EvaluatePanel(AppCompatActivity activity) {
        mActivity = activity;
        ButterKnife.bind(this, activity);
    }

    public EvaluatePanel(Context context, View view) {
        mActivity = (AppCompatActivity) context;
        ButterKnife.bind(this, view);
    }

    public void setEvaluate(Evaluate evaluate) {
        if (evaluate.getUser() != null && evaluate.getUser().getUser_id() > 0) {
            mDvAvatar.setImageURI(Uri.parse(evaluate.getUser().getImg()));
            mTvUserName.setText(evaluate.getUser().getUsername());
            mTvUserAge.setText(evaluate.getUser().getAge() > 0 ? evaluate.getUser().getAge() + "岁" : "");
            if (TextUtils.isEmpty(evaluate.getUser().getSkin())) {
                mTvLabel.setVisibility(View.GONE);
                mLlUserInfo.setGravity(Gravity.CENTER_VERTICAL);
            } else {
                mTvLabel.setText(evaluate.getUser().getSkin());
            }
        } else {
            mDvAvatar.setImageURI(Uri.parse(evaluate.getImg()));
            mTvUserName.setText(evaluate.getUsername());
            mTvUserAge.setText(evaluate.getAge() > 0 ? evaluate.getAge() + "岁" : "");
            if (TextUtils.isEmpty(evaluate.getSkin())) {
                mTvLabel.setVisibility(View.GONE);
                mLlUserInfo.setGravity(Gravity.CENTER_VERTICAL);
            } else {
                mTvLabel.setText(evaluate.getSkin());
            }
        }
        mTvEvaluateEssence.setVisibility(evaluate.getIs_digest() == 1 ? View.VISIBLE : View.GONE);

        mIvLike.setImageResource(evaluate.getIsLike() == 0 ? R.mipmap.ic_like_normal : R.mipmap.ic_like_pressed);
        mTvLike.setText(evaluate.getLike_num() > 0 ? String.valueOf((evaluate.getLike_num())) : "赞");
        mLlEvaluateLike.setOnClickListener(v -> {
            if (UserPreferences.getUserID() > 0) {
                ArticleModel.getInstance().addEvaluateLike(evaluate.getId())
                        .subscribe(new ServicesResponse<String>() {
                            @Override
                            public void onNext(String s) {
                                int curLikeCount = mTvLike.getText().equals("赞") ? 0 :
                                        Integer.valueOf(mTvLike.getText().toString());
                                int likeCount = curLikeCount + (evaluate.getIsLike() == 0 ? 1 : -1);
                                evaluate.setIsLike(evaluate.getIsLike() == 0 ? 1 : 0);
                                mTvLike.setText(likeCount == 0 ? "赞" : String.valueOf(likeCount));
                                mIvLike.setImageResource(evaluate.getIsLike() == 1 ?
                                        R.mipmap.ic_like_pressed : R.mipmap.ic_like_normal);
                            }
                        });
            } else {
                mActivity.startActivity(new Intent(mActivity, LoginActivity.class));
            }
        });
    }
    
}
