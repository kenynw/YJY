package com.miguan.yjy.module.product;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dsk.chain.expansion.list.BaseListActivityPresenter;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.miguan.yjy.R;
import com.miguan.yjy.model.bean.Component;
import com.miguan.yjy.model.bean.ComponentTag;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;

/**
 * @作者 cjh
 * @日期 2017/3/29 10:52
 * @描述
 */

public class ProductReadPresenter extends BaseListActivityPresenter<ProductReadActivity, Component> implements TabLayout.OnTabSelectedListener {

    public static final String EXTRA_PRODUCT_COMPONENT_LIST = "component_list";
    public static final String EXTRA_PRODUCT_COMPONENT_TAGS = "component_tags";
    public static final String EXTRA_PRODUCT_COMPONENT_POSITION = "component_position";

    private List<Component> mComponents;

    private List<ComponentTag> mComponentTags;

    private int mPosition = 0;

    private ArrayList<Component> mReadLists = new ArrayList<>();

    public static void start(Context context, ArrayList<Component> components, ArrayList<ComponentTag> componentTags, int position) {
        Intent intent = new Intent(context, ProductReadActivity.class);
        intent.putParcelableArrayListExtra(EXTRA_PRODUCT_COMPONENT_LIST, components);
        intent.putParcelableArrayListExtra(EXTRA_PRODUCT_COMPONENT_TAGS, componentTags);
        intent.putExtra(EXTRA_PRODUCT_COMPONENT_POSITION, position);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(ProductReadActivity view, Bundle saveState) {
        super.onCreate(view, saveState);
        mComponents = getView().getIntent().getParcelableArrayListExtra(EXTRA_PRODUCT_COMPONENT_LIST);
        mComponentTags = getView().getIntent().getParcelableArrayListExtra(EXTRA_PRODUCT_COMPONENT_TAGS);
        mPosition = getView().getIntent().getIntExtra(EXTRA_PRODUCT_COMPONENT_POSITION, -1);
    }

    @Override
    protected void onCreateView(ProductReadActivity view) {
        super.onCreateView(view);
        getView().initData(mPosition, mComponentTags);
        getAdapter().addHeader(new RecyclerArrayAdapter.ItemView() {
            @Override
            public View onCreateView(ViewGroup parent) {
                return LayoutInflater.from(getView()).inflate(R.layout.include_component, parent, false);
            }

            @Override
            public void onBindView(View headerView) {

            }
        });

        onRefresh();
    }

    @Override
    public void onRefresh() {
        mReadLists.clear();
        ComponentTag componentTag = mComponentTags.get(mPosition);
        for (String s : componentTag.getId()) {
            for (Component component : mComponents) {
                if (component.getId().equals(s)) {
                    mReadLists.add(component);
                }
            }
        }

        Observable.just(mReadLists).unsafeSubscribe(getRefreshSubscriber());
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        mPosition = tab.getPosition();
        onRefresh();
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }
}
