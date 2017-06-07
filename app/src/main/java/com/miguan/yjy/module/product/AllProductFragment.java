package com.miguan.yjy.module.product;

import android.view.ViewGroup;

import com.dsk.chain.bijection.RequiresPresenter;
import com.dsk.chain.expansion.list.BaseListFragment;
import com.dsk.chain.expansion.list.ListConfig;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.miguan.yjy.adapter.viewholder.SearchReslutViewHolder;
import com.miguan.yjy.model.bean.Product;

/**
 * @作者 cjh
 * @日期 2017/5/19 17:46
 * @描述
 */
@RequiresPresenter(AllProductPresenter.class)
public class AllProductFragment extends BaseListFragment<AllProductPresenter,Product>{
    @Override
    public BaseViewHolder<Product> createViewHolder(ViewGroup parent, int viewType) {
        return new SearchReslutViewHolder(parent, false);
    }

    @Override
    public ListConfig getListConfig() {
        return super.getListConfig().setLoadMoreAble(true).setRefreshAble(false);
    }
}
