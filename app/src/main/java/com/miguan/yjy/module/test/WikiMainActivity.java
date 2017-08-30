package com.miguan.yjy.module.test;

import android.os.Bundle;
import android.view.ViewGroup;

import com.dsk.chain.bijection.RequiresPresenter;
import com.dsk.chain.expansion.list.BaseListActivity;
import com.dsk.chain.expansion.list.ListConfig;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.miguan.yjy.R;

/**
 * @作者 cjh
 * @日期 2017/8/23 16:00
 * @描述
 */
@RequiresPresenter(WikiMainPresenter.class)
public class WikiMainActivity extends BaseListActivity<WikiMainPresenter> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setToolbarTitle("护肤科普");
    }

    @Override
    protected BaseViewHolder createViewHolder(ViewGroup parent, int viewType) {

        return new WikiViewHolder(parent);
    }

    @Override
    public ListConfig getListConfig() {
        return super.getListConfig().setContainerLayoutRes(R.layout.common_activity_list).setFooterNoMoreRes(R.layout.include_default_footer).setLoadMoreAble(false);
    }
}
