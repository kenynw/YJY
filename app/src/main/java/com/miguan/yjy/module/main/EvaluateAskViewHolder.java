package com.miguan.yjy.module.main;

import android.text.Html;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.miguan.yjy.R;
import com.miguan.yjy.model.bean.Ask;
import com.miguan.yjy.model.bean.Evaluate;
import com.miguan.yjy.module.article.EvaluateCommendVH;
import com.miguan.yjy.module.ask.AskDetailActivityPresenter;

import java.util.List;

import butterknife.BindView;

/**
 * Copyright (c) 2017/7/25. LiaoPeiKun Inc. All rights reserved.
 */

public class EvaluateAskViewHolder extends EvaluateCommendVH {

    @BindView(R.id.tv_evaluate_ask_title)
    TextView mTvAskTitle;

    @BindView(R.id.tv_evaluate_ask_total)
    TextView mTvAskTotal;

    @BindView(R.id.dv_evaluate_ask_thumb)
    SimpleDraweeView mDvAskThumb;

    @BindView(R.id.ll_evaluate_ask)
    LinearLayout mLlAsk;

    private Ask mAsk;

    private OnLoadAskListener mLoadAskListener;

    public EvaluateAskViewHolder(ViewGroup parent, Ask ask) {
        super(parent, R.layout.item_list_evaluate_ask);
        mAsk = ask;
    }

    public EvaluateAskViewHolder(ViewGroup parent, OnLoadAskListener listener) {
        super(parent, R.layout.item_list_evaluate_ask);
        mLoadAskListener = listener;
    }

    @Override
    public void setData(Evaluate data) {
        super.setData(data);
        int curPage = getLayoutPosition() / 9 - 1;
        if (mLoadAskListener != null && mLoadAskListener.getAsks().size() > 0
                && mLoadAskListener.getAsks().size() > curPage) {
            Ask ask = mLoadAskListener.getAsks().get(curPage);
            mLlAsk.setVisibility(View.VISIBLE);
            mTvAskTitle.setText(ask.getSubject());
            String total = String.format(getContext().getString(R.string.text_home_ask_total), ask.getNum());
            mTvAskTotal.setText(Html.fromHtml(total));
            mDvAskThumb.setImageURI(ask.getProduct_img());
            mLlAsk.setOnClickListener(v -> AskDetailActivityPresenter.start(getContext(), ask.getProduct_id(), ask.getAskid()));
        } else {
            mLlAsk.setVisibility(View.GONE);
        }
    }

    public interface OnLoadAskListener {
        List<Ask> getAsks();
    }

}
