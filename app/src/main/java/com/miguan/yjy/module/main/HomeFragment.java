package com.miguan.yjy.module.main;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.dsk.chain.bijection.RequiresPresenter;
import com.dsk.chain.expansion.list.BaseListFragment;
import com.dsk.chain.expansion.list.ListConfig;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.miguan.yjy.R;
import com.miguan.yjy.adapter.viewholder.ArticleViewHolder;
import com.miguan.yjy.model.bean.Article;
import com.miguan.yjy.utils.LUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Copyright (c) 2017/3/17. LiaoPeiKun Inc. All rights reserved.
 */
@RequiresPresenter(HomeFragmentPresenter.class)
public class HomeFragment extends BaseListFragment<HomeFragmentPresenter, Article> {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @BindView(R.id.fl_home_search)
    FrameLayout mflSearch;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, view);

        getListView().getSwipeToRefresh().setProgressViewOffset(true, 100, 200);
        getListView().setOnScrollListener(new RecyclerView.OnScrollListener() {
            int y = 0;

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                y += dy;
                float ratio = y * 1.0f / LUtils.dp2px(200)  > 1 ? 1 : y * 1.0f / LUtils.dp2px(200);

                int white = changeAlpha(getResources().getColor(R.color.white), ratio);
                mToolbar.setBackgroundColor(white);

                int gray = (int) (255 - (10 * ratio));
                GradientDrawable gd = new GradientDrawable();
                gd.setColor(Color.argb(255, gray, gray, gray));
                gd.setCornerRadius(LUtils.dp2px(32));
                mflSearch.setBackgroundDrawable(gd);
            }
        });
        return view;
    }

    @Override
    public ListConfig getListConfig() {
        return super.getListConfig().setContainerLayoutRes(R.layout.main_fragment_home).hasItemDecoration(false);
    }

    @Override
    public BaseViewHolder<Article> createViewHolder(ViewGroup parent, int viewType) {
        return new ArticleViewHolder(parent);
    }

    @Override
    public void setMenuVisibility(boolean menuVisible) {
        super.setMenuVisibility(menuVisible);
        if (getView() != null) {
            getView().setVisibility(menuVisible ? View.VISIBLE : View.INVISIBLE);
        }
    }

    private int changeAlpha(int color, float fraction) {
        int red = Color.red(color);
        int green = Color.green(color);
        int blue = Color.blue(color);
        int alpha = (int) (Color.alpha(color) * fraction);
        return Color.argb(alpha, red, green, blue);
    }

}
