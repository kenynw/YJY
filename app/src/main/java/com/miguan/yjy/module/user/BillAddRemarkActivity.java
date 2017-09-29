package com.miguan.yjy.module.user;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.dsk.chain.bijection.ChainBaseActivity;
import com.dsk.chain.bijection.RequiresPresenter;
import com.miguan.yjy.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Copyright (c) 2017/9/26. LiaoPeiKun Inc. All rights reserved.
 */
@RequiresPresenter(BillAddRemarkPresenter.class)
public class BillAddRemarkActivity extends ChainBaseActivity<BillAddRemarkPresenter> implements TextWatcher {

    @BindView(R.id.et_add_bill_remark)
    EditText mEtAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_activity_add_bill_remark);
        setToolbarTitle(R.string.text_add_bill_remark);
        ButterKnife.bind(this);
        mEtAdd.addTextChangedListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_submit, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (!TextUtils.isEmpty(mEtAdd.getText().toString().trim())) {
            getPresenter().submit(mEtAdd.getText().toString().trim());
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        getToolbar().getMenu().getItem(0).setEnabled(s.length() > 0);
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    @Override
    public int[] getHideSoftViewIds() {
        return new int[] {R.id.et_add_bill_remark};
    }

}
