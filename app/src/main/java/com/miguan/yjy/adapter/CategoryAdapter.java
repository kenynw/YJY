package com.miguan.yjy.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.miguan.yjy.R;
import com.miguan.yjy.model.bean.Category;
import com.miguan.yjy.module.product.ProductSortActivity;
import com.miguan.yjy.module.product.SearchResultPresenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Copyright (c) 2017/3/23. LiaoPeiKun Inc. All rights reserved.
 */

public class CategoryAdapter extends BaseAdapter {

    private Context mContext;

    private List<Category> mList;

    public CategoryAdapter(Context context) {
        mContext = context;
        mList = new ArrayList<>();
    }

    @Override
    public int getCount() {
        return mList.size() > 0 ? mList.size() + 1 : 8;
    }

    @Override
    public Category getItem(int position) {
        return mList != null && mList.size() > 0 && position != mList.size()
                ? mList.get(position) : null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        CategoryViewHolder holder;
        if (convertView == null) {
            holder = new CategoryViewHolder(parent);
            convertView = holder.itemView;
            convertView.setTag(holder);
        } else {
            holder = (CategoryViewHolder) convertView.getTag();
        }

        Category data = getItem(position);
        if (data != null) {
            holder.mIvThumb.setImageURI(Uri.parse(data.getCate_img()));
            holder.mTvName.setText(data.getCate_name());
            holder.itemView.setOnClickListener(v ->
                    SearchResultPresenter.start(mContext, "", data.getId(), data.getCate_name())
            );
        } else if (position == mList.size()) {
            holder.mVsCateMore.inflate();
            holder.mTvName.setText("全部");
            holder.itemView.setOnClickListener(v -> mContext.startActivity(new Intent(mContext, ProductSortActivity.class)));
        }

        return holder.itemView;
    }

    public void addAll(List<Category> list) {
        mList.clear();
        mList.addAll(list);
        notifyDataSetChanged();
    }

    class CategoryViewHolder extends BaseViewHolder<Category> {

        @BindView(R.id.iv_category_thumb)
        SimpleDraweeView mIvThumb;

        @BindView(R.id.tv_category_name)
        TextView mTvName;

        @BindView(R.id.vs_cate_more)
        ViewStub mVsCateMore;

        public CategoryViewHolder(ViewGroup parent) {
            super(parent, R.layout.item_grid_cate);
            ButterKnife.bind(this, itemView);
        }

    }

}
