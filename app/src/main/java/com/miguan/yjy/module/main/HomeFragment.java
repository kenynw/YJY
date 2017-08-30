 package com.miguan.yjy.module.main;

 import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.dsk.chain.bijection.RequiresPresenter;
import com.dsk.chain.expansion.list.BaseListFragment;
import com.dsk.chain.expansion.list.ListConfig;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.jude.easyrecyclerview.decoration.SpaceDecoration;
import com.miguan.yjy.R;
import com.miguan.yjy.model.bean.Evaluate;
import com.miguan.yjy.model.bean.Home;
import com.miguan.yjy.module.article.EvaluateCommendVH;
import com.miguan.yjy.module.product.SearchActivity;
import com.miguan.yjy.utils.LUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Copyright (c) 2017/3/17. LiaoPeiKun Inc. All rights reserved.
 */
@RequiresPresenter(HomeFragmentPresenter.class)
public class HomeFragment extends BaseListFragment<HomeFragmentPresenter, Evaluate> {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @BindView(R.id.fl_home_search)
    FrameLayout mFlSearch;

    @BindView(R.id.tv_home_search)
    TextView mTvSearch;

    private HomeHeader mHeader;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ButterKnife.bind(this, getRootView());
        getListView().getSwipeToRefresh().setProgressViewOffset(true, 120, 220);

        RecyclerArrayAdapter adapter = getPresenter().getAdapter();
        if (getPresenter().getAdapter().getHeaderCount() == 0) {
            mHeader = new HomeHeader(getActivity());
            adapter.addHeader(mHeader);
        }
    }

    public void setSearchHint(int count) {
        String productNum = String.format(getString(R.string.hint_home_search), count);
        mTvSearch.setText(productNum);
        mFlSearch.setOnClickListener(v -> SearchActivity.start(getActivity(), productNum));
    }

    public void setHeader(Home header) {
        mHeader.setHome(header);
    }

    @Override
    public ListConfig getListConfig() {
        SpaceDecoration decoration = new SpaceDecoration(LUtils.dp2px(8));
        decoration.setPaddingEdgeSide(false);
        decoration.setPaddingStart(false);

        return super.getListConfig()
                .setItemDecoration(decoration)
                .setContainerErrorAble(false)
                .setContainerLayoutRes(R.layout.main_fragment_home)
                .setFooterNoMoreRes(R.layout.empty_article_list);
    }

    @Override
    public BaseViewHolder<Evaluate> createViewHolder(ViewGroup parent, int viewType) {
        if (viewType == 2) {
            return new EvaluateArticleViewHolder(parent, getPresenter());
        } else {
            return new EvaluateCommendVH(parent);
        }
    }

    @Override
    public int getViewType(int position) {
        return position;
    }

    @Override
    public void setMenuVisibility(boolean menuVisible) {
        super.setMenuVisibility(menuVisible);
        if (getView() != null) {
            getView().setVisibility(menuVisible ? View.VISIBLE : View.INVISIBLE);
        }
    }

    public void setScrollListener() {
        getListView().addOnScrollListener(new RecyclerView.OnScrollListener() {
            int y = 0;

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                y += dy;
                if (y < 0) y = 0;
                float ratio = y * 1.0f / LUtils.dp2px(200) > 1 ? 1 : y * 1.0f / LUtils.dp2px(200);

                int white = changeAlpha(getResources().getColor(R.color.white), ratio);
                mToolbar.setBackgroundColor(white);

                int gray = (int) (255 - (10 * ratio));
                GradientDrawable gd = new GradientDrawable();
                gd.setColor(Color.argb(255, gray, gray, gray));
                gd.setCornerRadius(LUtils.dp2px(32));
                mFlSearch.setBackgroundDrawable(gd);
            }
        });
    }

    private int changeAlpha(int color, float fraction) {
        int red = Color.red(color);
        int green = Color.green(color);
        int blue = Color.blue(color);
        int alpha = (int) (Color.alpha(color) * fraction);
        return Color.argb(alpha, red, green, blue);
    }

}
