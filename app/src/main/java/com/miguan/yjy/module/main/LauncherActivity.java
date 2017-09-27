package com.miguan.yjy.module.main;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

import com.miguan.yjy.module.article.ArticleDetailPresenter;
import com.miguan.yjy.module.common.WebViewActivity;
import com.miguan.yjy.module.product.ProductDetailPresenter;
import com.miguan.yjy.utils.LUtils;
import com.miguan.yjy.utils.PermissionUtils;

public class LauncherActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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
            if (LUtils.getPreferences().getBoolean("is_first", true)) {
                startActivity(new Intent(LauncherActivity.this, GuideActivity.class));
            } else {
                Intent intent = getIntent();
                String action = intent.getAction();
                String dataString = intent.getDataString();
                if (Intent.ACTION_VIEW.equals(action)) {
                    Uri uri = intent.getData();
                    if (uri != null) {
                        String relation = uri.getQueryParameter("unlrelation");
                        String type = uri.getQueryParameter("unltype");
                        String path = uri.getPath();
                        if (dataString.startsWith("yjyappscheme")) {
                            switch (Integer.parseInt(type)) {
                                case 0://活动
                                    WebViewActivity.start(LauncherActivity.this, "", relation);
                                    break;
                                case 1://产品详情
                                    ProductDetailPresenter.start(LauncherActivity.this, Integer.parseInt(relation));
                                    break;
                                case 2://文章
                                    ArticleDetailPresenter.start(LauncherActivity.this, Integer.parseInt(relation));
                                    break;
                            }
                        }
                    }
                } else {
                    startActivity(new Intent(LauncherActivity.this, MainActivity.class));
                }
            }
            finish();
        }, 1000);
    }

}
