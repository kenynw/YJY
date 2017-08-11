package com.miguan.yjy.module.account;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.dsk.chain.bijection.ChainBaseActivity;
import com.dsk.chain.bijection.RequiresPresenter;
import com.miguan.yjy.R;
import com.miguan.yjy.dialogs.DialogCallback;
import com.miguan.yjy.model.AccountModel;
import com.miguan.yjy.utils.LUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Copyright (c) 2017/3/30. LiaoPeiKun Inc. All rights reserved.
 */
@RequiresPresenter(LoginPresenter.class)
public class LoginActivity extends ChainBaseActivity<LoginPresenter> implements TextWatcher, DialogCallback {

    @BindView(R.id.et_login_username)
    EditText mEtUsername;

    @BindView(R.id.et_login_password)
    EditText mEtPassword;

    @BindView(R.id.btn_login_login)
    Button mBtnSubmit;

    @BindView(R.id.tv_login_register)
    TextView mTvRegister;

    @BindView(R.id.tv_login_forgot)
    TextView mTvForgot;

    @BindView(R.id.iv_login_weixin)
    ImageView mIvLoginWeixin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.account_activity_login);
        setToolbarTitle(R.string.btn_login);
        ButterKnife.bind(this);

        UserTextWatcher watcher = new UserTextWatcher(mBtnSubmit,mEtPassword, mEtUsername);
        mEtUsername.addTextChangedListener(watcher);
        mEtPassword.addTextChangedListener(watcher);

        mBtnSubmit.setOnClickListener(v -> checkInput());
        mTvRegister.setOnClickListener(v -> startActivityForResult(new Intent(this, RegisterActivity.class), 100));
        mTvForgot.setOnClickListener(v -> startActivityForResult(new Intent(this, ForgotActivity.class), 100));
        mIvLoginWeixin.setOnClickListener(v -> getPresenter().wxLogin());
    }

    private void checkInput() {
        if (TextUtils.isEmpty(mEtUsername.getText())) {
            LUtils.toast("用户名/手机不能为空");
            return;
        }
        if (TextUtils.isEmpty(mEtPassword.getText())) {
            LUtils.toast("密码不能为空");
            return;
        }
        if (mEtPassword.getText().length() < 6 || mEtPassword.getText().length() > 15) {
            LUtils.toast("密码长度在6-15位");
            return;
        }

        getPresenter().login(mEtUsername.getText().toString().trim(), mEtPassword.getText().toString().trim());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (AccountModel.getInstance().isLogin()) finish();
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    public void closeKeyboard() {
        LUtils.closeKeyboard(mEtPassword);
        LUtils.closeKeyboard(mEtUsername);
    }

    @Override
    public void onPositiveClick(@NonNull View view) {
        getPresenter().loginSuccess();
    }

    @Override
    public void onNegativeClick(@NonNull View view) {
        getPresenter().loginSuccess();
    }

    @Override
    public int[] getHideSoftViewIds() {
        return new int[] {R.id.et_login_username, R.id.et_login_password};
    }

}
