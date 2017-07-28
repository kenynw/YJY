package com.miguan.yjy.module.test;

import android.view.ViewGroup;

import com.dsk.chain.bijection.RequiresPresenter;
import com.dsk.chain.expansion.list.BaseListFragment;
import com.dsk.chain.expansion.list.ListConfig;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.miguan.yjy.adapter.viewholder.WikiViewHolder;
import com.miguan.yjy.model.bean.Wiki;

/**
 * @作者 cjh
 * @日期 2017/6/21 14:33
 * @描述
 */
@RequiresPresenter(SkinGuideFragmentPresenter.class)
public class SkinGuideFragment extends BaseListFragment<SkinGuideFragmentPresenter,Wiki> {
    @Override
    public BaseViewHolder<Wiki> createViewHolder(ViewGroup parent, int viewType) {
        return new WikiViewHolder(parent);
    }

    @Override
    public ListConfig getListConfig() {
        return super.getListConfig().setRefreshAble(false).setLoadMoreAble(false).setContainerEmptyAble(false);
    }
}
