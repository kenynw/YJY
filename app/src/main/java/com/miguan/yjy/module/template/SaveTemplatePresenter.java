package com.miguan.yjy.module.template;

import android.content.Context;
import android.content.Intent;

import com.dsk.chain.bijection.Presenter;

/**
 * Copyright (c) 2017/4/6. LiaoPeiKun Inc. All rights reserved.
 */

public class SaveTemplatePresenter extends Presenter<SaveTemplateActivity> {

    public static final String EXTRA_IMAGE = "image";

    public static void start(Context context, String uri) {
        Intent intent = new Intent(context, SaveTemplateActivity.class);
        intent.putExtra(EXTRA_IMAGE, uri);
        context.startActivity(intent);
    }

}
