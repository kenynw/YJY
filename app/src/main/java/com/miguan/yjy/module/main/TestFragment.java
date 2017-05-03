package com.miguan.yjy.module.main;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
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
import com.miguan.yjy.R;
import com.miguan.yjy.model.TestModel;
import com.miguan.yjy.model.UserModel;
import com.miguan.yjy.model.bean.Skin;
import com.miguan.yjy.model.bean.Test;
import com.miguan.yjy.model.bean.User;
import com.miguan.yjy.model.local.UserPreferences;
import com.miguan.yjy.model.services.ServicesResponse;
import com.miguan.yjy.module.account.LoginActivity;
import com.miguan.yjy.module.test.TestGuideActivity;
import com.miguan.yjy.module.user.ProfilePresenter;
import com.miguan.yjy.utils.DateUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;
import rx.functions.Func1;

/**
 * @作者 cjh
 * @日期 2017/3/30 16:00
 * @描述 肤质测试
 */
@RequiresPresenter(TestFragmentPrensenter.class)
public class TestFragment extends BaseDataFragment<TestFragmentPrensenter, Test> implements View.OnClickListener {

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

    private TextView mTvTestSelectBirthday;
    private TextView mTvTestMan;
    private TextView mTvTestWoman;
    private TextView mTvTestInto;
    private ImageView mTvTestClose;
    private PopupWindow popupWindow;
    String birthDay;
    private int sex = 0;
    private int tag = 0;
    private User userInfo;
    private List<Integer> nums = new ArrayList<>();


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = View.inflate(getActivity(), R.layout.main_fragment_test, null);
        ButterKnife.bind(this, view);
        initListener();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        loadData();
    }

    public void loadData() {
        if (UserPreferences.getUserID() > 0) {
            UserModel.getInstance().getUserInfo().subscribe(new ServicesResponse<User>() {
                @Override
                public void onNext(User user) {
                    nums.clear();
                    userInfo = user;
                    if (!TextUtils.isEmpty(userInfo.getCompact())) {
                        mLlTestWrinkle.setBackgroundResource(R.drawable.bg_shape_test_a3e);
                        mTvTestWrinkle.setText(Skin.getCompact(Integer.parseInt(userInfo.getCompact())));
                        nums.add(0);
                    }
                    if (!TextUtils.isEmpty(userInfo.getDry())) {
                        mLlTestOily.setBackgroundResource(R.drawable.bg_shape_test_a9d);
                        nums.add(1);
                        mTvTestOily.setText(Skin.getDryOil(Integer.parseInt(userInfo.getDry())));
                    }
                    if (!TextUtils.isEmpty(userInfo.getTolerance())) {
                        mLlTestSensitive.setBackgroundResource(R.drawable.bg_shape_test_a9d);
                        nums.add(2);
                        mTvTestSensitive.setText(Skin.getTolerance(Integer.parseInt(userInfo.getTolerance())));
                    }
                    if (!TextUtils.isEmpty(userInfo.getPigment())) {
                        mLlTestPigment.setBackgroundResource(R.drawable.bg_shape_test_a3e);
                        nums.add(3);
                        mTvTestPigment.setText(Skin.getPigment(Integer.parseInt(userInfo.getPigment())));
                    }
                    if (nums.size() == 4) {
                        mTvTestResult.setText("查看结果");
                    } else {
                        mTvTestResult.setText("完成度\n" + nums.size() + "/4");
                    }

                }
            });
        } else {
            Log.e("userId", UserPreferences.getUserID() + "--=0--");
            mLlTestWrinkle.setBackgroundResource(R.drawable.bg_shape_white);
            mTvTestWrinkle.setText(R.string.text_no_test);
            mLlTestOily.setBackgroundResource(R.drawable.bg_shape_white);
            mTvTestOily.setText(R.string.text_no_test);
            mLlTestSensitive.setBackgroundResource(R.drawable.bg_shape_white);
            mTvTestSensitive.setText(R.string.text_no_test);
            mLlTestPigment.setBackgroundResource(R.drawable.bg_shape_white);
            mTvTestPigment.setText(R.string.text_no_test);
            mTvTestResult.setText("完成度\n"+ "0/4");

        }

    }

    @Override
    public void setMenuVisibility(boolean menuVisible) {
        super.setMenuVisibility(menuVisible);
        if (getView() != null) {
            getView().setVisibility(menuVisible ? View.VISIBLE : View.INVISIBLE);
        }
    }

    private void initListener() {
        mLlTestWrinkle.setOnClickListener(this);
        mLlTestOily.setOnClickListener(this);
        mLlTestSensitive.setOnClickListener(this);
        mLlTestPigment.setOnClickListener(this);
        mTvTestResult.setOnClickListener(this);

//        mLlTestWrinkle.setBackgroundResource(R.drawable.bg_shape_test_a3e);
//        mLlTestOily.setBackgroundResource(R.drawable.bg_shape_test_a9d);
//        mLlTestSensitive.setBackgroundResource(R.drawable.bg_shape_test_a9d);
//        mLlTestPigment.setBackgroundResource(R.drawable.bg_shape_test_a3e);

    }

    @Override
    public void onClick(View v) {
        Intent intent = null;
        switch (v.getId()) {
            case R.id.ll_test_wrinkle:
                if (UserPreferences.getUserID() > 0) {
                    tag = 0;
                    if (TextUtils.isEmpty(userInfo.getBirth_day())) {
                        showTestUserInfoPop();
                    } else {
                        TestGuideActivity.start(getActivity(), TestModel.getInstantce().setTestData().get(0), TestGuideActivity.EXTRA_TEST_FOUR_TYPE);
                    }
                } else {
                    startToLogin();
                }
                break;
            case R.id.ll_test_oily:
                if (UserPreferences.getUserID() > 0) {
                    tag = 1;
                    if (TextUtils.isEmpty(userInfo.getBirth_day())) {
                        showTestUserInfoPop();
                    } else {
                        TestGuideActivity.start(getActivity(), TestModel.getInstantce().setTestData().get(1), TestGuideActivity.EXTRA_TEST_FIRST_TYPE);
                    }
                } else {
                    startToLogin();
                }
                break;
            case R.id.ll_test_sensitive:
                if (UserPreferences.getUserID() > 0) {
                    tag = 2;
                    if (TextUtils.isEmpty(userInfo.getBirth_day())) {
                        showTestUserInfoPop();
                    } else {
                        TestGuideActivity.start(getActivity(), TestModel.getInstantce().setTestData().get(2), TestGuideActivity.EXTRA_TEST_SECOND_TYPE);
                    }
                } else {
                    startToLogin();
                }
                break;
            case R.id.ll_test_pigment:
                if (UserPreferences.getUserID() > 0) {
                    tag = 3;
                    if (TextUtils.isEmpty(userInfo.getBirth_day())) {
                        showTestUserInfoPop();
                    } else {
                        TestGuideActivity.start(getActivity(), TestModel.getInstantce().setTestData().get(3), TestGuideActivity.EXTRA_TEST_THIRD_TYPE);
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
                mTvTestWoman.setTextColor(getResources().getColor(R.color.f9));
                break;
            case R.id.tv_test_woman:
                sex = 0;
                mTvTestMan.setBackgroundResource(R.drawable.bg_round_stroke_div);
                mTvTestWoman.setBackgroundResource(R.drawable.bg_shape_fb7);
                mTvTestMan.setTextColor(getResources().getColor(R.color.f9));
                mTvTestWoman.setTextColor(getResources().getColor(R.color.white));
                break;
            case R.id.tv_test_into:
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
                                        TestGuideActivity.start(getActivity(), TestModel.getInstantce().setTestData().get(0), TestGuideActivity.EXTRA_TEST_FIRST_TYPE);
                                        break;
                                    case 1:
                                        TestGuideActivity.start(getActivity(), TestModel.getInstantce().setTestData().get(1), TestGuideActivity.EXTRA_TEST_SECOND_TYPE);
                                        break;
                                    case 2:
                                        TestGuideActivity.start(getActivity(), TestModel.getInstantce().setTestData().get(2), TestGuideActivity.EXTRA_TEST_THIRD_TYPE);
                                        break;
                                    case 3:
                                        TestGuideActivity.start(getActivity(), TestModel.getInstantce().setTestData().get(3), TestGuideActivity.EXTRA_TEST_FOUR_TYPE);
                                        break;
                                }

                            }
                        });
                popupWindow.dismiss();
                break;
            case R.id.iv_test_close:
                popupWindow.dismiss();
                break;
            case R.id.tv_test_result:
                if (UserPreferences.getUserID() > 0) {
                    if (mMyOnTabClick != null) {
                        mMyOnTabClick.tabClickStart();
                    }
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


    }

    private void selectDate() {

        //时间选择器
        TimePickerView pvTime = new TimePickerView.Builder(getActivity(), new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调
                birthDay = String.valueOf(date.getTime() / 1000);
                mTvTestSelectBirthday.setText(DateUtils.DateToStr(date));
            }
        }).setCancelColor(getResources().getColor(R.color.f9)).setSubmitColor(getResources().getColor(R.color.f32d)).isDialog(true).setType(TimePickerView.Type.YEAR_MONTH_DAY).build();
        pvTime.setDate(Calendar.getInstance());//注：根据需求来决定是否使用该方法（一般是精确到秒的情况），此项可以在弹出选择器的时候重新设置当前时间，避免在初始化之后由于时间已经设定，导致选中时间与当前时间不匹配的问题。
        pvTime.show();
    }

    private void setUserInfo() {

    }

    public void startToLogin() {
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        startActivity(intent);
    }

    public interface MyOnTabClick {
        void tabClickStart();
    }

    public MyOnTabClick mMyOnTabClick;

    public void setMyOnTabClick(MyOnTabClick myOnTabClick) {
        mMyOnTabClick = myOnTabClick;
    }

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

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void setShowView(String showViewType) {
        if (UserPreferences.getUserID() > 0) {
            if (mMyOnTabClick != null) {
               mMyOnTabClick.tabClickStart();
            }
        }
    }


}
