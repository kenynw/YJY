package com.miguan.yjy.module.user;

import android.os.Bundle;
import android.view.ViewGroup;

import com.dsk.chain.bijection.RequiresPresenter;
import com.dsk.chain.expansion.list.BaseListActivity;
import com.dsk.chain.expansion.list.ListConfig;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.decoration.SpaceDecoration;
import com.miguan.yjy.R;
import com.miguan.yjy.adapter.viewholder.EvaluateMeViewHolder;
import com.miguan.yjy.utils.LUtils;

/**
 * Copyright (c) 2017/4/1. LiaoPeiKun Inc. All rights reserved.
 */
@RequiresPresenter(EvaluateListPresenter.class)
public class EvaluateListActivity extends BaseListActivity<EvaluateListPresenter> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setToolbarTitle(R.string.btn_me_evaluate);
        getListView().showProgress();
    }

    @Override
    protected BaseViewHolder createViewHolder(ViewGroup parent, int viewType) {
        return new EvaluateMeViewHolder(parent);
    }

    @Override
    public ListConfig getListConfig() {
        SpaceDecoration spaceDecoration = new SpaceDecoration(LUtils.dp2px(10));
        spaceDecoration.setPaddingEdgeSide(false);
        spaceDecoration.setPaddingStart(false);
        return super.getListConfig()
                .setItemDecoration(spaceDecoration)
                .setContainerLayoutRes(R.layout.common_activity_list);
    }
}
