package com.miguan.yjy.module.main;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.dsk.chain.expansion.list.BaseListFragmentPresenter;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.jude.exgridview.ExGridView;
import com.miguan.yjy.R;
import com.miguan.yjy.adapter.BannerPagerAdapter;
import com.miguan.yjy.adapter.CategoryAdapter;
import com.miguan.yjy.adapter.viewholder.ArticleCate;
import com.miguan.yjy.model.AccountModel;
import com.miguan.yjy.model.ArticleModel;
import com.miguan.yjy.model.bean.Ask;
import com.miguan.yjy.model.bean.Evaluate;
import com.miguan.yjy.model.bean.Home;
import com.miguan.yjy.module.account.LoginActivity;
import com.miguan.yjy.module.product.QueryCodeActivity;
import com.miguan.yjy.module.user.UsedListActivity;
import com.miguan.yjy.utils.LUtils;
import com.miguan.yjy.widget.CirclePageIndicator;
import com.miguan.yjy.widget.HeadViewPager;
import com.miguan.yjy.widget.LoadingImageView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Copyright (c) 2017/3/20. LiaoPeiKun Inc. All rights reserved.
 */

public class HomeFragmentPresenter extends BaseListFragmentPresenter<HomeFragment, Evaluate> implements
        EvaluateArticleViewHolder.OnLoadDataListener,
        EvaluateAskViewHolder.OnLoadAskListener {

    private boolean mIsInit = false;

    private boolean mIsLogin;

    private ArrayList<ArticleCate> mArticleCates;

    private List<Ask> mAskList;

    @Override
    protected void onCreateView(HomeFragment view) {
        super.onCreateView(view);
        mAskList = new ArrayList<>();
        HomeHeader homeHeader = new HomeHeader();
        getAdapter().addHeader(homeHeader);

        ArrayList<Evaluate> evaluates = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            evaluates.add(new Evaluate());
        }
        getAdapter().addAll(evaluates);
        onRefresh();
    }

    @Override
    public void onRefresh() {
        ArticleModel.getInstance().getHomeList()
                .map(home -> {
                    mIsInit = true;
                    mIsLogin = AccountModel.getInstance().isLogin();

                    mArticleCates = home.getArticleGory();
                    getView().setSearchHint(home.getNum());
                    ((HomeHeader) getAdapter().getHeader(0)).setHome(home);
                    mAskList.clear();
                    if (home.getAsk() != null) {
                        mAskList.add(home.getAsk());
                    }
                    return home.getEvaluateList();
                })
                .doOnCompleted(() -> getView().setScrollListener())
                .unsafeSubscribe(getRefreshSubscriber());
    }

    @Override
    public void onLoadMore() {
        if (mIsInit) {
            ArticleModel.getInstance().getEssenceList(getCurPage())
                    .map(evaluate -> {
                        if (evaluate.getAsk() != null) {
                            mAskList.add(evaluate.getAsk());
                        }
                        return evaluate.getEssence();
                    })
                    .unsafeSubscribe(getMoreSubscriber());
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mIsLogin && !AccountModel.getInstance().isLogin()) {
            mAskList = null;
            onRefresh();
        }
    }

    @Override
    public ArrayList<ArticleCate> getCates() {
        return mArticleCates;
    }

    @Override
    public List<Ask> getAsks() {
        return mAskList;
    }

    public class HomeHeader implements RecyclerArrayAdapter.ItemView {

        @BindView(R.id.hv_home_banner)
        HeadViewPager mHvBanner;

        @BindView(R.id.indicator_main_home)
        CirclePageIndicator mIndicator;

        @BindView(R.id.recy_home_category)
        ExGridView mCategory;

        @BindView(R.id.btn_home_query_batch)
        ImageView mIvQuery;

        @BindView(R.id.btn_home_my_product)
        ImageView mIvMyProduct;

        @BindView(R.id.iv_home_evaluate_label)
        LoadingImageView mIvEvaluateLabel;

        private CategoryAdapter mCategoryAdapter;

        private Home mHome;

        public HomeHeader() {
            mCategoryAdapter = new CategoryAdapter(getView().getActivity());
        }

        @Override
        public View onCreateView(ViewGroup parent) {
            View view = LayoutInflater.from(getView().getActivity()).inflate(R.layout.header_main_home, parent, false);
            ButterKnife.bind(this, view);

            ViewGroup.LayoutParams lp = mHvBanner.getLayoutParams();
            lp.height = (int) (LUtils.getScreenWidth() / 1.7);
            mHvBanner.setLayoutParams(lp);

            mCategory.setAdapter(mCategoryAdapter);

            return view;
        }

        @Override
        public void onBindView(View headerView) {
            if (mHome != null) {
                mIvQuery.setImageResource(R.mipmap.bg_home_query);
                mIvQuery.setOnClickListener(v -> getView().startActivity(new Intent(getView().getActivity(), QueryCodeActivity.class)));
                mIvMyProduct.setImageResource(R.mipmap.bg_home_repository);
                mIvMyProduct.setOnClickListener(v -> getView().startActivity(new Intent(getView().getActivity(),
                        AccountModel.getInstance().isLogin() ? UsedListActivity.class : LoginActivity.class)));
                mIvEvaluateLabel.setImageResource(R.mipmap.bg_commend_evaluate);

                if (mHome.getBanner() != null && mHome.getBanner().size() > 0) {
                    mHvBanner.setAdapter(new BannerPagerAdapter(getView().getActivity(), mHome.getBanner()));
                    mIndicator.setViewPager(mHvBanner);
                }
                if (mHome.getBanner() != null && mHome.getBanner().size() <= 1)
                    mIndicator.setVisibility(View.GONE);
                mCategory.removeAllViews();
                mCategoryAdapter.addAll(mHome.getCategory());
            }
        }

        public void setHome(Home home) {
            mHome = home;
        }
    }

}
