package com.miguan.yjy.module.user;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.dsk.chain.bijection.ChainBaseActivity;
import com.dsk.chain.bijection.RequiresPresenter;
import com.miguan.yjy.R;
import com.miguan.yjy.model.AccountModel;
import com.miguan.yjy.module.account.LoginActivity;
import com.miguan.yjy.utils.LUtils;
import com.miguan.yjy.widget.ShareBottomDialog;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Copyright (c) 2017/8/17. LiaoPeiKun Inc. All rights reserved.
 */
@RequiresPresenter(AboutActivityPresenter.class)
public class AboutActivity extends ChainBaseActivity<AboutActivityPresenter> implements View.OnClickListener {

    @BindView(R.id.btn_about_wx_account)
    Button mBtnWxAccount;

    @BindView(R.id.btn_about_commend)
    Button mBtnCommend;

    @BindView(R.id.btn_about_market)
    Button mBtnMarket;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_activity_about);
        setToolbarTitle(R.string.btn_me_about);
        ButterKnife.bind(this);

        mBtnWxAccount.setOnClickListener(this);
        mBtnCommend.setOnClickListener(this);
        mBtnMarket.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_about_wx_account:

                break;
            case R.id.btn_about_commend:
                showSharePopupWindow();
                break;
            case R.id.btn_about_market:
                try {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse("market://details?id=" + getPackageName()));
                    startActivity(intent);
                } catch (Exception e) {
                    LUtils.toast("您的手机没有安装应用市场");
                }
                break;
        }
    }

    private void showSharePopupWindow() {
        if (AccountModel.getInstance().isLogin()) {
            new ShareBottomDialog.Builder(this)
                    .setViewTitle("推荐颜究院给")
                    .setTitle("颜究院，一款专业的护肤应用")
                    .setContent("查成分、测肤质，颜究院根据肤质推荐安全有效护肤品")
                    .setUrl("http://m.yjyapp.com/site/download")
                    .setWxCircleTitle("颜究院，帮你查成分、测肤质，根据肤质推荐安全有效护肤品，你还不用吗？")
                    .setWbContent("颜究院，帮你查成分、测肤质，根据肤质推荐安全有效护肤品，你还不用吗？#颜究院APP# http://m.yjyapp.com/site/download")
                    .show();
        } else {
            startActivity(new Intent(this, LoginActivity.class));
        }
    }

}
