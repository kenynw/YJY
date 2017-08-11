package com.miguan.yjy.dialogs;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.Html;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import com.gyf.barlibrary.ImmersionBar;
import com.miguan.yjy.R;
import com.miguan.yjy.utils.LUtils;

/**
 * Copyright (c) 2017/8/4. LiaoPeiKun Inc. All rights reserved.
 */

public class BaseTopAlertDialog extends DialogFragment {

    private static final String EXTRA_RES_ID = "res_id";
    private static final String EXTRA_TITLE = "title";

    public static BaseTopAlertDialog newInstance(@LayoutRes int resId, String title, Object callback) {
        BaseTopAlertDialog dialog = new BaseTopAlertDialog();

        Bundle bundle = new Bundle();
        bundle.putString(EXTRA_TITLE, title);
        bundle.putInt(EXTRA_RES_ID, resId);
        dialog.setArguments(bundle);

        if (callback instanceof Fragment) {
            dialog.setTargetFragment((Fragment) callback, 0);
        }

        return dialog;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        String titleText = bundle.getString(EXTRA_TITLE);
        int resId = bundle.getInt(EXTRA_RES_ID);

        View v = null;
        if (resId > 0) {
            v = LayoutInflater.from(getActivity()).inflate(resId, null);
        }

        initButtonView(v);

        if (v != null && !TextUtils.isEmpty(titleText)) {
            TextView tvTitle = v.findViewById(R.id.tv_dialog_title);
            tvTitle.setText(Html.fromHtml(titleText));
        }

        AlertDialog dialog = new AlertDialog.Builder(getActivity(), R.style.AppTheme_BindDialog)
                .setView(v)
                .create();

        dialog.setOnShowListener(dialogInterface -> {
            if (getTargetFragment() != null) {
                if (getTargetFragment() instanceof BaseAlertDialog.OnDialogShowListener) {
                    ((BaseAlertDialog.OnDialogShowListener) getTargetFragment()).onShow(dialog);
                }
            } else {
                if (getActivity() instanceof BaseAlertDialog.OnDialogShowListener) {
                    ((BaseAlertDialog.OnDialogShowListener) getActivity()).onShow(dialog);
                }
            }
        });

        return dialog;
    }

    @Override
    public void onStart() {
        super.onStart();
        Window window = getDialog().getWindow();
        if (window != null) {
            window.setGravity(Gravity.TOP);
            window.setWindowAnimations(R.style.TopDialog);
            window.setLayout(LUtils.getScreenWidth(), ViewGroup.LayoutParams.WRAP_CONTENT);
            //解决无法弹出输入法的问题
//            window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE |
//                    WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
//            window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        }
        ImmersionBar.with(getActivity(), getDialog(), "top")
                .fitsSystemWindows(true)
                .statusBarColor(R.color.white)
                .statusBarDarkFont(true, 0.2f)
                .init();
    }

    protected void initButtonView(View v) {
        if (v != null) {
            TextView btnPositive = v.findViewById(R.id.btn_dialog_positive);
            if (btnPositive != null) btnPositive.setOnClickListener(view -> click(0, v));
            TextView btnNegative = v.findViewById(R.id.btn_dialog_negative);
            if (btnNegative != null) btnNegative.setOnClickListener(view -> click(1, v));
        }
    }

    public void click(int type, View v) {
        DialogCallback targetFragment = null;
        if (getTargetFragment() != null && getTargetFragment() instanceof DialogCallback) {
            targetFragment = (DialogCallback) getTargetFragment();
        } else if (getActivity() != null && getActivity() instanceof DialogCallback) {
            targetFragment = (DialogCallback) getActivity();
        }
        if (targetFragment != null) {
            if (type == 0) targetFragment.onPositiveClick(v);
            else if (type == 1) targetFragment.onNegativeClick(v);
        }
        dismiss();
    }

}
