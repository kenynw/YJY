package com.miguan.yjy.dialogs;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import com.miguan.yjy.R;
import com.miguan.yjy.utils.LUtils;

/**
 * Copyright (c) 2017/7/24. LiaoPeiKun Inc. All rights reserved.
 */

public class BaseAlertDialog extends DialogFragment implements View.OnClickListener {

    protected static final String EXTRA_LAYOUT_RES = "layout_res";

    public interface OnButtonClickListener {
        void onPositiveClick(@NonNull View view);
        void onNegativeClick(@NonNull View view);
    }

    public interface OnDialogShowListener {
        void onShow(@NonNull AlertDialog dialog);
    }

    public static BaseAlertDialog newInstance(@LayoutRes int layoutRes, Object callback) {
        BaseAlertDialog dialog = new BaseAlertDialog();

        Bundle bundle = new Bundle();
        bundle.putInt(EXTRA_LAYOUT_RES, layoutRes);
        dialog.setArguments(bundle);

        if (callback instanceof Fragment) {
            dialog.setTargetFragment((Fragment) callback, 0);
        }

        return dialog;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        int layoutRes = getArguments().getInt(EXTRA_LAYOUT_RES);

        View view = LayoutInflater.from(getActivity()).inflate(layoutRes, null);
        TextView btnNegative = view.findViewById(R.id.btn_dialog_negative);
        if (btnNegative != null) {
            btnNegative.setOnClickListener(this);
        }
        TextView btnPositive = view.findViewById(R.id.btn_dialog_positive);
        if (btnPositive != null) {
            btnPositive.setOnClickListener(this);
        }

        AlertDialog dialog = new AlertDialog.Builder(getActivity())
                .setView(view)
                .create();

        if (dialog.getWindow() != null) dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);

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
        if (getDialog() != null && getDialog().getWindow() != null) {
            getDialog().getWindow().setLayout((int) (LUtils.getScreenWidth() * 0.75), ViewGroup.LayoutParams.WRAP_CONTENT);
            getDialog().getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        }
    }

    @Override
    public void onClick(View view) {
        OnButtonClickListener targetFragment = null;
        if (getTargetFragment() != null && getTargetFragment() instanceof OnButtonClickListener) {
            targetFragment = (OnButtonClickListener) getTargetFragment();
        } else if (getActivity() instanceof OnButtonClickListener) {
            targetFragment = (OnButtonClickListener) getActivity();
        }
        if (targetFragment != null) {
            if (view.getId() == R.id.btn_dialog_positive) targetFragment.onPositiveClick(view);
            else if (view.getId() == R.id.btn_dialog_negative) targetFragment.onNegativeClick(view);
        }
        dismiss();
    }

}
