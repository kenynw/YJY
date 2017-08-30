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
    public ListConfig getListConfig() {
        View view = LayoutInflater.from(this).inflate(R.layout.empty_common_list, null);
        TextView tv = view.findViewById(R.id.tv_empty);
        tv.setText("还没收到任何消息哦~");

        Drawable drawable = getResources().getDrawable(R.mipmap.ic_empty_message);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        tv.setCompoundDrawables(null, drawable, null, null);

        return super.getListConfig().setContainerEmptyView(view)
                .setFooterNoMoreRes(R.layout.include_default_footer)
                .setContainerLayoutRes(R.layout.common_activity_list);
    }

    @Override
    protected BaseViewHolder createViewHolder(ViewGroup parent, int viewType) {
        return new MessageViewHolder(parent);
    }

}
