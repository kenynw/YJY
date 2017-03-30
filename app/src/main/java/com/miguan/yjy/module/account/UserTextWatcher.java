package com.miguan.yjy.module.account;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;

/**
 * Copyright (c) 2017/3/30. LiaoPeiKun Inc. All rights reserved.
 */

public class UserTextWatcher implements TextWatcher {

    private Button mButton;

    private EditText[] mEditTexts;

    public UserTextWatcher(Button button, EditText... editTexts) {
        mButton = button;
        mEditTexts = new EditText[editTexts.length];
        for (int i = 0; i < editTexts.length; i++) {
            mEditTexts[i] = editTexts[i];
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        boolean enabled = true;
        for (EditText editText : mEditTexts) {
            if (TextUtils.isEmpty(editText.getText())) enabled = false;
        }

        mButton.setEnabled(enabled);
    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}
