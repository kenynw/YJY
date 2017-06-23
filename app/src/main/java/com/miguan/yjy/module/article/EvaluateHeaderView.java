package com.miguan.yjy.module.article;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.miguan.yjy.R;
import com.miguan.yjy.model.bean.Evaluate;
import com.miguan.yjy.utils.LUtils;

/**
 * Copyright (c) 2017/6/22. LiaoPeiKun Inc. All rights reserved.
 */

public class EvaluateHeaderView implements RecyclerArrayAdapter.ItemView {

    private Context mContext;

    private Evaluate mEvaluate;

    private EvaluatePanel mEvaluatePanel;

    public EvaluateHeaderView(Context context, Evaluate evaluate) {
        mContext = context;
        mEvaluate = evaluate;
    }

    @Override
    public View onCreateView(ViewGroup parent) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_list_evaluate, null);
        mEvaluatePanel = new EvaluatePanel(mContext, view);
        return view;
    }

    @Override
    public void onBindView(View headerView) {
        mEvaluatePanel.setEvaluate(mEvaluate);
        LUtils.log("onBindView");
    }

}
