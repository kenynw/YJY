package com.miguan.yjy.adapter.viewholder;

import android.net.Uri;
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

    @BindView(R.id.iv_proudct_thumb)
    ImageView mDvThumb;

    @BindView(R.id.tv_product_name)
    TextView mTvName;

    @BindView(R.id.ratbar_product)
    RatingBar mRatbar;

    @BindView(R.id.tv_product_money)
    TextView mTvSpec;

    public SearchReslutViewHolder(ViewGroup parent) {
        super(parent, R.layout.item_product_list);
        ButterKnife.bind(this, itemView);
    }

    @Override
    public void setData(Product data) {
        mDvThumb.setImageURI(Uri.parse(data.getProduct_img()));
        mTvName.setText(data.getProduct_name());
        mRatbar.setRating(data.getStar());
        mTvSpec.setText(data.getPrice().equals("0") ? "暂无报价" : getSpec(data));
        itemView.setOnClickListener(v -> ProductDetailPresenter.start(getContext(), data.getId()));
    }

    private String getSpec(Product data) {
        return String.format(getContext().getString(R.string.text_product_spec), data.getPrice(), data.getForm());
    }

}
