package com.miguan.yjy.module.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dsk.chain.bijection.RequiresPresenter;
import com.dsk.chain.expansion.data.BaseDataFragment;
import com.jude.exgridview.ExGridView;
import com.miguan.yjy.R;
import com.miguan.yjy.adapter.CategoryAdapter;
import com.miguan.yjy.model.bean.MainProduct;
import com.miguan.yjy.module.billboard.BillboardListActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Copyright (c) 2017/8/22. LiaoPeiKun Inc. All rights reserved.
 */
@RequiresPresenter(MainProductPresenter.class)
public class MainProductFragment extends BaseDataFragment<MainProductPresenter, MainProduct> {

    @BindView(R.id.rv_main_product_cate)
    ExGridView mCategory;

    @BindView(R.id.tv_main_product_count)
    TextView mTvCount;

    @BindView(R.id.rv_main_product_brand)
    RecyclerView mRvBrand;

    @BindView(R.id.rv_main_product_product)
    RecyclerView mRvProduct;

    @BindView(R.id.tv_main_product_billboard)
    TextView mTvBillboard;

    private CategoryAdapter mCategoryAdapter;

    private Unbinder mUnbinder;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.main_fragment_product, container, false);
        mUnbinder = ButterKnife.bind(this, view);

        mTvBillboard.setOnClickListener(v -> startActivity(new Intent(getActivity(), BillboardListActivity.class)));

        mCategoryAdapter = new CategoryAdapter(getActivity());
        mCategory.setAdapter(mCategoryAdapter);

        return view;
    }

    @Override
    public void setData(MainProduct mainProduct) {
        mCategoryAdapter.addAll(mainProduct.getCategoryList());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
    }

    @Override
    public void setMenuVisibility(boolean menuVisible) {
        super.setMenuVisibility(menuVisible);
        if (getView() != null) {
            getView().setVisibility(menuVisible ? View.VISIBLE : View.INVISIBLE);
        }
    }

}
