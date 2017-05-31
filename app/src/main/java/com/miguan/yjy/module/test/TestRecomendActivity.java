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
import com.miguan.yjy.adapter.viewholder.SearchReslutViewHolder;
import com.miguan.yjy.model.bean.Skin;

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
    public ArrayList<Skin> categoryList;
    public int position;
    private String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setToolbarTitle(R.string.text_test_recommend_product);
        categoryList = getIntent().getParcelableArrayListExtra(TestRecomendPresenter.EXTRA_CATEGORY_LIST);
        position = getIntent().getIntExtra(TestRecomendPresenter.EXTRA_CATEGORY_position, 0);
        name = getIntent().getStringExtra(TestRecomendPresenter.EXTRA_CATEGORY_NAME);
        ButterKnife.bind(this);
        setData();
    }

    @Override
    protected BaseViewHolder createViewHolder(ViewGroup parent, int viewType) {
        return new SearchReslutViewHolder(parent,2);
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
                getPresenter().onRefresh();
//                TestModel.getInstantce().getSkinRecommendList(categoryList.get(position).getId(), 1).
//                        unsafeSubscribe(getPresenter().getRefreshSubscriber());
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

}
