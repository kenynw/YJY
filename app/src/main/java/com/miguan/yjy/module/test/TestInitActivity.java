package com.miguan.yjy.module.test;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bigkoo.pickerview.TimePickerView;
import com.dsk.chain.bijection.RequiresPresenter;
import com.dsk.chain.expansion.data.BaseDataActivity;
import com.miguan.yjy.R;
import com.miguan.yjy.model.AccountModel;
import com.miguan.yjy.model.bean.Skin;
import com.miguan.yjy.model.bean.Test;
import com.miguan.yjy.model.bean.User;
import com.miguan.yjy.module.account.LoginActivity;
import com.miguan.yjy.utils.DateUtils;

import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;

/**
 *
 * 肤质测试V
 *
 * */
@RequiresPresenter(TestInitActivityPresenter.class)
public class TestInitActivity extends BaseDataActivity<TestInitActivityPresenter, Test> implements View.OnClickListener {

    @BindViews({R.id.ll_test_wrinkle, R.id.ll_test_oily, R.id.ll_test_sensitive, R.id.ll_test_pigment})
    LinearLayout[] mLlSkins;

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

    public static TestInitActivity testInitActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_fragment_test);
        setToolbarTitle(R.string.text_test);
        ButterKnife.bind(this);

        testInitActivity = this;
    }

    public void setUnloginView() {
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

    @Override
    public void setData(Test test) {
        ButterKnife.apply(mLlSkins, new ButterKnife.Action<LinearLayout>() {
            @Override
            public void apply(@NonNull LinearLayout view, int index) {
                view.setOnClickListener(TestInitActivity.this);
            }
        });

        if (test.getDesc() != null && test.getDesc().size() > 0) {
            for (Skin skin : test.getDesc()) {
                String skinName = skin.getName();
                switch (skin.getType()) {
                    case "compact":
                        mLlTestWrinkle.setBackgroundResource(R.drawable.bg_shape_test_a3e);
                        mTvTestWrinkle.setText(Skin.getCompact(skinName));
                        mIvTestWrinkle.setBackgroundResource(R.mipmap.ic_test_wrinkle_reslut);
                        break;
                    case "dry":
                        mLlTestOily.setBackgroundResource(R.drawable.bg_shape_test_a9d);
                        mTvTestOily.setText(Skin.getDryOil(skinName));
                        mIvTestOily.setBackgroundResource(R.mipmap.ic_test_oily_reslut);
                        break;
                    case "tolerance":
                        mLlTestSensitive.setBackgroundResource(R.drawable.bg_shape_test_a9d);
                        mTvTestSensitive.setText(Skin.getTolerance(skinName));
                        mIvTestSensitive.setBackgroundResource(R.mipmap.ic_test_sensitive_reslut);
                        break;
                    case "pigment":
                        mLlTestPigment.setBackgroundResource(R.drawable.bg_shape_test_a3e);
                        mTvTestPigment.setText(Skin.getPigment(skinName));
                        mIvTestPigment.setBackgroundResource(R.mipmap.ic_test_pigment_reslut);
                        break;
                }
            }
        }

        if (test.getDesc().size() == 4) {
            mTvTestResult.setText("查看结果");
            mTvTestResult.setClickable(true);
            mTvTestResult.setOnClickListener(this);
        } else {
            mTvTestResult.setText("完成度\n" + test.getDesc().size() + "/4");
            mTvTestResult.setClickable(false);
        }

    }

    public void showProfileDialog(int tag) {
        View view = View.inflate(this, R.layout.popwindow_test_info, null);
        TextView tvSelectBirthday = ButterKnife.findById(view, R.id.tv_test_select_birthday);
        RadioButton rbMale = ButterKnife.findById(view, R.id.rb_test_man);
        RadioButton rbFemale = ButterKnife.findById(view, R.id.rb_test_woman);
        TextView tvInto = ButterKnife.findById(view, R.id.tv_test_into);
        TextView tvClose = ButterKnife.findById(view, R.id.iv_test_close);

        AlertDialog dialog = new AlertDialog.Builder(this).create();
        dialog.setContentView(view);
        dialog.show();

        User user = getPresenter().getUser();
        if (!TextUtils.isEmpty(user.getBirth_day())){
            tvSelectBirthday.setText(String.format(getString(R.string.text_date),
                    Integer.parseInt(user.getBirth_year()),
                    Integer.parseInt(user.getBirth_month()),
                    Integer.parseInt(user.getBirth_day())
            ));
        }
        rbFemale.setChecked(user.getSex() == 0);
        tvSelectBirthday.setOnClickListener(v -> {
            TimePickerView pvTime = new TimePickerView.Builder(this, new TimePickerView.OnTimeSelectListener() {
                @Override
                public void onTimeSelect(Date date, View v) {
                    tvSelectBirthday.setText(DateUtils.DateToStr(date));
                }
            }).setCancelColor(getResources().getColor(R.color.textSecondary)).setSubmitColor(getResources().getColor(R.color.colorPrimary)).isDialog(true).setType(TimePickerView.Type.YEAR_MONTH_DAY).build();
            pvTime.setDate(Calendar.getInstance());//注：根据需求来决定是否使用该方法（一般是精确到秒的情况），此项可以在弹出选择器的时候重新设置当前时间，避免在初始化之后由于时间已经设定，导致选中时间与当前时间不匹配的问题。
            pvTime.show();
        });
        tvInto.setOnClickListener(v -> getPresenter().modifyProfile(tag,
                DateUtils.getTime(tvSelectBirthday.getText().toString()),
                rbFemale.isChecked() ? 2 : 1));
        tvClose.setOnClickListener(v -> dialog.dismiss());
    }

    @Override
    public void onClick(View v) {
        if (!AccountModel.getInstance().isLogin()) {
            startActivity(new Intent(this, LoginActivity.class));
            return;
        }

        User user = getPresenter().getUser();
        if (user != null && (TextUtils.isEmpty(user.getBirth_day()) || user.getSex() == 2)) {
            showProfileDialog(Integer.valueOf(v.getTag().toString()));
        } else if (v.getId() == R.id.tv_test_result){
            Intent intent  = new Intent(this, TestResultActivity.class);
            startActivity(intent);
            finish();
        } else {
            getPresenter().startTest(Integer.valueOf(v.getTag().toString()));
        }
    }

}
