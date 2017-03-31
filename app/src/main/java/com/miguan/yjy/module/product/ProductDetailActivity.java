package com.miguan.yjy.module.product;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RatingBar;
import android.widget.TextView;

import com.dsk.chain.bijection.RequiresPresenter;
import com.dsk.chain.expansion.data.BaseDataActivity;
import com.miguan.yjy.R;
import com.miguan.yjy.model.bean.Component;
import com.miguan.yjy.model.bean.Product;
import com.miguan.yjy.widget.FlowTagLayout;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @作者 cjh
 * @日期 2017/3/22 10:11
 * @描述 产品详情页
 */
@RequiresPresenter(ProductDetailPresenter.class)
public class ProductDetailActivity extends BaseDataActivity<ProductDetailPresenter, Product> implements View.OnClickListener {
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
    TextView tvRemark;
    @BindView(R.id.ll_product_detail_info)
    LinearLayout ll_info;
    @BindView(R.id.ll_product_include_remark_info)
    LinearLayout llRemarkInfo;
    @BindView(R.id.iv_product_down)
    ImageView mIvProductDown;

    /**
     * 分享相关
     */

    TextView tvWxFirend;
    TextView tvWxCircle;
    TextView tvWeibo;
    TextView tvCancel;
    PopupWindow popupWindow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_activity_detail);
        setToolbarTitle("产品详情");
        ButterKnife.bind(this);

    }

    @Override
    public void setData(Product product) {
        tvDate.setOnClickListener(v -> QueryCodePresenter.start(this, product));
        tvName.setText(product.getProduct_name());
        tvRemark.setOnClickListener(v -> ProductRemarkPresenter.start(this, product));
        Component component = new Component();
        tvLockAllComponent.setOnClickListener(v -> ProductComponentListPresenter.start(this, component));
        ll_info.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_share, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        showSharePopWindow();
        return super.onOptionsItemSelected(item);
    }

    private void showSharePopWindow() {
        View sharePop = View.inflate(ProductDetailActivity.this, R.layout.popwindow_share, null);
        tvWxFirend = ButterKnife.findById(sharePop, R.id.tv_share_wx_friend);
        tvWxCircle = ButterKnife.findById(sharePop, R.id.tv_share_wx_circle);
        tvWeibo = ButterKnife.findById(sharePop, R.id.tv_share_weibo);
        tvCancel = ButterKnife.findById(sharePop, R.id.tv_share_cancel);

        popupWindow = new PopupWindow(sharePop, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, true);
        ColorDrawable bg = new ColorDrawable(0x55000000);
        popupWindow.setTouchable(true);
        popupWindow.setBackgroundDrawable(bg);
        popupWindow.showAtLocation(getToolbar(), Gravity.BOTTOM, 0, 0);

        tvWxFirend.setOnClickListener(this);
        tvWxCircle.setOnClickListener(this);
        tvWeibo.setOnClickListener(this);
        tvCancel.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_share_wx_friend:
                popupWindow.dismiss();
                break;
            case R.id.tv_share_wx_circle:
                popupWindow.dismiss();
                break;
            case R.id.tv_share_weibo:
                popupWindow.dismiss();
                break;
            case R.id.tv_share_cancel:
                popupWindow.dismiss();
                break;
            case R.id.ll_product_detail_info:
                if (llRemarkInfo.getVisibility() == View.VISIBLE) {
                    llRemarkInfo.setVisibility(View.GONE);
                    mIvProductDown.setBackgroundResource(R.mipmap.ic_product_detail_up);
                } else {
                    llRemarkInfo.setVisibility(View.VISIBLE);
                    mIvProductDown.setBackgroundResource(R.mipmap.ic_product_detail_record_down);
                }

                break;

        }
    }
}
