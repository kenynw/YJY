package com.miguan.yjy.module.product;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dsk.chain.bijection.RequiresPresenter;
import com.dsk.chain.expansion.list.BaseListActivity;
import com.dsk.chain.expansion.list.ListConfig;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.miguan.yjy.R;
import com.miguan.yjy.adapter.viewholder.BrandLetterViewHolder;
import com.miguan.yjy.adapter.viewholder.BrandViewHolder;
import com.miguan.yjy.widget.ScrollIndexer;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Copyright (c) 2017/3/28. LiaoPeiKun Inc. All rights reserved.
 */
@RequiresPresenter(BrandListPresenter.class)
public class BrandListActivity extends BaseListActivity<BrandListPresenter> {

    @BindView(R.id.indexer_brand_list)
    ScrollIndexer mIndexer;

    @BindView(R.id.tv_brand_list_index)
    TextView mTvIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setToolbarTitle(R.string.text_brand_list);
        ButterKnife.bind(this);

        mIndexer.setOnTextSelectedListener(new ScrollIndexer.OnTextSelectedListener() {
            @Override
            public void onTextSelected(int position, String text) {
                mTvIndex.setVisibility(View.VISIBLE);
                mTvIndex.setText(text);

                int selectedPosition = 0;
                for (int i=0; i<getPresenter().getAdapter().getCount(); i++) {
                    if (text.equals(getPresenter().getAdapter().getAllData().get(i).getLetter())) {
                        selectedPosition = i;
                    }
                }
                getListView().scrollToPosition(selectedPosition);
            }

            @Override
            public void onTouchUp() {
                mTvIndex.setVisibility(View.GONE);
            }
        });
    }

    @Override
    protected BaseViewHolder createViewHolder(ViewGroup parent, int viewType) {
        return viewType == 1 ? new BrandViewHolder(parent) : new BrandLetterViewHolder(parent);
    }

    @Override
    public ListConfig getListConfig() {
        return super.getListConfig().setRefreshAble(false).setContainerLayoutRes(R.layout.product_brand_list_activity);
    }

    @Override
    public int getViewType(int position) {
        if (position == 0) return 2;
        String curLetter = getPresenter().getAdapter().getItem(position).getLetter();
        String lastLetter = getPresenter().getAdapter().getItem(position - 1).getLetter();
        return TextUtils.equals(curLetter, lastLetter) ? 1 : 2;
    }

}
