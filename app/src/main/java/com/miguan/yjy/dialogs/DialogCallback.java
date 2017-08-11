package com.miguan.yjy.dialogs;

import android.support.annotation.NonNull;
import android.view.View;

/**
 * Copyright (c) 2017/8/7. LiaoPeiKun Inc. All rights reserved.
 */

public interface DialogCallback {
    void onPositiveClick(@NonNull View view);

    void onNegativeClick(@NonNull View view);
}
