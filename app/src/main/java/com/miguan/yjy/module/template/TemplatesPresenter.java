package com.miguan.yjy.module.template;

import com.dsk.chain.bijection.Presenter;
import com.miguan.yjy.model.local.TemplatePreferences;

/**
 * Copyright (c) 2017/4/5. LiaoPeiKun Inc. All rights reserved.
 */

public class TemplatesPresenter extends Presenter<TemplatesActivity> {

    @Override
    protected void onCreateView(TemplatesActivity view) {
        super.onCreateView(view);
        if (TemplatePreferences.isFirstHome()) getView().showGuide();
    }

}
