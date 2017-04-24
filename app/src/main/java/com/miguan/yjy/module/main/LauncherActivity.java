package com.miguan.yjy.module.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.miguan.yjy.utils.LUtils;

public class LauncherActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (LUtils.getPreferences().getBoolean("is_first", true)) {
            startActivity(new Intent(this, GuideActivity.class));
        } else {
            startActivity(new Intent(this, MainActivity.class));
        }
        finish();
    }

}
