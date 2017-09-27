package com.miguan.yjy.adapter.viewholder;

import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.miguan.yjy.R;
import com.miguan.yjy.model.bean.Test;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @作者 cjh
 * @日期 2017/3/23 15:11
 * @描述 推荐产品清单列表
 */

public class TestListViewHolder extends BaseViewHolder<Test> {

    @BindView(R.id.iv_prouduct_thumb)
    ImageView imgProudct;
    @BindView(R.id.tv_product_name)
    TextView tvProductName;
    @BindView(R.id.ratbar_product)
    RatingBar ratbarProduct;
    @BindView(R.id.tv_product_money)
    TextView tvProductMoney;

    public TestListViewHolder(ViewGroup parent) {
        super(parent, R.layout.item_product_list);
        ButterKnife.bind(this, itemView);
    }

    @Override
    public void setData(Test data) {
        tvProductName.setText(data.getTestName());
        ratbarProduct.setRating(3);
        tvProductMoney.setText("产品价格");
//        itemView.setOnClickListener(v -> ProductDetailPresenter.start(getContext(), data.getId()));
    }
}
