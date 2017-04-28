package com.miguan.yjy.module.product;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.dsk.chain.bijection.RequiresPresenter;
import com.dsk.chain.expansion.list.BaseListActivity;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.miguan.yjy.R;
import com.miguan.yjy.adapter.ProductReadTabPagerAdapter;
import com.miguan.yjy.adapter.viewholder.ProductReadViewHolder;
import com.miguan.yjy.model.bean.Component;
import com.miguan.yjy.model.bean.ComponentTag;
import com.miguan.yjy.model.bean.Product;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @作者 cjh
 * @日期 2017/3/29 9:57
 * @描述 官方解读
 */
@RequiresPresenter(ProductReadPresenter.class)
public class ProductReadActivity extends BaseListActivity<ProductReadPresenter> implements View.OnClickListener {
    //    @BindView(R.id.recy_product_read)
//    RecyclerView mRecyProductRead;
    //    @BindView(R.id.tv_product_compare)
//    TextView mTvProductCompare;
//    @BindView(R.id.tv_product_no_compare)
//    TextView mTvProductNoCompare;
//    @BindView(R.id.tv_product_safe)
//    TextView mTvProductSafe;
//    @BindView(R.id.tv_product_function_info)
//    TextView mTvProductFunctionInfo;
    @BindView(R.id.tab_product_read)
    TabLayout mTabProductRead;
    private int type = 0;
//    private ProductReadAdapter productReadAdapter;
    private List<Component> mReadLists = new ArrayList<>();
    private List<ComponentTag> mComponentTags = new ArrayList<>();
    ProductReadTabPagerAdapter tabadapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_activity_read);
        ButterKnife.bind(this);
        setToolbarTitle(R.string.tv_title_read);
        initData();
        initListener();
    }

    @Override
    protected BaseViewHolder createViewHolder(ViewGroup parent, int viewType) {
        return new ProductReadViewHolder(parent);
    }


    private void initData() {
        Log.e("test", mReadLists.size()+"==走吗====");
        if (getPresenter().type == 1) {
            mReadLists = getEffectLists(getPresenter().product);
            mComponentTags=  getPresenter().product.getEffect();
            Log.e("mReadLists.size()", mReadLists.size()+"======");

        } else if (getPresenter().type == 2) {
            mReadLists = getSafeLists(getPresenter().product);
            mComponentTags=  getPresenter().product.getSecurity();
            Log.e("mReadLists.size(22222)", mReadLists.size()+"======");
        } else {

        }
//        mRecyProductRead.setFocusable(false);
//        mRecyProductRead.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
//        productReadAdapter = new ProductReadAdapter(ProductReadActivity.this, mReadLists);
//        mRecyProductRead.setAdapter(productReadAdapter);
        tabadapter = new ProductReadTabPagerAdapter(getSupportFragmentManager(), ProductReadActivity.this, 0, mReadLists,mComponentTags);
//       int positon= getPresenter().position;
//        mTabProductRead.getTabAt(getPresenter().position).select();
//        mTabProductRead.addTab( mTabProductRead.newTab(),getPresenter().position);
        mTabProductRead.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                tabadapter.notifyDataSetChanged();

            }
        });

        for (int i = 0; i < tabadapter.getCount(); i++) {
            TabLayout.Tab tab = mTabProductRead.newTab();
            mTabProductRead.addTab(tab);
            tab.setText(tabadapter.getPageTitle(i));
        }

    }

    private void initListener() {
//        mTvProductCompare.setSelected(true);
//        mTvProductCompare.setOnClickListener(this);
//        mTvProductNoCompare.setOnClickListener(this);
//        mTvProductSafe.setOnClickListener(this);
//        mTvProductFunctionInfo.setOnClickListener(this);

    }

//    @Override
//    public void onClick(View v) {
//        switch (v.getId()) {
//            case R.id.tv_product_compare:
//                mTvProductCompare.setSelected(true);
//                mTvProductNoCompare.setSelected(false);
//                mTvProductSafe.setSelected(false);
//                mTvProductFunctionInfo.setSelected(false);
//                getRecommendLists(getPresenter().product);
//                break;
//            case R.id.tv_product_no_compare:
//                getNoRecommendLists(getPresenter().product);
//                mTvProductCompare.setSelected(false);
//                mTvProductNoCompare.setSelected(true);
//                mTvProductSafe.setSelected(false);
//                mTvProductFunctionInfo.setSelected(false);
//                break;
//            case R.id.tv_product_safe:
//                getSafeLists(getPresenter().product);
//                mTvProductCompare.setSelected(false);
//                mTvProductNoCompare.setSelected(false);
//                mTvProductSafe.setSelected(true);
//                mTvProductFunctionInfo.setSelected(false);
//                break;
//            case R.id.tv_product_function_info:
//
//                getEffectLists(getPresenter().product);
//                mTvProductCompare.setSelected(false);
//                mTvProductNoCompare.setSelected(false);
//                mTvProductSafe.setSelected(false);
//                mTvProductFunctionInfo.setSelected(true);
//                break;
//        }
//    }

    private List<Component> getRecommendLists(Product product) {
//        productReadAdapter.clear();
        mReadLists.clear();
        for (int i = 0; i < product.getRecommend().size(); i++) {
            for (int j = 0; j < product.getComponentList().size(); j++) {
                Component component = product.getComponentList().get(j);
                List<String> recommendId = product.getRecommend();
                String productId = component.getId();
                if (recommendId.contains(productId)) {
                    mReadLists.add(component);
                }
            }
        }
//        productReadAdapter.addAll(mReadLists);
        return mReadLists;
    }

    private List<Component> getNoRecommendLists(Product product) {
//        productReadAdapter.clear();
        mReadLists.clear();
        for (int i = 0; i < product.getNotRecommend().size(); i++) {
            for (int j = 0; j < product.getComponentList().size(); j++) {
                Component component = product.getComponentList().get(j);
                List<String> noRecommendId = product.getNotRecommend();
                String productId = component.getId();
                if (noRecommendId.contains(productId)) {
                    mReadLists.add(component);
                }
            }
        }
//        productReadAdapter.addAll(mReadLists);
        return mReadLists;
    }

    private List<Component> getEffectLists(Product product) {
//        productReadAdapter.clear();
        mReadLists.clear();
        for (int i = 0; i < product.getEffect().size(); i++) {
            for (int j = 0; j < product.getComponentList().size(); j++) {
                Component component = product.getComponentList().get(j);
                List<String> effectId = product.getEffect().get(i).getId();
                String productId = component.getId();
                if (effectId.contains(productId)) {
                    mReadLists.add(component);
                }
            }
        }
//        productReadAdapter.addAll(mReadLists);
        return mReadLists;
    }

    private List<Component> getSafeLists(Product product) {
//        productReadAdapter.clear();
        mReadLists.clear();
        for (int i = 0; i < product.getSecurity().size(); i++) {
            for (int j = 0; j < product.getComponentList().size(); j++) {
                Component component = product.getComponentList().get(j);
                List<String> effectId = product.getSecurity().get(i).getId();
                String productId = component.getId();
                if (effectId.contains(productId)) {
                    mReadLists.add(component);
                }
            }
        }
//        productReadAdapter.addAll(mReadLists);
        return mReadLists;
    }

    @Override
    public void onClick(View v) {

    }
}
