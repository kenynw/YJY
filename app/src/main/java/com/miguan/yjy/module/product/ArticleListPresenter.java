package com.miguan.yjy.module.product;

import android.os.Bundle;

import com.dsk.chain.expansion.list.BaseListFragmentPresenter;
import com.miguan.yjy.adapter.BrandPagerAdapter;
import com.miguan.yjy.model.ArticleModel;
import com.miguan.yjy.model.bean.Article;

/**
 * @作者 cjh
 * @日期 2017/5/19 18:12
 * @描述
 */

public class ArticleListPresenter extends BaseListFragmentPresenter<ArticleListFragment,Article> {

    long brandId;
    @Override
    protected void onCreate(ArticleListFragment view, Bundle saveState) {
        super.onCreate(view, saveState);
        brandId = getView().getArguments().getLong(BrandPagerAdapter.EXTRA_BRAND_ID);
        onRefresh();
    }

    @Override
    public void onRefresh() {
        super.onRefresh();
        ArticleModel.getInstance().getArticleList(brandId,1).unsafeSubscribe(getRefreshSubscriber());
    }

    @Override
    public void onLoadMore() {
        super.onLoadMore();
        ArticleModel.getInstance().getArticleList(brandId,getCurPage()).unsafeSubscribe(getMoreSubscriber());
    }
}
