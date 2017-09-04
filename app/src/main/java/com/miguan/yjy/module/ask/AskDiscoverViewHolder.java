package com.miguan.yjy.module.ask;

import android.text.Html;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.miguan.yjy.R;
import com.miguan.yjy.model.bean.Ask;
import com.miguan.yjy.widget.SuperTextView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 发现页面最新问答列表
 * <p>
 * Copyright (c) 2017/8/22. LiaoPeiKun Inc. All rights reserved.
 */

public class AskDiscoverViewHolder extends BaseViewHolder<Ask> {

    @BindView(R.id.dv_discover_ask_thumb)
    SimpleDraweeView mDvThumb;

    @BindView(R.id.tv_discover_label)
    SuperTextView mTvLabel;

    @BindView(R.id.tv_discover_ask_title)
    TextView mTvTitle;

    @BindView(R.id.tv_discover_ask_total)
    TextView mTvTotal;

    public AskDiscoverViewHolder(ViewGroup parent) {
        super(parent, R.layout.item_list_ask_discover);
        ButterKnife.bind(this, itemView);
    }

    @Override
    public void setData(Ask ask) {
        mTvTitle.setText(Html.fromHtml(ask.getSubject()));
        String total = String.format(getContext().getString(R.string.text_home_ask_total), ask.getNum());
        mTvTotal.setText(Html.fromHtml(total));
        mDvThumb.setImageURI(ask.getProduct_img());
        itemView.setOnClickListener(v -> AskDetailActivityPresenter.start(getContext(), ask.getProduct_id(), ask.getAskid()));
    }

}
