package com.miguan.yjy.module.test;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ScrollView;
import android.widget.TextView;

import com.dsk.chain.bijection.RequiresPresenter;
import com.dsk.chain.expansion.data.BaseDataActivity;
import com.miguan.yjy.R;
import com.miguan.yjy.model.AccountModel;
import com.miguan.yjy.model.TestModel;
import com.miguan.yjy.model.UserModel;
import com.miguan.yjy.model.bean.Test;
import com.miguan.yjy.model.bean.User;
import com.miguan.yjy.model.local.UserPreferences;
import com.miguan.yjy.model.services.ServicesResponse;
import com.miguan.yjy.module.user.ProfilePresenter;
import com.miguan.yjy.utils.LUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;
import rx.functions.Func1;

/**
 * @作者 cjh
 * @日期 2017/8/29 16:39
 * @描述
 */
@RequiresPresenter(TestInitActivityPresenter.class)
public class TestInitActivity extends BaseDataActivity<TestInitActivityPresenter, Test> implements View.OnClickListener {

    //未测试页面(测试主页)
    @BindView(R.id.iv_test_wrinkle)
    ImageView mIvTestWrinkle;
    @BindView(R.id.tv_test_wrinkle)
    TextView mTvTestWrinkle;
    @BindView(R.id.ll_test_wrinkle)
    LinearLayout mLlTestWrinkle;
    @BindView(R.id.iv_test_oily)
    ImageView mIvTestOily;
    @BindView(R.id.tv_test_oily)
    TextView mTvTestOily;
    @BindView(R.id.ll_test_oily)
    LinearLayout mLlTestOily;
    @BindView(R.id.iv_test_sensitive)
    ImageView mIvTestSensitive;
    @BindView(R.id.tv_test_sensitive)
    TextView mTvTestSensitive;
    @BindView(R.id.ll_test_sensitive)
    LinearLayout mLlTestSensitive;
    @BindView(R.id.iv_test_pigment)
    ImageView mIvTestPigment;
    @BindView(R.id.tv_test_pigment)
    TextView mTvTestPigment;
    @BindView(R.id.ll_test_pigment)
    LinearLayout mLlTestPigment;
    @BindView(R.id.tv_test_result)
    TextView mTvTestResult;
    @BindView(R.id.scr_main_test)
    ScrollView mScrMainTest;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;

    public TextView mTvTestSelectBirthday;
    public TextView mTvTestMan;
    public TextView mTvTestWoman;
    public TextView mTvTestInto;
    public ImageView mTvTestClose;
    public PopupWindow popupWindow;
    public String birthDay = "";
    public int sex = 0;
    public int tag = 0;
    public User userInfo;
    public static List<Integer> nums = new ArrayList<>();
    public static TestInitActivity testInitActivity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_fragment_test);
        testInitActivity = this;
        ButterKnife.bind(this);
        mToolbarTitle.setText("我的肤质");
        initListener();
        getPresenter().loadMainData();
    }

    public void initListener() {
        mLlTestWrinkle.setOnClickListener(this);
        mLlTestOily.setOnClickListener(this);
        mLlTestSensitive.setOnClickListener(this);
        mLlTestPigment.setOnClickListener(this);
        mTvTestResult.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        Intent intent = null;
        switch (v.getId()) {
            case R.id.ll_test_wrinkle:
                if (AccountModel.getInstance().isLogin()) {
                    if (nums.size() == 4) {
                        UserPreferences.setIsShowTest(false);
                    }
                    tag = 0;
                    if (TextUtils.isEmpty(birthDay)) {
                        getPresenter().showTestUserInfoPop();
                    } else if (sex == 2) {
                        getPresenter().showTestUserInfoPop();
                        getPresenter().setUserBirthDay();
                    } else {
                        getPresenter().start(TestInitActivity.this, TestModel.getInstantce().setTestData().get(0), TestGuideActivity.EXTRA_TEST_FOUR_TYPE);
                    }
                } else {
                    getPresenter().startToLogin();
                }
                break;
            case R.id.ll_test_oily:
                if (AccountModel.getInstance().isLogin()) {
                    if (nums.size() == 4) {
                        UserPreferences.setIsShowTest(false);
                    }
                    tag = 1;
                    if (TextUtils.isEmpty(birthDay)) {
                        getPresenter().showTestUserInfoPop();
                    } else if (sex == 2) {
                        getPresenter().showTestUserInfoPop();
                        getPresenter().setUserBirthDay();
                    } else {
                        getPresenter().start(TestInitActivity.this, TestModel.getInstantce().setTestData().get(1), TestGuideActivity.EXTRA_TEST_FIRST_TYPE);
                    }
                } else {
                    getPresenter().startToLogin();
                }
                break;
            case R.id.ll_test_sensitive:
                if (AccountModel.getInstance().isLogin()) {
                    if (nums.size() == 4) {
                        UserPreferences.setIsShowTest(false);
                    }
                    tag = 2;
                    if (TextUtils.isEmpty(birthDay)) {
                        getPresenter().showTestUserInfoPop();
                    } else if (sex == 2) {
                        getPresenter().showTestUserInfoPop();
                        getPresenter().setUserBirthDay();
                    } else {
                        getPresenter().start(TestInitActivity.this, TestModel.getInstantce().setTestData().get(2), TestGuideActivity.EXTRA_TEST_SECOND_TYPE);
                    }
                } else {
                    getPresenter().startToLogin();
                }
                break;
            case R.id.ll_test_pigment:
                if (AccountModel.getInstance().isLogin()) {
                    if (nums.size() == 4) {
                        UserPreferences.setIsShowTest(false);
                    }
                    tag = 3;
                    if (TextUtils.isEmpty(birthDay)) {
                        getPresenter().showTestUserInfoPop();
                    } else if (sex == 2) {
                        getPresenter().showTestUserInfoPop();
                        getPresenter().setUserBirthDay();
                    } else {
                        getPresenter().start(TestInitActivity.this, TestModel.getInstantce().setTestData().get(3), TestGuideActivity.EXTRA_TEST_THIRD_TYPE);
                    }
                } else {
                    getPresenter().startToLogin();
                }
                break;
            case R.id.tv_test_man:
                sex = 1;
                mTvTestMan.setBackgroundResource(R.drawable.bg_shape_63c);
                mTvTestWoman.setBackgroundResource(R.drawable.bg_round_stroke_div);
                mTvTestMan.setTextColor(getResources().getColor(R.color.white));
                mTvTestWoman.setTextColor(getResources().getColor(R.color.textSecondary));
                break;
            case R.id.tv_test_woman:
                sex = 0;
                mTvTestMan.setBackgroundResource(R.drawable.bg_round_stroke_div);
                mTvTestWoman.setBackgroundResource(R.drawable.bg_shape_fb7);
                mTvTestMan.setTextColor(getResources().getColor(R.color.textSecondary));
                mTvTestWoman.setTextColor(getResources().getColor(R.color.white));
                break;
            case R.id.tv_test_into:
                if (TextUtils.isEmpty(birthDay)) {
                    LUtils.toast("请输入完整信息喔~");
                } else {
                    UserModel.getInstance()
                            .modifyProfile(ProfilePresenter.KEY_PROFILE_BIRTHDAY, birthDay)
                            .flatMap(new Func1<String, Observable<String>>() {
                                @Override
                                public Observable<String> call(String s) {
                                    return UserModel.getInstance().modifyProfile("sex", sex + "");
                                }
                            })
                            .subscribe(new ServicesResponse<String>() {
                                @Override
                                public void onNext(String s) {
                                    switch (tag) {
                                        case 0:
                                            getPresenter().start(TestInitActivity.this, TestModel.getInstantce().setTestData().get(0), TestGuideActivity.EXTRA_TEST_FOUR_TYPE);
                                            break;
                                        case 1:
                                            getPresenter().start(TestInitActivity.this, TestModel.getInstantce().setTestData().get(1), TestGuideActivity.EXTRA_TEST_FIRST_TYPE);
                                            break;
                                        case 2:
                                            getPresenter().start(TestInitActivity.this, TestModel.getInstantce().setTestData().get(2), TestGuideActivity.EXTRA_TEST_SECOND_TYPE);
                                            break;
                                        case 3:
                                            getPresenter().start(TestInitActivity.this, TestModel.getInstantce().setTestData().get(3), TestGuideActivity.EXTRA_TEST_THIRD_TYPE);
                                            break;
                                    }

                                }
                            });
                    popupWindow.dismiss();
                }
                break;
            case R.id.iv_test_close:
                birthDay = "";
                sex = 2;
                popupWindow.dismiss();
                break;
            case R.id.tv_test_result:
                if (AccountModel.getInstance().isLogin()) {
//                  setData();显示我的肤质
//                   setSencondData();
                    UserPreferences.setIsShowTest(false);
                    intent = new Intent(TestInitActivity.this, TestResultActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    getPresenter().startToLogin();
                }
                break;
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.e("testInitResult", requestCode + "==" + resultCode);
        if (resultCode == 0) {
            switch (requestCode) {
                case Activity.RESULT_FIRST_USER:
                    getPresenter().loadMainData();
                    break;
            }

        }

    }


}
