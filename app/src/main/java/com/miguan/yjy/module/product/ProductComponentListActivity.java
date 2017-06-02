package com.miguan.yjy.module.product;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dsk.chain.bijection.RequiresPresenter;
import com.dsk.chain.expansion.data.BaseDataActivity;
import com.facebook.drawee.view.SimpleDraweeView;
import com.miguan.yjy.R;
import com.miguan.yjy.adapter.ProductReadAdapter;
import com.miguan.yjy.model.bean.Component;
import com.miguan.yjy.module.common.WebViewActivity;
import com.miguan.yjy.utils.LUtils;
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

    SimpleDraweeView mIvThumb;
    TextView mTvName;
    TextView mTvSpec;


    public static final String WEBVIEW_READ = "http://m.yjyapp.com/site/skin-unscramble";


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
        WebViewActivity.start(ProductComponentListActivity.this, "解读表", WEBVIEW_READ);
        return super.onOptionsItemSelected(item);
    }

    private void initData() {
        mRecyProductComponentCreate.setFocusable(false);
        mRecyProductComponentCreate.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        ProductReadAdapter productReadAdapter = new ProductReadAdapter(ProductComponentListActivity.this, getPresenter().mComponents);
        mRecyProductComponentCreate.setAdapter(productReadAdapter);
        mTvProductConponentNum.setText(String.format(getString(R.string.tv_product_component_num), getPresenter().mComponents.size()));

    }

    private void waterMark() {
        mllProductCreateImg.setClickable(false);
        View mRlWaterTop = View.inflate(ProductComponentListActivity.this, R.layout.water_top, null);


        View mLlWaterBottom = View.inflate(ProductComponentListActivity.this, R.layout.water_bottom, null);

        mIvThumb = ButterKnife.findById(mRlWaterTop,R.id.iv_product_detail);
        mTvName = ButterKnife.findById(mRlWaterTop,R.id.tv_product_detail_name);
        mTvSpec = ButterKnife.findById(mRlWaterTop,R.id.tv_product_detail_spec);

        mIvThumb.setImageURI(Uri.parse(getPresenter().product.getProduct_img()));
        mTvName.setText(getPresenter().product.getProduct_name());
        mTvSpec.setText(getPresenter().product.getPrice().equals("0") ? "暂无报价" : String.format(getString(R.string.text_product_spec),getPresenter(). product.getPrice(), getPresenter().product.getForm()));

        Bitmap waterMiddle = ScreenShot.getInstance().takeScreenShotOfJustView(mLlWaterMiddle);
        Bitmap waterTop = ScreenShot.getInstance().takeScreenShotOfJustView(mRlWaterTop);
        Bitmap waterBottom = ScreenShot.getInstance().takeScreenShotOfJustView(mLlWaterBottom);
       Bitmap temp= ScreenShot.addBitmap(waterTop,waterMiddle);
       Bitmap reslut= ScreenShot.addBitmap(temp,waterBottom);
        Resources res = getResources();
        Bitmap bmp = BitmapFactory.decodeResource(res, R.mipmap.screenshot_water_logo2);
//        Bitmap bmp = BitmapFactory.decodeResource(res, R.mipmap.ic_water);
        reslut = ScreenShot.waterMark(reslut, bmp);
//        tv_test_img.setImageBitmap(bitmap);
        ScreenShot.getInstance().saveScreenshotToPicturesFolder(this, reslut, new ScreenShot.OnSaveListener() {
            @Override
            public void onPictureSaved(String path, Uri uri) {
                LUtils.toast("图片已生成,请至相册查收!");
                mllProductCreateImg.setClickable(true);
            }

        });
    }
}
