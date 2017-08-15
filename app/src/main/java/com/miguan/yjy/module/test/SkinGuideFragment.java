package com.miguan.yjy.module.test;

import android.view.ViewGroup;

import com.dsk.chain.bijection.RequiresPresenter;
import com.dsk.chain.expansion.list.BaseListFragment;
import com.dsk.chain.expansion.list.ListConfig;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.decoration.DividerDecoration;
import com.miguan.yjy.R;
import com.miguan.yjy.adapter.viewholder.WikiViewHolder;
import com.miguan.yjy.model.bean.Wiki;
import com.miguan.yjy.utils.LUtils;

/**
 * @作者 cjh
 * @日期 2017/6/21 14:33
 * @描述
 */
@RequiresPresenter(SkinGuideFragmentPresenter.class)
public class SkinGuideFragment extends BaseListFragment<SkinGuideFragmentPresenter,Wiki.RelationInfo> {
    @Override
    public BaseViewHolder<Wiki.RelationInfo> createViewHolder(ViewGroup parent, int viewType) {
        return new WikiViewHolder(parent);
    }

    @Override
    public ListConfig getListConfig() {
        DividerDecoration decoration = new DividerDecoration(
                getResources().getColor(R.color.bgWindow),
                LUtils.dp2px(1),
                LUtils.dp2px(28),
                LUtils.dp2px(28)
        );
        return super.getListConfig()
                .setRefreshAble(false)
                .setLoadMoreAble(false)
                .setItemDecoration(decoration)
                .setContainerEmptyAble(false);
    }

}
