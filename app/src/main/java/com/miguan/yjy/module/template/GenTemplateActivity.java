package com.miguan.yjy.module.template;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.dsk.chain.bijection.RequiresPresenter;
import com.dsk.chain.expansion.list.BaseListActivity;
import com.dsk.chain.expansion.list.ListConfig;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.decoration.SpaceDecoration;
import com.miguan.yjy.R;
import com.miguan.yjy.model.bean.Product;
import com.miguan.yjy.utils.LUtils;
import com.miguan.yjy.utils.ScreenShot;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Copyright (c) 2017/4/5. LiaoPeiKun Inc. All rights reserved.
 */
@RequiresPresenter(GenTemplatePresenter.class)
public class GenTemplateActivity extends BaseListActivity<GenTemplatePresenter> {

    @BindView(R.id.fl_template_gen_add)
    FrameLayout mFlAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setToolbarTitle(R.string.text_title_template);
        ButterKnife.bind(this);

        mFlAdd.setOnClickListener(v -> getPresenter().getAdapter().add(new Product()));
    }

    @Override
    protected BaseViewHolder createViewHolder(ViewGroup parent, int viewType) {
        return new TemplateViewHolder(parent);
    }

    @Override
    public ListConfig getListConfig() {
        SpaceDecoration spaceDecoration = new SpaceDecoration(LUtils.dp2px(8));
        spaceDecoration.setPaddingStart(false);
        spaceDecoration.setPaddingEdgeSide(false);

        return super.getListConfig()
                .setContainerLayoutRes(R.layout.template_activity_gen)
                .setItemDecoration(spaceDecoration)
                .setFooterErrorAble(false)
                .setNoMoreAble(false)
                .setLoadMoreAble(false)
                .setRefreshAble(false);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_gen, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Bitmap bitmap1 = ScreenShot.getInstance().takeScreenShotOfJustView(getListView());
        String filePath = ScreenShot.getInstance().saveScreenshotToPicturesFolder(this, bitmap1, "yjy");
        SaveTemplatePresenter.start(this, filePath);
        finish();
        return super.onOptionsItemSelected(item);
    }

}
