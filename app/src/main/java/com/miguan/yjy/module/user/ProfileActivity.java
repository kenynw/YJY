package com.miguan.yjy.module.user;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.Html;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.TimePickerView;
import com.dsk.chain.bijection.RequiresPresenter;
import com.dsk.chain.expansion.data.BaseDataActivity;
import com.facebook.drawee.view.SimpleDraweeView;
import com.miguan.yjy.R;
import com.miguan.yjy.dialogs.BaseAlertDialog;
import com.miguan.yjy.dialogs.BaseTopAlertDialog;
import com.miguan.yjy.dialogs.BindMobileAlertDialog;
import com.miguan.yjy.dialogs.DialogCallback;
import com.miguan.yjy.model.bean.User;
import com.miguan.yjy.utils.LUtils;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;

import static butterknife.ButterKnife.findById;

/**
 * Copyright (c) 2017/3/31. LiaoPeiKun Inc. All rights reserved.
 */
@RequiresPresenter(ProfilePresenter.class)
public class ProfileActivity extends BaseDataActivity<ProfilePresenter, User> implements
        DialogCallback, BaseAlertDialog.OnDialogShowListener {

    public static final int REQUEST_CODE_CITY = 0x1233;

    public static final String TAG_MODIFY_USERNAME = "modify_username";

    public static final String TAG_MOBILE_BOUND = "mobile_bound";

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

    private BottomSheetDialog mImagePicker;

    private TimePickerView mTimePickerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_activity_profile);
        setToolbarTitle(R.string.text_profile);
        ButterKnife.bind(this);

        mLlAvatar.setOnClickListener(v -> showImagePicker());
        mTvBirthday.setOnClickListener(v -> showTimePicker(mTvBirthday));
        mTvLogout.setOnClickListener(v -> getPresenter().logout());
        mTvArea.setOnClickListener(v -> startActivityForResult(new Intent(this, CityListActivity.class), REQUEST_CODE_CITY));
    }

    private void showImagePicker() {
        if (mImagePicker == null) {
            mImagePicker = new BottomSheetDialog(this);
            mImagePicker.setContentView(R.layout.dialog_image_picker);

            Button btnGallery = findById(mImagePicker, R.id.btn_pick_from_gallery);
            Button btnCamera = findById(mImagePicker, R.id.btn_pick_from_camera);
            Button btnCancel = findById(mImagePicker, R.id.btn_pick_cancel);
            btnGallery.setOnClickListener(v -> getPresenter().pickImage(0));
            btnCamera.setOnClickListener(v -> getPresenter().pickImage(1));
            btnCancel.setOnClickListener(v -> mImagePicker.dismiss());
        }
        mImagePicker.show();
    }

    private void showTimePicker(View view) {
        if (mTimePickerView == null) {
            mTimePickerView = new TimePickerView.Builder(this, (date, v) -> {
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                ((TextView) v).setText(format.format(date));
                getPresenter().modify(ProfilePresenter.KEY_PROFILE_BIRTHDAY, String.valueOf(date.getTime() / 1000));
            }).setType(TimePickerView.Type.YEAR_MONTH_DAY)
                    .setSubmitText("完成")
                    .setSubmitColor(getResources().getColor(R.color.colorPrimary))
                    .setCancelColor(getResources().getColor(R.color.textSecondary))
                    .setLabel("", "", "", "", "", "")
                    .setDate(Calendar.getInstance())
                    .setRangDate(null, Calendar.getInstance())
                    .build();
        }
        mTimePickerView.show(view);
    }

    @Override
    public void setData(User user) {
        mDvAvatar.setImageURI(Uri.parse(user.getImg()));
        mTvUsername.setText(getString(user.getUsername()));
        mTvUsername.setOnClickListener(v -> {
            BaseTopAlertDialog.newInstance(
                    R.layout.popupwindow_user_name_modify,
                    user.getUsername(),
                    this)
                    .show(getSupportFragmentManager(), TAG_MODIFY_USERNAME);
        });
        boolean isMobile = Patterns.PHONE.matcher(user.getMobile()).matches();
        mTvMobile.setText(getString(isMobile ? user.getMobile() : ""));
        mTvMobile.setOnClickListener(v -> {
            if (!isMobile) {
                new BindMobileAlertDialog().show(getSupportFragmentManager(), "bind");
            } else {
                BaseTopAlertDialog.newInstance(
                        R.layout.dialog_mobile_bound,
                        "您已绑定<font color=\"#32DAC3\"> " + mTvMobile.getText() + " </font>",
                        this)
                        .show(getSupportFragmentManager(), TAG_MOBILE_BOUND);
            }
        });
        mTvBirthday.setText(TextUtils.isEmpty(user.getBirth_year()) ? "未设置" :
                String.format("%1$04d-%2$02d-%3$02d",
                        Integer.parseInt(user.getBirth_year()),
                        Integer.parseInt(user.getBirth_month()),
                        Integer.parseInt(user.getBirth_day())));
        mTvArea.setText(getString(user.getCity()));
    }

    public void setAvatar(Uri uri) {
        mDvAvatar.setImageURI(uri);
        if (mImagePicker.isShowing()) mImagePicker.dismiss();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE_CITY) {
            String cityName = data.getStringExtra("city");
            mTvArea.setText(cityName);
            getPresenter().modify(ProfilePresenter.KEY_PROFILE_CITY, cityName);
        }
    }

    private String getString(String str) {
        return TextUtils.isEmpty(str) ? "未设置" : str;
    }

    @Override
    public void onShow(@NonNull AlertDialog dialog) {
        if (getSupportFragmentManager().findFragmentByTag(TAG_MODIFY_USERNAME) != null) {
            dialog.setCancelable(false);
            EditText et = (EditText) dialog.findViewById(R.id.et_user_dialog_content);
            ImageView ivClose = (ImageView) dialog.findViewById(R.id.iv_user_dialog_close);
            if (et != null && ivClose != null) {
                et.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        ivClose.setVisibility(s.length() > 0 ? View.VISIBLE : View.GONE);
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });
                ivClose.setOnClickListener(v -> et.setText(""));
                LUtils.openKeyboard(et);
            }
        }
        if (getSupportFragmentManager().findFragmentByTag(TAG_MOBILE_BOUND) != null) {
            TextView tvMessage = (TextView) dialog.findViewById(R.id.tv_dialog_message);
            if (tvMessage != null)
                tvMessage.setText(Html.fromHtml(getString(R.string.btn_user_bined_content)));
        }
    }

    @Override
    public void onPositiveClick(@NonNull View view) {
        if (getSupportFragmentManager().findFragmentByTag(TAG_MODIFY_USERNAME) != null) {
            EditText et = view.findViewById(R.id.et_user_dialog_content);

            if (et != null && !TextUtils.isEmpty(et.getText().toString().trim())) {
                mTvUsername.setText(et.getText());
                getPresenter().modify(ProfilePresenter.KEY_PROFILE_USERNAME, et.getText().toString().trim());
            }

        }
    }

    @Override
    public void onNegativeClick(@NonNull View view) {
        if (getSupportFragmentManager().findFragmentByTag(TAG_MODIFY_USERNAME) != null) {
            EditText et = view.findViewById(R.id.et_user_dialog_content);

            if (et != null) {
                LUtils.closeKeyboard(et);
            }

        }
    }

}