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
 * @日期 2017/3/24 15:30
 * @描述 历史搜索
 */

public class HistorySearchAdpter extends RecyclerArrayAdapter<Product> {
    public HistorySearchAdpter(Context context, List<Product> objects) {
        super(context, objects);
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new HistorySearchViewHolder(parent);
    }

    @Override
    public void OnBindViewHolder(BaseViewHolder holder, int position) {
        holder.setData(mObjects.get(position));
    }

    class HistorySearchViewHolder extends BaseViewHolder<Product> {
        @BindView(R.id.tv_product_name)
        TextView tvProductName;

        public HistorySearchViewHolder(ViewGroup parent) {
            super(parent, R.layout.product_item_history_serach);
            ButterKnife.bind(this, itemView);
        }

        @Override
        public void setData(Product data) {
            super.setData(data);
            tvProductName.setText(data.getProduct_name());
        }
    }

}
