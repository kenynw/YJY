package com.miguan.yjy.module.product;

import android.support.annotation.LayoutRes;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.miguan.yjy.R;
import com.miguan.yjy.model.bean.Bill;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Copyright (c) 2017/9/26. LiaoPeiKun Inc. All rights reserved.
 */

public class BillSimpleViewHolder extends BaseViewHolder<Bill> {

    @BindView(R.id.dv_bill_thumb)
    SimpleDraweeView mDvThumb;

    @BindView(R.id.tv_bill_thumb_count)
    TextView mTvThumbCount;

    @BindView(R.id.tv_bill_name)
    TextView mTvName;

    @BindView(R.id.tv_bill_num)
    TextView mTvNum;

    public BillSimpleViewHolder(ViewGroup parent) {
        this(parent, R.layout.item_list_bill_simple);
    }

    public BillSimpleViewHolder(ViewGroup parent, @LayoutRes int res) {
        super(parent, res);
        ButterKnife.bind(this, itemView);
    }

    @Override
    public void setData(Bill data) {
        setBill(data);
    }

    protected void setBill(Bill bill) {
        mTvName.setText(bill.getTitle());
        if (bill.getNum() > 0) {
            String num = bill.getNum() + "";
            mTvNum.setVisibility(View.VISIBLE);
            mTvThumbCount.setVisibility(View.VISIBLE);
            mTvNum.setText(num + "产品");
            mTvThumbCount.setText(num);
        } else {
            mTvNum.setVisibility(View.GONE);
            mTvThumbCount.setVisibility(View.GONE);
        }
        mDvThumb.setImageURI(bill.getPicture());
    }

}
