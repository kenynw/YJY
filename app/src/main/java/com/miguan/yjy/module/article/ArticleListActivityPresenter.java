package com.miguan.yjy.module.article;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.dsk.chain.bijection.Presenter;
import com.miguan.yjy.adapter.viewholder.ArticleCate;

import java.util.ArrayList;
import java.util.List;

/**
 * Copyright (c) 2017/6/15. LiaoPeiKun Inc. All rights reserved.
 */

public class ArticleListActivityPresenter extends Presenter<ArticleListActivity> {

    public static final String EXTRA_CATE_LIST = "cate_list";

    public static final String EXTRA_CUR_POSITION = "cur_position";

    private PagerAdapter mAdapter;

    private List<ArticleCate> mCateList;

    private int mCurPosition;

    public static void start(Context context, ArrayList<ArticleCate> list, int curPos) {
        Intent intent = new Intent(context, ArticleListActivity.class);
        intent.putParcelableArrayListExtra(EXTRA_CATE_LIST, list);
        intent.putExtra(EXTRA_CUR_POSITION, curPos);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(ArticleListActivity view, Bundle saveState) {
        super.onCreate(view, saveState);
        mCateList = getView().getIntent().getParcelableArrayListExtra(EXTRA_CATE_LIST);
        mCurPosition = getView().getIntent().getIntExtra(EXTRA_CUR_POSITION, 0);
    }

    public PagerAdapter getAdapter() {
        if (mAdapter == null) {
            mAdapter = new PagerAdapter(getView().getSupportFragmentManager());
        }
        return mAdapter;
    }

    private class PagerAdapter extends FragmentStatePagerAdapter {

        private List<Fragment> mFragments;

        public PagerAdapter(FragmentManager fm) {
            super(fm);
            mFragments = new ArrayList<>();
            for (ArticleCate articleCate : mCateList) {
                mFragments.add(ArticleListFragmentPresenter.get(articleCate.getId()));
            }
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mCateList.get(position).getCate_name();
        }

    }

}
