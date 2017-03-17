package com.miguan.yjy.module.main;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;

import com.miguan.yjy.R;
import com.miguan.yjy.adapter.MainTabPagerAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.container_main)
    FrameLayout mContainer;

    @BindView(R.id.tab_main)
    TabLayout mTab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        ButterKnife.bind(this);

        mTab.setTabsFromPagerAdapter(new MainTabPagerAdapter(getSupportFragmentManager(), this));
    }
}
