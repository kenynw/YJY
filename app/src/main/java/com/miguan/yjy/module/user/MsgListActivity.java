package com.miguan.yjy.module.user;

import android.os.Bundle;
import android.view.ViewGroup;

import com.dsk.chain.bijection.RequiresPresenter;
import com.dsk.chain.expansion.list.BaseListActivity;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.miguan.yjy.R;
import com.miguan.yjy.adapter.viewholder.MessageViewHolder;

/**
 * Copyright (c) 2017/4/1. LiaoPeiKun Inc. All rights reserved.
 */
@RequiresPresenter(MsgListPresenter.class)
public class MsgListActivity extends BaseListActivity<MsgListPresenter> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setToolbarTitle(R.string.btn_me_message);
    }

    @Override
    protected int getLayout() {
        return R.layout.common_activity_list;
    }

    @Override
    protected BaseViewHolder createViewHolder(ViewGroup parent, int viewType) {
        return new MessageViewHolder(parent);
    }

}
