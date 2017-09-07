package com.miguan.yjy.module.ask;

import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.miguan.yjy.R;
import com.miguan.yjy.model.ProductModel;
import com.miguan.yjy.model.bean.Ask;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Subscriber;

/**
 * Copyright (c) 2017/6/27. LiaoPeiKun Inc. All rights reserved.
 */

public class AnswerViewHolder extends BaseViewHolder<Ask> {

    @BindView(R.id.dv_evaluate_avatar)
    SimpleDraweeView mDvAvatar;

    @BindView(R.id.tv_evaluate_user_name)
    TextView mTvUserName;

    @BindView(R.id.tv_evaluate_user_age)
    TextView mTvUserAge;

    @BindView(R.id.tv_evaluate_essence)
    TextView mTvEssence;

    @BindView(R.id.ll_evaluate_like)
    LinearLayout mLlLike;

    @BindView(R.id.iv_evaluate_like)
    ImageView mIvLike;

    @BindView(R.id.tv_evaluate_like)
    TextView mTvLike;

    @BindView(R.id.tv_evaluate_label)
    TextView mTvLabel;

    @BindView(R.id.tv_evaluate_content)
    TextView mTvContent;

    @BindView(R.id.tv_evaluate_date)
    TextView mTvDate;

    public AnswerViewHolder(ViewGroup parent) {
        super(parent, R.layout.item_list_evaluate_reply);
        ButterKnife.bind(this, itemView);
        mTvEssence.setVisibility(View.GONE);
        itemView.setBackgroundColor(getContext().getResources().getColor(R.color.white));
    }

    @Override
    public void setData(Ask data) {
        mDvAvatar.setImageURI(data.getUser_info().getImg());
        mTvUserName.setText(data.getUser_info().getUsername());
        mTvUserAge.setText(data.getUser_info().getAge() > 0 ? data.getUser_info().getAge() + "岁" : "");
        if (TextUtils.isEmpty(data.getUser_info().getSkin())) {
            mTvLabel.setVisibility(View.GONE);
        } else {
            mTvLabel.setVisibility(View.VISIBLE);
            mTvLabel.setText(data.getUser_info().getSkin());
        }
        mTvContent.setText(Html.fromHtml(data.getReply()));
        mTvDate.setText(data.getAdd_time());
        mLlLike.setOnClickListener(v -> {
            ProductModel.getInstance().addAskLike(data.getAskReplyId()).unsafeSubscribe(new Subscriber<String>() {
                @Override
                public void onCompleted() {

                }

                @Override
                public void onError(Throwable e) {

                }

                @Override
                public void onNext(String s) {
                    int curCount = TextUtils.isDigitsOnly(mTvLike.getText())
                            ? Integer.valueOf(mTvLike.getText().toString()) : 0;
                    int result = curCount + (data.getIs_like() == 1 ? -1 : 1);
                    data.setIs_like(data.getIs_like() == 1 ? 2 : 1);
                    data.setLike_num(result);
                    setLike(data);
                }
            });
        });
        setLike(data);
    }

    private void setLike(Ask data) {
        mTvLike.setText(data.getLike_num() > 0 ? data.getLike_num() + "" : "点赞");
        mIvLike.setImageResource(data.getIs_like() == 1 ? R.mipmap.ic_like_pressed : R.mipmap.ic_like_normal);
    }

}
