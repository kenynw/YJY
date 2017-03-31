package com.miguan.yjy.module.user;

import android.content.Context;
import android.content.Intent;

import com.dsk.chain.expansion.data.BaseDataActivityPresenter;
import com.miguan.yjy.model.bean.User;
import com.miguan.yjy.model.local.UserPreferences;

/**
 * Copyright (c) 2017/3/31. LiaoPeiKun Inc. All rights reserved.
 */

public class ProfilePresenter extends BaseDataActivityPresenter<ProfileActivity, User> {

    public static final String EXTRA_USER = "user";

    public static void start(Context context, User user) {
        Intent intent = new Intent(context, ProfileActivity.class);
        intent.putExtra(EXTRA_USER, user);
        context.startActivity(intent);
    }

    @Override
    protected void onCreateView(ProfileActivity view) {
        super.onCreateView(view);
        publishObject(getView().getIntent().getParcelableExtra(EXTRA_USER));
    }

    public void logout() {
        UserPreferences.setUserID(0);
        if (UserPreferences.getUserID() <= 0) {
            getView().finish();
        }
    }

}
