package com.miguan.yjy.adapter.viewholder;

import android.net.Uri;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.miguan.yjy.R;
import com.miguan.yjy.model.ArticleModel;
import com.miguan.yjy.model.bean.Evaluate;
import com.miguan.yjy.model.services.ServicesResponse;

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

    @BindView(R.id.tv_evaluate_label)
    TextView mTvLabel;

    public EvaluateViewHolder(ViewGroup parent) {
        super(parent, R.layout.item_list_evaluate);
    }

    @Override
    public void setData(Evaluate data) {
        super.setData(data);
        mDvAvatar.setImageURI(Uri.parse(data.getImg()));
        mTvUserName.setText(data.getUsername());
        mTvUserAge.setText(data.getAge() + "岁");
        mTvLabel.setText(data.getSkin());
        mIvLike.setImageResource(data.getIsLike() == 0 ? R.mipmap.ic_like_normal : R.mipmap.ic_like_pressed);
        mTvLike.setText(String.valueOf((data.getLike_num())));
        mLlEvaluateLike.setOnClickListener(v -> ArticleModel.getInstance().addEvaluateLike(data.getId())
                .subscribe(new ServicesResponse<String>() {
                    @Override
                    public void onNext(String s) {
                        mTvLike.setText(String.valueOf(data.getLike_num() + 1));
                        mIvLike.setImageResource(R.mipmap.ic_like_pressed);
                    }
                }));
    }

}
