package com.miguan.yjy.module.product;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dsk.chain.expansion.list.BaseListActivityPresenter;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.miguan.yjy.R;
import com.miguan.yjy.adapter.viewholder.BrandViewHolder;
import com.miguan.yjy.model.ProductModel;
import com.miguan.yjy.model.bean.Brand;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Copyright (c) 2017/3/28. LiaoPeiKun Inc. All rights reserved.
 */

public class BrandListPresenter extends BaseListActivityPresenter<BrandListActivity, Brand> {

    @Override
    protected void onCreateView(BrandListActivity view) {
        super.onCreateView(view);
        onRefresh();
    }

    @Override
    public void onRefresh() {
        ProductModel.getInstance().getBrandList()
                .map(brandList -> {
                    getAdapter().addHeader(new BrandHotHeader(brandList.getHotCosmetics()));

                    return brandList.getOtherCosmetics();
                })
//                .doOnNext(list -> {
//                    StickyHeaderDecoration decoration = new StickyHeaderDecoration(new StickHeaderAdapter(list));
//                    decoration.setIncludeHeader(false);
//                    getView().getListView().addItemDecoration(decoration);
//                })
                .unsafeSubscribe(getRefreshSubscriber());
    }

    class BrandHotHeader implements RecyclerArrayAdapter.ItemView {

        @BindView(R.id.tv_brand_header_hot)
        TextView mTvHot;

        @BindView(R.id.recy_brand_header_list)
        RecyclerView mRecyList;

        @BindView(R.id.tv_brand_header_other)
        TextView mTvOther;

        private List<Brand> mHotList;

        public BrandHotHeader(List<Brand> hotList) {
            mHotList = hotList;
        }

        @Override
        public View onCreateView(ViewGroup parent) {
            View view = LayoutInflater.from(getView()).inflate(R.layout.header_brand_list, parent, false);
            ButterKnife.bind(this, view);

            mRecyList.setLayoutManager(new LinearLayoutManager(getView()));

            return view;
        }

        @Override
        public void onBindView(View headerView) {
            mRecyList.setAdapter(new RecyclerArrayAdapter<Brand>(getView(), mHotList) {
                @Override
                public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
                    return new BrandViewHolder(parent);
                }
            });
        }

    }

}
