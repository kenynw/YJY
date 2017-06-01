package com.miguan.yjy.module.product;

import android.content.Intent;
import android.net.Uri;
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
import com.facebook.drawee.view.SimpleDraweeView;
import com.jude.library.imageprovider.ImageProvider;
import com.jude.library.imageprovider.OnImageSelectListener;
import com.miguan.yjy.R;
import com.miguan.yjy.model.bean.Product;
import com.miguan.yjy.model.services.Services;
import com.miguan.yjy.module.common.WebViewActivity;
import com.miguan.yjy.utils.DateUtils;
import com.miguan.yjy.utils.LUtils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.miguan.yjy.module.product.AddRepositoryPresenter.REQUEST_CODE_BRAND;
import static com.miguan.yjy.module.product.AddRepositoryPresenter.REQUEST_CODE_PRODUCT;

/**
 * Copyright (c) 2017/3/27. LiaoPeiKun Inc. All rights reserved.
 */
@RequiresPresenter(AddRepositoryPresenter.class)
public class AddRepositoryActivity extends ChainBaseActivity<AddRepositoryPresenter> implements OnImageSelectListener {

    @BindView(R.id.et_add_repository_brand)
    EditText mEtBrand;

    @BindView(R.id.tv_add_repository_name)
    TextView mTvProduct;

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

    @BindView(R.id.ll_add_repository_image)
    LinearLayout mLlImage;

    @BindView(R.id.tv_add_repository_query)
    TextView mTvQuery;

    @BindView(R.id.dv_add_repository_image)
    SimpleDraweeView mDvImage;

    @BindView(R.id.tv_add_repository_intro)
    TextView mTvIntro;

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
        mLlImage.setOnClickListener(v -> ImageProvider.getInstance(this).getImageFromCameraOrAlbum(this));
        mTvQuery.setOnClickListener(v -> startActivity(new Intent(this, QueryCodeActivity.class)));
        mTvIntro.setOnClickListener(v -> WebViewActivity.start(this, "开封保质期说明", Services.BASE_BETA_URL + "site/quality"));
        mTvProduct.setOnClickListener(v -> startActivityForResult(new Intent(this, RepositoryListActivity.class), REQUEST_CODE_PRODUCT));

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

        mEtOpenDate.setOnClickListener(v -> mTimePickerView.show(v));
        mTvExpTime.setOnClickListener(v -> mTimeDialog.show());
    }

    public void setBrand(String brandName, String overtime) {
        if (TextUtils.isEmpty(brandName)) {
            mEtBrand.setEnabled(true);
        } else {
            mEtBrand.setText(brandName);
            mEtBrand.setEnabled(false);
            mEtBrand.setOnClickListener(v -> startActivityForResult(new Intent(this, BrandListActivity.class), REQUEST_CODE_BRAND));
        }
        if (TextUtils.isEmpty(overtime)) {
            mTvExpiration.setOnClickListener(v -> mTimePickerView.show(v));
            mTvQuery.setVisibility(View.VISIBLE);
        } else {
            mTvExpiration.setText(overtime);
            mTvQuery.setVisibility(View.GONE);
        }
        mEtOpenDate.setText(DateUtils.getCurrentFormatDate("yyyy年MM月dd日"));
    }

    public void setProduct(Product product) {
        getPresenter().setBrandId(product.getBrand_id());
        mEtBrand.setText(product.getBrand_name());
        mTvProduct.setText(product.getProduct_name());
        mDvImage.setImageURI(Uri.parse(product.getProduct_img()));
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
        if (TextUtils.isEmpty(mTvProduct.getText())) {
            LUtils.toast("请输入产品");
            return;
        }

        if (TextUtils.isEmpty(mTvExpiration.getText())) {
            LUtils.toast("请选择过期时间");
            return;
        }

        if (TextUtils.isEmpty(mEtBrand.getText())) {
            LUtils.toast("请输入品牌");
            return;
        }

        String format = mTvExpiration.getText().toString().contains("日") ? "yyyy年MM月dd日" : "yyyy年MM月";

        getPresenter().submit(
                mEtBrand.getText().toString().trim(),
                mTvProduct.getText().toString().trim(),
                mIsSeal,
                DateUtils.getTime(mEtOpenDate.getText().toString(), "yyyy年MM月dd日"),
                Integer.valueOf(mTvExpTime.getText().toString().trim().replace("个月", "")),
                DateUtils.getTime(mTvExpiration.getText().toString(), format)
        );
    }

    public void setImage(Uri uri) {
        mDvImage.setImageURI(uri);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        ImageProvider.getInstance(this).onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onImageSelect() {

    }

    @Override
    public void onImageLoaded(Uri uri) {
        getPresenter().uploadImage(uri);
    }

    @Override
    public void onError() {

    }

}
