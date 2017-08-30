package com.miguan.yjy.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.BaseAdapter;

import com.facebook.drawee.view.SimpleDraweeView;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.miguan.yjy.R;
import com.miguan.yjy.model.bean.Brand;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Copyright (c) 2017/8/25. LiaoPeiKun Inc. All rights reserved.
 */

public class DiscoverBrandAdapter extends BaseAdapter {

    private Context mContext;

    private List<Brand> mList;

    public DiscoverBrandAdapter(Context context, List<Brand> list) {
        mContext = context;
        mList = list;
    }

    @Override
    public int getCount() {
        return mList.size() + 1;
    }

    @Override
    public Object getItem(int position) {
        return position == mList.size() ? null : mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder(parent);
            convertView = holder.itemView;
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        if (position != mList.size()) {
            Brand brand = (Brand) getItem(position);
            holder.mDvThumb.setImageURI(brand.getImg());
        } else {
            holder.mVsMore.inflate();
        }

        return holder.itemView;
    }

    public class ViewHolder extends BaseViewHolder<Brand> {

        @BindView(R.id.dv_brand_thumb)
        SimpleDraweeView mDvThumb;

        @BindView(R.id.vs_brand_more)
        ViewStub mVsMore;

        public ViewHolder(ViewGroup parent) {
            super(parent, R.layout.item_grid_brand);
            ButterKnife.bind(this, itemView);
        }

    }

}
