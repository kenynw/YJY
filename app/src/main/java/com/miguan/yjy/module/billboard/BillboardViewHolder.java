package com.miguan.yjy.module.billboard;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.decoration.DividerDecoration;
import com.miguan.yjy.R;
import com.miguan.yjy.model.bean.Product;
import com.miguan.yjy.model.bean.Rank;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 排行榜。发现和排行榜列表页有用到
 * <p>
 * Copyright (c) 2017/8/23. LiaoPeiKun Inc. All rights reserved.
 */
public class BillboardViewHolder extends BaseViewHolder<Rank> {

    @BindView(R.id.dv_billboard_thumb)
    SimpleDraweeView mDvThumb;

    @BindView(R.id.rv_billboard_product)
    RecyclerView mRvProduct;

    public BillboardViewHolder(ViewGroup parent) {
        super(parent, R.layout.item_list_billboard);
        ButterKnife.bind(this, itemView);
        mRvProduct.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        mRvProduct.setHasFixedSize(true);
        mRvProduct.setClickable(true);
        mRvProduct.addItemDecoration(new DividerDecoration(
                getContext().getResources().getColor(R.color.white),
                (int) getContext().getResources().getDimension(R.dimen.spacing_small)
        ));
    }

    @Override
    public void setData(Rank data) {
        mDvThumb.setImageURI(data.getRank_banner());
        mRvProduct.setAdapter(new ProductAdapter(getContext(), data));
        itemView.setOnClickListener(v -> BillboardActivityPresenter.start(getContext(), data.getRank_id()));
    }

    public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {

        private Context mContext;

        private List<Product> mProductList;

        private Rank mRank;

        public ProductAdapter(Context context, Rank rank) {
            mContext = context;
            mProductList = rank.getProductList();
            mRank = rank;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(parent);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            holder.itemView.setOnClickListener(v -> BillboardActivityPresenter.start(getContext(), mRank.getRank_id()));
            if (position == mProductList.size()) {
                holder.mVsMore.inflate();
                holder.mDvThumb.setVisibility(View.GONE);
                holder.mTvName.setText("");
            } else {
                Product data = mProductList.get(position);
                holder.mDvThumb.setImageURI(data.getProduct_img());
                holder.mTvName.setText(data.getProduct_name());
            }
        }

        @Override
        public int getItemCount() {
            return mProductList.size() + 1;
        }

        class ViewHolder extends BaseViewHolder<Product> {

            @BindView(R.id.dv_billboard_product_thumb)
            SimpleDraweeView mDvThumb;

            @BindView(R.id.tv_billboard_product_name)
            TextView mTvName;

            @BindView(R.id.vs_billboard_product_more)
            ViewStub mVsMore;

            public ViewHolder(ViewGroup parent) {
                super(parent, R.layout.item_list_billboard_product);
                ButterKnife.bind(this, itemView);
            }

        }

    }

}
