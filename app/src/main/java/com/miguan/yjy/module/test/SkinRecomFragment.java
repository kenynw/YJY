package com.miguan.yjy.module.test;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.ViewGroup;

import com.dsk.chain.bijection.RequiresPresenter;
import com.dsk.chain.expansion.list.BaseListFragment;
import com.dsk.chain.expansion.list.ListConfig;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.miguan.yjy.adapter.viewholder.MyViewHolder;
import com.miguan.yjy.model.bean.Skin;

/**
 * @作者 cjh
 * @日期 2017/6/16 15:45
 * @描述
 */
@RequiresPresenter(SkinRecomFragmentPresenter.class)
public class SkinRecomFragment extends BaseListFragment<SkinRecomFragmentPresenter,Skin> {
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getListView().getRecyclerView().setClipToPadding(false);
        getListView().getRecyclerView().setPadding(0,60,0,0);
    }

    @Override
    public BaseViewHolder<Skin> createViewHolder(ViewGroup parent, int viewType) {

        return new MyViewHolder(parent,getPresenter().mTest);
    }

    @Override
    public ListConfig getListConfig() {
        return super.getListConfig().setLoadMoreAble(false).setRefreshAble(false);
    }
}
