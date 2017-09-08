package com.miguan.yjy.module.product;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dsk.chain.bijection.RequiresPresenter;
import com.dsk.chain.expansion.list.BaseListFragment;
import com.dsk.chain.expansion.list.ListConfig;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.miguan.yjy.R;
import com.miguan.yjy.adapter.viewholder.ArticleViewHolder;
import com.miguan.yjy.model.bean.Article;
import com.miguan.yjy.utils.LUtils;

/**
 * @作者 cjh
 * @日期 2017/5/19 18:12
 * @描述
 */
@RequiresPresenter(ArticleListPresenter.class)
public class ArticleListFragment extends BaseListFragment<ArticleListPresenter, Article> {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        getListView().getRecyclerView().setPadding(0, LUtils.dp2px(15), 0, 0);
        getListView().setClipToPadding(false);
        getListView().setClipChildren(false);
        return view;
    }

    @Override
    public BaseViewHolder<Article> createViewHolder(ViewGroup parent, int viewType) {
        return new ArticleViewHolder(parent);
    }

    @Override
    public ListConfig getListConfig() {
        return super.getListConfig().setRefreshAble(false).hasItemDecoration(false).setFooterNoMoreRes(R.layout.include_default_footer);
    }

}
