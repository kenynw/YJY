package com.miguan.yjy.adapter.viewholder;

import android.support.annotation.LayoutRes;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.TextView;

import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.miguan.yjy.R;
import com.miguan.yjy.model.bean.Evaluate;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Copyright (c) 2017/4/1. LiaoPeiKun Inc. All rights reserved.
 */

public class BaseEvaluateViewHolder extends BaseViewHolder<Evaluate> {

    private final int MAX_LINE_COUNT = 3;

    private final int STATE_UNKNOW = -1;

    private final int STATE_NOT_OVERFLOW = 1; //文本行数不超过限定行数

    private final int STATE_COLLAPSED = 2; //文本行数超过限定行数,处于折叠状态

    private final int STATE_EXPANDED = 3; //文本行数超过限定行数,被点击全文展开

    private SparseArray<Integer> mTextStateList;

    @BindView(R.id.tv_evaluate_content)
    TextView mTvContent;

    @BindView(R.id.tv_evaluate_collapse)
    TextView mTvCollapse;

    public BaseEvaluateViewHolder(ViewGroup parent, @LayoutRes int res) {
        super(parent, res);
        ButterKnife.bind(this, itemView);

        mTextStateList = new SparseArray<>();
        int state = mTextStateList.get(getAdapterPosition(), STATE_UNKNOW);
        if (state == STATE_UNKNOW) {
            mTvContent.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
                @Override
                public boolean onPreDraw() {
                    mTvContent.getViewTreeObserver().removeOnPreDrawListener(this);
                    if (mTvContent.getLineCount() > MAX_LINE_COUNT) {
                        mTvContent.setMaxLines(MAX_LINE_COUNT);
                        mTvCollapse.setVisibility(View.VISIBLE);
                        mTvCollapse.setText("查看全文");
                        mTextStateList.put(getAdapterPosition(), STATE_COLLAPSED);
                    } else {
                        mTvCollapse.setVisibility(View.GONE);
                        mTextStateList.put(getAdapterPosition(), STATE_NOT_OVERFLOW);
                    }
                    return true;
                }
            });
        } else {
            switch (state) {
                case STATE_NOT_OVERFLOW :
                    mTvCollapse.setVisibility(View.GONE);
                    break;
                case STATE_COLLAPSED :
                    mTvContent.setMaxLines(MAX_LINE_COUNT);
                    mTvCollapse.setVisibility(View.VISIBLE);
                    mTvCollapse.setText("查看全文");
                    break;
                case STATE_EXPANDED :
                    mTvContent.setMaxLines(MAX_LINE_COUNT);
                    mTvCollapse.setVisibility(View.VISIBLE);
                    mTvCollapse.setText("收起全文");
                    break;
            }
        }
        mTvCollapse.setOnClickListener(v -> {
            switch (mTextStateList.get(getAdapterPosition())) {
                case STATE_COLLAPSED :
                    mTextStateList.put(getAdapterPosition(), STATE_EXPANDED);
                    mTvCollapse.setText("收起全文");
                    mTvContent.setMaxLines(Integer.MAX_VALUE);
                    break;
                case STATE_EXPANDED :
                    mTextStateList.put(getAdapterPosition(), STATE_COLLAPSED);
                    mTvCollapse.setText("查看全文");
                    mTvContent.setMaxLines(MAX_LINE_COUNT);
            }
        });
    }

    @Override
    public void setData(Evaluate data) {
        mTvContent.setText(data.getComment());
    }
}
