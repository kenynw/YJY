package com.miguan.yjy.module.user;

import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.miguan.yjy.R;
import com.miguan.yjy.model.bean.Bill;
import com.miguan.yjy.utils.StringUtils;
import com.miguan.yjy.widget.SuperTextView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Copyright (c) 2017/9/21. LiaoPeiKun Inc. All rights reserved.
 */

public class BillProductViewHolder extends BaseViewHolder<Bill> {

    @BindView(R.id.dv_bill_product_thumb)
    SimpleDraweeView mDvThumb;

    @BindView(R.id.tv_bill_product_name)
    TextView mTvName;

    @BindView(R.id.rtb_bill_product_star)
    RatingBar mRtbStar;

    @BindView(R.id.tv_bill_product_spec)
    TextView mTvSpec;

    @BindView(R.id.tv_bill_product_remark)
    SuperTextView mTvRemark;

    @BindView(R.id.iv_bill_product_sort)
    ImageView mIvSort;

    private BillDetailPresenter mPresenter;

    public BillProductViewHolder(ViewGroup parent, BillDetailPresenter presenter) {
        super(parent, R.layout.item_list_bill_product);
        ButterKnife.bind(this, itemView);
        mPresenter = presenter;
    }

    @Override
    public void setData(Bill data) {
        mDvThumb.setImageURI(data.getProduct_img());
        mRtbStar.setRating(data.getStar());
        mTvSpec.setText(StringUtils.getFormatSpec(getContext(), data.getPrice(), data.getForm()));
        mTvName.setText(data.getProduct_name());
        mTvRemark.setSolid(getContext().getResources().getColor(mPresenter.isEditMode() ? R.color.white : R.color.bgWindow));
        if (mPresenter.isEditMode()) {
            mTvRemark.setVisibility(View.VISIBLE);
            mTvRemark.setText("添加备注");
            mTvRemark.setTextColor(getContext().getResources().getColor(R.color.textSecondary));
            mTvRemark.setSolid(getContext().getResources().getColor(R.color.white));
        } else {
            if (TextUtils.isEmpty(data.getDesc())) {
                mTvRemark.setVisibility(View.GONE);
            } else {
                mTvRemark.setVisibility(View.VISIBLE);
                mTvRemark.setTextColor(getContext().getResources().getColor(R.color.textPrimary));
                mTvRemark.setSolid(getContext().getResources().getColor(R.color.bgWindow));
                mTvRemark.setText(data.getDesc());
            }
        }
        mIvSort.setVisibility(mPresenter.isEditMode() ? View.VISIBLE : View.GONE);
        mIvSort.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (mPresenter.isEditMode() && event.getAction() == MotionEvent.ACTION_DOWN) {
                    mPresenter.getItemTouchHelper().startDrag(BillProductViewHolder.this);
                }
                return false;
            }
        });
    }

}
