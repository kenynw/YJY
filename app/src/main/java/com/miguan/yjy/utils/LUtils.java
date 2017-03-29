package com.miguan.yjy.utils;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.StringRes;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Copyright (c) 2015. LiaoPeiKun Inc. All rights reserved.
 */
public class LUtils {
    private static final String TAG = "DSK";

    private static final String PREFERENCES_NAME = "prefs";

    public static boolean isDebug = true;

    private static Toast toast;

    private static Context sContext;

    public static void initialize(Application app) {
        sContext = app.getApplicationContext();
    }

    /**
     * 获取Context
     * @return
     */
    public static Context getAppContext() {
        return sContext;
    }

    /**
     * 打印
     * @param msg
     */
    public static void log(String msg) {
        log(TAG, msg);
    }

    public static void log(String tag, String msg) {
        if (isDebug)
            android.util.Log.i(tag, msg);
    }

    public static void toast(CharSequence text) {
        if (toast == null) {
            toast = Toast.makeText(sContext, text, Toast.LENGTH_SHORT);
        } else {
            toast.setText(text);
        }
        toast.setGravity(Gravity.CENTER,0,0);
        toast.show();
    }

    public static void toast(@StringRes int resId) {
        toast(sContext.getString(resId));
    }

    public static SharedPreferences getPreferences() {
        return getPreferences(PREFERENCES_NAME, Activity.MODE_PRIVATE);
    }

    public static SharedPreferences getPreferences(String name) {
        return getPreferences(name, Activity.MODE_PRIVATE);
    }

    public static SharedPreferences getPreferences(String name, int mode) {
        return sContext.getSharedPreferences(name, mode);
    }

    /**
     * 获取屏幕宽度
     * @return
     */
    public static int getScreenWidth() {
        WindowManager wm = (WindowManager) sContext.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.widthPixels;
    }

    /**
     * 获取屏幕高度
     * @return
     */
    public static int getScreenHeight() {
        WindowManager wm = (WindowManager) sContext.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.heightPixels;
    }

    /**
     * dp转化成px
     * @param dpVal
     * @return
     */
    public static int dp2px(float dpVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                dpVal, sContext.getResources().getDisplayMetrics());
    }

    /**
     * 打开虚拟键盘
     * @param editText
     */
    public static void openKeyboard(EditText editText) {
        InputMethodManager imm = (InputMethodManager) sContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(editText, InputMethodManager.RESULT_SHOWN);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
    }

    /**
     * 关闭虚拟键盘
     * @param editText
     */
    public static void closeKeyboard(EditText editText) {
        InputMethodManager imm = (InputMethodManager) sContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
    }

    /**
     * 检查当前网络是否可用
     * @return
     */
    public static boolean isNetWorkAvilable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) sContext
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetInfo = connectivityManager.getActiveNetworkInfo();
        if (activeNetInfo == null || !activeNetInfo.isAvailable()) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * 获取版本号
     * @return
     */
    public static int getAppVersionCode(){
        try {
            PackageManager mPackageManager = sContext.getPackageManager();
            PackageInfo _info = mPackageManager.getPackageInfo(sContext.getPackageName(),0);
            return _info.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            return 0;
        }
    }

    /**
     * 获取版本名
     * @return
     */
    public static String getAppVersionName(){
        try {
            PackageManager mPackageManager = sContext.getPackageManager();
            PackageInfo _info = mPackageManager.getPackageInfo(sContext.getPackageName(),0);
            return _info.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            return null;
        }
    }

    /**
     * 获取当前进程名
     * @param context
     * @return 进程名
     */
    public static final String getProcessName(Context context) {
        String processName = null;

        // ActivityManager
        ActivityManager am = ((ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE));

        while (true) {
            for (ActivityManager.RunningAppProcessInfo info : am.getRunningAppProcesses()) {
                if (info.pid == android.os.Process.myPid()) {
                    processName = info.processName;

                    break;
                }
            }

            // go home
            if (!TextUtils.isEmpty(processName)) {
                return processName;
            }

            // take a rest and again
            try {
                Thread.sleep(100L);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }

    /**
     * MD5加密
     * @param s
     * @return
     */
    public static String md5(String s) {
        String result = "";
        try {
            // Create MD5 Hash
            MessageDigest digest = MessageDigest.getInstance("MD5");
            digest.update(s.getBytes());
            BigInteger hash = new BigInteger(1, digest.digest());
            result = hash.toString(16);
            while(result.length() < 32) { //40 for SHA-1
                result = "0" + result;
            }
//
//            byte messageDigest[] = digest.digest();
//
//            // Create Hex String
//            StringBuffer hexString = new StringBuffer();
//            for (int i=0; i<messageDigest.length; i++)
//                hexString.append(Integer.toHexString(0xFF & messageDigest[i]));
//
//            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return result;
    }

}
