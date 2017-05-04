package com.miguan.yjy.module.user;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.BottomSheetDialog;
import android.text.Html;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.bigkoo.pickerview.TimePickerView;
import com.dsk.chain.bijection.RequiresPresenter;
import com.dsk.chain.expansion.data.BaseDataActivity;
import com.facebook.drawee.view.SimpleDraweeView;
import com.miguan.yjy.R;
import com.miguan.yjy.model.bean.User;
import com.miguan.yjy.utils.LUtils;
import com.miguan.yjy.widget.SecurityCodeView;
import com.miguan.yjy.widget.SendValidateButton;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;

import static butterknife.ButterKnife.findById;

/**
 * Copyright (c) 2017/3/31. LiaoPeiKun Inc. All rights reserved.
 */
@RequiresPresenter(ProfilePresenter.class)
public class ProfileActivity extends BaseDataActivity<ProfilePresenter, User> implements SecurityCodeView.InputCompleteListener {

    public static final int TYPE_USERNAME = 0x001;

    public static final int TYPE_BIND_MOBILE = 0x002;

    public static final int TYPE_BIND_SUCCESS = 0x003;

    public static final int TYPE_CAPTCHA = 0x004;

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

    private PopupWindow mPopupWindow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_activity_profile);
        setToolbarTitle(R.string.text_profile);
        ButterKnife.bind(this);

        mLlAvatar.setOnClickListener(v -> showImagePicker());
        mTvBirthday.setOnClickListener(v -> showTimePicker(mTvBirthday));
        mTvLogout.setOnClickListener(v -> getPresenter().logout());
        mTvArea.setOnClickListener(v -> CityListActivity.star(ProfileActivity.this));
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
        mTvUsername.setOnClickListener(v -> showPopupWindow(TYPE_USERNAME));
        mTvMobile.setText(getString(user.getMobile()));
        mTvMobile.setOnClickListener(v -> {
            if (user.getMobile().isEmpty()) {
                showPopupWindow(TYPE_BIND_MOBILE);
            } else {
                showPopupWindow(TYPE_BIND_SUCCESS);
            }
        });
        mTvBirthday.setText(user.getBirth_year().equals("0") ? "未设置" : String.format("%1$s-%2$s-%3$s", user.getBirth_year(), user.getBirth_month(), user.getBirth_day()));
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
                String cityName = data.getStringExtra("city");
                mTvArea.setText(cityName);
                getPresenter().modify(ProfilePresenter.KEY_PROFILE_CITY, cityName);
            }
        }
    }

    public void showPopupWindow(int type) {
        if (mPopupWindow == null) {
            mPopupWindow = new PopupWindow(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            mPopupWindow.setFocusable(true);
            mPopupWindow.setTouchable(true);
            mPopupWindow.setOutsideTouchable(true);
            mPopupWindow.setBackgroundDrawable(new ColorDrawable(0x55000000));
        } else {
            mPopupWindow.dismiss();
        }
        switch (type) {
            case TYPE_USERNAME :
                mPopupWindow.setContentView(createModifyUsernameView());
                break;
            case TYPE_BIND_MOBILE :
                mPopupWindow.setContentView(createBindMobileView());
                break;
            case TYPE_BIND_SUCCESS:
                mPopupWindow.setContentView(createBindSuccessView());
                break;
            case TYPE_CAPTCHA:
                mPopupWindow.setContentView(createCodeView());
                break;
        }
        mPopupWindow.showAtLocation(getContent(), Gravity.BOTTOM, 0, 0);
    }

    // 编辑(修改)用户名
    private View createModifyUsernameView() {
        View view = View.inflate(this, R.layout.popwindow_user_bind_mobile, null);
        TextView tvTitle = findById(view, R.id.tv_user_dialog_title);
        EditText etContent = findById(view, R.id.et_user_dialog_content);
        ImageView tvClear = findById(view, R.id.iv_user_dialog_close);
        TextView tvCancel = findById(view, R.id.tv_user_dialog_cancel);
        TextView tvSure = findById(view, R.id.tv_user_dialog_sure);
        etContent.setHint(R.string.hint_username);
        etContent.setSelection(0);
        etContent.setInputType(EditorInfo.TYPE_CLASS_TEXT);
        tvSure.setText(R.string.btn_save);
        tvTitle.setText(mTvUsername.getText());
        tvCancel.setOnClickListener(v -> mPopupWindow.dismiss());
        tvClear.setOnClickListener(v -> etContent.setText(""));
        tvSure.setOnClickListener(v -> {
            mPopupWindow.dismiss();
            mTvUsername.setText(etContent.getText());
            getPresenter().modify(ProfilePresenter.KEY_PROFILE_USERNAME, etContent.getText().toString().trim());
        });
        LUtils.openKeyboard(etContent);
        return view;
    }

    //绑定手机号
    private View createBindMobileView() {
        View view = View.inflate(this, R.layout.popwindow_user_bind_mobile, null);
        EditText etMobile = findById(view, R.id.et_user_dialog_content);
        LUtils.openKeyboard(etMobile);

        TextView mTvDialogSure = findById(view, R.id.tv_user_dialog_sure);
        mTvDialogSure.setText(R.string.text_user_next);
        mTvDialogSure.setOnClickListener(v -> {
            if (etMobile.getText().toString().isEmpty()) LUtils.toast("手机号码不能为空");
            else getPresenter().sendCaptcha(etMobile.getText().toString());
        });

        findById(view, R.id.tv_user_dialog_cancel).setOnClickListener(v -> mPopupWindow.dismiss());
        findById(view, R.id.iv_user_dialog_close).setOnClickListener(v -> etMobile.setText(""));

        return view;
    }

    //获取验证码
    private View createCodeView() {
        View view = View.inflate(this, R.layout.popwindow_user_get_code, null);
        SecurityCodeView scvEdt = findById(view, R.id.scv_edt);
        EditText etCaptcha = findById(scvEdt, R.id.item_edittext);
        SendValidateButton btnResend = findById(view, R.id.btn_user_dialog_sure);

        findById(view, R.id.tv_user_dialog_cancel).setOnClickListener(v -> showPopupWindow(TYPE_BIND_MOBILE));

        scvEdt.setInputCompleteListener(this);
        btnResend.setOnClickListener(v -> getPresenter().sendCaptcha(""));
        btnResend.startTickWork();

        LUtils.openKeyboard(etCaptcha);
        return view;
    }

    //绑定成功
    public View createBindSuccessView() {
        View view = View.inflate(this, R.layout.popwindow_user_binded_mobile, null);
        TextView mTvDialogTitle = findById(view, R.id.tv_user_dialog_title);
        ImageView mIvDialogClose = findById(view, R.id.iv_user_dialog_close);
        TextView mTvDialogCancel = findById(view, R.id.tv_user_dialog_code_cancel);
        TextView mEtDialogContent = findById(view, R.id.et_user_dialog_bind_content);
        TextView mTvDialogSure = findById(view, R.id.tv_user_dialog_sure);
        String mobile = "您已绑定<font color=\"#32DAC3\"> " +mTvMobile.getText() + " </font>";
        mTvDialogTitle.setText(Html.fromHtml(mobile));
        String wx = "tmshuo520";
//        如您需要更换绑定手机号，请微信关注\n颜究院(微信号:tmshuo520)，\n联系客服进行更改。
        String content = "如您需要更换绑定手机号，请微信关注<br/>颜究院(微信号:<font color=\"#32DAC3\"> " +wx+"</font> )，<br/>联系客服进行更改。";
        mEtDialogContent.setText(Html.fromHtml(content));
        mTvDialogCancel.setOnClickListener(v -> mPopupWindow.dismiss());
        mTvDialogSure.setOnClickListener(v -> mPopupWindow.dismiss());
        return view;
    }

    @Override
    public void inputComplete(String captcha) {
        LUtils.log("captcha: " + captcha);
        getPresenter().bindMobile(captcha);
    }

    @Override
    public void deleteContent(boolean isDelete) {

    }

    private String getString(String str) {
        return TextUtils.isEmpty(str) ? "未设置" : str;
    }

}