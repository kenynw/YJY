package com.miguan.yjy.module.user;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.dsk.chain.bijection.RequiresPresenter;
import com.dsk.chain.expansion.list.BaseListActivity;
import com.dsk.chain.expansion.list.ListConfig;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.decoration.DividerDecoration;
import com.miguan.yjy.R;
import com.miguan.yjy.utils.LUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.view.View.OVER_SCROLL_NEVER;

/**
 * Copyright (c) 2017/9/21. LiaoPeiKun Inc. All rights reserved.
 */
@RequiresPresenter(BillDetailPresenter.class)
public class BillDetailActivity extends BaseListActivity<BillDetailPresenter> {

    @BindView(R.id.tv_bill_detail_count)
    TextView mTvCount;

    @BindView(R.id.fl_bill_detail_export)
    FrameLayout mFlExport;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getListView().setBackgroundColor(getResources().getColor(R.color.white));
        getListView().getRecyclerView().setOverScrollMode(OVER_SCROLL_NEVER);
        ButterKnife.bind(this);
        mFlExport.setOnClickListener(v -> getPresenter().exportToImage());
    }

    @Override
    protected BaseViewHolder createViewHolder(ViewGroup parent, int viewType) {
        return new BillProductViewHolder(parent, getPresenter());
    }

    @Override
    public ListConfig getListConfig() {
        View view = LayoutInflater.from(this).inflate(R.layout.empty_common_list, null);
        TextView tv = view.findViewById(R.id.tv_empty);
        tv.setText("还没有添加在用的产品哦~");

        Drawable drawable = getResources().getDrawable(R.mipmap.ic_empty_bill);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        tv.setCompoundDrawables(null, drawable, null, null);

        return super.getListConfig()
                .setItemDecoration(getDividerDecoration())
                .setNoMoreAble(false)
                .setRefreshAble(false)
                .setLoadMoreAble(false)
                .setFooterErrorAble(false)
                .setContainerEmptyView(view)
                .setContainerLayoutRes(R.layout.user_activity_bill_detail);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_edit, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        getPresenter().setEditMode(!getPresenter().isEditMode());
        getPresenter().getAdapter().notifyDataSetChanged();
        item.setTitle(getPresenter().isEditMode() ? "完成" : "编辑");
        mFlExport.setVisibility(getPresenter().isEditMode() ? View.GONE : View.VISIBLE);
        if (!getPresenter().isEditMode()) getPresenter().updateSort();
        return super.onOptionsItemSelected(item);
    }

    public DividerDecoration getDividerDecoration() {
        DividerDecoration decoration = new DividerDecoration(getResources().getColor(R.color.white), LUtils.dp2px(10));
        decoration.setDrawLastItem(false);
        decoration.setDrawHeaderFooter(false);
        return decoration;
    }

    public void setCount(int count) {
        mTvCount.setText("共" + count + "款产品");
        mFlExport.setVisibility(count > 0 ? View.VISIBLE : View.GONE);
    }

}
