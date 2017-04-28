package com.miguan.yjy.module.template;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.dsk.chain.bijection.ChainBaseActivity;
import com.dsk.chain.bijection.RequiresPresenter;
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
public class GenTemplateActivity extends ChainBaseActivity<GenTemplatePresenter> {

    public static final int REQUEST_FILTER = 0x1003;

    @BindView(R.id.fl_template_gen_add)
    FrameLayout mFlAdd;

    @BindView(R.id.rcv_template_gen_list)
    RecyclerView mRcvList;

    @BindView(R.id.tv_template_delete)
    TextView mTvDelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.template_activity_gen);
        setToolbarTitle(R.string.text_title_template);
        ButterKnife.bind(this);

        mRcvList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mRcvList.setAdapter(getPresenter().getAdapter());
        mFlAdd.setOnClickListener(v -> {
            GenTemplatePresenter.TemplateAdapter adapter = getPresenter().getAdapter();
            if (adapter.getCount() >= 5) {
                LUtils.toast("最多只能添加5个");
            } else {
                getPresenter().getAdapter().add(new Product());
            }
        });
        mTvDelete.setOnClickListener(v -> {
            GenTemplatePresenter.TemplateAdapter adapter = getPresenter().getAdapter();
            if (adapter.getCount() <= 1) {
                LUtils.toast("至少要保留一个吧");
            } else {
                adapter.remove(adapter.getCount() - 1);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_gen, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        getExpansionDelegate().showProgressBar("正在生成图片");
//        getPresenter().takeShot(mRcvList);
//        ScreenShot.getInstance().takeScreenShotOfJustView(mRcvList, );
        ScreenShot.getInstance().takeScreenShotOfJustView(mRcvList, (path, uri) -> {
            getExpansionDelegate().hideProgressBar();
            SaveTemplatePresenter.start(GenTemplateActivity.this, path, uri);
            finish();
        });

        return super.onOptionsItemSelected(item);
    }

}
