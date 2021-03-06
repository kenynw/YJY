package com.miguan.yjy.module.account;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;

import com.dsk.chain.bijection.ChainBaseActivity;
import com.dsk.chain.bijection.RequiresPresenter;
import com.miguan.yjy.R;
import com.miguan.yjy.widget.SendValidateButton;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Copyright (c) 2017/3/30. LiaoPeiKun Inc. All rights reserved.
 */
@RequiresPresenter(ForgotPresenter.class)
public class ForgotActivity extends ChainBaseActivity<ForgotPresenter> implements TextWatcher, SendValidateButton.SendValidateButtonListener {

    @BindView(R.id.et_account_username)
    EditText mEtUsername;

    @BindView(R.id.et_account_captcha)
    EditText mEtCaptcha;

    @BindView(R.id.btn_account_captcha)
    SendValidateButton mBtnCaptcha;

    @BindView(R.id.et_account_password)
    EditText mEtPassword;

    @BindView(R.id.btn_forgot_submit)
    Button mBtnSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.account_activity_forgot);
        setToolbarTitle(R.string.text_forgot_password);
        ButterKnife.bind(this);

        UserTextWatcher watcher = new UserTextWatcher(mBtnSubmit, mEtCaptcha, mEtPassword, mEtUsername);
        mEtUsername.addTextChangedListener(watcher);
        mEtUsername.addTextChangedListener(this);
        mEtPassword.addTextChangedListener(watcher);
        mEtCaptcha.addTextChangedListener(watcher);
        if (mBtnCaptcha.isClickable()) {
            mBtnCaptcha.setTextColor(getResources().getColor(R.color.white));
       }
        mBtnCaptcha.setListener(this);
        mBtnCaptcha.setmEnableColor(getResources().getColor(R.color.white));
        mBtnCaptcha.setmEnableString("获取验证码");
        mBtnCaptcha.setOnClickListener(v->getPresenter().getCode());
        mBtnSubmit.setOnClickListener(v->getPresenter().updatePwd());
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        mBtnCaptcha.setEnabled(!TextUtils.isEmpty(mEtUsername.getText()) && !mBtnCaptcha.isDisable());
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    @Override
    public void onClickSendValidateButton() {

    }

    @Override
    public void onTick() {

    }

    @Override
    public void onTickStop() {
        mBtnCaptcha.setEnabled(!TextUtils.isEmpty(mEtUsername.getText()));
    }

    @Override
    public int[] getHideSoftViewIds() {
        return new int[] {R.id.et_account_username, R.id.et_account_password, R.id.et_account_captcha};
    }

}
