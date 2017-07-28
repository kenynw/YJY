package com.miguan.yjy.module.main;

import android.Manifest;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;

import com.miguan.yjy.utils.LUtils;
import com.miguan.yjy.utils.PermissionUtils;

public class LauncherActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        } else {
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            );
        }

        if ((getIntent().getFlags() & Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT) != 0) {
            finish();
            return;
        }

        requestPermission();
    }

    private void requestPermission() {
        if (!PermissionUtils.checkPermissionGranted(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            String[] permissions = new String[]{
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.CAMERA
            };

            PermissionUtils.requestPermission(this, permissions, 100);
        } else {
            onPermissionGranted();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (!PermissionUtils.checkPermissionGranted(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            new AlertDialog.Builder(LauncherActivity.this)
                    .setCancelable(false)
                    .setMessage("拒绝权限将会影响功能使用，请务必同意权限请求")
                    .setPositiveButton("去授权", (dialogInterface, i) -> requestPermission())
                    .show();
        } else {
            onPermissionGranted();
        }
    }

    private void onPermissionGranted() {
        new Handler().postDelayed(() -> {
            Class target = LUtils.getPreferences().getBoolean("is_first", true) ? GuideActivity.class : MainActivity.class;
            startActivity(new Intent(LauncherActivity.this, target));
            finish();
        }, 1000);
    }

}
