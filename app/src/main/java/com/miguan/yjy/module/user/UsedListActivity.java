package com.miguan.yjy.module.user;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dsk.chain.bijection.RequiresPresenter;
import com.dsk.chain.expansion.list.BaseListActivity;
import com.dsk.chain.expansion.list.ListConfig;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.miguan.yjy.R;
import com.miguan.yjy.adapter.viewholder.UsedViewHolder;
import com.miguan.yjy.module.product.AddRepositoryActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Copyright (c) 2017/3/30. LiaoPeiKun Inc. All rights reserved.
 */
@RequiresPresenter(UsedListPresenter.class)
public class UsedListActivity extends BaseListActivity<UsedListPresenter> {

    private final String[] TITLES = new String[] {"全部", "已开封", "未开封", "已过期"};

    @BindView(R.id.tab_used_type)
    TabLayout mTabType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setToolbarTitle(R.string.btn_me_used);
        ButterKnife.bind(this);

        mTabType.addOnTabSelectedListener(getPresenter());
        for (String s : TITLES) {
            mTabType.addTab(mTabType.newTab().setText(s));
        }
    }

    @Override
    protected BaseViewHolder createViewHolder(ViewGroup parent, int viewType) {
        return new UsedViewHolder(parent);
    }

    @Override
    protected int getLayout() {
        return R.layout.user_activity_used;
    }

    @Override
    public ListConfig getListConfig() {
        View view = LayoutInflater.from(this).inflate(R.layout.empty_common_list, null);
        TextView tv = view.findViewById(R.id.tv_empty);
        tv.setText("还没有添加在用的产品哦~");

        Drawable drawable = getResources().getDrawable(R.mipmap.ic_empty_used);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        tv.setCompoundDrawables(null, drawable, null, null);

        return super.getListConfig().setContainerEmptyView(view);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        startActivity(new Intent(this, AddRepositoryActivity.class));
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add, menu);
        return super.onCreateOptionsMenu(menu);
    }

}
