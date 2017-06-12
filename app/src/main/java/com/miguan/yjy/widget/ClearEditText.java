package com.miguan.yjy.widget;

import android.content.Context;
import android.support.v7.widget.AppCompatEditText;
import android.util.AttributeSet;
import android.view.View;

/**
 * Copyright (c) 2017/5/5. LiaoPeiKun Inc. All rights reserved.
 */

public class ClearEditText extends AppCompatEditText implements View.OnFocusChangeListener {

    private boolean mIsEdit = false;

    public ClearEditText(Context context) {
        super(context);
        setOnFocusChangeListener(this);
    }

    public ClearEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        setOnFocusChangeListener(this);
    }

    public ClearEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setOnFocusChangeListener(this);
    }


    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (hasFocus && !mIsEdit) {
            setText("");
            mIsEdit = true;
        }
    }

    public boolean isEdit() {
        return mIsEdit;
    }

}
