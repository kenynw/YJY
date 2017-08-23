package com.miguan.yjy.module.product;

import android.view.ViewGroup;

import com.dsk.chain.bijection.RequiresPresenter;
import com.dsk.chain.expansion.list.BaseListFragment;
import com.dsk.chain.expansion.list.ListConfig;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.decoration.DividerDecoration;
import com.miguan.yjy.R;
import com.miguan.yjy.adapter.viewholder.SearchReslutViewHolder;
import com.miguan.yjy.model.bean.Product;
import com.miguan.yjy.utils.LUtils;


/**
 * @作者 cjh
 * @日期 2017/5/19 15:53
 * @描述
 */
@RequiresPresenter(StarProductPresenter.class)
public class StarProductFragment extends BaseListFragment<StarProductPresenter, Product> {

    @Override
    public BaseViewHolder<Product> createViewHolder(ViewGroup parent, int viewType) {
        return new SearchReslutViewHolder(parent, true);
    }

    @Override
    public ListConfig getListConfig() {
        DividerDecoration div = new DividerDecoration(getResources().getColor(R.color.bgWindow), LUtils.dp2px(1), LUtils.dp2px(15), LUtils.dp2px(15));
        return super.getListConfig().setRefreshAble(false)
                .setLoadMoreAble(false)
                .setNoMoreAble(true).setItemDecoration(div);
    }

}
