package com.miguan.yjy.module.user;

import android.os.Bundle;
import android.view.ViewGroup;

import com.dsk.chain.bijection.RequiresPresenter;
import com.dsk.chain.expansion.list.BaseListActivity;
import com.dsk.chain.expansion.list.ListConfig;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.miguan.yjy.R;
import com.miguan.yjy.module.ask.AskDiscoverViewHolder;

/**
 * Copyright (c) 2017/8/25. LiaoPeiKun Inc. All rights reserved.
 */
@RequiresPresenter(MyAskPresenter.class)
public class MyAskActivity extends BaseListActivity<MyAskPresenter> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setToolbarTitle(R.string.btn_me_ask);
    }

    @Override
    protected BaseViewHolder createViewHolder(ViewGroup parent, int viewType) {
        return new AskDiscoverViewHolder(parent);
    }

    @Override
    public ListConfig getListConfig() {
        return super.getListConfig()
                .setFooterNoMoreRes(R.layout.include_default_footer)
                .setContainerLayoutRes(R.layout.user_activity_ask);
    }

}
