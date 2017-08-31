package com.miguan.yjy.module.test;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.dsk.chain.bijection.RequiresPresenter;
import com.dsk.chain.expansion.data.BaseDataActivity;
import com.facebook.drawee.view.SimpleDraweeView;
import com.miguan.yjy.R;
import com.miguan.yjy.adapter.SkinTestViewPager;
import com.miguan.yjy.model.AccountModel;
import com.miguan.yjy.model.bean.Test;
import com.miguan.yjy.model.bean.User;
import com.miguan.yjy.widget.NoScrollViewPager;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @作者 cjh
 * @日期 2017/8/29 16:38
 * @描述
 */
@RequiresPresenter(TestResultActivityPresenter.class)
public class TestResultActivity extends BaseDataActivity<TestResultActivityPresenter, Test> {

    //测试结果(我的肤质)
    public static final String H5_SCORE = "http://m.yjyapp.com/site/score-tip";
    @BindView(R.id.img_skin_test)
    SimpleDraweeView mImgSkinTest;
    @BindView(R.id.tv_skin_username)
    TextView mTvSkinUsername;
    @BindView(R.id.tv_skin_time)
    TextView mTvSkinTime;
    @BindView(R.id.tv_skin_again)
    TextView mTvSkinAgain;

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;

    @BindView(R.id.id_stickynavlayout_tab)
    TabLayout mTabSkinTest;
    @BindView(R.id.id_stickynavlayout_viewpager)
    NoScrollViewPager mViewpagerSkinTest;


    public SkinTestViewPager mSkinTestViewPager;

    public User userInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.skin_test_result);
        ButterKnife.bind(this);
        mToolbarTitle.setText("我的肤质");
        mToolbar.inflateMenu(R.menu.menu_share);
        mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (AccountModel.getInstance().isLogin()) {
                    getPresenter().share();
                } else {
                    getPresenter().startToLogin();
                }
                return false;
            }
        });
        getPresenter().loadMainData();
    }

}
