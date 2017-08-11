package com.miguan.yjy.module.main;

import android.content.Intent;

import com.dsk.chain.bijection.ChainBaseActivity;
import com.dsk.chain.expansion.data.BaseDataFragmentPresenter;
import com.miguan.yjy.model.AccountModel;
import com.miguan.yjy.model.UserModel;
import com.miguan.yjy.model.bean.User;
import com.miguan.yjy.module.account.LoginActivity;
import com.umeng.socialize.UMShareAPI;

/**
 * Copyright (c) 2017/3/30. LiaoPeiKun Inc. All rights reserved.
 */

public class MeFragmentPresenter extends BaseDataFragmentPresenter<MeFragment, User> {

    public void loadData() {
        if (AccountModel.getInstance().isLogin()) {
            UserModel.getInstance().getUserInfo().unsafeSubscribe(getSubscriber());
        } else {
            getView().setUnloginView();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadData();
    }

    @Override
    protected void onResult(int requestCode, int resultCode, Intent data) {
        super.onResult(requestCode, resultCode, data);
        UMShareAPI.get(getView().getActivity()).onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        UMShareAPI.get(getView().getActivity()).release();
    }

    public void toActivity(Class<? extends ChainBaseActivity> cls) {
        Class target = AccountModel.getInstance().isLogin() ? cls : LoginActivity.class;
        getView().startActivity(new Intent(getView().getActivity(), target));
    }

}
