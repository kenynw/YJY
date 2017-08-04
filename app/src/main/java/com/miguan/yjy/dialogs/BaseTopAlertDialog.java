package com.miguan.yjy.dialogs;

import android.support.v4.app.DialogFragment;
import android.view.Gravity;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.gyf.barlibrary.ImmersionBar;
import com.miguan.yjy.R;
import com.miguan.yjy.utils.LUtils;

/**
 * Copyright (c) 2017/8/4. LiaoPeiKun Inc. All rights reserved.
 */

public class BaseTopAlertDialog extends DialogFragment {

    @Override
    public void onStart() {
        super.onStart();
        Window window = getDialog().getWindow();
        if (window != null) {
            window.setGravity(Gravity.TOP);
            window.setWindowAnimations(R.style.TopDialog);
            window.setLayout(LUtils.getScreenWidth(), ViewGroup.LayoutParams.WRAP_CONTENT);
            //解决无法弹出输入法的问题
            window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE |
                    WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
            window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        }
        ImmersionBar.with(getActivity(), getDialog(), "bind")
                .statusBarColor(R.color.white)
                .init();
    }

}
