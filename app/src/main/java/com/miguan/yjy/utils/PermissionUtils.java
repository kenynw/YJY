package com.miguan.yjy.utils;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;

/**
 * Copyright (c) 2017/4/7. LiaoPeiKun Inc. All rights reserved.
 */

public class PermissionUtils {

    private PermissionUtils() {}

    public static void onRequestPermissionsResult(int[] grantResults, RPResultListener RPResultListener) {
        if (grantResults.length > 0) {
            boolean result = true;
            for (int grantResult : grantResults) {
                result = result && (grantResult == PackageManager.PERMISSION_GRANTED);
                LUtils.log("result: " + result + ", grant; " + grantResult);
            }
            if (result) {
                RPResultListener.onPermissionGranted();
            } else {
                RPResultListener.onPermissionDenied();
            }
        }
    }

    @TargetApi(value = Build.VERSION_CODES.M)
    public static void requestPermission(Activity activity, String[] permissions, int requestCode) {
        ActivityCompat.requestPermissions(activity, permissions, requestCode);
    }

    @TargetApi(value = Build.VERSION_CODES.M)
    public static void requestPermission(Activity activity, String permission, int requestCode) {
        ActivityCompat.requestPermissions(activity, new String[]{permission}, requestCode);
    }

    public static boolean checkPermissionGranted(Context context, String permission) {
        return ActivityCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED;
    }

}
