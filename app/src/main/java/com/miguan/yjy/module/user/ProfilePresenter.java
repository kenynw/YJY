package com.miguan.yjy.module.user;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.widget.PopupWindow;

import com.dsk.chain.expansion.data.BaseDataActivityPresenter;
import com.jude.library.imageprovider.ImageProvider;
import com.jude.library.imageprovider.OnImageSelectListener;
import com.miguan.yjy.model.AccountModel;
import com.miguan.yjy.model.ImageModel;
import com.miguan.yjy.model.UserModel;
import com.miguan.yjy.model.bean.TestStart;
import com.miguan.yjy.model.bean.User;
import com.miguan.yjy.model.local.UserPreferences;
import com.miguan.yjy.model.services.ServicesResponse;
import com.miguan.yjy.module.main.MainActivity;
import com.miguan.yjy.utils.LUtils;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

import cn.jpush.android.api.JPushInterface;

/**
 * Copyright (c) 2017/3/31. LiaoPeiKun Inc. All rights reserved.
 */

public class ProfilePresenter extends BaseDataActivityPresenter<ProfileActivity, User> implements OnImageSelectListener {

    public static final String KEY_PROFILE_USERNAME = "username";

    public static final String KEY_PROFILE_MOBILE = "mobile";

    public static final String KEY_PROFILE_BIRTHDAY = "birthday";

    public static final String KEY_PROFILE_CITY = "city";

    public static final String KEY_PROFILE_AVATAR = "img";

    public static final String EXTRA_USER = "user";

    private String mMobile;

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
        UserPreferences.setToken("");
        UserPreferences.setTestPosition(0);
        UserPreferences.setUserID(0);
        UserPreferences.setIsShowTest(false);
        if (TextUtils.isEmpty(UserPreferences.getToken())) {
            Intent intent = new Intent(getView(), MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            getView().startActivity(intent);
            UMShareAPI.get(getView()).deleteOauth(getView(), SHARE_MEDIA.WEIXIN, null);
            EventBus.getDefault().post(new TestStart());
            EventBus.getDefault().post(0);

            Set<String> set = new HashSet<>();
            set.add("logout");
            JPushInterface.setAliasAndTags(getView(), UserPreferences.getToken() + "", set, null);
        }
    }

    public void sendCaptcha(String mobile) {
        AccountModel.getInstance().bindCaptcha(mobile.isEmpty() ? mMobile : mobile).subscribe(new ServicesResponse<Boolean>() {
            @Override
            public void onNext(Boolean aBoolean) {
                if (!mobile.isEmpty()) mMobile = mobile;
                getView().showPopupWindow(ProfileActivity.TYPE_CAPTCHA);
            }
        });
    }

    public void modify(String key, String value) {
        UserModel.getInstance().modifyProfile(key, value).unsafeSubscribe(new ServicesResponse<String>() {
            @Override
            public void onNext(String s) {
                LUtils.toast(s);
            }
        });
    }

    public void bindMobile(String captcha) {
        AccountModel.getInstance().bindMobile(mMobile, captcha).unsafeSubscribe(new ServicesResponse<String>() {
            @Override
            public void onNext(String s) {
                getView().showPopupWindow(ProfileActivity.TYPE_BIND_SUCCESS);
            }
        });
    }

    private void closePopWindow(PopupWindow popupWindow) {
        if (popupWindow != null) {
            popupWindow.dismiss();
        }

    }

    public void pickImage(int type) {
        switch (type) {
            case 0:
                ImageProvider.getInstance(getView()).getImageFromAlbum(this);
                break;
            case 1:
                ImageProvider.getInstance(getView()).getImageFromCamera(this);
                break;
        }
    }

    @Override
    protected void onResult(int requestCode, int resultCode, Intent data) {
        super.onResult(requestCode, resultCode, data);
        ImageProvider.getInstance(getView()).onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onImageSelect() {

    }

    @Override
    public void onImageLoaded(Uri uri) {
        ImageProvider.getInstance(getView()).corpImage(uri, 200, 200, new OnImageSelectListener() {
            @Override
            public void onImageSelect() {

            }

            @Override
            public void onImageLoaded(Uri uri) {
                ImageModel.getInstance().uploadImageAsync(new File(uri.getPath()).getPath())
                        .flatMap(s -> UserModel.getInstance().modifyProfile(KEY_PROFILE_AVATAR, s))
                        .unsafeSubscribe(new ServicesResponse<String>() {
                            @Override
                            public void onNext(String s) {
                                getView().setAvatar(uri);
                            }
                        });
            }

            @Override
            public void onError() {

            }
        });
    }

    @Override
    public void onError() {

    }

}

