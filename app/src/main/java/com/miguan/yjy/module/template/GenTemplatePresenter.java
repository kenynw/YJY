package com.miguan.yjy.module.template;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.View;

import com.dsk.chain.bijection.Presenter;
import com.google.gson.Gson;
import com.jude.library.imageprovider.ImageProvider;
import com.miguan.yjy.R;
import com.miguan.yjy.model.local.TemplatePreferences;
import com.miguan.yjy.utils.ScreenShot;
import com.miguan.yjy.widget.ClearEditText;

import java.util.ArrayList;
import java.util.List;


/**
 * Copyright (c) 2017/4/5. LiaoPeiKun Inc. All rights reserved.
 */

public class GenTemplatePresenter extends Presenter<GenTemplateActivity> {

    public static final String EXTRA_TEMPLATE = "template";

    private boolean mIsRunning = true;

    private Thread mThread = new Thread(() -> {
        while (mIsRunning) {
            try {
                Thread.sleep(5 * 1000 * 60);
                saveCraft();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    });

    public static void start(Context context, int index) {
        Intent intent = new Intent(context, GenTemplateActivity.class);
        intent.putExtra(EXTRA_TEMPLATE, index);
        context.startActivity(intent);
    }

    @Override
    protected void onCreateView(GenTemplateActivity view) {
        super.onCreateView(view);

        int index = getView().getIntent().getIntExtra(EXTRA_TEMPLATE, 0);

        if (TemplatePreferences.isFirstDetail()) getView().showGuide();

        String templateStr;
        if (!TextUtils.isEmpty(templateStr = TemplatePreferences.getTemplate())) {
            new AlertDialog.Builder(getView())
                    .setCancelable(false)
                    .setTitle("上次的创作还未完成哦~")
                    .setItems(new String[]{"加载草稿", "重新创作", "取消"}, (dialog, which) -> {
                        switch (which) {
                            case 0:
                                Template template = new Gson().fromJson(templateStr, Template.class);
                                getView().getIntent().putExtra(EXTRA_TEMPLATE, template.getType());
                                getView().initTemplate(template.getType(), template);
                                break;
                            case 1:
                                TemplatePreferences.setTemplate("");
                                getView().initTemplate(index, null);
                                break;
                            case 2:
                                getView().finish();
                                break;
                        }
                    })
                    .show();
        } else {
            getView().initTemplate(index, null);
        }

        mThread.start();
    }

    public void saveCraft() {
        boolean isSave = false; // 是否保存草稿
        Template template = new Template();
        template.setType(getView().getIntent().getIntExtra(EXTRA_TEMPLATE, 0));
        if (getView().getHeader() != null) {
            ClearEditText et = (ClearEditText) getView().getHeader().findViewById(R.id.et_template_header);
            if (et != null) {
                template.setTitle(et.getText().toString());
                isSave = et.isEdit() || isSave;
            }
            ClearEditText et2 = (ClearEditText) getView().getHeader().findViewById(R.id.et_template_header_2);
            if (et2 != null) {
                template.setDesc(et2.getText().toString());
                isSave = isSave || et2.isEdit();
            }
        }
        if (getView().getTemplateList() != null && getView().getTemplateList().size() > 0) {
            List<Template.Item> itemList = new ArrayList<>();
            for (TemplateView templateView : getView().getTemplateList()) {
                Template.Item item = new Template.Item();
                item.setEtContents(templateView.getTexts());
                item.setUris(templateView.getUris());
                itemList.add(item);
                if (!isSave) {
                    isSave = templateView.getUris().size() > 0;
                    for (ClearEditText editText : templateView.getEditTexts()) {
                        isSave = isSave || editText.isEdit();
                    }
                }
            }
            template.setItems(itemList);
        }
        TemplatePreferences.setTemplate(isSave ? new Gson().toJson(template) : "");
    }

    public void takeShot(View layout) {
        ScreenShot.getInstance().takeScreenShotOfJustView(layout, (path, uri) -> {
            getView().getExpansionDelegate().hideProgressBar();
            TemplatePreferences.setTemplate("");
            for (TemplateView templateView : getView().getTemplateList()) {
                templateView.completeCapture();
            }
            SaveTemplatePresenter.start(getView(), path, uri);
        });
    }

    @Override
    protected void onResult(int requestCode, int resultCode, Intent data) {
        super.onResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            ImageProvider.getInstance(getView()).onActivityResult(requestCode, resultCode, data);
//            if (requestCode == TemplateView.REQUEST_CODE_FILTER && data != null) {
//                boolean applyAll = data.getBooleanExtra(FilterActivity.EXTRA_APPLY_ALL, false);
//                String path  = data.getStringExtra(FilterActivity.EXTRA_PATH);
//                if (applyAll) {
//                    for (TemplateView templateView : getView().getTemplateList()) {
////                        templateView.
//                    }
//                } else {
//
//                }
//            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mIsRunning = false;
    }

}
