package com.miguan.yjy.module.user;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
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
import com.miguan.yjy.widget.SecurityCodeView;
import com.miguan.yjy.widget.SendValidateButton;

import java.io.File;

import static butterknife.ButterKnife.findById;

/**
 * Copyright (c) 2017/3/31. LiaoPeiKun Inc. All rights reserved.
 */

public class ProfilePresenter extends BaseDataActivityPresenter<ProfileActivity, User> implements OnImageSelectListener, SecurityCodeView.InputCompleteListener {

    public static final String EXTRA_USER = "user";

    private ImageProvider mImageProvider; // 图片浏览
    TextView mTvDialogCancel;
    PopupWindow bindMobiePop;
    PopupWindow codePop;
    PopupWindow suceessPop;
    private EditText edtCode;


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


    //编辑(修改)用户名
    public void updateUserName() {

        View bindMobile = View.inflate(getView(), R.layout.popwindow_user_bind_mobile, null);
        TextView mTvDialogTitle = findById(bindMobile, R.id.tv_user_dialog_title);
        EditText mEtDialogContent = findById(bindMobile, R.id.et_user_dialog_content);
        ImageView mIvDialogClose = findById(bindMobile, R.id.iv_user_dialog_close);
        TextView mTvDialogCancel = findById(bindMobile, R.id.tv_user_dialog_cancel);
        TextView mTvDialogSure = findById(bindMobile, R.id.tv_user_dialog_sure);
        mTvDialogSure.setText(R.string.btn_save);
        mTvDialogTitle.setText("用户名a");
        bindMobiePop = new PopupWindow(bindMobile, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, true);
        bindMobiePop.setTouchable(true);
        ColorDrawable bg = new ColorDrawable(0x55000000);
        bindMobiePop.setBackgroundDrawable(bg);
        bindMobiePop.showAtLocation(getView().getToolbar(), Gravity.BOTTOM, 0, 0);
        mTvDialogCancel.setOnClickListener(v -> closePopWindow(bindMobiePop));
        mTvDialogSure.setOnClickListener(v -> closePopWindow(bindMobiePop));
        mIvDialogClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEtDialogContent.setText("");
            }
        });
        LUtils.closeKeyboard(mEtDialogContent);
    }


    //绑定手机号
    public void bindMobile() {
        if (codePop != null) {
            codePop.dismiss();
        }
        View bindMobile = View.inflate(getView(), R.layout.popwindow_user_bind_mobile, null);
        TextView mTvDialogTitle = findById(bindMobile, R.id.tv_user_dialog_title);
        EditText mEtDialogContent = findById(bindMobile, R.id.et_user_dialog_content);
        ImageView mIvDialogClose = findById(bindMobile, R.id.iv_user_dialog_close);
        TextView mTvDialogCancel = findById(bindMobile, R.id.tv_user_dialog_cancel);
        TextView mTvDialogSure = findById(bindMobile, R.id.tv_user_dialog_sure);
        mTvDialogSure.setText(R.string.text_user_next);
        bindMobiePop = new PopupWindow(bindMobile, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, true);
        bindMobiePop.setTouchable(true);
        ColorDrawable bg = new ColorDrawable(0x55000000);
        bindMobiePop.setBackgroundDrawable(bg);
        bindMobiePop.showAtLocation(getView().getToolbar(), Gravity.BOTTOM, 0, 0);
        mTvDialogCancel.setOnClickListener(v -> closePopWindow(bindMobiePop));
        mTvDialogSure.setOnClickListener(v -> getCode(bindMobiePop));
        mIvDialogClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEtDialogContent.setText("");
            }
        });
        LUtils.closeKeyboard(mEtDialogContent);
    }

    //获取验证码
    public void getCode(PopupWindow pop) {
        pop.dismiss();
        if (bindMobiePop != null) {
            bindMobiePop.dismiss();
        }
        View bindMobile = View.inflate(getView(), R.layout.popwindow_user_get_code, null);
        TextView mTvDialogTitle = findById(bindMobile, R.id.tv_user_dialog_title);
        SecurityCodeView scvEdt = findById(bindMobile, R.id.scv_edt);
        edtCode = findById(scvEdt, R.id.item_edittext);

        TextView mTvDialogCancel = findById(bindMobile, R.id.tv_user_dialog_cancel);
        SendValidateButton mBtnDialogSure = findById(bindMobile, R.id.btn_user_dialog_sure);
        mBtnDialogSure.startTickWork();
        mBtnDialogSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mBtnDialogSure.isClickable()) {
                    mBtnDialogSure.startTickWork();
                }
            }
        });
        scvEdt.setInputCompleteListener(this);
//        mBtnDialogSure.setText(R.string.text_user_next);
        codePop = new PopupWindow(bindMobile, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, true);
        codePop.setTouchable(true);
        ColorDrawable bg = new ColorDrawable(0x55000000);
        codePop.setBackgroundDrawable(bg);
        codePop.showAtLocation(getView().getToolbar(), Gravity.BOTTOM, 0, 0);
        LUtils.closeKeyboard(edtCode);
        mTvDialogCancel.setOnClickListener(v -> bindMobile());

    }

    //绑定成功
    public void bindSuccess() {
        View bindMobile = View.inflate(getView(), R.layout.popwindow_user_binded_mobile, null);
        TextView mTvDialogTitle = findById(bindMobile, R.id.tv_user_dialog_title);
        ImageView mIvDialogClose = findById(bindMobile, R.id.iv_user_dialog_close);
        TextView mTvDialogCancel = findById(bindMobile, R.id.tv_user_dialog_code_cancel);
        TextView mTvDialogSure = findById(bindMobile, R.id.tv_user_dialog_sure);
        suceessPop = new PopupWindow(bindMobile, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, true);
        suceessPop.setTouchable(true);
        ColorDrawable bg = new ColorDrawable(0x55000000);
        suceessPop.setBackgroundDrawable(bg);
        suceessPop.showAtLocation(getView().getToolbar(), Gravity.BOTTOM, 0, 0);
        mTvDialogCancel.setOnClickListener(v -> closePopWindow(suceessPop));
        mTvDialogSure.setOnClickListener(v -> closePopWindow(suceessPop));
        LUtils.closeKeyboard(edtCode);
        closePopWindow(codePop);
        closePopWindow(bindMobiePop);
    }


    private void closePopWindow(PopupWindow popupWindow) {
        if (popupWindow != null) {
            popupWindow.dismiss();
        }

    }

    @Override
    public void inputComplete() {
        bindSuccess();
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
    @Override
    public void deleteContent(boolean isDelete) {

    }
}

