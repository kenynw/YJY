package com.miguan.yjy.module.template;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.miguan.yjy.R;

/**
 * Copyright (c) 2017/4/18. LiaoPeiKun Inc. All rights reserved.
 */

public class FilterAdapter extends BaseAdapter {

    private Context mContext;

    public FilterAdapter(Context context) {
        mContext = context;
    }

    @Override
    public int getCount() {
        return Filter.values().length;
    }

    @Override
    public Filter getItem(int position) {
        return Filter.getFilter(position);
    }

    @Override
    public long getItemId(int position) {
        return getItem(position).mIndex;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        FilterViewHolder holder = new FilterViewHolder(parent, R.layout.item_grid_filter);
        holder.setData(getItem(position));
        return holder.itemView;
    }

    class FilterViewHolder extends BaseViewHolder<Filter> {

        public FilterViewHolder(ViewGroup parent, @LayoutRes int res) {
            super(parent, res);
        }

        @Override
        public void setData(Filter data) {
            if (data.mIsCheck) {

            }
        }

    }

}
