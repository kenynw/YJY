package com.miguan.yjy.module.test;

import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.TabLayout;
import android.util.Log;
import android.view.View;

import com.dsk.chain.expansion.data.BaseDataActivityPresenter;
import com.miguan.yjy.adapter.SkinTestViewPager;
import com.miguan.yjy.model.AccountModel;
import com.miguan.yjy.model.TestModel;
import com.miguan.yjy.model.UserModel;
import com.miguan.yjy.model.bean.Test;
import com.miguan.yjy.model.bean.User;
import com.miguan.yjy.model.local.UserPreferences;
import com.miguan.yjy.model.services.ServicesResponse;
import com.miguan.yjy.module.account.LoginActivity;
import com.miguan.yjy.utils.DateUtils;
import com.miguan.yjy.utils.LUtils;
import com.miguan.yjy.widget.ShareBottomDialog;

import static com.miguan.yjy.model.local.UserPreferences.getTestPosition;

/**
 * @作者 cjh
 * @日期 2017/8/29 16:42
 * @描述
 */

public class TestResultActivityPresenter extends BaseDataActivityPresenter<TestResultActivity, Test> {


    public void startToLogin() {
        Intent intent = new Intent(getView(), LoginActivity.class);
        getView().startActivity(intent);
    }

    /**
     * 肤质测试分享
     */
    public void share() {
        String url = "http://m." + (LUtils.isDebug ? "beta." : "") + "yjyapp.com/site/skin-share?user_id=";
        new ShareBottomDialog.Builder(getView())
                .setTitle("护肤界的16种肤质，你知道你是哪种吗?")
                .setUrl(url + UserPreferences.getUserID())//等待地址替换
                .setContent("搞不清楚肤质就护肤？测清楚总不会错！")
                .setWxCircleTitle("What？！活了20+年才搞清楚自己，原来是这种肤质！")
                .setWbContent("护肤界的16种肤质，你知道你是哪种吗？#颜究院APP#" + url + UserPreferences.getUserID())//等待地址替换
                .setType(3)
                .show();
    }


    public void setSencondData() {

        TestModel.getInstantce().getSkinRecommend().subscribe(new ServicesResponse<Test>() {
            @Override
            public void onNext(Test test) {
                if (AccountModel.getInstance().isLogin()) {
                    getView().mSkinTestViewPager = new SkinTestViewPager(getView().getSupportFragmentManager(), test);
                    getView().mViewpagerSkinTest.setNoScroll(true);
                    getView().mViewpagerSkinTest.setAdapter(getView().mSkinTestViewPager);
                    getView().mTabSkinTest.setupWithViewPager(getView().mViewpagerSkinTest);
                    getView().mTvSkinUsername.setText(getView().userInfo.getUsername());
                    if (getView().userInfo.getAdd_time().equals("0")) {
                        getView().mTvSkinTime.setText("");
                    } else {
                        getView().mTvSkinTime.setText(DateUtils.getStrTime(getView().userInfo.getAdd_time()));
                    }
                    getView().mImgSkinTest.setImageURI(Uri.parse(getView().userInfo.getImg()));
                    Log.e("getTestPosition()", getTestPosition() + "====");
                    getView().mTabSkinTest.getTabAt(getTestPosition()).select();
                    getView().mTabSkinTest.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                        @Override
                        public void onTabSelected(TabLayout.Tab tab) {
                            Log.e("tab.getPosition()", tab.getPosition() + "====");
                            UserPreferences.setTestPosition(tab.getPosition());
                        }

                        @Override
                        public void onTabUnselected(TabLayout.Tab tab) {

                        }

                        @Override
                        public void onTabReselected(TabLayout.Tab tab) {

                        }
                    });

                    getView().mTvSkinAgain.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //显示肤质测试主页
//                            loadFirstData();
                            UserPreferences.setIsShowTest(true);
                        }
                    });
                }
            }
        });

    }

    public void loadMainData() {
        if (AccountModel.getInstance().isLogin()) {
            UserModel.getInstance().getUserInfo().subscribe(new ServicesResponse<User>() {
                @Override
                public void onNext(User user) {
                    getView().userInfo = user;
                    setSencondData();
                }
            });
        }

    }



}
