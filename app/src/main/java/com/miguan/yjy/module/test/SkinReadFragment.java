package com.miguan.yjy.module.test;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dsk.chain.bijection.RequiresPresenter;
import com.dsk.chain.expansion.list.BaseListFragment;
import com.dsk.chain.expansion.list.ListConfig;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.miguan.yjy.adapter.viewholder.SkinListViewHolder;
import com.miguan.yjy.model.bean.Skin;

/**
 * @作者 cjh
 * @日期 2017/6/16 15:38
 * @描述 肤质解读
 */
@RequiresPresenter(SkinReadFragmentPresenter.class)
public class SkinReadFragment extends BaseListFragment<SkinReadFragmentPresenter, Skin> {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public BaseViewHolder<Skin> createViewHolder(ViewGroup parent, int viewType) {
       SkinListViewHolder skinListViewHolder= new SkinListViewHolder(parent);
       skinListViewHolder.setIsRecyclable(false);
        return skinListViewHolder;
    }

    @Override
    public ListConfig getListConfig() {
        return super.getListConfig().setLoadMoreAble(false);
    }
}
