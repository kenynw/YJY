package com.miguan.yjy.module.main;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
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
import com.miguan.yjy.model.bean.Test;
import com.miguan.yjy.model.local.UserPreferences;
import com.miguan.yjy.module.test.TestGuideActivity;
import com.miguan.yjy.module.test.TestResultActivity;
import com.miguan.yjy.utils.DateUtils;

import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

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


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = View.inflate(getActivity(), R.layout.main_fragment_test, null);
        ButterKnife.bind(this, view);
        initListener();
        return view;
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

        mLlTestWrinkle.setBackgroundResource(R.drawable.bg_shape_test_a3e);
        mLlTestOily.setBackgroundResource(R.drawable.bg_shape_test_a9d);
        mLlTestSensitive.setBackgroundResource(R.drawable.bg_shape_test_589);
        mLlTestPigment.setBackgroundResource(R.drawable.bg_shape_test_4ba);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_test_wrinkle:
                if (UserPreferences.getUserID() <= 0) {
                    showTestUserInfoPop();
                } else {
                    TestGuideActivity.start(getActivity(), TestModel.getInstantce().setTestData().get(0), TestGuideActivity.EXTRA_TEST__FIRST_TYPE);
                }
                break;
            case R.id.ll_test_oily:
                TestResultActivity.star(getActivity());
//                TestGuideActivity.start(getActivity(), TestModel.getInstantce().setTestData().get(1), TestGuideActivity.EXTRA_TEST_SECOND_TYPE);
                break;
            case R.id.ll_test_sensitive:
                TestGuideActivity.start(getActivity(), TestModel.getInstantce().setTestData().get(2), TestGuideActivity.EXTRA_TEST_THIRD_TYPE);
                break;
            case R.id.ll_test_pigment:
                TestGuideActivity.start(getActivity(), TestModel.getInstantce().setTestData().get(3), TestGuideActivity.EXTRA_TEST_FOUR_TYPE);
                break;
            case R.id.tv_test_man:
                mTvTestMan.setBackgroundResource(R.drawable.bg_shape_63c);
                mTvTestWoman.setBackgroundResource(R.drawable.bg_round_stroke_div);
                mTvTestMan.setTextColor(getResources().getColor(R.color.white));
                mTvTestWoman.setTextColor(getResources().getColor(R.color.f9));
                break;
            case R.id.tv_test_woman:
                mTvTestMan.setBackgroundResource(R.drawable.bg_round_stroke_div);
                mTvTestWoman.setBackgroundResource(R.drawable.bg_shape_fb7);
                mTvTestMan.setTextColor(getResources().getColor(R.color.f9));
                mTvTestWoman.setTextColor(getResources().getColor(R.color.white));
                break;
            case R.id.tv_test_into:
                popupWindow.dismiss();
                break;
            case R.id.iv_test_close:
                popupWindow.dismiss();
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
                mTvTestSelectBirthday.setText(DateUtils.DateToStr(date));
            }
        }).setCancelColor(getResources().getColor(R.color.f9)).setSubmitColor(getResources().getColor(R.color.f32d)).isDialog(true).setType(TimePickerView.Type.YEAR_MONTH_DAY).build();
        pvTime.setDate(Calendar.getInstance());//注：根据需求来决定是否使用该方法（一般是精确到秒的情况），此项可以在弹出选择器的时候重新设置当前时间，避免在初始化之后由于时间已经设定，导致选中时间与当前时间不匹配的问题。
        pvTime.show();


    }
}
