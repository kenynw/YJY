package com.miguan.yjy.adapter.viewholder;

import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.miguan.yjy.R;
import com.miguan.yjy.model.bean.Product;
import com.miguan.yjy.module.product.ProductDetailPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @作者 cjh
 * @日期 2017/3/23 15:11
 * @描述 搜索结果（产品列表）
 */

public class SearchReslutViewHolder extends BaseViewHolder<Product> {

    @BindView(R.id.img_proudct)
    ImageView imgProudct;
    @BindView(R.id.tv_product_name)
    TextView tvProductName;
    @BindView(R.id.ratbar_product)
    RatingBar ratbarProduct;
    @BindView(R.id.tv_product_money)
    TextView tvProductMoney;

    public SearchReslutViewHolder(ViewGroup parent) {
        super(parent, R.layout.item_product_list);
        ButterKnife.bind(this, itemView);
    }

    @Override
    public void setData(Product data) {
        tvProductName.setText(data.getProduct_name());
        ratbarProduct.setRating(3);
        tvProductMoney.setText("产品价格");
        itemView.setOnClickListener(v -> ProductDetailPresenter.start(getContext(), data.getId()));
    }
}
