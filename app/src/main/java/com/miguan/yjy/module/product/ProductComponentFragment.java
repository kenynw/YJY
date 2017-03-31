package com.miguan.yjy.module.product;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dsk.chain.bijection.RequiresPresenter;
import com.dsk.chain.expansion.list.BaseListFragment;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.miguan.yjy.adapter.viewholder.ProductComponentViewHolder;
import com.miguan.yjy.model.bean.Component;

/**
 * @作者 cjh
 * @日期 2017/3/29 11:46
 * @描述
 */
@RequiresPresenter(ProductComponentPresenter.class)
public class ProductComponentFragment extends BaseListFragment<ProductComponentPresenter,Component> {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        return super.onCreateView(inflater, container, savedInstanceState);
    }


    @Override
    public BaseViewHolder<Component> createViewHolder(ViewGroup parent, int viewType) {
        return new ProductComponentViewHolder(parent);
    }
}
