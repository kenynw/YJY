package com.miguan.yjy.module.question;

import android.view.ViewGroup;
import android.widget.TextView;

import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.miguan.yjy.R;
import com.miguan.yjy.model.bean.Ask;
import com.miguan.yjy.widget.SuperTextView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Copyright (c) 2017/6/23. LiaoPeiKun Inc. All rights reserved.
 */

public class AskTitleViewHolder extends BaseViewHolder<Ask> {

    @BindView(R.id.tv_question_title)
    TextView mTvTitle;

    @BindView(R.id.btn_question_to_ask)
    SuperTextView mBtnToAsk;

    public AskTitleViewHolder(ViewGroup parent) {
        super(parent, R.layout.item_list_ask_title);
        ButterKnife.bind(this, itemView);
    }

    @Override
    public void setData(Ask data) {
        mTvTitle.setText(data.getSubject());
    }

}
