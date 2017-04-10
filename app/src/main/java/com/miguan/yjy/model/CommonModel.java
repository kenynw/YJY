package com.miguan.yjy.model;

import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.AlertDialog;
import android.text.Html;

import com.dsk.chain.model.AbsModel;
import com.miguan.yjy.R;
import com.miguan.yjy.model.bean.Version;
import com.miguan.yjy.model.services.DefaultTransform;
import com.miguan.yjy.model.services.ServicesClient;
import com.miguan.yjy.model.services.ServicesResponse;
import com.miguan.yjy.module.common.UpdateService;
import com.miguan.yjy.utils.LUtils;

/**
 * Copyright (c) 2017/1/13. LiaoPeiKun Inc. All rights reserved.
 */

public class CommonModel extends AbsModel {

    public static CommonModel getInstance() {
        return getInstance(CommonModel.class);
    }

    public void update(Context context) {
        ServicesClient.getServices().checkUpdate()
                .compose(new DefaultTransform<>())
                .subscribe(new ServicesResponse<Version>() {
                    @Override
                    public void onNext(Version version) {
                        if (!version.getNumber().equals(LUtils.getAppVersionName()))
                            showUpdateDialog(context, version);
                    }
                });
    }

    public void checkUpdate(Context context) {
        ServicesClient.getServices().checkUpdate()
                .compose(new DefaultTransform<>())
                .subscribe(new ServicesResponse<Version>() {
                    @Override
                    public void onNext(Version version) {
                        if (version.getType() == 0) {
                            LUtils.log("已经是最新版本了");
                        } else if (!version.getNumber().equals(LUtils.getAppVersionName())) {
                            showUpdateDialog(context, version);
                        }
                    }
                });
    }

    private void showUpdateDialog(Context context, Version version) {
        new AlertDialog.Builder(context)
                .setTitle("新版本发布啦~" + version.getNumber())
                .setMessage(Html.fromHtml(version.getContent()[0]))
                .setNegativeButton("取消", null)
                .setPositiveButton("去更新", (dialog, which) -> {
                    LUtils.log("开始下载");
                    Intent intent = new Intent(context, UpdateService.class);
                    intent.putExtra("title", "正在下载" + context.getString(R.string.app_name));
                    intent.putExtra("url", version.getDownloadUrl());
                    intent.putExtra("path", findDownLoadDirectory());
                    intent.putExtra("name", context.getString(R.string.app_name) + "v" + version.getNumber() + ".apk");
                    context.startService(intent);
                })
                .show();
    }

    private String findDownLoadDirectory(){
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
            return Environment.getExternalStorageDirectory() + "/" + "download/";
        }else{
            return Environment.getRootDirectory() + "/" + "download/";
        }
    }

}
