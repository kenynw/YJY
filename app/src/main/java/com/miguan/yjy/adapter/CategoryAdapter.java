package com.miguan.yjy.adapter;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.miguan.yjy.R;
import com.miguan.yjy.model.bean.Category;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Copyright (c) 2017/3/23. LiaoPeiKun Inc. All rights reserved.
 */

public class CategoryAdapter extends RecyclerArrayAdapter<Category> {

    public CategoryAdapter(Context context, List<Category> objects) {
        super(context, objects);
    }

    @Override
    public CategoryViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new CategoryViewHolder(parent);
    }

    @Override
    public void OnBindViewHolder(BaseViewHolder holder, int position) {
        holder.setData(mObjects.get(position));
    }

    class CategoryViewHolder extends BaseViewHolder<Category> {

        @BindView(R.id.iv_category_thumb)
        ImageView mIvThumb;

        @BindView(R.id.tv_category_name)
        TextView mTvName;

        public CategoryViewHolder(ViewGroup parent) {
            super(parent, R.layout.item_grid_cate);
            ButterKnife.bind(this, itemView);
        }

        @Override
        public void setData(Category data) {
            Glide.with(getContext())
                    .load(data.getCate_img())
                    .placeholder(R.mipmap.def_image_loading)
                    .error(R.mipmap.def_image_loading)
                    .centerCrop()
                    .into(mIvThumb);

            mTvName.setText(data.getCate_name());
        }
    }

}
