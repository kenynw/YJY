package com.miguan.yjy.module.product;

import android.os.Bundle;
import android.view.ViewGroup;

import com.dsk.chain.bijection.RequiresPresenter;
import com.dsk.chain.expansion.list.BaseListActivity;
import com.dsk.chain.expansion.list.ListConfig;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.decoration.SpaceDecoration;
import com.miguan.yjy.R;
import com.miguan.yjy.utils.LUtils;

/**
 * Copyright (c) 2017/8/28. LiaoPeiKun Inc. All rights reserved.
 */
@RequiresPresenter(BenefitListPresenter.class)
public class BenefitListActivity extends BaseListActivity<BenefitListPresenter> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setToolbarTitle(R.string.text_home_benefits);
    }

    @Override
    protected BaseViewHolder createViewHolder(ViewGroup parent, int viewType) {
        return new BenefitViewHolder(parent);
    }

    @Override
    public ListConfig getListConfig() {
        SpaceDecoration decoration = new SpaceDecoration(LUtils.dp2px(15));
        decoration.setPaddingEdgeSide(false);
        decoration.setPaddingStart(false);
        return super.getListConfig()
                .setItemDecoration(decoration)
                .setContainerLayoutRes(R.layout.common_activity_list)
                .setFooterNoMoreRes(R.layout.include_default_footer);
    }

}
