package com.miguan.yjy.module.test;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dsk.chain.bijection.RequiresPresenter;
import com.dsk.chain.expansion.list.BaseListActivity;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.miguan.yjy.R;
import com.miguan.yjy.adapter.TestTabPagerAdapter;
import com.miguan.yjy.adapter.viewholder.TestListViewHolder;
import com.miguan.yjy.model.bean.Test;

import java.util.List;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setToolbarTitle(R.string.text_test_recommend_product);
        ButterKnife.bind(this);
    }

    @Override
    protected BaseViewHolder createViewHolder(ViewGroup parent, int viewType) {
        return new TestListViewHolder(parent);
    }

    @Override
    protected int getLayout() {
        return R.layout.test_activity_recomend;
    }

    public void setData(List<Test>tests) {
        TestTabPagerAdapter testTabPagerAdapter = new TestTabPagerAdapter(getSupportFragmentManager(),TestRecomendActivity.this,0,tests);

        mtabTest.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        for (int i=0; i<testTabPagerAdapter.getCount(); i++) {
            TabLayout.Tab tab = mtabTest.newTab();
            mtabTest.addTab(tab);
            tab.setText(testTabPagerAdapter.getPageTitle(i));
        }

    }


}
