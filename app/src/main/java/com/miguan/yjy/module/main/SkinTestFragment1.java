package com.miguan.yjy.module.main;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bigkoo.pickerview.TimePickerView;
import com.dsk.chain.bijection.RequiresPresenter;
import com.dsk.chain.expansion.data.BaseDataFragment;
import com.facebook.drawee.view.SimpleDraweeView;
import com.miguan.yjy.R;
import com.miguan.yjy.adapter.SkinTestViewPager;
import com.miguan.yjy.model.TestModel;
import com.miguan.yjy.model.UserModel;
import com.miguan.yjy.model.bean.Skin;
import com.miguan.yjy.model.bean.Test;
import com.miguan.yjy.model.bean.TestStart;
import com.miguan.yjy.model.bean.User;
import com.miguan.yjy.model.local.UserPreferences;
import com.miguan.yjy.model.services.ServicesResponse;
import com.miguan.yjy.module.account.LoginActivity;
import com.miguan.yjy.module.test.TestGuideActivity;
import com.miguan.yjy.module.user.ProfilePresenter;
import com.miguan.yjy.utils.DateUtils;
import com.miguan.yjy.utils.LUtils;
import com.miguan.yjy.widget.NoScrollViewPager;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import rx.Observable;
import rx.functions.Func1;

import static com.miguan.yjy.model.local.UserPreferences.getTestPosition;

/**
 * @作者 cjh
 * @日期 2017/6/5 17:29
 * @描述
 */
@RequiresPresenter(SkinTestFragmentPresenter.class)
public class SkinTestFragment1 extends BaseDataFragment<SkinTestFragmentPresenter, Test> implements View.OnClickListener {
    int type = 0;

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
    @BindView(R.id.id_stickynavlayout_tab)
    TabLayout mTabSkinTest;
    @BindView(R.id.id_stickynavlayout_viewpager)
    NoScrollViewPager mViewpagerSkinTest;

    private TextView mTvTestSelectBirthday;
    private TextView mTvTestMan;
    private TextView mTvTestWoman;
    private TextView mTvTestInto;
    private ImageView mTvTestClose;
    private PopupWindow popupWindow;
    String birthDay="";
    private int sex = 0;
    private int tag = 0;
    private User userInfo;
    public static List<Integer> nums = new ArrayList<>();
    private int testPosition;


    //测试结果(我的肤质)
    public static final String H5_SCORE = "http://m.yjyapp.com/site/score-tip";
    private Unbinder mBind;
    @BindView(R.id.img_skin_test)
    SimpleDraweeView mImgSkinTest;
    @BindView(R.id.tv_skin_username)
    TextView mTvSkinUsername;
    @BindView(R.id.tv_skin_time)
    TextView mTvSkinTime;
    @BindView(R.id.tv_skin_again)
    TextView mTvSkinAgain;

    @BindView(R.id.ll_test_no)
    LinearLayout mLlTestNo;
    @BindView(R.id.ll_test_ok)
    LinearLayout mLlTestOk;

    private SkinTestViewPager mSkinTestViewPager;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.skin_test_main_fragment, null);
        mBind = ButterKnife.bind(this, view);
        mToolbar.inflateMenu(R.menu.menu_share);
        mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (UserPreferences.getUserID() != 0) {
                    getPresenter().share();
                } else {
                    startToLogin();
                }
                return false;
            }
        });
        loadMainData();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
//        loadMainData();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.e("onActivityResult", requestCode + "==" + resultCode);
        if (resultCode == 0) {
            switch (requestCode) {
//                case 2:
//                 loadFirstData();
//                    break;
                case Activity.RESULT_FIRST_USER:
                    loadFirstData();
                    break;
            }

        }
    }

    public void loadFirstData() {
        mToolbarTitle.setText("肤质测试");
        mLlTestOk.setVisibility(View.GONE);
        mLlTestNo.setVisibility(View.VISIBLE);

        initListener();
        if (UserPreferences.getUserID() > 0) {
            UserModel.getInstance().getUserInfo().subscribe(new ServicesResponse<User>() {
                @Override
                public void onNext(User user) {
                    nums.clear();
                    userInfo = user;
                    birthDay = userInfo.getBirth_day();
                    sex = userInfo.getSex();
                    if (userInfo.getCompact() != 0) {
                        mLlTestWrinkle.setBackgroundResource(R.drawable.bg_shape_test_a3e);
                        mTvTestWrinkle.setText(Skin.getCompact(userInfo.getCompact()));
                        mIvTestWrinkle.setBackgroundResource(R.mipmap.ic_test_wrinkle_reslut);
                        nums.add(0);
                    }
                    if (userInfo.getDry() != 0) {
                        mLlTestOily.setBackgroundResource(R.drawable.bg_shape_test_a9d);
                        nums.add(1);
                        mTvTestOily.setText(Skin.getDryOil(userInfo.getDry()));
                        mIvTestOily.setBackgroundResource(R.mipmap.ic_test_oily_reslut);
                    }
                    if (userInfo.getTolerance() != 0) {
                        mLlTestSensitive.setBackgroundResource(R.drawable.bg_shape_test_a9d);
                        nums.add(2);
                        mTvTestSensitive.setText(Skin.getTolerance(userInfo.getTolerance()));
                        mIvTestSensitive.setBackgroundResource(R.mipmap.ic_test_sensitive_reslut);
                    }
                    if (userInfo.getPigment() != 0) {
                        mLlTestPigment.setBackgroundResource(R.drawable.bg_shape_test_a3e);
                        nums.add(3);
                        mTvTestPigment.setText(Skin.getPigment(userInfo.getPigment()));
                        mIvTestPigment.setBackgroundResource(R.mipmap.ic_test_pigment_reslut);
                    }
                    if (nums.size() == 4) {
                        mTvTestResult.setText("查看结果");
                        mTvTestResult.setClickable(true);
                    } else {
                        mTvTestResult.setText("完成度\n" + nums.size() + "/4");
                        mTvTestResult.setClickable(false);
                    }

                }
            });
        } else {
            Log.e("userId", UserPreferences.getUserID() + "--=0--");
            mLlTestWrinkle.setBackgroundResource(R.drawable.bg_shape_white);
            mTvTestWrinkle.setText(R.string.text_no_test);
            mIvTestWrinkle.setBackgroundResource(R.mipmap.ic_test_wrinkle);

            mLlTestOily.setBackgroundResource(R.drawable.bg_shape_white);
            mTvTestOily.setText(R.string.text_no_test);
            mIvTestOily.setBackgroundResource(R.mipmap.ic_test_oily);

            mLlTestSensitive.setBackgroundResource(R.drawable.bg_shape_white);
            mTvTestSensitive.setText(R.string.text_no_test);
            mIvTestSensitive.setBackgroundResource(R.mipmap.ic_test_sensitive);

            mLlTestPigment.setBackgroundResource(R.drawable.bg_shape_white);
            mTvTestPigment.setText(R.string.text_no_test);
            mIvTestPigment.setBackgroundResource(R.mipmap.ic_test_pigment);

            mTvTestResult.setText("完成度\n" + "0/4");
            mTvTestResult.setClickable(true);
        }

    }

    private void initListener() {
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
                if (UserPreferences.getUserID() > 0) {
                    if (nums.size() == 4) {
                        UserPreferences.setIsShowTest(false);
                    }
                    tag = 0;
                    if (TextUtils.isEmpty(birthDay)) {
                        showTestUserInfoPop();
                    } else if (userInfo.getSex() == 2) {
                        showTestUserInfoPop();
                        setUserBirthDay();
                    } else {
                        start(getActivity(), TestModel.getInstantce().setTestData().get(0), TestGuideActivity.EXTRA_TEST_FOUR_TYPE);
                    }
                } else {
                    startToLogin();
                }
                break;
            case R.id.ll_test_oily:
                if (UserPreferences.getUserID() > 0) {
                    if (nums.size() == 4) {
                        UserPreferences.setIsShowTest(false);
                    }
                    tag = 1;
                    if (TextUtils.isEmpty(birthDay)) {
                        showTestUserInfoPop();
                    } else if (userInfo.getSex() == 2) {
                        showTestUserInfoPop();
                        setUserBirthDay();
                    } else {
                        start(getActivity(), TestModel.getInstantce().setTestData().get(1), TestGuideActivity.EXTRA_TEST_FIRST_TYPE);
                    }
                } else {
                    startToLogin();
                }
                break;
            case R.id.ll_test_sensitive:
                if (UserPreferences.getUserID() > 0) {
                    if (nums.size() == 4) {
                        UserPreferences.setIsShowTest(false);
                    }
                    tag = 2;
                    if (TextUtils.isEmpty(birthDay)) {
                        showTestUserInfoPop();
                    } else if (userInfo.getSex() == 2) {
                        showTestUserInfoPop();
                        setUserBirthDay();
                    } else {

                        start(getActivity(), TestModel.getInstantce().setTestData().get(2), TestGuideActivity.EXTRA_TEST_SECOND_TYPE);
                    }
                } else {
                    startToLogin();
                }
                break;
            case R.id.ll_test_pigment:
                if (UserPreferences.getUserID() > 0) {
                    if (nums.size() == 4) {
                        UserPreferences.setIsShowTest(false);
                    }
                    tag = 3;
                    if (TextUtils.isEmpty(birthDay)) {
                        showTestUserInfoPop();
                    } else if (userInfo.getSex() == 2) {
                        showTestUserInfoPop();
                        setUserBirthDay();
                    } else {
                        start(getActivity(), TestModel.getInstantce().setTestData().get(3), TestGuideActivity.EXTRA_TEST_THIRD_TYPE);
                    }
                } else {
                    startToLogin();
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
                                            start(getActivity(), TestModel.getInstantce().setTestData().get(0), TestGuideActivity.EXTRA_TEST_FOUR_TYPE);
                                            break;
                                        case 1:
                                            start(getActivity(), TestModel.getInstantce().setTestData().get(1), TestGuideActivity.EXTRA_TEST_FIRST_TYPE);
                                            break;
                                        case 2:
                                            start(getActivity(), TestModel.getInstantce().setTestData().get(2), TestGuideActivity.EXTRA_TEST_SECOND_TYPE);
                                            break;
                                        case 3:
                                            start(getActivity(), TestModel.getInstantce().setTestData().get(3), TestGuideActivity.EXTRA_TEST_THIRD_TYPE);
                                            break;
                                    }

                                }
                            });
                    popupWindow.dismiss();
                }
                break;
            case R.id.iv_test_close:
                popupWindow.dismiss();
                break;
            case R.id.tv_test_result:
                if (UserPreferences.getUserID() > 0) {
//                  setData();显示我的肤质
                    setSencondData();
                    UserPreferences.setIsShowTest(false);
                } else {
                    startToLogin();
                }
                break;
        }
    }

    /**
     * 显示测试用户基础信息
     */

    private void showTestUserInfoPop() {
        View view = View.inflate(getActivity(), R.layout.popwindow_test_info, null);
        mTvTestSelectBirthday = ButterKnife.findById(view, R.id.tv_test_select_birthday);
        mTvTestMan = ButterKnife.findById(view, R.id.tv_test_man);
        mTvTestWoman = ButterKnife.findById(view, R.id.tv_test_woman);
        mTvTestInto = ButterKnife.findById(view, R.id.tv_test_into);
        mTvTestClose = ButterKnife.findById(view, R.id.iv_test_close);

        mTvTestSelectBirthday.setOnClickListener(v -> selectDate());
        mTvTestMan.setOnClickListener(this);
        mTvTestWoman.setOnClickListener(this);
        mTvTestInto.setOnClickListener(this);
        mTvTestClose.setOnClickListener(this);

        popupWindow = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, true);
        ColorDrawable bg = new ColorDrawable(0x55000000);
        popupWindow.setBackgroundDrawable(bg);
        popupWindow.showAtLocation(mToolbar, Gravity.BOTTOM, 0, 0);

        switch (userInfo.getSex()) {
            case 0://女
                sex = 0;
                mTvTestMan.setBackgroundResource(R.drawable.bg_round_stroke_div);
                mTvTestWoman.setBackgroundResource(R.drawable.bg_shape_fb7);
                mTvTestMan.setTextColor(getResources().getColor(R.color.textSecondary));
                mTvTestWoman.setTextColor(getResources().getColor(R.color.white));
            case 1://男
                sex = 1;
                mTvTestMan.setBackgroundResource(R.drawable.bg_shape_63c);
                mTvTestWoman.setBackgroundResource(R.drawable.bg_round_stroke_div);
                mTvTestMan.setTextColor(getResources().getColor(R.color.white));
                mTvTestWoman.setTextColor(getResources().getColor(R.color.textSecondary));
//                mTvTestMan.performClick();
                break;
            case 2://未填
                sex = 0;
                mTvTestMan.setBackgroundResource(R.drawable.bg_round_stroke_div);
                mTvTestWoman.setBackgroundResource(R.drawable.bg_shape_fb7);
                mTvTestMan.setTextColor(getResources().getColor(R.color.textSecondary));
                mTvTestWoman.setTextColor(getResources().getColor(R.color.white));
                break;
        }
    }


    private void selectDate() {

        //时间选择器
        TimePickerView pvTime = new TimePickerView.Builder(getActivity(), new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调
                birthDay = String.valueOf(date.getTime() / 1000);
                mTvTestSelectBirthday.setText(DateUtils.DateToStr(date));
            }
        }).setCancelColor(getResources().getColor(R.color.textSecondary)).setSubmitColor(getResources().getColor(R.color.colorPrimary)).isDialog(true).setType(TimePickerView.Type.YEAR_MONTH_DAY).build();
        pvTime.setDate(Calendar.getInstance());//注：根据需求来决定是否使用该方法（一般是精确到秒的情况），此项可以在弹出选择器的时候重新设置当前时间，避免在初始化之后由于时间已经设定，导致选中时间与当前时间不匹配的问题。
        pvTime.show();
    }

    public void startToLogin() {
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        startActivity(intent);
    }


    public void loadMainData() {
        if (UserPreferences.getUserID() > 0) {
            testPosition = UserPreferences.getTestPosition();
            Log.e("testPosition", testPosition + "testPosition===");
            UserModel.getInstance().getUserInfo().subscribe(new ServicesResponse<User>() {
                @Override
                public void onNext(User user) {
                    nums.clear();
                    userInfo = user;
                    if (userInfo.getCompact() != 0) {
                        Log.e("userInfo.getCompact()", userInfo.getCompact() + "------");
                        nums.add(0);
                    }
                    if (userInfo.getDry() != 0) {
                        nums.add(1);
                        Log.e("userInfo.getDry()", userInfo.getDry() + "------");
                    }
                    if (userInfo.getTolerance() != 0) {
                        nums.add(2);
                        Log.e("userInfo.getTolerance()", userInfo.getTolerance() + "------");
                    }
                    if (userInfo.getPigment() != 0) {
                        nums.add(3);
                        Log.e("userInfo.getPigment()", userInfo.getPigment() + "------");
                    }
                    Log.e("nums", nums.size() + "--大小 boolean--" + UserPreferences.getIsShowTest());
                    if (nums.size() == 4 && !UserPreferences.getIsShowTest()) {
                        //显示测试结果
                        setSencondData();
                    } else {
                        //显示未测试页面
                        loadFirstData();

                    }
                }
            });
        } else {
            //显示未测试页面
            loadFirstData();
        }

    }

    @Override
    public void setMenuVisibility(boolean menuVisible) {
        super.setMenuVisibility(menuVisible);
        if (getView() != null) {
            getView().setVisibility(menuVisible ? View.VISIBLE : View.INVISIBLE);
        }
    }

    Test mTest;

    private void setSencondData() {
        mLlTestOk.setVisibility(View.VISIBLE);
        mLlTestNo.setVisibility(View.GONE);
        mToolbarTitle.setText("我的肤质");
        TestModel.getInstantce().getSkinRecommend().subscribe(new ServicesResponse<Test>(){
            @Override
            public void onNext(Test test) {
                if (UserPreferences.getUserID() > 0) {
                    mSkinTestViewPager = new SkinTestViewPager(getActivity().getSupportFragmentManager(), test);
                    mViewpagerSkinTest.setNoScroll(true);
                    mViewpagerSkinTest.setAdapter(mSkinTestViewPager);
                    mTabSkinTest.setupWithViewPager(mViewpagerSkinTest);
                    mTvSkinUsername.setText(userInfo.getUsername());
                    mTvSkinTime.setText(DateUtils.getStrTime(userInfo.getAdd_time()));
                    mImgSkinTest.setImageURI(Uri.parse(userInfo.getImg()));
                    Log.e("getTestPosition()", getTestPosition() + "====");
                    mTabSkinTest.getTabAt(testPosition).select();
                    mTabSkinTest.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
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

//                    for (int i = 0; i < mSkinTestViewPager.getCount(); i++) {
//                        TabLayout.Tab tab = mTabSkinTest.newTab();
//                        mTabSkinTest.addTab(tab, i == UserPreferences.getTestPosition());
//                    }
                    mTvSkinAgain.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //显示肤质测试主页
                            loadFirstData();
                            UserPreferences.setIsShowTest(true);
                        }
                    });
                }
            }
        });

//        TestModel.getInstantce().userSkin().subscribe(new ServicesResponse<Test>() {
//            @Override
//            public void onNext(Test test) {
//                if (UserPreferences.getUserID() > 0) {
//                    mSkinTestViewPager = new SkinTestViewPager(getActivity().getSupportFragmentManager(), test);
//                    mViewpagerSkinTest.setNoScroll(true);
//                    mViewpagerSkinTest.setAdapter(mSkinTestViewPager);
//                    mTabSkinTest.setupWithViewPager(mViewpagerSkinTest);
//                    mTvSkinUsername.setText(userInfo.getUsername());
//                    mTvSkinTime.setText(DateUtils.getStrTime(userInfo.getAdd_time()));
//                    mImgSkinTest.setImageURI(Uri.parse(userInfo.getImg()));
//                    Log.e("getTestPosition()", getTestPosition() + "====");
//                    mTabSkinTest.getTabAt(testPosition).select();
//                    mTabSkinTest.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
//                        @Override
//                        public void onTabSelected(TabLayout.Tab tab) {
//                            Log.e("tab.getPosition()", tab.getPosition() + "====");
//                            UserPreferences.setTestPosition(tab.getPosition());
//                        }
//
//                        @Override
//                        public void onTabUnselected(TabLayout.Tab tab) {
//
//                        }
//
//                        @Override
//                        public void onTabReselected(TabLayout.Tab tab) {
//
//                        }
//                    });
//
////                    for (int i = 0; i < mSkinTestViewPager.getCount(); i++) {
////                        TabLayout.Tab tab = mTabSkinTest.newTab();
////                        mTabSkinTest.addTab(tab, i == UserPreferences.getTestPosition());
////                    }
//                    mTvSkinAgain.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            //显示肤质测试主页
//                            loadFirstData();
//                            UserPreferences.setIsShowTest(true);
//                        }
//                    });
//                }
//            }
//        });
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mBind.unbind();
    }

    private void setUserBirthDay() {
        mTvTestSelectBirthday.setText(String.format("%1$04d-%2$02d-%3$02d", Integer.parseInt(userInfo.getBirth_year()), Integer.parseInt(userInfo.getBirth_month()), Integer.parseInt(userInfo.getBirth_day())));
    }

    private interface OnRefreshData {
        void onrefresh();
    }

    public OnRefreshData mOnRefreshData;

    public void setOnRefreshData(OnRefreshData onRefreshData) {
        mOnRefreshData = onRefreshData;
    }

    private void setShowView() {
        UserPreferences.setIsShowTest(false);
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (UserPreferences.getUserID() != 0) {
            getPresenter().share();
        } else {
            startToLogin();
        }

        return super.onOptionsItemSelected(item);
    }

    public void start(Context context, Test test, int type) {
        Intent intent = new Intent(context, TestGuideActivity.class);
        intent.putExtra(TestGuideActivity.EXTRA_TEST, test);
        intent.putExtra(TestGuideActivity.EXTRA_TEST_TYPE, type);
        startActivityForResult(intent, Activity.RESULT_FIRST_USER);

    }

    @Subscribe(threadMode = ThreadMode.MAIN)

    public void onRefresh(TestStart testStart) {
        Log.e("==onRefresh=", testStart+"====");
        if (testStart != null) {
            loadMainData();
        }
    }


}
