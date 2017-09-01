package com.miguan.yjy.module.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.dsk.chain.bijection.RequiresPresenter;
import com.dsk.chain.expansion.data.BaseDataFragment;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.jude.easyrecyclerview.decoration.DividerDecoration;
import com.miguan.yjy.R;
import com.miguan.yjy.adapter.BannerPagerAdapter;
import com.miguan.yjy.adapter.MySkinAdapter;
import com.miguan.yjy.model.AccountModel;
import com.miguan.yjy.model.bean.Ask;
import com.miguan.yjy.model.bean.Discover;
import com.miguan.yjy.model.bean.Skin;
import com.miguan.yjy.model.bean.Wiki;
import com.miguan.yjy.module.account.LoginActivity;
import com.miguan.yjy.module.ask.AnswerListActivity;
import com.miguan.yjy.module.ask.AskDiscoverViewHolder;
import com.miguan.yjy.module.template.TemplatesActivity;
import com.miguan.yjy.module.test.TestInitActivity;
import com.miguan.yjy.module.test.TestResultActivity;
import com.miguan.yjy.module.test.WikiMainActivity;
import com.miguan.yjy.module.test.WikiViewHolder;
import com.miguan.yjy.utils.LUtils;
import com.miguan.yjy.widget.CirclePageIndicator;
import com.miguan.yjy.widget.HeadViewPager;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Copyright (c) 2017/8/22. LiaoPeiKun Inc. All rights reserved.
 */
@RequiresPresenter(DiscoverFragmentPresenter.class)
public class DiscoverFragment extends BaseDataFragment<DiscoverFragmentPresenter, Discover> implements View.OnClickListener {

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

    @BindView(R.id.iv_discover_template)
    ImageView mIvTemplate;

    @BindView(R.id.tv_discover_ask_more)
    TextView mTvAskMore;

    @BindView(R.id.rv_discover_asks)
    RecyclerView mRvAsks;

    @BindView(R.id.tv_discover_wiki_more)
    TextView mTvWikiMore;

    @BindView(R.id.rv_discover_wikis)
    RecyclerView mRvWikis;

    private Unbinder mUnbinder;

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
        mIvTemplate.setOnClickListener(this);

        int paddingLeft = LUtils.dp2px(25);
        DividerDecoration decoration = new DividerDecoration(
                getResources().getColor(R.color.bgWindow), LUtils.dp2px(1), paddingLeft,paddingLeft
        );
        mRvAsks.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        mRvAsks.setAdapter(mAskAdapter);
        mRvAsks.addItemDecoration(decoration);

        mRvWikis.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        mRvWikis.setAdapter(mWikiAdapter);
        mRvWikis.addItemDecoration(decoration);

        mTvSkin.setOnClickListener(v -> {
            if (AccountModel.getInstance().isLogin()) {
                startActivity(new Intent(getActivity(), TestInitActivity.class));
            } else {
                startActivity(new Intent(getActivity(), LoginActivity.class));
            }
        });

        return view;
    }

    @Override
    public void setData(Discover discover) {
        if (discover.getBanner() == null || discover.getBanner().size() <= 0) {
            mFlBanner.setVisibility(View.GONE);
        } else {
            if (discover.getBanner().size() == 1) mIndicatorBanner.setVisibility(View.GONE);
            mVpBanner.setAdapter(new BannerPagerAdapter(getActivity(), discover.getBanner()));
            mIndicatorBanner.setViewPager(mVpBanner);
        }

        mGridSkin.setAdapter(new MySkinAdapter(getActivity(), discover.getUserSkin()));
        boolean completed = true;
        for (Skin skin : discover.getUserSkin()) {
            completed = completed && !TextUtils.isEmpty(skin.getLetter());
        }
        if (completed) {
            mTvSkin.setText(R.string.text_my_skin_detail);
            mTvSkin.setOnClickListener(v -> startActivity(new Intent(getActivity(), TestResultActivity.class)));
        }
        mAskAdapter.addAll(discover.getAsk());
        mWikiAdapter.addAll(discover.getBaike());
    }

    @Override
    public void onClick(View v) {
        Intent intent = null;
        switch (v.getId()) {
            case R.id.tv_discover_ask_more:
                intent = new Intent(getActivity(), AnswerListActivity.class);
                break;
            case R.id.tv_discover_wiki_more:
                intent = new Intent(getActivity(), WikiMainActivity.class);
                break;
            case R.id.iv_discover_template:
                intent = new Intent(getActivity(), TemplatesActivity.class);
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
