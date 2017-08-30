package com.miguan.yjy.adapter.viewholder;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.miguan.yjy.R;
import com.miguan.yjy.model.bean.Category;
import com.miguan.yjy.module.product.SearchResultPresenter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @作者 cjh
 * @日期 2017/8/29 11:48
 * @描述
 */

public class ProductSortViewHolder extends BaseViewHolder<Category> {

    @BindView(R.id.tv_sort_first_name)
    TextView mTvSortFirstName;
    @BindView(R.id.recy_product_sort)
    EasyRecyclerView mRecySort;


    public ProductSortViewHolder(ViewGroup parent) {
        super(parent, R.layout.activity_product_sort);
        ButterKnife.bind(this, itemView);
    }

    @Override
    public void setData(Category data) {
        super.setData(data);
        mTvSortFirstName.setText(data.getCate_name());
        mRecySort.setLayoutManager(new GridLayoutManager(getContext(), 4));
        SortAdapter sortAdapter = new SortAdapter(getContext(), data.getSub());
        mRecySort.setAdapter(sortAdapter);
    }

    class SortAdapter extends RecyclerArrayAdapter<Category> {


        public SortAdapter(Context context, List<Category> objects) {
            super(context, objects);
        }

        @Override
        public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
            return new SortViewHolder(parent);
        }
    }


    class SortViewHolder extends BaseViewHolder<Category> {

        @BindView(R.id.sdv_sort)
        SimpleDraweeView mSdvSort;
        @BindView(R.id.tv_sort_second_name)
        TextView mTvSortSecondName;

        public SortViewHolder(ViewGroup parent) {
            super(parent, R.layout.item_product_sencond_sort);
            ButterKnife.bind(this, itemView);
        }

        @Override
        public void setData(Category data) {
            super.setData(data);
            mSdvSort.setImageURI(data.getCate_img());
            mTvSortSecondName.setText(data.getCate_name());
            itemView.setOnClickListener(v-> SearchResultPresenter.start(getContext(),"",data.getId(),data.getCate_name()));
        }
    }


}
