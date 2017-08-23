package com.miguan.yjy.module.test;

import android.view.ViewGroup;

import com.dsk.chain.bijection.RequiresPresenter;
import com.dsk.chain.expansion.list.BaseListActivity;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;

/**
 * @作者 cjh
 * @日期 2017/8/23 16:00
 * @描述
 */
@RequiresPresenter(WikiMainPresenter.class)
public class WikiMainActivity extends BaseListActivity<WikiMainPresenter> {

    @Override
    protected BaseViewHolder createViewHolder(ViewGroup parent, int viewType) {
        return new WikiViewHolder(parent);
    }

}
