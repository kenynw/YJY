package com.miguan.yjy.module.test;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dsk.chain.bijection.RequiresPresenter;
import com.dsk.chain.expansion.list.BaseListFragment;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.miguan.yjy.adapter.viewholder.SearchReslutViewHolder;
import com.miguan.yjy.model.bean.Product;
import com.miguan.yjy.module.main.TestFragmentPrensenter;

/**
 * @作者 cjh
 * @日期 2017/4/6 14:30
 * @描述
 */
@RequiresPresenter(TestFragmentPrensenter.class)
public class TestTabFragment extends BaseListFragment<TestTabFragmentPrensenter,Product> {

    public interface GetPosition {
        void getCurPricePostion();
    }

    public GetPosition mGetPosition;

    public void setGetPosition(GetPosition getPosition) {
        mGetPosition = getPosition;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public BaseViewHolder<Product> createViewHolder(ViewGroup parent, int viewType) {
        return new SearchReslutViewHolder(parent, false);
    }



}
