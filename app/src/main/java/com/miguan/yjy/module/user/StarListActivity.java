package com.miguan.yjy.module.user;

import android.os.Bundle;
import android.view.ViewGroup;

import com.dsk.chain.bijection.RequiresPresenter;
import com.dsk.chain.expansion.list.BaseListActivity;
import com.dsk.chain.expansion.list.ListConfig;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.decoration.SpaceDecoration;
import com.miguan.yjy.R;

/**
 * Copyright (c) 2017/3/30. LiaoPeiKun Inc. All rights reserved.
 */
@RequiresPresenter(StarListPresenter.class)
public class StarListActivity extends BaseListActivity<StarListPresenter> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setToolbarTitle(R.string.text_me_star);
    }

    @Override
    protected BaseViewHolder createViewHolder(ViewGroup parent, int viewType) {
        return new StarViewHolder(parent);
    }

    @Override
    public ListConfig getListConfig() {
        return super.getListConfig()
                .setItemDecoration(new SpaceDecoration((int) getResources().getDimension(R.dimen.spacing_small)))
                .setContainerLayoutRes(R.layout.common_activity_list);
    }

}
