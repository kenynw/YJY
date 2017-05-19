package com.miguan.yjy.module.template;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.dsk.chain.bijection.ChainBaseActivity;
import com.dsk.chain.bijection.RequiresPresenter;
import com.miguan.yjy.R;
import com.miguan.yjy.utils.LUtils;
import com.miguan.yjy.utils.ScreenShot;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.miguan.yjy.module.template.GenTemplatePresenter.EXTRA_TEMPLATE;

/**
 * Copyright (c) 2017/4/5. LiaoPeiKun Inc. All rights reserved.
 */
@RequiresPresenter(GenTemplatePresenter.class)
public class GenTemplateActivity extends ChainBaseActivity<GenTemplatePresenter> {

    @BindView(R.id.fl_template_gen_add)
    FrameLayout mFlAdd;

    @BindView(R.id.ll_template_gen)
    LinearLayout mLlGen;

    private List<BaseTemplateViewHolder> mViewHolders;

    private View mHeader;

    private View mFooter;

    private List<View> mViewList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.template_activity_gen);
        setToolbarTitle(R.string.text_title_template);
        ButterKnife.bind(this);
        mToolbar.setNavigationOnClickListener(v -> new AlertDialog.Builder(GenTemplateActivity.this)
                .setMessage("确认退出模板")
                .setNegativeButton("取消", null)
                .setPositiveButton("退出", (dialog, which) -> finish()).show());

        mViewHolders = new ArrayList<>();

        int index = getIntent().getIntExtra(EXTRA_TEMPLATE, 0);
        int layoutRes = getResources().getIdentifier("item_list_template_" + index, "layout", getPackageName());
        int headerRes = getResources().getIdentifier("header_template_" + index, "layout", getPackageName());
        int footerRes = getResources().getIdentifier("footer_template_" + index, "layout", getPackageName());

        addHeader(headerRes);
        addItem(layoutRes);
        addFooter(footerRes);

        mFlAdd.setOnClickListener(v -> addItem(layoutRes));
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
        ScreenShot.getInstance().takeScreenShotOfJustView(mLlGen, (path, uri) -> {
            getExpansionDelegate().hideProgressBar();
            SaveTemplatePresenter.start(GenTemplateActivity.this, path, uri);
            finish();
        });

        return super.onOptionsItemSelected(item);
    }

    public List<BaseTemplateViewHolder> getViewHolders() {
        return mViewHolders;
    }

    private void addHeader(@LayoutRes int res) {
        if (res <= 0) return;
        if (mHeader == null) {
            mHeader = LayoutInflater.from(this).inflate(res, null);
        }
        if (mLlGen.indexOfChild(mHeader) != -1) {
            return;
        }
        mLlGen.addView(mHeader, 0);
    }

    private void addItem(@LayoutRes int res) {
        if (res <= 0) return;

        if (mViewList.size() >= 5) {
            LUtils.toast("最多只能添加5个");
            return;
        }

        View view = LayoutInflater.from(this).inflate(res, null, false);
        TemplateView templateView = ButterKnife.findById(view, R.id.template_view_item);
        templateView.setOnDeleteListener(() -> {
            mLlGen.removeView(view);
            mViewList.remove(view);
        });

        int index = mLlGen.indexOfChild(mFooter) != -1 ? mLlGen.indexOfChild(mFooter) : mLlGen.getChildCount();
        mLlGen.addView(templateView, index);
        mViewList.add(templateView);
    }

    private void addFooter(@LayoutRes int res) {
        if (res <= 0) return;
        if (mFooter == null) {
            mFooter = LayoutInflater.from(this).inflate(res, null);
        }
        if (mLlGen.indexOfChild(mFooter) != -1) {
            return;
        }
        mLlGen.addView(mFooter, mLlGen.getChildCount());
    }

}
