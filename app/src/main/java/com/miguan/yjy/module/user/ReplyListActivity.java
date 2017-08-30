package com.miguan.yjy.module.user;

import android.os.Bundle;
import android.view.ViewGroup;

import com.dsk.chain.bijection.RequiresPresenter;
import com.dsk.chain.expansion.list.BaseListActivity;
import com.dsk.chain.expansion.list.ListConfig;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.miguan.yjy.R;

/**
 * Copyright (c) 2017/6/21. LiaoPeiKun Inc. All rights reserved.
 */
@RequiresPresenter(ReplyListActivityPresenter.class)
public class ReplyListActivity extends BaseListActivity<ReplyListActivityPresenter> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setToolbarTitle(R.string.btn_me_reply);
    }

    @Override
    protected BaseViewHolder createViewHolder(ViewGroup parent, int viewType) {
        return new ReplyViewHolder(parent);
    }

    @Override
    protected int getLayout() {
        return R.layout.common_activity_list;
    }

    @Override
    public ListConfig getListConfig() {
        return super.getListConfig()
                .setContainerLayoutRes(R.layout.common_activity_list)
                .setFooterNoMoreRes(R.layout.include_default_footer);
    }
}
