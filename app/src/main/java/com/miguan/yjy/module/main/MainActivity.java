package com.miguan.yjy.module.main;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;

import com.dsk.chain.bijection.RequiresPresenter;
import com.dsk.chain.expansion.data.BaseDataActivity;
import com.miguan.yjy.R;
import com.miguan.yjy.adapter.MainTabPagerAdapter;
import com.miguan.yjy.dialogs.BaseAlertDialog;
import com.miguan.yjy.model.bean.Version;
import com.miguan.yjy.model.local.UserPreferences;
import com.miguan.yjy.utils.LUtils;
import com.miguan.yjy.widget.MobileBindPopupWindow;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;

@RequiresPresenter(MainActivityPresenter.class)
public class MainActivity extends BaseDataActivity<MainActivityPresenter, Version> implements BaseAlertDialog.OnButtonClickListener {

    private static final String KEY_CUR_SELECT = "cur_select";

    @BindView(R.id.container_main)
    FrameLayout mContainer;

    @BindView(R.id.tab_main)
    TabLayout mTab;

    private long mPressedTime = 0L;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        ButterKnife.bind(this);

        initTab(savedInstanceState);
    }

    private void initTab(Bundle savedInstanceState) {
        MainTabPagerAdapter adapter = new MainTabPagerAdapter(getSupportFragmentManager(), this);
        mTab.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
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

        int curPos = 0;
        if (savedInstanceState != null && savedInstanceState.containsKey(KEY_CUR_SELECT)) {
            curPos = savedInstanceState.getInt(KEY_CUR_SELECT);
        }

        for (int i = 0; i < adapter.getCount(); i++) {
            TabLayout.Tab tab = mTab.newTab();
            mTab.addTab(tab, i == curPos);
            tab.setCustomView(R.layout.item_tab_main)
                    .setIcon(adapter.getIconRes(i))
                    .setText(adapter.getPageTitle(i));
        }
    }

    public TabLayout.Tab getTab(int position) {
        return mTab.getTabAt(position);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void  setCurrentTab(Integer index) {
        if (index < 0 || index > 3) return;
        mTab.getTabAt(index).select();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putInt(KEY_CUR_SELECT, mTab.getSelectedTabPosition());
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onBackPressed() {
        if (System.currentTimeMillis() - mPressedTime > 2000) {
            LUtils.toast("再按一次退出颜究院");
            mPressedTime = System.currentTimeMillis();
            UserPreferences.setIsShowTest(false);
            return;
        }
        finish();
    }

    @Override
    public void onPositiveClick(@NonNull View v) {
        MobileBindPopupWindow mobileBindPopupWindow = new MobileBindPopupWindow(this);
        mobileBindPopupWindow.showAtLocation(getContent(), Gravity.NO_GRAVITY, 0, 0);
    }

    @Override
    public void onNegativeClick(@NonNull View v) {
        getPresenter().saveCheckBindTime();
    }

}
