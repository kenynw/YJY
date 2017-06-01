package com.miguan.yjy.module.product;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dsk.chain.bijection.RequiresPresenter;
import com.dsk.chain.expansion.list.BaseListFragment;
import com.dsk.chain.expansion.list.ListConfig;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.miguan.yjy.R;
import com.miguan.yjy.adapter.viewholder.SearchReslutViewHolder;
import com.miguan.yjy.model.bean.Product;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @作者 cjh
 * @日期 2017/5/19 15:53
 * @描述
 */
@RequiresPresenter(StarProductPresenter.class)
public class StarProductFragment extends BaseListFragment<StarProductPresenter, Product> {


    @BindView(R.id.recycle)
    EasyRecyclerView mRecycle;
    Unbinder unbinder;


    @Override
    public BaseViewHolder<Product> createViewHolder(ViewGroup parent, int viewType) {
        return new SearchReslutViewHolder(parent, true);
    }

    @Override
    public int getLayout() {
        return R.layout.common_recylerview;
    }

    @Override
    public ListConfig getListConfig() {

        return super.getListConfig().setLoadMoreAble(false).setNoMoreAble(false);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
