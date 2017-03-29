package com.miguan.yjy.module.product;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.Menu;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bigkoo.pickerview.TimePickerView;
import com.dsk.chain.bijection.ChainBaseActivity;
import com.dsk.chain.bijection.RequiresPresenter;
import com.miguan.yjy.R;
import com.miguan.yjy.model.bean.Product;
import com.miguan.yjy.utils.DateUtils;

import java.text.SimpleDateFormat;
import java.util.Calendar;

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

    @BindView(R.id.tv_add_repository_open_date)
    TextView mEtOpenDate;

    @BindView(R.id.tv_add_repository_expiration_time)
    TextView mTvExpirationTime;

    private TimePickerView mTimePickerView;

    private AlertDialog mTimeDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_activity_add_repository);
        setToolbarTitle(R.string.text_my_product_repository);
        ButterKnife.bind(this);

        initViews();
    }

    public void setData(Product product) {
        mTvBrand.setText(product.getBrand());
        mEtOpenDate.setText(DateUtils.getCurrentFormatDate());
    }

    private void initViews() {
        mTimePickerView = new TimePickerView.Builder(this, (date, v) -> {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
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
                .setItems(times, (dialog, which) -> {
                    mTvExpirationTime.setText(times[which]);
                }).create();

        mIvIsOpen.setOnClickListener(v -> mIvIsOpen.setImageResource(R.mipmap.ic_swc_off));
        mTvBrand.setOnClickListener(v -> startActivity(new Intent(this, BrandListActivity.class)));
        mEtOpenDate.setOnClickListener(v -> mTimePickerView.show(v));
        mTvExpirationTime.setOnClickListener(v -> mTimeDialog.show());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_save, menu);
        return super.onCreateOptionsMenu(menu);
    }

}
