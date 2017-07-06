package com.miguan.yjy.module.user;

import android.text.Html;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.miguan.yjy.R;
import com.miguan.yjy.model.bean.Message;
import com.miguan.yjy.module.article.ArticleDetailPresenter;
import com.miguan.yjy.module.article.EvaluateDetailPresenter;
import com.miguan.yjy.module.ask.AskDetailActivityPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Copyright (c) 2017/6/21. LiaoPeiKun Inc. All rights reserved.
 */

public class ReplyViewHolder extends BaseViewHolder<Message> {

    @BindView(R.id.tv_reply_date)
    TextView mTvDate;

    @BindView(R.id.tv_reply_content)
    TextView mTvContent;

    public ReplyViewHolder(ViewGroup parent) {
        super(parent, R.layout.item_list_reply);
        ButterKnife.bind(this, itemView);
    }

    @Override
    public void setData(Message data) {
        mTvDate.setText(data.getCreated_at());
        String content = data.getContent();
        switch (data.getType()) {
            case 1:
                content = "回复了产品 <b>" + content + "</b> 的评论";
                itemView.setOnClickListener(v -> EvaluateDetailPresenter.start(getContext(), data.getId()));
                break;
            case 2:
                content = "回复了文章 <b>《" + content + "</b>》的评论";
                itemView.setOnClickListener(v -> ArticleDetailPresenter.start(getContext(), data.getId()));
                break;
            case 3:
                content = "回答了产品 <b> " + content + "</b> 的提问";
                itemView.setOnClickListener(v -> {
                    if (data.getProduct_id() > 0)
                        AskDetailActivityPresenter.start(getContext(), data.getProduct_id(), data.getId());
                });
                break;
        }
        mTvContent.setText(Html.fromHtml(content));
    }

}
