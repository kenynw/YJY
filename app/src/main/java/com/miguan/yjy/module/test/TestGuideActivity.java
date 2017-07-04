package com.miguan.yjy.module.test;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.dsk.chain.bijection.RequiresPresenter;
import com.dsk.chain.expansion.data.BaseDataActivity;
import com.miguan.yjy.R;
import com.miguan.yjy.model.TestModel;
import com.miguan.yjy.model.bean.Test;
import com.miguan.yjy.model.bean.TestStart;
import com.miguan.yjy.model.constant.Constants;
import com.miguan.yjy.model.local.UserPreferences;
import com.miguan.yjy.model.services.ServicesResponse;
import com.miguan.yjy.module.common.WebViewActivity;
import com.miguan.yjy.utils.LUtils;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @作者 cjh
 * @日期 2017/3/31 15:38
 * @描述 测试引导页
 */
@RequiresPresenter(TestGuidePresenter.class)

public class TestGuideActivity extends BaseDataActivity<TestGuidePresenter, Test> implements View.OnClickListener {


    public static final String EXTRA_TEST = "test";
    public static final String EXTRA_TEST_TYPE = "type";
    public static final String EXTRA_TEST_TITLE = "title";
    public static final int EXTRA_TEST_FIRST_TYPE = 1;
    public static final int EXTRA_TEST_SECOND_TYPE = 2;
    public static final int EXTRA_TEST_THIRD_TYPE = 3;
    public static final int EXTRA_TEST_FOUR_TYPE = 4;

    @BindView(R.id.tv_test_my_skin)
    TextView mTvTestMySkin;
    @BindView(R.id.tv_test_into)
    TextView mTvTestInto;
    @BindView(R.id.iv_test_img)
    ImageView mIvTestImg;
    @BindView(R.id.tv_test_describe)
    TextView mTvTestDescribe;
    @BindView(R.id.tv_test_describe_sencond)
    TextView mTvTestDescribeSencond;
    View view;
    PopupWindow popupWindow;

    TextView mTvHeightDry;
    TextView mTvLowDry;
    TextView mTvLowOily;
    TextView mTvHeightOily;
    TextView mtvTestWhich;
    LinearLayout mLlTestAll;
    int type;

    public static TestGuideActivity testGuideActivity;

    public static void start(Context context, Test test, int type) {
        Intent intent = new Intent(context, TestGuideActivity.class);
        intent.putExtra(EXTRA_TEST, test);
        intent.putExtra(EXTRA_TEST_TYPE, type);
        ((Activity) context).startActivityForResult(intent, 2);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_activity_guide);
        ButterKnife.bind(this);
        Test test = getIntent().getParcelableExtra(TestGuideActivity.EXTRA_TEST);
        type = getIntent().getIntExtra(TestGuideActivity.EXTRA_TEST_TYPE, -1);
        setToolbarTitle(test.getTitle());
        initData(test);
        mTvTestMySkin.setOnClickListener(v -> showSkinPopWindow(type));
        view = View.inflate(TestGuideActivity.this, R.layout.test_popwindow_guide, null);
        initView();
//        TestResultActivity.star(TestGuideActivity.this)//测试结果页
        mTvTestInto.setOnClickListener(v -> WebViewActivity.start(TestGuideActivity.this, getString(test.getTitle()), Constants.testLink + type + "&user_id=" + UserPreferences.getUserID() + "&from=android"));
    }


    private void initData(Test test) {
        mIvTestImg.setBackgroundResource(test.getImg());
        mTvTestDescribe.setText(test.getDescribe());
    }

    private void initView() {
        mtvTestWhich = ButterKnife.findById(view, R.id.tv_test_which);
        mTvHeightDry = ButterKnife.findById(view, R.id.tv_test_height_dry);
        mTvLowDry = ButterKnife.findById(view, R.id.tv_test_low_dry);
        mTvLowOily = ButterKnife.findById(view, R.id.tv_test_low_oily);
        mTvHeightOily = ButterKnife.findById(view, R.id.tv_test_height_oily);
        mLlTestAll = ButterKnife.findById(view, R.id.ll_test_all);
        popupWindow = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, true);
        ColorDrawable bg = new ColorDrawable(0x55000000);
        popupWindow.setTouchable(true);
        popupWindow.setTouchable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setBackgroundDrawable(bg);
        mTvHeightDry.setOnClickListener(this);
        mTvLowDry.setOnClickListener(this);
        mTvLowOily.setOnClickListener(this);
        mTvHeightOily.setOnClickListener(this);
        view.setOnClickListener(v -> popupWindow.dismiss());
        mLlTestAll.setOnClickListener(null);
    }

    private void showSkinPopWindow(int type) {
        switch (type) {
            case EXTRA_TEST_FIRST_TYPE:
                mtvTestWhich.setText("干性/油性皮肤中,您属于哪一种");
                mTvHeightDry.setText(R.string.tv_test_height_dry);
                mTvLowDry.setText(R.string.tv_test_low_dry);
                mTvLowOily.setText(R.string.tv_test_low_oily);
                mTvHeightOily.setText(R.string.text_test_height_oily);
                break;
            case EXTRA_TEST_SECOND_TYPE:
                mtvTestWhich.setText("敏感/耐受皮肤中,您属于哪一种");
                mTvHeightDry.setText("重度耐受性皮肤");
                mTvLowDry.setText("轻度耐受性皮肤");
                mTvLowOily.setText("轻度敏感性皮肤");
                mTvHeightOily.setText("重度敏感性皮肤");

                break;
            case EXTRA_TEST_THIRD_TYPE:
                mtvTestWhich.setText("色素/非色素皮肤中,您属于哪一种");
                mTvHeightDry.setText("色素性皮肤");
                mTvLowDry.setText("非色素性皮肤");
                mTvLowOily.setVisibility(View.GONE);
                mTvHeightOily.setVisibility(View.GONE);
                break;
            case EXTRA_TEST_FOUR_TYPE://皱纹/紧致
                mtvTestWhich.setText("皱纹/紧致皮肤中,您属于哪一种");
                mTvHeightDry.setText("皱纹性皮肤");
                mTvLowDry.setText("紧致性皮肤");
                mTvLowOily.setVisibility(View.GONE);
                mTvHeightOily.setVisibility(View.GONE);
                break;
        }
        popupWindow.showAtLocation(getToolbar(), Gravity.BOTTOM, 0, 0);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_test_height_dry:
//                type(string) － dry,tolerance,pigment,compact四种类型
                switch (type) {
                    case EXTRA_TEST_FIRST_TYPE:
                        submitSkin("dry", 11);
                        break;
                    case EXTRA_TEST_SECOND_TYPE:
                        submitSkin("tolerance", 17);
                        break;
                    case EXTRA_TEST_THIRD_TYPE:
                        submitSkin("pigment", 31);
                        break;
                    case EXTRA_TEST_FOUR_TYPE://皱纹/紧致
                        submitSkin("compact", 41);
                        mtvTestWhich.setText("皱纹/紧致皮肤中,您属于哪一种");
                        break;
                }
                popupWindow.dismiss();
                break;
            case R.id.tv_test_low_dry:
                switch (type) {
                    case EXTRA_TEST_FIRST_TYPE:
                        submitSkin("dry", 17);
                        break;
                    case EXTRA_TEST_SECOND_TYPE:
                        submitSkin("tolerance", 25);
                        break;
                    case EXTRA_TEST_THIRD_TYPE:
                        submitSkin("pigment", 10);
                        break;
                    case EXTRA_TEST_FOUR_TYPE://皱纹/紧致
                        submitSkin("compact", 20);
                        break;
                }
                popupWindow.dismiss();
                break;
            case R.id.tv_test_low_oily:
                switch (type) {
                    case EXTRA_TEST_FIRST_TYPE:
                        submitSkin("dry", 27);
                        break;
                    case EXTRA_TEST_SECOND_TYPE:
                        submitSkin("tolerance", 30);
                        break;
                }
                popupWindow.dismiss();
                break;
            case R.id.tv_test_height_oily:
                switch (type) {
                    case EXTRA_TEST_FIRST_TYPE:
                        submitSkin("dry", 34);
                        break;
                    case EXTRA_TEST_SECOND_TYPE:
                        submitSkin("tolerance", 34);
                        break;
                }
                popupWindow.dismiss();
                break;

        }
    }


    private void submitSkin(String key, int value) {
        TestModel.getInstantce().saveSkin(key, value).unsafeSubscribe(new ServicesResponse<String>() {
            @Override
            public void onNext(String s) {
                LUtils.toast("提交成功");
                finish();
                EventBus.getDefault().post(new TestStart());
            }
        });
    }

}
