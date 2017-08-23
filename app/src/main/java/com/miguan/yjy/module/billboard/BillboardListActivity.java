package com.miguan.yjy.module.billboard;

import android.os.Bundle;
import android.view.ViewGroup;

import com.dsk.chain.bijection.RequiresPresenter;
import com.dsk.chain.expansion.list.BaseListActivity;
import com.dsk.chain.expansion.list.ListConfig;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.miguan.yjy.R;

/**
 * Copyright (c) 2017/8/23. LiaoPeiKun Inc. All rights reserved.
 */
@RequiresPresenter(BillboardListPresenter.class)
public class BillboardListActivity extends BaseListActivity<BillboardListPresenter> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setToolbarTitle(R.string.text_billboard);
    }

    @Override
    protected BaseViewHolder createViewHolder(ViewGroup parent, int viewType) {
        return new BillboardViewHolder(parent);
    }

    @Override
    public ListConfig getListConfig() {
        return super.getListConfig()
                .setContainerLayoutRes(R.layout.common_activity_list)
                .setFooterNoMoreRes(R.layout.include_default_footer);
    }

}
