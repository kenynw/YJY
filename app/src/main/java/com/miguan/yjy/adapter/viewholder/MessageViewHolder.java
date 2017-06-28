package com.miguan.yjy.adapter.viewholder;

import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.miguan.yjy.R;
import com.miguan.yjy.model.bean.Message;
import com.miguan.yjy.module.article.ArticleDetailPresenter;
import com.miguan.yjy.module.ask.AskDetailActivityPresenter;
import com.miguan.yjy.module.product.ProductDetailPresenter;
import com.miguan.yjy.utils.SpanUtils;
import com.miguan.yjy.widget.SuperTextView;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Copyright (c) 2017/4/1. LiaoPeiKun Inc. All rights reserved.
 */

public class MessageViewHolder extends BaseViewHolder<Message> {

    @BindView(R.id.dv_message_avatar)
    SimpleDraweeView mDvAvatar;

    @BindView(R.id.tv_message_name)
    TextView mTvName;

    @BindView(R.id.tv_message_time)
    TextView mTvTime;

    @BindView(R.id.tv_message_content)
    TextView mTvContent;

    @BindView(R.id.iv_message_more)
    ImageView mIvMore;

    @BindView(R.id.tv_message_ask_label)
    TextView mTvAskLabel;

    @BindView(R.id.tv_message_ask)
    SuperTextView mTvAsk;

    @BindView(R.id.ll_message_content)
    LinearLayout mLlContent;

    public MessageViewHolder(ViewGroup parent) {
        super(parent, R.layout.item_list_message);
        ButterKnife.bind(this, itemView);
    }

    @Override
    public void setData(Message data) {
        mDvAvatar.setImageURI(Uri.parse(data.getImg()));
        mTvName.setText(data.getUser_name());
        mTvTime.setText(data.getCreated_at());
        itemView.setOnClickListener(null);
        mIvMore.setVisibility(data.getType() == 2 ? View.INVISIBLE : View.VISIBLE);

        if (data.getType() == 1) {
            mLlContent.setVisibility(View.GONE);
            mTvTime.append(" 赞了你的评论");
        } else {
            mLlContent.setVisibility(View.VISIBLE);
            mTvContent.setText(SpanUtils.getContentSpannable(data.getContent(), mTvContent));
        }

        if (data.getRelation_id() > 0) {
            itemView.setOnClickListener(v -> {
                if (data.getType() == 3) {
                    AskDetailActivityPresenter.start(getContext(), data.getRelation_id(), data.getAskid());
                } else if (data.getOtype() == 1) {
                    ProductDetailPresenter.start(getContext(), data.getRelation_id());
                } else if (data.getOtype() == 2) {
                    ArticleDetailPresenter.start(getContext(), data.getRelation_id());
                }
            });
        }
        mTvAskLabel.setVisibility(data.getType() == 3 ? View.VISIBLE : View.GONE);
        mTvAsk.setVisibility(data.getType() == 3 ? View.VISIBLE : View.GONE);
    }

}
