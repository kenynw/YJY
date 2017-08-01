package com.miguan.yjy.module.product;

import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.miguan.yjy.R;
import com.miguan.yjy.model.bean.Rank;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Copyright (c) 2017/7/27. LiaoPeiKun Inc. All rights reserved.
 */

public class BillboardViewHolder extends BaseViewHolder<Rank> {

    @BindView(R.id.dv_billboard_top1)
    SimpleDraweeView mDvTop1;

    @BindView(R.id.dv_billboard_top2)
    SimpleDraweeView mDvTop2;

    @BindView(R.id.dv_billboard_top3)
    SimpleDraweeView mDvTop3;

    @BindView(R.id.tv_billboard_title)
    TextView mTvTitle;

    public BillboardViewHolder(ViewGroup parent) {
        super(parent, R.layout.item_list_billboard);
        ButterKnife.bind(this, itemView);
    }

    @Override
    public void setData(Rank data) {
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), BillboardActivity.class);
                getContext().startActivity(intent);
            }
        });
        for(int i=0;i<data.getImg_list().size();i++){
            switch (i) {
                case 0:
                    mDvTop1.setImageURI(data.getImg_list().get(0));
                    break;
                case 1:
                    mDvTop2.setImageURI(data.getImg_list().get(1));
                    break;
                case 2:
                    mDvTop3.setImageURI(data.getImg_list().get(2));
                    break;

            }
        }



        mTvTitle.setText(data.getRank_name());
    }

}
