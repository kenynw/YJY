package com.miguan.yjy.module.test;

import android.content.Context;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.design.widget.TabLayout;
import android.util.AttributeSet;
import android.util.Log;
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
import com.miguan.yjy.model.bean.SelectPrice;
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
        if (savedInstanceState != null) {
            mTest = savedInstanceState.getParcelable("test");
        } else {
            mTest = getIntent().getParcelableExtra(TestRecomendPresenter.EXTRA_TEST);
        }
        if (mTest != null) {
            categoryList = mTest.getCategoryList();
        }
        position = getIntent().getIntExtra(TestRecomendPresenter.EXTRA_CATEGORY_position, 0);
        name = getIntent().getStringExtra(TestRecomendPresenter.EXTRA_CATEGORY_NAME);
        ButterKnife.bind(this);
        setData();
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        outState.putParcelable("test", mTest);
    }


    @Override
    public View onCreateView(String name, Context context, AttributeSet attrs) {
        return super.onCreateView(name, context, attrs);
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
        TestTabPagerAdapter testTabPagerAdapter = new TestTabPagerAdapter(getSupportFragmentManager(), TestRecomendActivity.this, 0, mTest.getCategoryList());
        mtabTest.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                position = tab.getPosition();
                setPriceData(position);
                getPresenter().onRefresh();
                mTvTestRecommend.setText(mTest.getCategoryList().get(position).getCopy());

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        for (int i = 0; i < mTest.getCategoryList().size(); i++) {
            if (name.equals(mTest.getCategoryList().get(i).getName())) {
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

    private void setPriceData(int position) {
        ArrayList<SelectPrice> list = new ArrayList<>();
        list.clear();
        SelectPrice curSelect = mTest.getCategoryList().get(position).getCondition().get(0);
        if (curSelect.getMin() == 0 && curSelect.getMax() == 0) {

        } else {
            SelectPrice selectPrice = new SelectPrice();
            selectPrice.setMin(0);
            selectPrice.setMax(0);
            selectPrice.setSelect(true);
            list.add(0, selectPrice);
        }

        list.addAll(mTest.getCategoryList().get(position).getCondition());
        setSelectTag(0, list);
        getPresenter().maxPrice = 0;
        getPresenter().maxPrice = 0;
        getPresenter().skinPriceAdapter.onlyAddAll(list);

    }

    public void setSelectTag(int selectPosition, ArrayList<SelectPrice> list) {

        if (list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                list.get(i).setSelect(false);
                list.get(selectPosition).setSelect(true);
                Log.e("===setSelectTag===", list + "===谁为空==" + getPresenter().skinPriceAdapter);
                getPresenter().skinPriceAdapter.notifyDataSetChanged();
            }
        }
    }

    public void setSelectTag(int selectPosition) {

        if (getPresenter().selectPrices.size() > 0) {
            for (int i = 0; i < getPresenter().selectPrices.size(); i++) {
                getPresenter().selectPrices.get(i).setSelect(false);
                getPresenter().selectPrices.get(selectPosition).setSelect(true);
                Log.e("===setSelectTag===", getPresenter().selectPrices + "===谁为空==" + getPresenter().skinPriceAdapter);
                getPresenter().skinPriceAdapter.notifyDataSetChanged();
            }
        }

    }


}
