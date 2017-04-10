package com.miguan.yjy.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.miguan.yjy.R;
import com.miguan.yjy.model.bean.Product;

import java.util.List;

import butterknife.BindView;

/**
 * @作者 cjh
 * @日期 2017/4/7 10:12
 * @描述
 */

public class ProductSortAdapter1 extends RecyclerView.Adapter<ProductSortAdapter1.MyViewHolder> {

    int type = 1;
    List<String> effects;
    List<Product> products;
    private Context mContext;


    public ProductSortAdapter1(Context context, List<Product> products, List<String> effects, int type) {
        this.type = type;
        this.products = products;
        this.effects = effects;
        mContext = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(View.inflate(mContext, R.layout.item_product_name, null));
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        if (type == 1) {
            holder.mTvProductSortName.setText(products.get(position).getName());
        } else {
            holder.mTvProductSortName.setText(effects.get(position));
        }

    }

    @Override
    public int getItemCount() {
        if (type == 1) {
            return products.size();
        } else {
            return effects.size();
        }
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_product_sort_name)
        TextView mTvProductSortName;

        public MyViewHolder(View itemView) {
            super(itemView);
            mTvProductSortName = (TextView) itemView.findViewById(R.id.tv_product_sort_name);
        }

    }


}
