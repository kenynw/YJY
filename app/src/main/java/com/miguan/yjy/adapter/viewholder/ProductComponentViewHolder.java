package com.miguan.yjy.adapter.viewholder;

import android.view.ViewGroup;

import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.miguan.yjy.R;
import com.miguan.yjy.model.bean.Component;

/**
 * @作者 cjh
 * @日期 2017/3/29 14:00
 * @描述
 */

public class ProductComponentViewHolder extends BaseViewHolder<Component> {
    public ProductComponentViewHolder(ViewGroup parent) {
        super(parent, R.layout.item_product_read);
    }

    @Override
    public void setData(Component data) {
        super.setData(data);
    }
}
