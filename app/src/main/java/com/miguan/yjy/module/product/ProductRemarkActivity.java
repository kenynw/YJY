package com.miguan.yjy.module.product;

import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;

import com.dsk.chain.bijection.ChainBaseActivity;
import com.dsk.chain.bijection.RequiresPresenter;
import com.facebook.drawee.view.SimpleDraweeView;
import com.miguan.yjy.R;
import com.miguan.yjy.model.bean.Product;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @作者 cjh
 * @日期 2017/3/24 18:17
 * @描述 写点评
 */
@RequiresPresenter(ProductRemarkPresenter.class)
public class ProductRemarkActivity extends ChainBaseActivity<ProductRemarkPresenter> {

    @BindView(R.id.dv_product_detail)
    SimpleDraweeView mDvThumb;

    @BindView(R.id.tv_product_detail_name)
    TextView mTvName;

    @BindView(R.id.tv_product_detail_spec)
    TextView mTvSpec;

    @BindView(R.id.tv_product_date_detail)
    TextView mTvDate;

    @BindView(R.id.ratbar_product)
    RatingBar mRatbarProduct;

    @BindView(R.id.tv_product_remark_lack_num)
    TextView mTvRemarkLackNum;

    @BindView(R.id.tv_product_remark_evaluate)
    TextView mTvRemarkEvaluate;

    @BindView(R.id.et_product_remark)
    EditText mEtRemark;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_activity_remark);
        ButterKnife.bind(this);
        setToolbarTitle("写点评");

        initListener();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_submit, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        getPresenter().submit((int) mRatbarProduct.getRating(), mEtRemark.getText().toString().trim());
        return super.onOptionsItemSelected(item);
    }

    public void setProduct(Product product) {
        mDvThumb.setImageURI(Uri.parse(product.getProduct_img()));
        mTvName.setText(product.getProduct_name());
        mTvSpec.setText(product.getPrice().equals("0") ? "暂无报价" : String.format(getString(R.string.text_product_spec), product.getPrice(), product.getForm()));
    }

    private void initListener() {
        mEtRemark.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() < 20) {
                    mTvRemarkLackNum.setVisibility(View.VISIBLE);
                    mTvRemarkLackNum.setText("还需要输入" + (20 - s.length()) + "个字");
                    getToolbar().getMenu().getItem(0).setEnabled(false);
                } else {
                    mTvRemarkLackNum.setVisibility(View.GONE);
                    getToolbar().getMenu().getItem(0).setEnabled(mRatbarProduct.getRating() > 0);
                }
            }
        });

        mRatbarProduct.setOnRatingBarChangeListener((ratingBar, rating, fromUser) -> {
            final int numStars = ratingBar.getNumStars();
            if (mRatbarProduct.getNumStars() != numStars) {
                mRatbarProduct.setNumStars(numStars);
            }
            if (mRatbarProduct.getRating() != rating) {
                mRatbarProduct.setRating(rating);
            }
            final float ratingBarStepSize = ratingBar.getStepSize();
            if (mRatbarProduct.getStepSize() != ratingBarStepSize) {
                mRatbarProduct.setStepSize(ratingBarStepSize);
            }
            switch ((int) rating) {
                case 1:
                case 2:
                    mTvRemarkEvaluate.setText("差评");
                    break;
                case 3:
                    mTvRemarkEvaluate.setText("中评");
                    break;
                case 4:
                case 5:
                    mTvRemarkEvaluate.setText("好评");
                    break;
            }
            getToolbar().getMenu().getItem(0).setEnabled(rating > 0 && mEtRemark.getText().length() > 20);
        });

    }

}
