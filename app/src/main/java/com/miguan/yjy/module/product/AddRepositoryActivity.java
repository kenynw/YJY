package com.miguan.yjy.module.product;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.TimePickerView;
import com.dsk.chain.bijection.ChainBaseActivity;
import com.dsk.chain.bijection.RequiresPresenter;
import com.miguan.yjy.R;
import com.miguan.yjy.utils.DateUtils;
import com.miguan.yjy.utils.LUtils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Copyright (c) 2017/3/27. LiaoPeiKun Inc. All rights reserved.
 */
@RequiresPresenter(AddRepositoryPresenter.class)
public class AddRepositoryActivity extends ChainBaseActivity<AddRepositoryPresenter> {

    @BindView(R.id.tv_add_repository_brand)
    TextView mTvBrand;

    @BindView(R.id.et_add_repository_name)
    EditText mEtName;

    @BindView(R.id.tv_add_repository_expiration)
    TextView mTvExpiration;

    @BindView(R.id.iv_add_repository_is_open)
    ImageView mIvIsOpen;

    @BindView(R.id.tv_add_repository_seal_date)
    TextView mEtOpenDate;

    @BindView(R.id.tv_add_repository_exp_time)
    TextView mTvExpTime;

    @BindView(R.id.ll_add_repository_is_seal)
    LinearLayout mLlIsSeal;

    private TimePickerView mTimePickerView;

    private AlertDialog mTimeDialog;

    private int mIsSeal = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_activity_add_repository);
        setToolbarTitle(R.string.text_my_product_repository);
        ButterKnife.bind(this);

        initViews();
    }

    private void initViews() {
        mTimePickerView = new TimePickerView.Builder(this, (date, v) -> {
            SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日", Locale.CHINA);
            ((TextView) v).setText(format.format(date));
        }).setType(TimePickerView.Type.YEAR_MONTH_DAY)
                .setSubmitText("完成")
                .setSubmitColor(getResources().getColor(R.color.colorPrimary))
                .setCancelColor(getResources().getColor(R.color.textSecondary))
                .setLabel("", "", "", "", "", "")
                .setDate(Calendar.getInstance())
                .build();

        String[] times = new String[]{"3个月", "6个月", "12个月", "24个月"};
        mTimeDialog = new AlertDialog.Builder(this)
                .setTitle("选择保质期")
                .setItems(times, (dialog, which) -> mTvExpTime.setText(times[which])).create();

        mIvIsOpen.setOnClickListener(v -> {
            mIsSeal = mIsSeal == 1 ? 0 : 1;
            mLlIsSeal.setVisibility(mIsSeal == 1 ? View.VISIBLE : View.GONE);
            mIvIsOpen.setImageResource(mIsSeal == 1 ? R.mipmap.ic_swc_on : R.mipmap.ic_swc_off);

        });
        mTvBrand.setOnClickListener(v -> startActivityForResult(new Intent(this, BrandListActivity.class), 100));
        mEtOpenDate.setOnClickListener(v -> mTimePickerView.show(v));
        mTvExpTime.setOnClickListener(v -> mTimeDialog.show());
    }

    public void setData(String brandName, String overtime) {
        mTvBrand.setText(brandName);
        if (TextUtils.isEmpty(overtime)) {
            mTvExpiration.setOnClickListener(v -> mTimePickerView.show(v));
        } else {
            mTvExpiration.setText(overtime);
        }
        mEtOpenDate.setText(DateUtils.getCurrentFormatDate("yyyy年MM月dd日"));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_save, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        checkInput();
        return super.onOptionsItemSelected(item);
    }

    private void checkInput() {
        if (TextUtils.isEmpty(mEtName.getText())) {
            LUtils.toast("请输入产品");
            return;
        }

        String format = mTvExpiration.getText().toString().contains("日") ? "yyyy年MM月dd日" : "yyyy年MM月";

        getPresenter().submit(mEtName.getText().toString().trim(),
                mIsSeal,
                DateUtils.getTime(mEtOpenDate.getText().toString(), "yyyy年MM月dd日"),
                Integer.valueOf(mTvExpTime.getText().toString().trim().replace("个月", "")),
                DateUtils.getTime(mTvExpiration.getText().toString(), format)
        );
    }

}
