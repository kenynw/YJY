package com.miguan.yjy.module.user;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.BottomSheetDialog;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dsk.chain.bijection.RequiresPresenter;
import com.dsk.chain.expansion.data.BaseDataActivity;
import com.facebook.drawee.view.SimpleDraweeView;
import com.miguan.yjy.R;
import com.miguan.yjy.model.bean.User;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Copyright (c) 2017/3/31. LiaoPeiKun Inc. All rights reserved.
 */
@RequiresPresenter(ProfilePresenter.class)
public class ProfileActivity extends BaseDataActivity<ProfilePresenter, User> {

    @BindView(R.id.dv_profile_avatar)
    SimpleDraweeView mDvAvatar;

    @BindView(R.id.ll_profile_avatar)
    LinearLayout mLlAvatar;

    @BindView(R.id.tv_profile_username)
    TextView mTvUsername;

    @BindView(R.id.tv_profile_mobile)
    TextView mTvMobile;

    @BindView(R.id.tv_profile_birthday)
    TextView mTvBirthday;

    @BindView(R.id.tv_profile_area)
    TextView mTvArea;

    @BindView(R.id.tv_profile_logout)
    TextView mTvLogout;

    private String cityName;

    private BottomSheetDialog mImagePicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_activity_profile);
        setToolbarTitle(R.string.text_profile);
        ButterKnife.bind(this);

        mLlAvatar.setOnClickListener(v -> showImagePicker());
        mTvLogout.setOnClickListener(v -> getPresenter().logout());
        mTvMobile.setOnClickListener(v -> getPresenter().bindMobile());
        mTvArea.setOnClickListener(v -> CityListActivity.star(ProfileActivity.this));
    }

    private void showImagePicker() {
        if (mImagePicker == null) {
            mImagePicker = new BottomSheetDialog(this);
            mImagePicker.setContentView(R.layout.dialog_image_picker);

            Button btnGallery = ButterKnife.findById(mImagePicker, R.id.btn_pick_from_gallery);
            Button btnCamera = ButterKnife.findById(mImagePicker, R.id.btn_pick_from_camera);
            Button btnCancel = ButterKnife.findById(mImagePicker, R.id.btn_pick_cancel);
            btnGallery.setOnClickListener(v -> getPresenter().pickImage(0));
            btnCamera.setOnClickListener(v -> getPresenter().pickImage(1));
            btnCancel.setOnClickListener(v -> mImagePicker.dismiss());
        }
        mImagePicker.show();
    }

    @Override
    public void setData(User user) {
        mDvAvatar.setImageURI(Uri.parse(user.getImg()));
        mTvUsername.setText(getString(user.getUsername()));
        mTvMobile.setText(getString(user.getMobile()));
        mTvBirthday.setText(getString(user.getBirth_year() + user.getBirth_month() + user.getBirth_day()));
        mTvArea.setText(getString(user.getCity()));
    }

    public void setAvatar(Uri uri) {
        mDvAvatar.setImageURI(uri);
        if (mImagePicker.isShowing()) mImagePicker.dismiss();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == CityListActivity.EXTRA_CODE_CITY_NAME) {
                cityName= data.getStringExtra("city");
                mTvArea.setText(cityName);
            }
        }
    }

    private String getString(String str) {
        return TextUtils.isEmpty(str) ? "未设置" : str;
    }

}