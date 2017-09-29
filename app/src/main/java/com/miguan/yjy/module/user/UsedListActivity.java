package com.miguan.yjy.module.user;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.dsk.chain.bijection.RequiresPresenter;
import com.dsk.chain.expansion.list.BaseListActivity;
import com.dsk.chain.expansion.list.ListConfig;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.miguan.yjy.R;
import com.miguan.yjy.adapter.viewholder.UsedViewHolder;
import com.miguan.yjy.dialogs.ShareImageDialog;
import com.miguan.yjy.model.bean.UserProduct;
import com.miguan.yjy.model.local.UserPreferences;
import com.miguan.yjy.module.product.AddRepositoryActivity;
import com.miguan.yjy.utils.LUtils;
import com.miguan.yjy.utils.ScreenShot;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Copyright (c) 2017/3/30. LiaoPeiKun Inc. All rights reserved.
 */
@RequiresPresenter(UsedListPresenter.class)
public class UsedListActivity extends BaseListActivity<UsedListPresenter> {

    @BindView(R.id.cb_used_sort)
    CheckBox mCbSort;

    @BindView(R.id.fl_used_add)
    FrameLayout mFlAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setToolbarTitle(R.string.btn_me_used);
        ButterKnife.bind(this);

        mCbSort.setOnCheckedChangeListener(getPresenter());
        mFlAdd.setOnClickListener(v -> startActivity(new Intent(this, AddRepositoryActivity.class)));
    }

    @Override
    protected BaseViewHolder createViewHolder(ViewGroup parent, int viewType) {
        return new UsedViewHolder(parent);
    }

    @Override
    public ListConfig getListConfig() {
        View view = LayoutInflater.from(this).inflate(R.layout.empty_common_list, null);
        TextView tv = view.findViewById(R.id.tv_empty);
        tv.setText("还没有添加在用的产品哦~");

        Drawable drawable = getResources().getDrawable(R.mipmap.ic_empty_used);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        tv.setCompoundDrawables(null, drawable, null, null);

        return super.getListConfig()
                .setContainerLayoutRes(R.layout.user_activity_used)
                .setFooterNoMoreRes(R.layout.include_default_footer)
                .setContainerEmptyView(view);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        List<UserProduct> allData = getPresenter().getAdapter().getAllData();
        if (allData != null && allData.size() > 0) {
            getExpansionDelegate().showProgressBar("正在保存图片");
            View view = View.inflate(this, R.layout.user_export_used, null);
            TextView tvUsername = view.findViewById(R.id.tv_export_used_username);
            tvUsername.setText("by " + UserPreferences.getUsername() + " from 颜究院");
            RecyclerView rv = view.findViewById(R.id.rv_export_used_list);
            rv.setLayoutManager(new LinearLayoutManager(this));
            rv.setHasFixedSize(true);
            RecyclerArrayAdapter<UserProduct> adapter = new RecyclerArrayAdapter<UserProduct>(this) {
                @Override
                public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
                    return new UsedExportViewHolder(parent);
                }
            };
            rv.setAdapter(adapter);
            adapter.addAll(allData);
            ScreenShot.getInstance().takeScreenShotOfJustView(view, Bitmap.Config.ARGB_4444, (path, uri) -> {
                getExpansionDelegate().hideProgressBar();
                ShareImageDialog.newInstance(path, UsedListActivity.this).show(getSupportFragmentManager(), "");
            });
        } else {
            LUtils.toast("列表为空~");
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_export, menu);
        return super.onCreateOptionsMenu(menu);
    }

}
