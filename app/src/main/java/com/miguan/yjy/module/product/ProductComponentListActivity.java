package com.miguan.yjy.module.product;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.dsk.chain.bijection.RequiresPresenter;
import com.dsk.chain.expansion.data.BaseDataActivity;
import com.miguan.yjy.R;
import com.miguan.yjy.adapter.ProductReadAdapter;
import com.miguan.yjy.model.bean.Component;
import com.miguan.yjy.module.common.WebViewActivity;
import com.miguan.yjy.utils.ScreenShot;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @作者 cjh
 * @日期 2017/3/29 19:46
 * @描述 成分表
 */
@RequiresPresenter(ProductComponentListPresenter.class)
public class ProductComponentListActivity extends BaseDataActivity<ProductComponentListPresenter, Component> {
    @BindView(R.id.tv_product_conponent_num)
    TextView mTvProductConponentNum;
    @BindView(R.id.recy_product_component_create)
    RecyclerView mRecyProductComponentCreate;
    @BindView(R.id.ll_product_create_img)
    LinearLayout mllProductCreateImg;
    @BindView(R.id.ll_water_middle)
    LinearLayout mLlWaterMiddle;
    @BindView(R.id.tv_test_img)
    ImageView tv_test_img;
    public static final String WEBVIEW_READ = "http://m.yjyapp.com/site/skin-unscramble";
    @BindView(R.id.rl_water_top)
    RelativeLayout mRlWaterTop;
    @BindView(R.id.ll_water_bottom)
    LinearLayout mLlWaterBottom;
    @BindView(R.id.iv_product_detail)
    ImageView mIvThumb;
    @BindView(R.id.tv_product_detail_name)
    TextView mTvName;
    @BindView(R.id.tv_product_detail_spec)
    TextView mTvSpec;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_activity_componentlist);
        ButterKnife.bind(this);
        setToolbarTitle("成分表");
        initData();
        mllProductCreateImg.setOnClickListener(v -> waterMark());

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_read_list, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        WebViewActivity.satr(ProductComponentListActivity.this, "解读表", WEBVIEW_READ);
        return super.onOptionsItemSelected(item);
    }

    private void initData() {
        mRecyProductComponentCreate.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        ProductReadAdapter productReadAdapter = new ProductReadAdapter(ProductComponentListActivity.this, getPresenter().mComponents);
        mRecyProductComponentCreate.setAdapter(productReadAdapter);
        mTvProductConponentNum.setText(String.format(getString(R.string.tv_product_component_num), getPresenter().mComponents.size()));
        Glide.with(this).load(getPresenter().product.getProduct_img()).placeholder(R.mipmap.def_image_loading).error(R.mipmap.def_image_loading).into(mIvThumb);
        mTvName.setText(getPresenter().product.getProduct_name());
        mTvSpec.setText(String.format(getString(R.string.text_product_spec), getPresenter().product.getPrice(), getPresenter().product.getForm()));

    }

    private void waterMark() {
        mRlWaterTop.setVisibility(View.VISIBLE);
        mLlWaterBottom.setVisibility(View.VISIBLE);
        ScreenShot.getInstance().takeScreenShotOfJustView(mLlWaterMiddle, new ScreenShot.OnSaveListener() {
            @Override
            public void onPictureSaved(Uri uri) {

            }
        });

//        mLlWaterMiddle.setVisibility(View.GONE);
//        Resources res = getResources();
//        Bitmap bmp = BitmapFactory.decodeResource(res, R.mipmap.ic_test_oily);
//        bitmap = ScreenShot.waterMark(bitmap, bmp);
//        tv_test_img.setImageBitmap(bitmap);
    }
}
