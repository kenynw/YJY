package com.miguan.yjy.module.user;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dsk.chain.bijection.RequiresPresenter;
import com.dsk.chain.expansion.list.BaseListActivity;
import com.dsk.chain.expansion.list.ListConfig;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.decoration.SpaceDecoration;
import com.miguan.yjy.R;
import com.miguan.yjy.adapter.viewholder.StarViewHolder;

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
        View view = LayoutInflater.from(this).inflate(R.layout.empty_componet_list, null);
        TextView tv = (TextView) view.findViewById(R.id.tv_empty);
        tv.setText("还没有收藏过任何产品哦~");

        Drawable drawable = getResources().getDrawable(R.mipmap.ic_empty_star);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        tv.setCompoundDrawables(null, drawable, null, null);

        return super.getListConfig()
                .setContainerEmptyView(view)
                .setItemDecoration(new SpaceDecoration((int) getResources().getDimension(R.dimen.spacing_small)))
                .setContainerLayoutRes(R.layout.common_activity_list);
    }

}
