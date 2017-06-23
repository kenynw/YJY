package com.miguan.yjy.module.article;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.LayoutRes;
import android.text.Html;
import android.text.TextUtils;
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

    private int mIsLike;

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

    @BindView(R.id.dv_evaluate_thumb)
    SimpleDraweeView mDvThumb;

    @BindView(R.id.tv_evaluate_reply)
    TextView mTvReply;

    private EvaluatePanel mEvaluatePanel;

    public EvaluateViewHolder(ViewGroup parent) {
        this(parent, R.layout.item_list_evaluate);
    }

    public EvaluateViewHolder(ViewGroup parent, @LayoutRes int res) {
        super(parent, res);
        mEvaluatePanel = new EvaluatePanel(getContext(), itemView);
    }

    @Override
    public void setData(Evaluate data) {
        super.setData(data);
        mEvaluatePanel.setEvaluate(data);

        if (TextUtils.isEmpty(data.getAttachment())) {
            mDvThumb.setVisibility(View.GONE);
        } else {
            mDvThumb.setVisibility(View.VISIBLE);
            mDvThumb.setImageURI(Uri.parse(data.getAttachment()));
        }

        if (data.getReply() == null) {
            mTvReply.setVisibility(View.GONE);
        } else {
            mTvReply.setText(Html.fromHtml(String.format(getContext().getString(R.string.text_evaluate_reply_from),
                    data.getReply().getAuthor(), data.getReply().getComment())));
        }

    }

    protected void setLike(Evaluate data) {
        mIvLike.setImageResource(data.getIsLike() == 0 ? R.mipmap.ic_like_normal : R.mipmap.ic_like_pressed);
        mTvLike.setText(data.getLike_num() > 0 ? String.valueOf((data.getLike_num())) : "赞");
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
