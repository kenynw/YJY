package com.miguan.yjy.module.test;

import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.miguan.yjy.R;
import com.miguan.yjy.model.bean.Wiki;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 *
 * 护肤科普。发现和护肤科普列表用到
 *
 * Copyright (c) 2017/8/22. LiaoPeiKun Inc. All rights reserved.
 */

public class WikiViewHolder extends BaseViewHolder<Wiki> {

    @BindView(R.id.dv_wiki_thumb)
    SimpleDraweeView mDvThumb;

    @BindView(R.id.tv_wiki_title)
    TextView mTvTitle;

    @BindView(R.id.tv_wiki_desc)
    TextView mTvDesc;

    public WikiViewHolder(ViewGroup parent) {
        super(parent, R.layout.item_list_wiki);
        ButterKnife.bind(this, itemView);
    }

    @Override
    public void setData(Wiki data) {
        mTvTitle.setText(data.getQuestion());
        mTvDesc.setText(data.getContent());
        if (TextUtils.isEmpty(data.getPicture())) {
            mDvThumb.setVisibility(View.GONE);
        } else {
            mDvThumb.setVisibility(View.VISIBLE);
            mDvThumb.setImageURI(data.getPicture());
        }
        itemView.setOnClickListener(v->WikiAskActivityPresenter.start(getContext(),data.getId()) );
    }

}
