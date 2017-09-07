package com.miguan.yjy.module.product;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.dsk.chain.expansion.list.BaseListActivityPresenter;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.miguan.yjy.adapter.viewholder.BrandViewHolder;
import com.miguan.yjy.model.ProductModel;
import com.miguan.yjy.model.bean.Brand;

import java.util.List;

/**
 * Copyright (c) 2017/3/28. LiaoPeiKun Inc. All rights reserved.
 */

public class BrandListPresenter extends BaseListActivityPresenter<BrandListActivity, Brand>
        implements BrandViewHolder.OnBrandDeleteListener, RecyclerArrayAdapter.OnItemClickListener {

    public static final String EXTRA_SHOW_ALL = "show_all";

    public static final String EXTRA_CAN_ADD = "can_add";

    private List<Brand> mBrandList;

    private boolean mShowAll;

    private boolean mCanAdd;

    public static void start(Activity context, int requestCode, boolean showAll, boolean canAdd) {
        Intent intent = new Intent(context, BrandListActivity.class);
        intent.putExtra(EXTRA_SHOW_ALL, showAll);
        intent.putExtra(EXTRA_CAN_ADD, canAdd);
        context.startActivityForResult(intent, requestCode);
    }

    @Override
    protected void onCreate(BrandListActivity view, Bundle saveState) {
        super.onCreate(view, saveState);
        mShowAll = getView().getIntent().getBooleanExtra(EXTRA_SHOW_ALL, true);
        mCanAdd = getView().getIntent().getBooleanExtra(EXTRA_CAN_ADD, true);
    }

    @Override
    protected void onCreateView(BrandListActivity view) {
        super.onCreateView(view);
        getAdapter().setOnItemClickListener(this);
        onRefresh();
    }

    @Override
    public void onRefresh() {
        ProductModel.getInstance().getBrandList(mShowAll)
                .map(brandList -> {
                    mBrandList = brandList.getCosmeticsList();
                    return brandList.getCosmeticsList();
                })
                .unsafeSubscribe(getRefreshSubscriber());
    }

    public void insertBrand(Brand brand) {
        ProductModel.getInstance().insertBrand(brand);
    }

    public List<Brand> getBrandList() {
        return mBrandList;
    }

    @Override
    public void onBrandDelete(Brand brand) {
        ProductModel.getInstance().deleteBrand(brand);
        getAdapter().remove(brand);
    }

    @Override
    public void onItemClick(int position) {
        Brand brand = getItem(position);
        if (mShowAll && !mCanAdd) {
            BrandMainPresenter.star(getView(), brand.getId());
        } else {
            getView().selectedFinish(brand);
        }
    }

}
