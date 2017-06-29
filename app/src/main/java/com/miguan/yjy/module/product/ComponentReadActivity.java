package com.miguan.yjy.module.product;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dsk.chain.bijection.RequiresPresenter;
import com.dsk.chain.expansion.list.BaseListActivity;
import com.dsk.chain.expansion.list.ListConfig;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.miguan.yjy.R;
import com.miguan.yjy.adapter.viewholder.SearchReslutViewHolder;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @作者 cjh
 * @日期 2017/6/27 13:45
 * @描述
 */
@RequiresPresenter(ComponentReadPresenter.class)
public class ComponentReadActivity extends BaseListActivity<ComponentReadPresenter> {

//    @BindView(R.id.recy_component_read)
//    EasyRecyclerView mRecyComponentRead;
    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.ll_toolbar)
    LinearLayout mLlToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_component_read);
        ButterKnife.bind(this);
        setToolbarTitle("成分表");
    }

    @Override
    protected BaseViewHolder createViewHolder(ViewGroup parent, int viewType) {
        return new SearchReslutViewHolder(parent,false);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_component_read;
    }

    @Override
    public ListConfig getListConfig() {
        return super.getListConfig().setLoadMoreAble(true);
    }
}
