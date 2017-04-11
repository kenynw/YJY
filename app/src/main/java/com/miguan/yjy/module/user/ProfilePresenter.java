package com.miguan.yjy.module.user;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.dsk.chain.expansion.data.BaseDataActivityPresenter;
import com.jude.library.imageprovider.ImageProvider;
import com.jude.library.imageprovider.OnImageSelectListener;
import com.miguan.yjy.R;
import com.miguan.yjy.model.ImageModel;
import com.miguan.yjy.model.UserModel;
import com.miguan.yjy.model.bean.User;
import com.miguan.yjy.model.local.UserPreferences;
import com.miguan.yjy.model.services.ServicesResponse;
import com.miguan.yjy.module.main.MainActivity;
import com.miguan.yjy.utils.LUtils;
import com.miguan.yjy.widget.SendValidateButton;

import java.io.File;

import butterknife.ButterKnife;

/**
 * Copyright (c) 2017/3/31. LiaoPeiKun Inc. All rights reserved.
 */

public class ProfilePresenter extends BaseDataActivityPresenter<ProfileActivity, User> implements TextWatcher, OnImageSelectListener {

    public static final String EXTRA_USER = "user";

    private ImageProvider mImageProvider; // 图片浏览

    EditText mEtUserDialogFirst;
    EditText mEtUserDialogSecond;
    EditText mEtUserDialogThird;
    EditText mEtUserDialogFour;
    TextView mTvDialogCancel;
    PopupWindow bindMobiePop;
    PopupWindow codePop;
    PopupWindow suceessPop;

    public static void start(Context context, User user) {
        Intent intent = new Intent(context, ProfileActivity.class);
        intent.putExtra(EXTRA_USER, user);
        context.startActivity(intent);
    }

    @Override
    protected void onCreateView(ProfileActivity view) {
        super.onCreateView(view);
        mImageProvider = new ImageProvider(view);
        publishObject(getView().getIntent().getParcelableExtra(EXTRA_USER));
    }

    public void logout() {
        UserPreferences.setUserID(0);
        if (UserPreferences.getUserID() <= 0) {
            Intent intent = new Intent(getView(), MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            getView().startActivity(intent);
        }
    }

    public void updateUserName() {
//        View
        PopupWindow popupWindow = new PopupWindow();
        ColorDrawable bg = new ColorDrawable(0x55000000);
        popupWindow.setBackgroundDrawable(bg);
    }

    //绑定手机号
    public void bindMobile() {
        if (codePop != null) {
            codePop.dismiss();
        }
        View bindMobile = View.inflate(getView(), R.layout.popwindow_user_bind_mobile, null);
        TextView mTvDialogTitle = ButterKnife.findById(bindMobile, R.id.tv_user_dialog_title);
        EditText mEtDialogContent = ButterKnife.findById(bindMobile, R.id.et_user_dialog_content);
        ImageView mIvDialogClose = ButterKnife.findById(bindMobile, R.id.iv_user_dialog_close);
        TextView mTvDialogCancel = ButterKnife.findById(bindMobile, R.id.tv_user_dialog_cancel);
        TextView mTvDialogSure = ButterKnife.findById(bindMobile, R.id.tv_user_dialog_sure);
        mTvDialogSure.setText(R.string.text_user_next);
        bindMobiePop = new PopupWindow(bindMobile, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, true);
        bindMobiePop.setTouchable(true);
        ColorDrawable bg = new ColorDrawable(0x55000000);
        bindMobiePop.setBackgroundDrawable(bg);
        bindMobiePop.showAtLocation(getView().getToolbar(), Gravity.BOTTOM, 0, 0);
        LUtils.openKeyboard(mEtDialogContent);
        mTvDialogCancel.setOnClickListener(v -> closePopWindow(bindMobiePop));
        mTvDialogSure.setOnClickListener(v -> getCode(bindMobiePop));
        mIvDialogClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEtDialogContent.setText("");
            }
        });
    }

    //获取验证码
    public void getCode(PopupWindow pop) {
        pop.dismiss();
        if (bindMobiePop != null) {
            bindMobiePop.dismiss();
        }
        View bindMobile = View.inflate(getView(), R.layout.popwindow_user_get_code, null);
        TextView mTvDialogTitle = ButterKnife.findById(bindMobile, R.id.tv_user_dialog_title);
        mEtUserDialogFirst = ButterKnife.findById(bindMobile, R.id.et_user_dialog_first);
        mEtUserDialogSecond = ButterKnife.findById(bindMobile, R.id.et_user_dialog_second);
        mEtUserDialogThird = ButterKnife.findById(bindMobile, R.id.et_user_dialog_third);
        mEtUserDialogFour = ButterKnife.findById(bindMobile, R.id.et_user_dialog_four);
        TextView mTvDialogCancel = ButterKnife.findById(bindMobile, R.id.tv_user_dialog_cancel);
        SendValidateButton mBtnDialogSure = ButterKnife.findById(bindMobile, R.id.btn_user_dialog_sure);
        mBtnDialogSure.startTickWork();
//        mBtnDialogSure.setText(R.string.text_user_next);
        codePop = new PopupWindow(bindMobile, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, true);
        codePop.setTouchable(true);
        ColorDrawable bg = new ColorDrawable(0x55000000);
        codePop.setBackgroundDrawable(bg);
        codePop.showAtLocation(getView().getToolbar(), Gravity.BOTTOM, 0, 0);
        LUtils.openKeyboard(mEtUserDialogFirst);
        mTvDialogCancel.setOnClickListener(v -> bindMobile());

        mEtUserDialogFirst.addTextChangedListener(this);
        mEtUserDialogSecond.addTextChangedListener(this);
        mEtUserDialogThird.addTextChangedListener(this);
        mEtUserDialogFour.addTextChangedListener(this);

    }

    //绑定成功
    public void bindSuccess() {
        View bindMobile = View.inflate(getView(), R.layout.popwindow_user_binded_mobile, null);
        TextView mTvDialogTitle = ButterKnife.findById(bindMobile, R.id.tv_user_dialog_title);
        ImageView mIvDialogClose = ButterKnife.findById(bindMobile, R.id.iv_user_dialog_close);
        TextView mTvDialogCancel = ButterKnife.findById(bindMobile, R.id.tv_user_dialog_code_cancel);
        TextView mTvDialogSure = ButterKnife.findById(bindMobile, R.id.tv_user_dialog_sure);
        PopupWindow suceessPop = new PopupWindow(bindMobile, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, true);
        suceessPop.setTouchable(true);
        ColorDrawable bg = new ColorDrawable(0x55000000);
        suceessPop.setBackgroundDrawable(bg);
        suceessPop.showAtLocation(getView().getToolbar(), Gravity.BOTTOM, 0, 0);
        mTvDialogCancel.setOnClickListener(v -> closePopWindow(suceessPop));
        mTvDialogSure.setOnClickListener(v -> closePopWindow(suceessPop));
        LUtils.closeKeyboard(mEtUserDialogFirst);
        closePopWindow(codePop);
        closePopWindow(bindMobiePop);
    }


    private void closePopWindow(PopupWindow popupWindow) {
        if (popupWindow != null) {
            popupWindow.dismiss();
        }

    }


    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        if (isCanSubmit()) {
            bindSuccess();
        }
    }

    private boolean isCanSubmit() {

        if (mEtUserDialogFirst.getText().length() != 1) {
            return false;
        }
        if (mEtUserDialogSecond.getText().length() != 1) {
            return false;
        }
        if (mEtUserDialogThird.getText().length() != 1) {
            return false;
        }
        if (mEtUserDialogFour.getText().length() != 1) {
            return false;
        }
        return true;
    }

    public void pickImage(int type) {
        switch (type) {
            case 0:
                mImageProvider.getImageFromAlbum(this);
                break;
            case 1:
                mImageProvider.getImageFromCamera(this);
                break;
        }
    }

    @Override
    protected void onResult(int requestCode, int resultCode, Intent data) {
        super.onResult(requestCode, resultCode, data);
        mImageProvider.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onImageSelect() {

    }

    @Override
    public void onImageLoaded(Uri uri) {
        ImageModel.getInstance().uploadImageAsync(new File(uri.getPath()).getPath())
                .flatMap(s -> UserModel.getInstance().modifyProfile(UserModel.KEY_PROFILE_AVATAR, s))
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
}

