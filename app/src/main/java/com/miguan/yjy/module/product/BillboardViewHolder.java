package com.miguan.yjy.module.product;

import android.text.Html;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.miguan.yjy.R;
import com.miguan.yjy.model.bean.Rank;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;

/**
 * Copyright (c) 2017/7/27. LiaoPeiKun Inc. All rights reserved.
 */

public class BillboardViewHolder extends BaseViewHolder<Rank> {

    @BindViews({R.id.dv_billboard_top1, R.id.dv_billboard_top2, R.id.dv_billboard_top3})
    SimpleDraweeView[] mDvTops;

    @BindView(R.id.tv_billboard_title)
    TextView mTvTitle;

    public BillboardViewHolder(ViewGroup parent) {
        super(parent, R.layout.item_list_billboard);
        ButterKnife.bind(this, itemView);
    }

    @Override
    public void setData(Rank data) {
        String num = data.getRank_name() + "<font color=\"#32DAC3\">" + "TOP" + data.getImg_list().size() + " </font>";
        mTvTitle.setText(Html.fromHtml(num));
        int size = data.getImg_list().size() > 3 ? 3 : data.getImg_list().size();
        for (int i = 0; i < size; i++) {
            mDvTops[i].setImageURI(data.getImg_list().get(i));
        }
        itemView.setOnClickListener(view -> BillboardActivityPresenter.start(getContext(), data.getRank_id()));
    }

}
