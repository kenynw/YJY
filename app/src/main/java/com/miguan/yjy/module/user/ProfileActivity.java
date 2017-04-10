package com.miguan.yjy.module.user;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.dsk.chain.bijection.RequiresPresenter;
import com.dsk.chain.expansion.data.BaseDataActivity;
import com.miguan.yjy.R;
import com.miguan.yjy.model.bean.User;

import butterknife.BindView;
import butterknife.ButterKnife;
import jp.wasabeef.glide.transformations.CropCircleTransformation;

/**
 * Copyright (c) 2017/3/31. LiaoPeiKun Inc. All rights reserved.
 */
@RequiresPresenter(ProfilePresenter.class)
public class ProfileActivity extends BaseDataActivity<ProfilePresenter, User> {

    @BindView(R.id.iv_profile_avatar)
    ImageView mIvAvatar;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_activity_profile);
        setToolbarTitle(R.string.text_profile);
        ButterKnife.bind(this);
        mTvLogout.setOnClickListener(v -> getPresenter().logout());
        mTvMobile.setOnClickListener(v -> getPresenter().bindMobile());
        mTvArea.setOnClickListener(v -> CityListActivity.star(ProfileActivity.this));
    }

    @Override
    public void setData(User user) {
        Glide.with(this).load(user.getImg())
                .bitmapTransform(new CropCircleTransformation(this))
                .placeholder(R.mipmap.def_image_loading)
                .error(R.mipmap.def_image_loading)
                .into(mIvAvatar);
        mTvUsername.setText(user.getUsername());
        mTvMobile.setText(user.getMobile());
        mTvBirthday.setText(user.getBirth_year() + user.getBirth_month() + user.getBirth_day());
        mTvArea.setText(user.getCity());
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
}