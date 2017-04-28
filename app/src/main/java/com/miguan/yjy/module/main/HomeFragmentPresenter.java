package com.miguan.yjy.module.main;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dsk.chain.expansion.list.BaseListFragmentPresenter;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.jude.easyrecyclerview.decoration.SpaceDecoration;
import com.miguan.yjy.R;
import com.miguan.yjy.adapter.BannerPagerAdapter;
import com.miguan.yjy.adapter.CategoryAdapter;
import com.miguan.yjy.model.ArticleModel;
import com.miguan.yjy.model.bean.Article;
import com.miguan.yjy.model.bean.Home;
import com.miguan.yjy.utils.LUtils;
import com.miguan.yjy.widget.CirclePageIndicator;
import com.miguan.yjy.widget.HeadViewPager;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Copyright (c) 2017/3/20. LiaoPeiKun Inc. All rights reserved.
 */

public class HomeFragmentPresenter extends BaseListFragmentPresenter<HomeFragment, Article> {

    public int productNum;
    @Override
    protected void onCreateView(HomeFragment view) {
        super.onCreateView(view);
        onRefresh();
    }

    @Override
    public void onRefresh() {
        ArticleModel.getInstance().getHomeList()
                .map(home -> {
                    productNum = home.getNum();
                    getView().setSearchHint(home.getNum());
                    getAdapter().removeAllHeader();
                    getAdapter().addHeader(new HomeHeader(home));
                    return home.getArticle();
                })
                .unsafeSubscribe(getRefreshSubscriber());
    }

    @Override
    public void onLoadMore() {
        ArticleModel.getInstance().getArticleList(getCurPage()).unsafeSubscribe(getMoreSubscriber());
    }

    public class HomeHeader implements RecyclerArrayAdapter.ItemView {

        @BindView(R.id.hv_home_banner)
        HeadViewPager mHvBanner;

        @BindView(R.id.indicator_main_home)
        CirclePageIndicator mIndicator;

        @BindView(R.id.recy_home_category)
        RecyclerView mCategory;

        private Home mHome;

        public HomeHeader(Home home) {
            mHome = home;
        }

        @Override
        public View onCreateView(ViewGroup parent) {
            View view = LayoutInflater.from(getView().getActivity()).inflate(R.layout.header_main_home, parent, false);
            ButterKnife.bind(this, view);

            mCategory.setLayoutManager(new GridLayoutManager(getView().getActivity(), 4));
            int space = (LUtils.getScreenWidth() - LUtils.dp2px(56) * 4) / 5;
            mCategory.addItemDecoration(new SpaceDecoration(space));

            return view;
        }

        @Override
        public void onBindView(View headerView) {
            if (mHome.getBanner() != null && mHome.getBanner().size() > 0) {
                mHvBanner.setAdapter(new BannerPagerAdapter(getView().getActivity(), mHome.getBanner()));
                mIndicator.setViewPager(mHvBanner);
            }
            if (mHome.getBanner() != null && mHome.getBanner().size() <= 1) mIndicator.setVisibility(View.GONE);
            mCategory.setAdapter(new CategoryAdapter(getView().getActivity(), mHome.getCategory()));
        }

    }

}
