package com.miguan.yjy.module.ask;

import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

import com.dsk.chain.bijection.ChainBaseActivity;
import com.dsk.chain.bijection.RequiresPresenter;
import com.facebook.drawee.view.SimpleDraweeView;
import com.miguan.yjy.R;
import com.miguan.yjy.utils.LUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Copyright (c) 2017/6/27. LiaoPeiKun Inc. All rights reserved.
 */
@RequiresPresenter(AddAskActivityPresenter.class)
public class AddAskActivity extends ChainBaseActivity<AddAskActivityPresenter> implements TextWatcher {

    @BindView(R.id.dv_ask_product_image)
    SimpleDraweeView mDvImage;

    @BindView(R.id.tv_ask_product_name)
    TextView mTvName;

    @BindView(R.id.et_ask_add_input)
    EditText mEtAddInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ask_acitivity_add);
        setToolbarTitle("提问");
        ButterKnife.bind(this);

        mEtAddInput.addTextChangedListener(this);
    }

    public void setProduct(String img, String name, String content) {
        if (!TextUtils.isEmpty(img)) mDvImage.setImageURI(img);
        if (!TextUtils.isEmpty(name)) {
            if (name.length() > 10) {
                name = name.subSequence(0, 10) + "...";
            }
            mTvName.setText(Html.fromHtml(String.format(getString(R.string.text_ask_product_name), name)));
        }
        if (!TextUtils.isEmpty(content)) mEtAddInput.setText(content);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_publish, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mEtAddInput.getText().length() <= 0) {
            LUtils.toast("请输入问题");
            return false;
        }
        getExpansionDelegate().showProgressBar();
        getPresenter().submit(mEtAddInput.getText().toString().trim());
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        mToolbar.getMenu().getItem(0).setEnabled(mEtAddInput.getText().length() > 0);
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }

}
