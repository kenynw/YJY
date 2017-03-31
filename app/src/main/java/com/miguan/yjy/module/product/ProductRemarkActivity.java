package com.miguan.yjy.module.product;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.dsk.chain.bijection.ChainBaseActivity;
import com.dsk.chain.bijection.RequiresPresenter;
import com.miguan.yjy.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @作者 cjh
 * @日期 2017/3/24 18:17
 * @描述 写点评
 */
@RequiresPresenter(ProductRemarkPresenter.class)
public class ProductRemarkActivity extends ChainBaseActivity<ProductRemarkPresenter> {
    @BindView(R.id.img_proudct)
    ImageView mImgProudct;
    @BindView(R.id.tv_product_name)
    TextView mTvProductName;
    @BindView(R.id.tv_product_money)
    TextView mTvProductMoney;
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
        submit(); //提交代码
        return super.onOptionsItemSelected(item);
    }

    private void submit() {

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
                if (s.length() > 0) {
                    getToolbar().getMenu().getItem(0).setEnabled(true);
//                    getToolbar().getMenu().getItem(0).setTitle(SpanUtils.addColor("提交", getResources().getColor(R.color.f32d)));
                }
                if (s.length() <= 20) {
                    mTvRemarkLackNum.setText("还需要输入" + (20 - s.length()) + "个字");
                }

            }
        });
        mRatbarProduct.setOnRatingBarChangeListener((ratingBar, rating, fromUser) -> {

            final int numStars = ratingBar.getNumStars();
            mTvProductName.setText( getString(R.string.tv_product_detail_user_evaluate) + " " + rating + "/" + numStars);

            // Since this rating bar is updated to reflect any of the other rating
            // bars, we should update it to the current values.
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
            switch ((int)rating) {
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
        });


    }

}
