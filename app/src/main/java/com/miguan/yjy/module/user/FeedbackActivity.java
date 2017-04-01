package com.miguan.yjy.module.user;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.dsk.chain.bijection.ChainBaseActivity;
import com.dsk.chain.bijection.RequiresPresenter;
import com.miguan.yjy.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Copyright (c) 2017/4/1. LiaoPeiKun Inc. All rights reserved.
 */
@RequiresPresenter(FeedbackPresenter.class)
public class FeedbackActivity extends ChainBaseActivity<FeedbackPresenter> {

    @BindView(R.id.et_input_content)
    EditText mEtContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.common_activity_input);
        setToolbarTitle(R.string.btn_feedback);
        ButterKnife.bind(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_save, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        return super.onOptionsItemSelected(item);
    }
}
