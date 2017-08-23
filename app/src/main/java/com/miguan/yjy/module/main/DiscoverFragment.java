package com.miguan.yjy.module.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.TextView;

import com.dsk.chain.bijection.RequiresPresenter;
import com.dsk.chain.expansion.data.BaseDataFragment;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.miguan.yjy.R;
import com.miguan.yjy.model.bean.Ask;
import com.miguan.yjy.model.bean.Discover;
import com.miguan.yjy.model.bean.Wiki;
import com.miguan.yjy.module.ask.AnswerListActivity;
import com.miguan.yjy.module.ask.AskDiscoverViewHolder;
import com.miguan.yjy.module.test.WikiAskActivity;
import com.miguan.yjy.module.test.WikiViewHolder;
import com.miguan.yjy.widget.CirclePageIndicator;
import com.miguan.yjy.widget.HeadViewPager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Copyright (c) 2017/8/22. LiaoPeiKun Inc. All rights reserved.
 */
@RequiresPresenter(DiscoverFragmentPresenter.class)
public class DiscoverFragment extends BaseDataFragment<DiscoverFragmentPresenter, Discover> implements View.OnClickListener {

    private Unbinder mUnbinder;

    @BindView(R.id.vp_discover_banner)
    HeadViewPager mVpBanner;

    @BindView(R.id.indicator_discover_banner)
    CirclePageIndicator mIndicatorBanner;

    @BindView(R.id.fl_discover_banner)
    FrameLayout mFlBanner;

    @BindView(R.id.tv_discover_skin)
    TextView mTvSkin;

    @BindView(R.id.grid_discover_skin)
    GridView mGridSkin;

    @BindView(R.id.tv_discover_ask_more)
    TextView mTvAskMore;

    @BindView(R.id.rv_discover_asks)
    RecyclerView mRvAsks;

    @BindView(R.id.tv_discover_wiki_more)
    TextView mTvWikiMore;

    @BindView(R.id.rv_discover_wikis)
    RecyclerView mRvWikis;

    private RecyclerArrayAdapter<Ask> mAskAdapter = new RecyclerArrayAdapter<Ask>(getActivity()) {
        @Override
        public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
            return new AskDiscoverViewHolder(parent);
        }
    };

    private RecyclerArrayAdapter<Wiki> mWikiAdapter = new RecyclerArrayAdapter<Wiki>(getActivity()) {
        @Override
        public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
            return new WikiViewHolder(parent);
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.main_fragment_discover, container, false);
        mUnbinder = ButterKnife.bind(this, view);

        mTvAskMore.setOnClickListener(this);
        mTvWikiMore.setOnClickListener(this);
        mRvAsks.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        mRvAsks.setNestedScrollingEnabled(false);
        mRvAsks.setAdapter(mAskAdapter);
        mRvWikis.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        mRvWikis.setNestedScrollingEnabled(false);
        mRvWikis.setAdapter(mWikiAdapter);

        return view;
    }

    @Override
    public void setData(Discover discover) {
        mTvSkin.setText("");
        mTvSkin.setOnClickListener(v -> {

        });
        List<Ask> list = new ArrayList<>();
        for (int i=0; i<3; i++) {
            Ask ask = new Ask();
            ask.setSubject("适不适合敏感皮适不适合敏感皮适不适合敏感皮适不适合敏感皮");
            ask.setNum(3 + i);
            ask.setProduct_img("http://www.sdfad.asldf");
            list.add(ask);
        }
        mAskAdapter.addAll(list);

        List<Wiki> wikis = new ArrayList<>();
        for (int i=0; i<3; i++) {
            Wiki wiki = new Wiki();
            wiki.setQuestion("一到下午脸色就发暗是怎么回事？一到下午脸色就发暗是怎么回事");
            wiki.setContent("轻度缺氧造成的");
            wikis.add(wiki);
        }
        mWikiAdapter.addAll(wikis);
    }

    @Override
    public void onClick(View v) {
        Intent intent = null;
        switch (v.getId()) {
            case R.id.tv_discover_ask_more:
                intent = new Intent(getActivity(), AnswerListActivity.class);
                break;
            case R.id.tv_discover_wiki_more:
                intent = new Intent(getActivity(), WikiAskActivity.class);
                break;
        }
        startActivity(intent);
    }

    @Override
    public void setMenuVisibility(boolean menuVisible) {
        super.setMenuVisibility(menuVisible);
        if (getView() != null) {
            getView().setVisibility(menuVisible ? View.VISIBLE : View.INVISIBLE);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
    }

}
