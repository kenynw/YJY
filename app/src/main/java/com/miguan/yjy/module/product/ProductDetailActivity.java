package com.miguan.yjy.module.product;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.dsk.chain.expansion.data.BaseDataActivity;
import com.miguan.yjy.R;
import com.miguan.yjy.model.bean.Product;
import com.miguan.yjy.widget.FlowTagLayout;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @作者 cjh
 * @日期 2017/3/22 10:11
 * @描述 产品详情页
 */

public class ProductDetailActivity extends BaseDataActivity<ProductDetailPresenter, Product> {
    @BindView(R.id.img_product_detail)
    ImageView img;
    @BindView(R.id.tv_product_name_detail)
    TextView tvName;
    @BindView(R.id.tv_product_money_detail)
    TextView tvMoney;
    @BindView(R.id.tv_product_date_detail)
    TextView tvDate;
    @BindView(R.id.tv_product_suit_num)
    TextView tvSuitNum;
    @BindView(R.id.tv_product_unsuit_num)
    TextView tvUnsuitNum;
    @BindView(R.id.tv_product_detail_record_inf0)
    TextView tvRecordInf0;
    @BindView(R.id.tv_product_detail_record_document_number)
    TextView tvRecordDocumentNumber;
    @BindView(R.id.tv_product_detail_country)
    TextView tvCountry;
    @BindView(R.id.tv_product_detail_company)
    TextView tvCompany;
    @BindView(R.id.ratbar_product_detail_grade)
    RatingBar ratbarGrade;
    @BindView(R.id.flowtag_product_detail_grade)
    FlowTagLayout flowtagGrade;
    @BindView(R.id.tv_product_detail_effect_info)
    TextView tvEffectInfo;
    @BindView(R.id.flowtag_product_detail_effect_info)
    FlowTagLayout flowtagEffectInfo;
    @BindView(R.id.tv_product_detail_lock_all_component)
    TextView tvLockAllComponent;
    @BindView(R.id.tv_product_user_evalute_num)
    TextView tvUserEvaluteNum;
    @BindView(R.id.tv_product_high_evaluate)
    TextView tvHighEvaluate;
    @BindView(R.id.tv_product_medium_evaluate)
    TextView tvMediumEvaluate;
    @BindView(R.id.tv_product_bad_evaluate)
    TextView tvBadEvaluate;
    @BindView(R.id.tv_product_detail_rough)
    TextView tvRough;
    @BindView(R.id.tv_product_detail_homework)
    TextView tvHomework;
    @BindView(R.id.tv_product_detail_remark)
    TextView tcRemark;
    @BindView(R.id.ll_product_detail_info)
    LinearLayout ll_info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_activity_detail);
        ButterKnife.bind(this);
    }


}
