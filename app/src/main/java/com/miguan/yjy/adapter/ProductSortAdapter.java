package com.miguan.yjy.adapter;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.miguan.yjy.R;
import com.miguan.yjy.model.bean.Product;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @作者 cjh
 * @日期 2017/4/7 10:12
 * @描述
 */

public class ProductSortAdapter extends RecyclerArrayAdapter<Product> {

    public ProductSortAdapter(Context context, List<Product> objects) {
        super(context, objects);
    }

    @Override
    public void OnBindViewHolder(BaseViewHolder holder, int position) {
        holder.setData(mObjects.get(position));
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(parent);
    }

    class MyViewHolder extends BaseViewHolder<Product> {
        @BindView(R.id.tv_product_sort_name)
        TextView mTvProductSortName;

        public MyViewHolder(ViewGroup parent) {
            super(parent, R.layout.item_product_name);
            ButterKnife.bind(this, itemView);
        }

        @Override
        public void setData(Product data) {
            super.setData(data);
            mTvProductSortName.setText(data.getProduct_name());
        }
    }


}
