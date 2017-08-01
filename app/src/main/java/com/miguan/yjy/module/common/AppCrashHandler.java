package com.miguan.yjy.module.common;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.miguan.yjy.module.main.MainActivity;

/**
 * Copyright (c) 2017/5/11. LiaoPeiKun Inc. All rights reserved.
 */

public class AppCrashHandler {

    private static AppCrashHandler mInstance;

    private Context mContext;

    private Thread.UncaughtExceptionHandler mUncaughtExceptionHandler;

    public static AppCrashHandler getInstance(Context context) {
        if (null == mInstance) {
            synchronized (AppCrashHandler.class) {
                if (null == mInstance) {
                    mInstance = new AppCrashHandler(context);
                }
            }
        }

        return mInstance;
    }

    private AppCrashHandler(Context context) {
        mContext = context;

        mUncaughtExceptionHandler = Thread.getDefaultUncaughtExceptionHandler();

        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread t, Throwable e) {
//                new Thread() {
//                    @Override
//                    public void run() {
//                        Looper.prepare();
//                        LUtils.toast("很抱歉,程序出现异常,即将重启");
//                        Looper.loop();
//                    }
//                }.start();
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ie) {
                    ie.printStackTrace();
                }

                restartApp();

                mUncaughtExceptionHandler.uncaughtException(t, e);
            }
        });
    }

    private void restartApp() {
        Intent intent = new Intent(mContext, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent restartIntent = PendingIntent.getActivity(mContext, 0, intent, PendingIntent.FLAG_ONE_SHOT);

        //重启应用
        AlarmManager mgr = (AlarmManager) mContext.getSystemService(Context.ALARM_SERVICE);
        mgr.set(AlarmManager.RTC, System.currentTimeMillis(), restartIntent);

        //退出程序
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(0);
        System.gc();
    }

}
