package com.miguan.yjy.module.billboard;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.miguan.yjy.R;
import com.miguan.yjy.adapter.viewholder.SearchReslutViewHolder;
import com.miguan.yjy.model.bean.Product;

/**
 * Copyright (c) 2017/7/27. LiaoPeiKun Inc. All rights reserved.
 */

public class BillboardProductViewHolder extends SearchReslutViewHolder {

    private ImageView mIvRank;

    public BillboardProductViewHolder(ViewGroup parent) {
        super(parent, false);
        mIvRank = new ImageView(getContext());
        mIvRank.setLayoutParams(createLayoutParams());
    }

    @Override
    public void setData(Product data) {
        super.setData(data);
        switch (getAdapterPosition()) {
            case 0:
                mIvRank.setImageResource(R.mipmap.ic_billboard_top1);
                ((RelativeLayout) itemView).addView(mIvRank);
                break;
            case 1:
                mIvRank.setImageResource(R.mipmap.ic_billboard_top2);
                ((RelativeLayout) itemView).addView(mIvRank);
                break;
            case 2:
                mIvRank.setImageResource(R.mipmap.ic_billboard_top3);
                ((RelativeLayout) itemView).addView(mIvRank);
                break;
            default:
                mIvRank.setVisibility(View.GONE);
                break;
        }
    }

    private RelativeLayout.LayoutParams createLayoutParams() {
        return new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }

}
