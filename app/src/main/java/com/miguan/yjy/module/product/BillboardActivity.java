package com.miguan.yjy.module.product;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;

import com.dsk.chain.bijection.RequiresPresenter;
import com.dsk.chain.expansion.data.BaseDataActivity;
import com.facebook.drawee.view.SimpleDraweeView;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.jude.easyrecyclerview.decoration.DividerDecoration;
import com.miguan.yjy.R;
import com.miguan.yjy.model.bean.Product;
import com.miguan.yjy.model.bean.Rank;
import com.miguan.yjy.module.common.LargeImageActivity;
import com.miguan.yjy.utils.LUtils;
import com.miguan.yjy.widget.SharePopupWindow;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Copyright (c) 2017/7/27. LiaoPeiKun Inc. All rights reserved.
 */
@RequiresPresenter(BillboardActivityPresenter.class)
public class BillboardActivity extends BaseDataActivity<BillboardActivityPresenter, Rank> {

    @BindView(R.id.recy_billboard_list)
    RecyclerView mRecyList;

    @BindView(R.id.recy_billboard_other)
    RecyclerView mRecyOther;

    @BindView(R.id.dv_billboard_image)
    SimpleDraweeView mDvImage;

    private RecyclerArrayAdapter<Product> mProductAdapter = new RecyclerArrayAdapter<Product>(this) {
        @Override
        public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
            return new BillboardProductViewHolder(parent);
        }
    };

    private RecyclerArrayAdapter<Rank> mRankAdapter = new RecyclerArrayAdapter<Rank>(this) {
        @Override
        public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
            return new BillboardViewHolder(parent);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_activity_billboard);
        ButterKnife.bind(this);

        int transparent = getResources().getColor(R.color.transparent);
        mRecyList.setLayoutManager(new LinearLayoutManager(this));
        mRecyList.setAdapter(mProductAdapter);
        mRecyList.addItemDecoration(new DividerDecoration(transparent, LUtils.dp2px(1),
                LUtils.dp2px(15), LUtils.dp2px(15)));
        mRecyOther.setLayoutManager(new LinearLayoutManager(this));
        mRecyOther.setAdapter(mRankAdapter);
        mRecyOther.addItemDecoration(new DividerDecoration(transparent,
                (int) getResources().getDimension(R.dimen.spacing_small)));
    }

    @Override
    public void setData(Rank rank) {
        Rank rankInfo = rank.getRankingInfo();
        setToolbarTitle(rankInfo.getRank_name() + "TOP" + rank.getProductList().size());
        mDvImage.setImageURI(rankInfo.getRank_banner());
        mDvImage.setOnClickListener(v -> LargeImageActivity.start(this, rankInfo.getRank_banner()));
        mProductAdapter.addAll(rank.getProductList());
        mRankAdapter.addAll(rank.getRelation_info());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_share, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Rank data = getPresenter().getData().getRankingInfo();
        if (data != null) {
            String title = "经典口碑产品，让你摆脱选择恐惧症";
            new SharePopupWindow.Builder(this)
                    .setTitle(data.getRank_name() + "【颜究院】")
                    .setContent(title)
                    .setUrl(data.getShare_url())
                    .setImageUrl(data.getRank_banner())
                    .setWxCircleTitle(data.getRank_name() + title)
                    .setWbContent(data.getRank_name() + title + ", 分享来自#颜究院APP#" + data.getShare_url())
                    .show(getContent());
        }
        return super.onOptionsItemSelected(item);
    }

}
