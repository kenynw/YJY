package com.miguan.yjy.module.product;

import android.os.Bundle;
import android.view.ViewGroup;

import com.dsk.chain.bijection.RequiresPresenter;
import com.dsk.chain.expansion.list.BaseListActivity;
import com.dsk.chain.expansion.list.ListConfig;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.decoration.DividerDecoration;
import com.miguan.yjy.R;
import com.miguan.yjy.adapter.viewholder.ProductSortViewHolder;

/**
 * @作者 cjh
 * @日期 2017/8/29 9:31
 * @描述
 */
@RequiresPresenter(ProductSortPresenter.class)
public class ProductSortActivity extends BaseListActivity<ProductSortPresenter> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setToolbarTitle("产品分类");
    }

    @Override
    protected BaseViewHolder createViewHolder(ViewGroup parent, int viewType) {
        return new ProductSortViewHolder(parent);

    }

    @Override
    protected int getLayout() {
        return R.layout.common_activity_list;
    }

    @Override
    public ListConfig getListConfig() {
        DividerDecoration div = new DividerDecoration(getResources().getColor(R.color.f5),10);
        return super.getListConfig().setLoadMoreAble(false).setRefreshAble(false).setItemDecoration(div);
    }
}
