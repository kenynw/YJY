package com.miguan.yjy.adapter.viewholder;

import android.content.Intent;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.miguan.yjy.R;
import com.miguan.yjy.model.bean.Product;
import com.miguan.yjy.module.product.SearchResultActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @作者 cjh
 * @日期 2017/3/21 12:00
 * @描述 为你推荐
 */
public class RecommedViewholder extends BaseViewHolder<Product> {
    @BindView(R.id.tv_product_think)
    TextView tvProductThink;

    public RecommedViewholder(ViewGroup parent) {
        super(parent, R.layout.item_product_recommend);
        ButterKnife.bind(this, itemView);
    }

    @Override
    public void setData(Product data) {
        super.setData(data);
        tvProductThink.setText(data.getName());
        tvProductThink.setOnClickListener(v -> gotoSearchResult());
    }

    private void gotoSearchResult() {
        Intent intent = new Intent(getContext(), SearchResultActivity.class);
        getContext().startActivity(intent);
    }

}
