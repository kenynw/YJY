package com.miguan.yjy.module.product;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.miguan.yjy.R;
import com.miguan.yjy.adapter.viewholder.BrandViewHolder;
import com.miguan.yjy.model.bean.Brand;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Copyright (c) 2017/4/18. LiaoPeiKun Inc. All rights reserved.
 */

public class BrandHeader implements RecyclerArrayAdapter.ItemView {

    @BindView(R.id.tv_brand_header_hot)
    TextView mTvHot;

    @BindView(R.id.recy_brand_header_list)
    RecyclerView mRecyList;

    @BindView(R.id.tv_brand_header_other)
    TextView mTvOther;

    @BindView(R.id.ll_brand_header)
    LinearLayout mLlHeader;

    private Activity mActivity;

    private List<Brand> mHotList;

    public BrandHeader(Activity activity, List<Brand> hotList) {
        mActivity = activity;
        mHotList = hotList;
    }

    @Override
    public View onCreateView(ViewGroup parent) {
        View view = LayoutInflater.from(mActivity).inflate(R.layout.header_brand_list, parent, false);
        ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onBindView(View headerView) {
        if (mHotList == null || mHotList.size() <= 0) {
            mTvOther.setText("搜索结果");
            mLlHeader.setVisibility(View.GONE);
        } else {
            mRecyList.setLayoutManager(new LinearLayoutManager(mActivity));
            RecyclerArrayAdapter<Brand> adapter = new RecyclerArrayAdapter<Brand>(mActivity, mHotList) {
                @Override
                public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
                    return new BrandViewHolder(parent);
                }
            };
            adapter.setOnItemClickListener(position -> {
                Intent intent = new Intent();
                intent.putExtra("brand", mHotList.get(position));
                mActivity.setResult(Activity.RESULT_OK, intent);
                mActivity.finish();
            });
            mRecyList.setAdapter(adapter);
            mLlHeader.setVisibility(View.VISIBLE);
        }
    }

}
