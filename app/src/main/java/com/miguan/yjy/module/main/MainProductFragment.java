package com.miguan.yjy.module.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dsk.chain.bijection.RequiresPresenter;
import com.dsk.chain.expansion.data.BaseDataFragment;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.jude.easyrecyclerview.decoration.DividerDecoration;
import com.jude.exgridview.ExGridView;
import com.miguan.yjy.R;
import com.miguan.yjy.adapter.CategoryAdapter;
import com.miguan.yjy.adapter.DiscoverBrandAdapter;
import com.miguan.yjy.model.bean.MainProduct;
import com.miguan.yjy.model.bean.Rank;
import com.miguan.yjy.module.billboard.BillboardListActivity;
import com.miguan.yjy.module.billboard.BillboardViewHolder;
import com.miguan.yjy.utils.LUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Copyright (c) 2017/8/22. LiaoPeiKun Inc. All rights reserved.
 */
@RequiresPresenter(MainProductPresenter.class)
public class MainProductFragment extends BaseDataFragment<MainProductPresenter, MainProduct> {

    private Unbinder mUnbinder;

    @BindView(R.id.rv_main_product_cate)
    ExGridView mCategory;

    @BindView(R.id.tv_main_product_count)
    TextView mTvCount;

    @BindView(R.id.rv_main_product_brand)
    ExGridView mRvBrand;

    @BindView(R.id.rv_main_product_product)
    RecyclerView mRvProduct;

    @BindView(R.id.tv_main_product_billboard)
    TextView mTvBillboard;

    private CategoryAdapter mCategoryAdapter;

    private RecyclerArrayAdapter<Rank> mBillboardAdapter = new RecyclerArrayAdapter<Rank>(getActivity()) {
        @Override
        public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
            return new BillboardViewHolder(parent);
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.main_fragment_product, container, false);
        mUnbinder = ButterKnife.bind(this, view);

        mCategoryAdapter = new CategoryAdapter(getActivity());
        mCategory.setAdapter(mCategoryAdapter);

        mRvProduct.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        mRvProduct.setAdapter(mBillboardAdapter);
        DividerDecoration decoration = new DividerDecoration(getResources().getColor(R.color.bgWindow), LUtils.dp2px(10));
        mRvProduct.addItemDecoration(decoration);

        mTvBillboard.setOnClickListener(v -> startActivity(new Intent(getActivity(), BillboardListActivity.class)));

        return view;
    }

    @Override
    public void setData(MainProduct mainProduct) {
        String count = String.format(getResources().getString(R.string.text_product_count), mainProduct.getSum());
        mTvCount.setText(Html.fromHtml(count));
        mCategoryAdapter.addAll(mainProduct.getCategoryList());
        mRvBrand.setAdapter(new DiscoverBrandAdapter(getActivity(), mainProduct.getBrandList()));

        mBillboardAdapter.addAll(mainProduct.getRankingList());
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
