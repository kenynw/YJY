package com.miguan.yjy.module.product;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.miguan.yjy.R;
import com.miguan.yjy.adapter.viewholder.SearchReslutViewHolder;
import com.miguan.yjy.model.bean.Product;
import com.miguan.yjy.utils.LUtils;

import butterknife.BindView;

/**
 * Copyright (c) 2017/5/23. LiaoPeiKun Inc. All rights reserved.
 */

public class RepositoryViewHolder extends SearchReslutViewHolder {

    @BindView(R.id.rl_product_info)
    RelativeLayout mRlInfo;

    @BindView(R.id.tv_product_delete)
    TextView mTvDelete;

    public RepositoryViewHolder(ViewGroup parent) {
        super(parent, R.layout.item_repository_product_list);
    }

    @Override
    public void setData(Product data) {
        mTvName.setText(data.getProduct_name());
        mRatbar.setVisibility(View.GONE);
        mTvSpec.setText(data.getPrice().equals("0") ? "暂无报价" : getSpec(data));
        mRlInfo.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.putExtra("product", data);
            ((Activity) getContext()).setResult(Activity.RESULT_OK, intent);
            ((Activity) getContext()).finish();
        });
        mTvDelete.setOnClickListener(v -> {
            LUtils.toast("delete");
        });
    }

}
