package com.miguan.yjy.module.question;

import android.graphics.Paint;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.miguan.yjy.R;
import com.miguan.yjy.model.bean.Ask;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Copyright (c) 2017/6/26. LiaoPeiKun Inc. All rights reserved.
 */

public class AskViewHolder extends BaseViewHolder<Ask> {

    @BindView(R.id.tv_ask_title)
    TextView mTvTitle;

    @BindView(R.id.tv_ask_content)
    TextView mTvContent;

    @BindView(R.id.tv_ask_view_more)
    TextView mTvViewMore;

    @BindView(R.id.tv_ask_date)
    TextView mTvDate;

    @BindView(R.id.ll_ask_content)
    LinearLayout mLlAskContent;

    @BindView(R.id.ll_ask_null)
    LinearLayout mLlAskNull;

    public AskViewHolder(ViewGroup parent) {
        super(parent, R.layout.item_list_ask);
        ButterKnife.bind(this, itemView);
        mTvViewMore.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
        mTvViewMore.getPaint().setAntiAlias(true);
    }

    @Override
    public void setData(Ask data) {
        if (TextUtils.isEmpty(data.getReply())) {
            mLlAskContent.setVisibility(View.GONE);
            mLlAskNull.setVisibility(View.VISIBLE);
        } else {
            mLlAskContent.setVisibility(View.VISIBLE);
            mLlAskNull.setVisibility(View.GONE);
            mTvContent.setText(data.getReply());
        }
        mTvTitle.setText(data.getSubject());
        mTvDate.setText(data.getAdd_time());
        mTvViewMore.setText(String.format(getContext().getString(R.string.text_view_more_answer), data.getNum()));
    }

}
