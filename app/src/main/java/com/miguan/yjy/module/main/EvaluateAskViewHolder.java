package com.miguan.yjy.module.main;

import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.miguan.yjy.R;
import com.miguan.yjy.model.bean.Ask;
import com.miguan.yjy.model.bean.Evaluate;
import com.miguan.yjy.module.article.EvaluateCommendVH;

import java.util.ArrayList;

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

    private OnLoadAskListener mDataListener;

    public EvaluateAskViewHolder(ViewGroup parent, OnLoadAskListener listener) {
        super(parent, R.layout.item_list_evaluate_ask);
        mDataListener = listener;
    }

    @Override
    public void setData(Evaluate data) {
        super.setData(data);
        if (getAdapterPosition() % 10 > 0 && getAdapterPosition() % 10 < mDataListener.getAskList().size()) {
            Ask ask = mDataListener.getAskList().get(getAdapterPosition() % 10);
            mTvAskTitle.setText(ask.getSubject());
            mTvAskTotal.setText(String.format(getContext().getString(R.string.text_home_ask_total), ask.getNum()));
//            mDvAskThumb.setImageURI(ask.getProduct_img());
        }
    }

    public interface OnLoadAskListener {
        ArrayList<Ask> getAskList();
    }

}
