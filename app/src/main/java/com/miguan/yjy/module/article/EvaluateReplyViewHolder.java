package com.miguan.yjy.module.article;

import android.text.Html;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.miguan.yjy.R;
import com.miguan.yjy.model.bean.Evaluate;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Copyright (c) 2017/6/21. LiaoPeiKun Inc. All rights reserved.
 */

public class EvaluateReplyViewHolder extends BaseViewHolder<Evaluate> {

    @BindView(R.id.tv_evaluate_date)
    TextView mTvDate;

    @BindView(R.id.tv_evaluate_content)
    TextView mTvContent;

    private EvaluatePanel mEvaluatePanel;

    public EvaluateReplyViewHolder(ViewGroup parent) {
        super(parent, R.layout.item_list_evaluate_reply);
        ButterKnife.bind(this, itemView);

        if (mEvaluatePanel == null) {
            mEvaluatePanel = new EvaluatePanel(getContext(), itemView);
        }
    }

    @Override
    public void setData(Evaluate data) {
        mEvaluatePanel.setEvaluate(data);
        mTvDate.setText(data.getCreated_at());
        if (data.getLevel() == 2) {
            String replyTo = getContext().getString(R.string.text_evaluate_reply_to);
            String content = String.format(replyTo, data.getReplyUserName(), data.getComment());
            mTvContent.setText(Html.fromHtml(content));
        } else {
            mTvContent.setText(data.getComment());
        }
    }

}
