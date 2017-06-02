package com.miguan.yjy.module.main;

import com.dsk.chain.bijection.Presenter;
import com.miguan.yjy.model.local.TemplatePreferences;

/**
 * Copyright (c) 2017/4/5. LiaoPeiKun Inc. All rights reserved.
 */

public class TemplatePresenter extends Presenter<TemplateFragment> {

    @Override
    protected void onCreateView(TemplateFragment view) {
        super.onCreateView(view);
        if (TemplatePreferences.isFirstHome()) getView().showGuide();
    }
}
