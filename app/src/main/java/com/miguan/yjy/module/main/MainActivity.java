package com.miguan.yjy.module.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.widget.FrameLayout;

import com.dsk.chain.bijection.RequiresPresenter;
import com.dsk.chain.expansion.data.BaseDataActivity;
import com.miguan.yjy.R;
import com.miguan.yjy.adapter.MainTabPagerAdapter;
import com.miguan.yjy.model.bean.Version;
import com.miguan.yjy.model.local.UserPreferences;
import com.miguan.yjy.module.account.LoginActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

@RequiresPresenter(MainActivityPresenter.class)
public class MainActivity extends BaseDataActivity<MainActivityPresenter, Version> {

    @BindView(R.id.container_main)
    FrameLayout mContainer;

    @BindView(R.id.tab_main)
    TabLayout mTab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        ButterKnife.bind(this);

        initTab();
    }

    private void initTab() {
        MainTabPagerAdapter adapter = new MainTabPagerAdapter(getSupportFragmentManager(), this);
        mTab.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                if (tab.getPosition() == 3 && UserPreferences.getUserID() <= 0) {
                    startActivity(new Intent(MainActivity.this, LoginActivity.class));
                    mTab.getTabAt(0).select();
                    position = 0;
                }
                Fragment fragment = (Fragment) adapter.instantiateItem(mContainer, position);
                adapter.setPrimaryItem(mContainer, position, fragment);
                adapter.finishUpdate(mContainer);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        for (int i=0; i<adapter.getCount(); i++) {
            TabLayout.Tab tab = mTab.newTab();
            mTab.addTab(tab);
            tab.setIcon(adapter.getIconRes(i))
                    .setText(adapter.getPageTitle(i));
        }
    }

}
