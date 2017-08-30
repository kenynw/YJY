package com.miguan.yjy.module.billboard;

import android.os.Bundle;
import android.view.ViewGroup;

import com.dsk.chain.bijection.RequiresPresenter;
import com.dsk.chain.expansion.list.BaseListActivity;
import com.dsk.chain.expansion.list.ListConfig;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.decoration.DividerDecoration;
import com.miguan.yjy.R;
import com.miguan.yjy.utils.LUtils;

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
        DividerDecoration decoration = new DividerDecoration(getResources().getColor(R.color.bgWindow), LUtils.dp2px(10));

        return super.getListConfig()
                .setItemDecoration(decoration)
                .setContainerLayoutRes(R.layout.common_activity_list)
                .setFooterNoMoreRes(R.layout.include_default_footer);
    }

}
