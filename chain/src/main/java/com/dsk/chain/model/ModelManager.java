package com.dsk.chain.model;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.text.TextUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Copyright (c) 2017/1/4. LiaoPeiKun Inc. All rights reserved.
 */

public class ModelManager {

    private static Context sContext;

    private static BackThread sBackThread = new BackThread();

    private static Map<Class<?>, AbsModel> sModelMap = new HashMap<>();

    public static void init(Context context) {
        sBackThread.start();
        sContext = context;
        Class<?>[] models = null;
        try {
            ApplicationInfo appInfo = context.getPackageManager()
                    .getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
            if (null != appInfo && null != appInfo.metaData && !TextUtils.isEmpty(appInfo.metaData.getString("MODEL"))) {
                String modelName = appInfo.metaData.getString("MODEL").trim();
                String[] modelStrs = modelName.split(",");
                models = new Class[modelStrs.length];
                for (int i=0; i<modelStrs.length; i++) {
                    try {
                        models[i] = Class.forName(modelStrs[i]);
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                for (Class m : models) {
                    launchModel(createModel(m));
                }
            }

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

    }

    public static <T extends AbsModel> T getInstance(Class<T> clazz) {
        if (sModelMap.get(clazz) == null) {
            synchronized (clazz) {
                launchModel(createModel(clazz));
            }
        }
        return (T) sModelMap.get(clazz);
    }

    public static void runOnBackThread(Runnable runnable) {
        sBackThread.execute(runnable);
    }

    private static <T extends AbsModel> T createModel(Class<T> clazz) {
        if (clazz != null && AbsModel.class.isAssignableFrom(clazz)) {
            try {
                AbsModel model = clazz.newInstance();
                sModelMap.put(clazz, model);
                return (T) model;
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        throw new IllegalArgumentException("your model must extends AbsModel");
    }

    private static void launchModel(final AbsModel model) {
        model.onAppCreate(sContext);
        sBackThread.execute(new Runnable() {
            @Override
            public void run() {
                model.onAppCreateOnBackThread(sContext);
            }
        });
    }

}
