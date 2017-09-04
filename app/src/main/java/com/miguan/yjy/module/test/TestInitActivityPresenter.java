package com.miguan.yjy.module.test;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

import com.bigkoo.pickerview.TimePickerView;
import com.dsk.chain.expansion.data.BaseDataActivityPresenter;
import com.miguan.yjy.R;
import com.miguan.yjy.model.AccountModel;
import com.miguan.yjy.model.TestModel;
import com.miguan.yjy.model.UserModel;
import com.miguan.yjy.model.bean.Skin;
import com.miguan.yjy.model.bean.Test;
import com.miguan.yjy.model.bean.User;
import com.miguan.yjy.model.local.UserPreferences;
import com.miguan.yjy.model.services.ServicesResponse;
import com.miguan.yjy.module.account.LoginActivity;
import com.miguan.yjy.utils.DateUtils;

import java.util.Calendar;
import java.util.Date;

import butterknife.ButterKnife;
import rx.Observable;
import rx.functions.Func1;

/**
 * @作者 cjh
 * @日期 2017/8/29 16:43
 * @描述
 */
public class TestInitActivityPresenter extends BaseDataActivityPresenter<TestInitActivity, Test> {


    public void setUnloginView() {
        getView().mLlTestWrinkle.setBackgroundResource(R.drawable.bg_shape_white);
        getView().mTvTestWrinkle.setText(R.string.text_no_test);
        getView().mIvTestWrinkle.setBackgroundResource(R.mipmap.ic_test_wrinkle);

        getView().mLlTestOily.setBackgroundResource(R.drawable.bg_shape_white);
        getView().mTvTestOily.setText(R.string.text_no_test);
        getView().mIvTestOily.setBackgroundResource(R.mipmap.ic_test_oily);

        getView().mLlTestSensitive.setBackgroundResource(R.drawable.bg_shape_white);
        getView().mTvTestSensitive.setText(R.string.text_no_test);
        getView().mIvTestSensitive.setBackgroundResource(R.mipmap.ic_test_sensitive);

        getView().mLlTestPigment.setBackgroundResource(R.drawable.bg_shape_white);
        getView().mTvTestPigment.setText(R.string.text_no_test);
        getView().mIvTestPigment.setBackgroundResource(R.mipmap.ic_test_pigment);

        getView().mTvTestResult.setText("完成度\n" + "0/4");
        getView().mTvTestResult.setClickable(true);
    }

    public void start(Context context, Test test, int type) {
        Intent intent = new Intent(context, TestGuideActivity.class);
        intent.putExtra(TestGuideActivity.EXTRA_TEST, test);
        intent.putExtra(TestGuideActivity.EXTRA_TEST_TYPE, type);
        ((Activity) context).startActivityForResult(intent, Activity.RESULT_FIRST_USER);
    }


    /**
     * 显示测试用户基础信息
     */

    public void showTestUserInfoPop() {
        View view = View.inflate(getView(), R.layout.popwindow_test_info, null);
        getView().mTvTestSelectBirthday = ButterKnife.findById(view, R.id.tv_test_select_birthday);
        getView().mTvTestMan = ButterKnife.findById(view, R.id.tv_test_man);
        getView().mTvTestWoman = ButterKnife.findById(view, R.id.tv_test_woman);
        getView().mTvTestInto = ButterKnife.findById(view, R.id.tv_test_into);
        getView().mTvTestClose = ButterKnife.findById(view, R.id.iv_test_close);

        getView().mTvTestSelectBirthday.setOnClickListener(v -> selectDate());
        getView().mTvTestMan.setOnClickListener(getView());
        getView().mTvTestWoman.setOnClickListener(getView());
        getView().mTvTestInto.setOnClickListener(getView());
        getView().mTvTestClose.setOnClickListener(getView());

        getView().popupWindow = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, true);
        ColorDrawable bg = new ColorDrawable(0x55000000);
        getView().popupWindow.setBackgroundDrawable(bg);
        getView().popupWindow.showAtLocation(getView().mToolbar, Gravity.BOTTOM, 0, 0);

        switch (getView().userInfo.getSex()) {
            case 0://女
                getView().sex = 0;
                getView().mTvTestMan.setBackgroundResource(R.drawable.bg_round_stroke_div);
                getView().mTvTestWoman.setBackgroundResource(R.drawable.bg_shape_fb7);
                getView().mTvTestMan.setTextColor(getView().getResources().getColor(R.color.textSecondary));
                getView().mTvTestWoman.setTextColor(getView().getResources().getColor(R.color.white));
            case 1://男
                getView().sex = 1;
                getView().mTvTestMan.setBackgroundResource(R.drawable.bg_shape_63c);
                getView().mTvTestWoman.setBackgroundResource(R.drawable.bg_round_stroke_div);
                getView().mTvTestMan.setTextColor(getView().getResources().getColor(R.color.white));
                getView().mTvTestWoman.setTextColor(getView().getResources().getColor(R.color.textSecondary));
//                mTvTestMan.performClick();
                break;
            case 2://未填
                getView().sex = 0;
                getView().mTvTestMan.setBackgroundResource(R.drawable.bg_round_stroke_div);
                getView().mTvTestWoman.setBackgroundResource(R.drawable.bg_shape_fb7);
                getView().mTvTestMan.setTextColor(getView().getResources().getColor(R.color.textSecondary));
                getView().mTvTestWoman.setTextColor(getView().getResources().getColor(R.color.white));
                break;
        }
    }

    public void selectDate() {

        //时间选择器
        TimePickerView pvTime = new TimePickerView.Builder(getView(), new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调
                getView().birthDay = String.valueOf(date.getTime() / 1000);
                getView().mTvTestSelectBirthday.setText(DateUtils.DateToStr(date));
            }
        }).setCancelColor(getView().getResources().getColor(R.color.textSecondary)).setSubmitColor(getView().getResources().getColor(R.color.colorPrimary)).isDialog(true).setType(TimePickerView.Type.YEAR_MONTH_DAY).build();
        pvTime.setDate(Calendar.getInstance());//注：根据需求来决定是否使用该方法（一般是精确到秒的情况），此项可以在弹出选择器的时候重新设置当前时间，避免在初始化之后由于时间已经设定，导致选中时间与当前时间不匹配的问题。
        pvTime.show();
    }

    public void startToLogin() {
        Intent intent = new Intent(getView(), LoginActivity.class);
        getView().startActivity(intent);
    }

    public void setUserBirthDay() {
        getView().mTvTestSelectBirthday.setText(String.format("%1$04d-%2$02d-%3$02d", Integer.parseInt(getView().userInfo.getBirth_year()), Integer.parseInt(getView().userInfo.getBirth_month()), Integer.parseInt(getView().userInfo.getBirth_day())));
    }


    public void loadMainData() {
        if (AccountModel.getInstance().isLogin()) {
            UserModel.getInstance().getUserInfo().subscribe(new ServicesResponse<User>() {
                @Override
                public void onNext(User user) {
                    getView().nums.clear();
                    getView().userInfo = user;
                    if (getView().userInfo.getCompact() != 0) {
                        Log.e("userInfo.getCompact()", getView().userInfo.getCompact() + "------");
                        getView().nums.add(0);
                    }
                    if (getView().userInfo.getDry() != 0) {
                        getView().nums.add(1);
                        Log.e("userInfo.getDry()", getView().userInfo.getDry() + "------");
                    }
                    if (getView().userInfo.getTolerance() != 0) {
                        getView().nums.add(2);
                        Log.e("userInfo.getTolerance()", getView().userInfo.getTolerance() + "------");
                    }
                    if (getView().userInfo.getPigment() != 0) {
                        getView().nums.add(3);
                        Log.e("userInfo.getPigment()", getView().userInfo.getPigment() + "------");
                    }
                    Log.e("nums", getView().nums.size() + "--大小 boolean--" + UserPreferences.getIsShowTest());
                    if (getView().nums.size() == 4 && !UserPreferences.getIsShowTest()) {
                        //显示测试结果
//                        setSencondData();
                        UserPreferences.setIsShowTest(false);
                        Intent intent = new Intent(getView(), TestResultActivity.class);
                        getView().startActivity(intent);
                        getView().finish();
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

    public void loadFirstData() {
        getView().mToolbarTitle.setText("肤质测试");
//        getView().initListener();
        if (AccountModel.getInstance().isLogin()) {
            TestModel.getInstantce().userSkin()
                    .flatMap(new Func1<Test, Observable<User>>() {
                        @Override
                        public Observable<User> call(Test test) {
                            getView().nums.clear();
                            if (test.getDesc().size() != 0) {
                                for (int i = 0; i < test.getDesc().size(); i++) {
                                    String skinName = test.getDesc().get(i).getName();
                                    if (test.getDesc().get(i).getType().equals("compact")) {
                                        getView().mLlTestWrinkle.setBackgroundResource(R.drawable.bg_shape_test_a3e);
                                        getView().mTvTestWrinkle.setText(Skin.getCompact(skinName));
                                        getView().mIvTestWrinkle.setBackgroundResource(R.mipmap.ic_test_wrinkle_reslut);
                                        getView().nums.add(0);
                                    }
                                    if (test.getDesc().get(i).getType().equals("dry")) {
                                        getView().mLlTestOily.setBackgroundResource(R.drawable.bg_shape_test_a9d);
                                        getView().nums.add(1);
                                        getView().mTvTestOily.setText(Skin.getDryOil(skinName));
                                        getView().mIvTestOily.setBackgroundResource(R.mipmap.ic_test_oily_reslut);
                                    }
                                    if (test.getDesc().get(i).getType().equals("tolerance")) {
                                        getView().mLlTestSensitive.setBackgroundResource(R.drawable.bg_shape_test_a9d);
                                        getView().nums.add(2);
                                        getView().mTvTestSensitive.setText(Skin.getTolerance(skinName));
                                        getView().mIvTestSensitive.setBackgroundResource(R.mipmap.ic_test_sensitive_reslut);
                                    }
                                    if (test.getDesc().get(i).getType().equals("pigment")) {
                                        getView().mLlTestPigment.setBackgroundResource(R.drawable.bg_shape_test_a3e);
                                        getView().nums.add(3);
                                        getView().mTvTestPigment.setText(Skin.getPigment(skinName));
                                        getView().mIvTestPigment.setBackgroundResource(R.mipmap.ic_test_pigment_reslut);
                                    }
                                }
                            }

                            if (getView().nums.size() == 4) {
                                getView().mTvTestResult.setText("查看结果");
                                getView().mTvTestResult.setClickable(true);
                            } else {
                                getView().mTvTestResult.setText("完成度\n" + getView().nums.size() + "/4");
                                getView().mTvTestResult.setClickable(false);

                            }

                            return UserModel.getInstance().getUserInfo();
                        }
                    })
                    .subscribe(new ServicesResponse<User>() {
                        @Override
                        public void onNext(User user) {
                            getView().userInfo = user;
                            getView().birthDay = getView().userInfo.getBirth_day();
                            getView().sex = getView().userInfo.getSex();
                        }
                    });
        } else {
            setUnloginView();
        }

    }


}
