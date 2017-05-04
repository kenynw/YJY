package com.miguan.yjy.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.miguan.yjy.R;
import com.miguan.yjy.model.bean.Category;
import com.miguan.yjy.module.product.SearchResultPresenter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Copyright (c) 2017/3/23. LiaoPeiKun Inc. All rights reserved.
 */

public class CategoryAdapter extends BaseAdapter {

    private Context mContext;

    private List<Category> mList;

    public CategoryAdapter(Context context, List<Category> list) {
        mContext = context;
        mList = list;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Category getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        CategoryViewHolder holder = new CategoryViewHolder(parent);
        holder.setData(getItem(position));
        return holder.itemView;
    }

    class CategoryViewHolder extends BaseViewHolder<Category> {

        @BindView(R.id.iv_category_thumb)
        SimpleDraweeView mIvThumb;

        @BindView(R.id.tv_category_name)
        TextView mTvName;

        public CategoryViewHolder(ViewGroup parent) {
            super(parent, R.layout.item_grid_cate);
            ButterKnife.bind(this, itemView);
        }

        @Override
        public void setData(Category data) {
            mIvThumb.setImageURI(Uri.parse(data.getCate_img()));
            mTvName.setText(data.getCate_name());
            itemView.setOnClickListener(v -> SearchResultPresenter.start(getContext(), "", data.getId(), data.getCate_name()));
        }
    }

}
