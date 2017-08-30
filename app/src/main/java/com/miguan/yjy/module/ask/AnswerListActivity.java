package com.miguan.yjy.module.ask;

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
 * Copyright (c) 2017/8/22. LiaoPeiKun Inc. All rights reserved.
 */
@RequiresPresenter(AnswerListPresenter.class)
public class AnswerListActivity extends BaseListActivity<AnswerListPresenter> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setToolbarTitle(R.string.text_ask_all);
    }

    @Override
    protected BaseViewHolder createViewHolder(ViewGroup parent, int viewType) {
        return new AskViewHolder(parent);
    }

    @Override
    public ListConfig getListConfig() {
        SpaceDecoration decoration = new SpaceDecoration(LUtils.dp2px(10));
        decoration.setPaddingEdgeSide(false);

        return super.getListConfig()
                .setItemDecoration(decoration)
                .setContainerLayoutRes(R.layout.common_activity_list)
                .setFooterNoMoreRes(R.layout.include_default_footer);
    }

}
