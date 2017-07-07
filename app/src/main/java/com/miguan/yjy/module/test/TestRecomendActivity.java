package com.miguan.yjy.module.test;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dsk.chain.bijection.RequiresPresenter;
import com.dsk.chain.expansion.list.BaseListActivity;
import com.dsk.chain.expansion.list.ListConfig;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.miguan.yjy.R;
import com.miguan.yjy.adapter.TestTabPagerAdapter;
import com.miguan.yjy.adapter.viewholder.SearchReslutViewHolder;
import com.miguan.yjy.model.bean.Skin;
import com.miguan.yjy.model.bean.Test;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @作者 cjh
 * @日期 2017/4/5 19:01
 * @描述 推荐产品
 */
@RequiresPresenter(TestRecomendPresenter.class)
public class TestRecomendActivity extends BaseListActivity<TestRecomendPresenter> {
    @BindView(R.id.tab_test)
    TabLayout mtabTest;
    @BindView(R.id.tv_test_recommend)
    TextView mTvTestRecommend;
    @BindView(R.id.recycle)
    EasyRecyclerView mRecycle;
    @BindView(R.id.tv_test_recommend_change)
    TextView mTvTestRecommendChange;
    @BindView(R.id.ll_show_dsc)
    LinearLayout mLlShowDsc;
    public ArrayList<Skin> categoryList;
    public Test mTest;
    public int position;
    private String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setToolbarTitle(R.string.text_test_recommend_product);
        mTest = getIntent().getParcelableExtra(TestRecomendPresenter.EXTRA_TEST);
        categoryList = mTest.getCategoryList();
        position = getIntent().getIntExtra(TestRecomendPresenter.EXTRA_CATEGORY_position, 0);
        name = getIntent().getStringExtra(TestRecomendPresenter.EXTRA_CATEGORY_NAME);
        ButterKnife.bind(this);
        setData();
    }

    @Override
    protected BaseViewHolder createViewHolder(ViewGroup parent, int viewType) {
        return new SearchReslutViewHolder(parent, false);
    }

    @Override
    protected int getLayout() {
        return R.layout.test_activity_recomend;
    }

    public void setData() {
        TestTabPagerAdapter testTabPagerAdapter = new TestTabPagerAdapter(getSupportFragmentManager(), TestRecomendActivity.this, 0, categoryList);
        mtabTest.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                position = tab.getPosition();
                switch (position) {
                    case 0:
                        setSelectTag(getPresenter().mFirstPosition);
                        break;
                    case 1:
                        setSelectTag(getPresenter().mSecondPosition);
                        break;
                    case 2:
                        setSelectTag(getPresenter().mThirdPosition);
                        break;
                }

                getPresenter().onRefresh();


                if (TextUtils.isEmpty(categoryList.get(position).getCopy())) {
                    mLlShowDsc.setVisibility(View.GONE);
                } else {
                    mLlShowDsc.setVisibility(View.VISIBLE);
                }

                mTvTestRecommend.setText(categoryList.get(position).getCopy());

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        for (int i = 0; i < categoryList.size(); i++) {
            if (name.equals(categoryList.get(i).getName())) {
                position = i;
            }
        }
        for (int i = 0; i < testTabPagerAdapter.getCount(); i++) {
            TabLayout.Tab tab = mtabTest.newTab();
            mtabTest.addTab(tab, i == position);
            tab.setText(testTabPagerAdapter.getPageTitle(i));
        }

    }

    @Override
    public ListConfig getListConfig() {
        return super.getListConfig().setContainerEmptyAble(false);
    }

    public void setSelectTag(int selectPosition) {
        if (getPresenter().selectPrices.size() > 0) {
            for (int i = 0; i < getPresenter().selectPrices.size(); i++) {
                getPresenter().selectPrices.get(i).setSelect(false);
                getPresenter().selectPrices.get(selectPosition).setSelect(true);
                getPresenter().skinPriceAdapter.notifyDataSetChanged();
            }
        }

    }


}
