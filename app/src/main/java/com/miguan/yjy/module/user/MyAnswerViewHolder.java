package com.miguan.yjy.module.user;

import android.text.Html;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.miguan.yjy.R;
import com.miguan.yjy.model.bean.Ask;
import com.miguan.yjy.module.ask.AskDetailActivityPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.miguan.yjy.module.user.MyAskPresenter.TYPE_ASK;

/**
 * Copyright (c) 2017/9/1. LiaoPeiKun Inc. All rights reserved.
 */

public class MyAnswerViewHolder extends BaseViewHolder<Ask> {

    @BindView(R.id.dv_my_answer_thumb)
    SimpleDraweeView mDvThumb;

    @BindView(R.id.tv_my_answer_title)
    TextView mTvTitle;

    @BindView(R.id.tv_my_answer_desc)
    TextView mTvDesc;

    @BindView(R.id.tv_my_answer_time)
    TextView mTvTime;

    @BindView(R.id.iv_my_answer_more)
    ImageView mIvMore;

    private int mType;

    public MyAnswerViewHolder(ViewGroup parent, int type) {
        super(parent, R.layout.item_list_my_answer);
        ButterKnife.bind(this, itemView);
        setIsRecyclable(false);
        mType = type;
    }

    @Override
    public void setData(Ask data) {
        mDvThumb.setImageURI(data.getProduct_img());
        mTvTitle.setText(Html.fromHtml(data.getSubject()));
        if (mType == TYPE_ASK) {
            LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) mTvTime.getLayoutParams();
            lp.gravity = Gravity.NO_GRAVITY;
            mTvTime.setLayoutParams(lp);
            mTvTime.setText(data.getFormatTime("HH:mm") + " | 共" + data.getNum() + "条回答");
            mTvDesc.setVisibility(View.GONE);
            mIvMore.setVisibility(View.VISIBLE);
        } else {
            mTvDesc.setVisibility(View.VISIBLE);
            mTvDesc.setText(Html.fromHtml(data.getReply()));
            mTvTime.setText(data.getFormatTime("HH:mm"));
            mIvMore.setVisibility(View.GONE);
        }
        itemView.setOnClickListener(v -> AskDetailActivityPresenter.start(getContext(), data.getProduct_id(), data.getAskid()));
    }

}
