package com.miguan.yjy.module.main;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.miguan.yjy.utils.LUtils;

public class LauncherActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if((getIntent().getFlags() & Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT) != 0){
            finish();
            return;
        }

        if (LUtils.getPreferences().getBoolean("is_first", true)) {
            startActivity(new Intent(this, GuideActivity.class));
        } else {
            new Handler().postDelayed(() -> {
                startActivity(new Intent(LauncherActivity.this, MainActivity.class));
                finish();
            }, 1000);
        }
    }

}
