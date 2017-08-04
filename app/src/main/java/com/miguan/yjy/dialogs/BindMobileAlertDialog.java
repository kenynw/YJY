package com.miguan.yjy.dialogs;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextWatcher;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.miguan.yjy.R;
import com.miguan.yjy.model.AccountModel;
import com.miguan.yjy.model.services.ServicesResponse;
import com.miguan.yjy.module.common.WebViewActivity;
import com.miguan.yjy.utils.LUtils;
import com.miguan.yjy.widget.SendValidateButton;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Copyright (c) 2017/8/3. LiaoPeiKun Inc. All rights reserved.
 */

public class BindMobileAlertDialog extends BaseTopAlertDialog implements TextWatcher, View.OnClickListener {

    private int mCaptchaTextColor = 0xff707070;

    private AlertDialog mHelpDialog;

    @BindView(R.id.ll_bind_mobile)
    LinearLayout mLlBindMobile;

    @BindView(R.id.tv_user_dialog_title)
    TextView mTvTitle;

    @BindView(R.id.et_user_dialog_content)
    EditText mEtContent;

    @BindView(R.id.iv_user_dialog_close)
    ImageView mIvClose;

    @BindView(R.id.et_bind_mobile_captcha)
    EditText mEtCaptcha;

    @BindView(R.id.tv_user_dialog_cancel)
    TextView mTvCancel;

    @BindView(R.id.tv_user_dialog_next)
    TextView mTvNext;

    @BindView(R.id.iv_bind_mobile_help)
    ImageView mIvHelp;

    @BindView(R.id.tv_bind_mobile_captcha)
    SendValidateButton mBtnCaptcha;

    private View mMobileView;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        mMobileView = View.inflate(getActivity(), R.layout.dialog_bind_mobile, null);
        ButterKnife.bind(this, mMobileView);
        initView();

        return new AlertDialog.Builder(getActivity(), R.style.AppTheme_BindDialog)
                .setCancelable(false)
                .setView(mMobileView)
                .create();
    }

    public void initView() {
        mIvHelp.setOnClickListener(this);
        mIvClose.setOnClickListener(this);
        mEtContent.addTextChangedListener(this);
        mEtCaptcha.addTextChangedListener(this);
        mTvCancel.setOnClickListener(this);
        mBtnCaptcha.setOnClickListener(this);
        mBtnCaptcha.setmEnableColor(getActivity().getResources().getColor(R.color.white));
        mBtnCaptcha.setmDisableColor(mCaptchaTextColor);
        mBtnCaptcha.setmEnableString("获取验证码");
        mTvNext.setOnClickListener(this);
        LUtils.openKeyboard(mEtContent);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_bind_mobile_help:
                if (mHelpDialog == null) {
                    View dialogView = View.inflate(getActivity(), R.layout.dialog_why_bind_mobile, null);
                    TextView tvWhy = dialogView.findViewById(R.id.tv_bind_mobile_why);
                    ClickableSpan clickableSpan = new ClickableSpan() {
                        @Override
                        public void onClick(View widget) {
                            WebViewActivity.start(getActivity(), "", "http://www.cac.gov.cn/2016-06/28/c_1119122192.htm");
                        }
                    };
                    SpannableString spann = new SpannableString(tvWhy.getText());
                    spann.setSpan(clickableSpan, 14, 33, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    tvWhy.setText(spann);
                    tvWhy.setMovementMethod(LinkMovementMethod.getInstance());

                    Button btnOk = dialogView.findViewById(R.id.btn_bind_mobile_ok);
                    btnOk.setOnClickListener(v -> mHelpDialog.dismiss());

                    mHelpDialog = new AlertDialog.Builder(getActivity()).setView(dialogView).create();
                }

                if (!mHelpDialog.isShowing()) {
                    mHelpDialog.show();
                }

                break;
            case R.id.iv_user_dialog_close:
                mEtContent.setText("");
                break;
            case R.id.tv_user_dialog_cancel:
                hide();
                break;
            case R.id.tv_bind_mobile_captcha:
                if (mEtContent.getText().length() <= 0) {
                    LUtils.toast("手机号不能为空");
                    return;
                }

                AccountModel.getInstance().bindCaptcha(mEtContent.getText().toString())
                        .subscribe(new ServicesResponse<Boolean>() {
                            @Override
                            public void onNext(Boolean aBoolean) {
                                mBtnCaptcha.startTickWork();
                            }
                        });
                break;
            case R.id.tv_user_dialog_next:
                checkMobile();
                break;
        }
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        if (mEtContent.getText().length() > 0) {
            mIvClose.setVisibility(View.VISIBLE);
            if (!mBtnCaptcha.isDisable()) {
                mBtnCaptcha.setEnabled(true);
                mBtnCaptcha.setTextColor(getActivity().getResources().getColor(R.color.white));
            }
            mTvNext.setEnabled(mEtCaptcha.getText().length() > 0);
        } else {
            mIvClose.setVisibility(View.GONE);
            mBtnCaptcha.setEnabled(false);
            mBtnCaptcha.setTextColor(mCaptchaTextColor);
        }
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }

    private void checkMobile() {
        if (mEtContent.getText().length() <= 0) {
            LUtils.toast("手机号不能为空");
            return;
        }

        if (mEtCaptcha.getText().length() <= 0) {
            LUtils.toast("验证码不能为空");
            return;
        }

        AccountModel.getInstance()
                .checkMobile(mEtContent.getText().toString().trim(), mEtCaptcha.getText().toString().trim())
                .unsafeSubscribe(new ServicesResponse<String>() {
                    @Override
                    public void onNext(String s) {
                        getDialog().setContentView(createPasswordView());
                    }
                });
    }

    private View createPasswordView() {
        View view = View.inflate(getActivity(), R.layout.popwindow_user_set_password, null);
        EditText input = view.findViewById(R.id.et_set_password);
        TextView done = view.findViewById(R.id.tv_set_password_done);
        done.setOnClickListener(v -> {
            AccountModel.getInstance().setPassword(
                    mEtContent.getText().toString().trim(),
                    mEtCaptcha.getText().toString().trim(),
                    input.getText().toString().trim()
            )
                    .unsafeSubscribe(new ServicesResponse<String>() {
                        @Override
                        public void onNext(String result) {
                            LUtils.closeKeyboard(input);
                            dismiss();
                        }

                        @Override
                        public void onError(Throwable e) {
                            LUtils.toast("验证码错误");
                            getDialog().setContentView(mMobileView);
                        }
                    });
        });
        input.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                done.setEnabled(input.getText().length() >= 6);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        return view;
    }

    private void hide() {
        LUtils.closeKeyboard(mEtCaptcha);
        LUtils.closeKeyboard(mEtContent);
        dismiss();
    }

}
