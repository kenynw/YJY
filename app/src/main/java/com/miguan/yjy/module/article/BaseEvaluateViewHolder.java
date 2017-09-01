package com.miguan.yjy.module.article;

import android.support.annotation.LayoutRes;
import android.text.Html;
import android.text.TextUtils;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.miguan.yjy.R;
import com.miguan.yjy.model.bean.Evaluate;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Copyright (c) 2017/4/1. LiaoPeiKun Inc. All rights reserved.
 */

public abstract class BaseEvaluateViewHolder extends BaseViewHolder<Evaluate> {

    @BindView(R.id.tv_evaluate_content)
    TextView mTvContent;

    public BaseEvaluateViewHolder(ViewGroup parent, @LayoutRes int res) {
        super(parent, res);
        ButterKnife.bind(this, itemView);
        mTvContent.setMaxLines(6);
        mTvContent.setEllipsize(TextUtils.TruncateAt.END);
    }

    @Override
    public void setData(Evaluate data) {
        mTvContent.setText(Html.fromHtml(data.getComment()));
        itemView.setOnClickListener(v -> EvaluateDetailPresenter.start(getContext(), data.getId()));
    }

}
