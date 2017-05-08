package com.miguan.yjy.module.template;

import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import com.dsk.chain.bijection.ChainBaseActivity;
import com.dsk.chain.bijection.RequiresPresenter;
import com.miguan.yjy.R;
import com.miguan.yjy.model.bean.Product;
import com.miguan.yjy.utils.LUtils;
import com.miguan.yjy.utils.ScreenShot;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Copyright (c) 2017/4/5. LiaoPeiKun Inc. All rights reserved.
 */
@RequiresPresenter(GenTemplatePresenter.class)
public class GenTemplateActivity extends ChainBaseActivity<GenTemplatePresenter> implements View.OnClickListener {

    @BindView(R.id.fl_template_gen_add)
    FrameLayout mFlAdd;

    @BindView(R.id.rcv_template_gen_list)
    RecyclerView mRcvList;

    private List<BaseTemplateViewHolder> mViewHolders;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.template_activity_gen);
        setToolbarTitle(R.string.text_title_template);
        ButterKnife.bind(this);

        mViewHolders = new ArrayList<>();

        mToolbar.setNavigationOnClickListener(v -> new AlertDialog.Builder(GenTemplateActivity.this)
                .setMessage("确认退出模板")
                .setNegativeButton("取消", null)
                .setPositiveButton("退出", (dialog, which) -> finish()).show());
        mRcvList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mRcvList.setAdapter(getPresenter().getAdapter());
        mRcvList.setHasFixedSize(true);
        mFlAdd.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_gen, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        getExpansionDelegate().showProgressBar("正在生成图片");

        getPresenter().hideCursor();

        for (BaseTemplateViewHolder viewHolder : mViewHolders) {
            viewHolder.hideOperatingViews();
        }
        ScreenShot.getInstance().takeScreenShotOfJustView(mRcvList, (path, uri) -> {
            getExpansionDelegate().hideProgressBar();
            SaveTemplatePresenter.start(GenTemplateActivity.this, path, uri);
            finish();
        });

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        GenTemplatePresenter.TemplateAdapter adapter = getPresenter().getAdapter();
        if (adapter.getCount() >= 3) {
            LUtils.toast("最多只能添加3个");
            return;
        }
        adapter.add(new Product());
    }

    public List<BaseTemplateViewHolder> getViewHolders() {
        return mViewHolders;
    }
}
