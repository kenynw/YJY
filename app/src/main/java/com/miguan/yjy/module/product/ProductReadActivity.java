package com.miguan.yjy.module.product;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.view.View;
import android.view.ViewGroup;

import com.dsk.chain.bijection.RequiresPresenter;
import com.dsk.chain.expansion.list.BaseListActivity;
import com.dsk.chain.expansion.list.ListConfig;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.miguan.yjy.R;
import com.miguan.yjy.adapter.viewholder.ProductReadViewHolder;
import com.miguan.yjy.model.bean.ComponentTag;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @作者 cjh
 * @日期 2017/3/29 9:57
 * @描述 官方解读
 */
@RequiresPresenter(ProductReadPresenter.class)
public class ProductReadActivity extends BaseListActivity<ProductReadPresenter> {

    @BindView(R.id.tab_product_read)
    TabLayout mTabProductRead;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setToolbarTitle(R.string.tv_title_read);
        ButterKnife.bind(this);

        mTabProductRead.addOnTabSelectedListener(getPresenter());
    }

    @Override
    protected BaseViewHolder createViewHolder(ViewGroup parent, int viewType) {
        return new ProductReadViewHolder(parent);
    }

    @Override
    public ListConfig getListConfig() {
        View emptyView = getLayoutInflater().inflate(R.layout.empty_componet_list, getListView());
        return super.getListConfig()
                .setContainerLayoutRes(R.layout.product_activity_read)
                .setContainerEmptyView(emptyView)
                .setRefreshAble(false)
                .setLoadMoreAble(false)
                .hasItemDecoration(false)
                .setNoMoreAble(false);
    }

    public void initData(int position, List<ComponentTag> tags) {
        for (int i = 0; i < tags.size(); i++) {
            TabLayout.Tab tab = mTabProductRead.newTab();
            tab.setText(tags.get(i).getName());
            mTabProductRead.addTab(tab, i == position);
        }
    }

}
