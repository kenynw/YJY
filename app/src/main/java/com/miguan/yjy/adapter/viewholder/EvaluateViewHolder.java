package com.miguan.yjy.adapter.viewholder;

import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
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

/**
 * Copyright (c) 2017/3/24. LiaoPeiKun Inc. All rights reserved.
 */

public class EvaluateViewHolder extends BaseEvaluateViewHolder {

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

    @BindView(com.miguan.yjy.R.id.tv_evaluate_label)
    TextView mTvLabel;

    private int mIsLike;

    public EvaluateViewHolder(ViewGroup parent) {
        super(parent, R.layout.item_list_evaluate);
    }

    @Override
    public void setData(Evaluate data) {
        super.setData(data);
        mDvAvatar.setImageURI(Uri.parse(data.getImg()));
        mTvUserName.setText(data.getUsername());
        mTvUserAge.setText(data.getAge() > 0 ? data.getAge() + "岁" : "");
        mTvLabel.setText(data.getSkin());
        mIvLike.setImageResource(data.getIsLike() == 0 ? R.mipmap.ic_like_normal : R.mipmap.ic_like_pressed);
        mTvLike.setText(data.getLike_num() > 0 ? String.valueOf((data.getLike_num())) : "赞");
        mIsLike = data.getIsLike();
        if (TextUtils.isEmpty(data.getSkin())) {
            mTvLabel.setVisibility(View.GONE);
            mLlUserInfo.setGravity(Gravity.CENTER_VERTICAL);
        }
        mLlEvaluateLike.setOnClickListener(v -> {
            if (UserPreferences.getUserID() > 0) {
                ArticleModel.getInstance().addEvaluateLike(data.getId())
                        .subscribe(new ServicesResponse<String>() {
                            @Override
                            public void onNext(String s) {
                                int curLikeCount = mTvLike.getText().equals("赞") ? 0 : Integer.valueOf(mTvLike.getText().toString());
                                int likeCount = curLikeCount + (mIsLike == 0 ? 1 : -1);
                                mTvLike.setText(likeCount == 0 ? "赞" : String.valueOf(likeCount));
                                mIvLike.setImageResource(mIsLike == 0 ? R.mipmap.ic_like_pressed : R.mipmap.ic_like_normal);
                                mIsLike = mIsLike == 0 ? 1 : 0;
                            }
                        });
            } else {
                getContext().startActivity(new Intent(getContext(), LoginActivity.class));
            }
        });
    }

}
