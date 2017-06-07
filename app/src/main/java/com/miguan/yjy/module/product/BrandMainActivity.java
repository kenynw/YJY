package com.miguan.yjy.module.product;

import android.content.Context;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dsk.chain.bijection.RequiresPresenter;
import com.dsk.chain.expansion.data.BaseDataActivity;
import com.facebook.drawee.view.SimpleDraweeView;
import com.miguan.yjy.R;
import com.miguan.yjy.adapter.BrandPagerAdapter;
import com.miguan.yjy.model.bean.Brand;
import com.miguan.yjy.widget.ChildViewPager;
import com.miguan.yjy.widget.MyScrollView;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.miguan.yjy.R.mipmap.ic_product_detail_record_down;
import static com.miguan.yjy.R.mipmap.ic_product_detail_up;

/**
 * @作者 cjh
 * @日期 2017/5/18 10:44
 * @描述
 */
@RequiresPresenter(BrandMainPresenter.class)
public class BrandMainActivity extends BaseDataActivity<BrandMainPresenter, Brand> implements MyScrollView.OnScrollListener {

    @BindView(R.id.sdv_brand_img)
    SimpleDraweeView mSdvBrandImg;
    @BindView(R.id.tv_brand_name)
    TextView mTvBrandName;
    @BindView(R.id.tv_brand_num)
    TextView mTvBrandNum;
    @BindView(R.id.tv_brand_brief)
    TextView mTvBrandBrief;
    @BindView(R.id.tab_product_brand)
    TabLayout mTabProductBrand;//嵌套在scrollView
    @BindView(R.id.tab_top_product_brand)
    TabLayout mTabTopProductBrand;//悬浮
    @BindView(R.id.vp_brand)
    ChildViewPager mVpBrand;

    BrandPagerAdapter mBrandPagerAdapter;
    @BindView(R.id.iv_brand_show)
    ImageView mIvBrandShow;
    @BindView(R.id.tv_brand_brief_show)
    TextView mTvBrandBriefShow;
    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.ll_brand_show)
    LinearLayout mLlBrandShow;
    @BindView(R.id.myScrollView)
    MyScrollView mMyScrollView;

    private int tag = 0;
    private int isShowArticle;

//    private LinearLayout mBuyLayout;
    private WindowManager mWindowManager;
    /**
     * 手机屏幕宽度
     */
    private int screenWidth;
    /**
     * 悬浮框View
     */
    private static View suspendView;
    /**
     * 悬浮框的参数
     */
    private static WindowManager.LayoutParams suspendLayoutParams;
    /**
     * tab布局的高度
     */
    private int tabLayoutHeight;
    /**
     * myScrollView与其父类布局的顶部距离
     */
    private int myScrollViewTop;

    /**
     * tab布局与其父类布局的顶部距离
     */
    private int tabLayoutTop;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_brand_main_activity);
        ButterKnife.bind(this);
        mMyScrollView.setFocusable(true);
        mMyScrollView.setFocusableInTouchMode(true);
        mMyScrollView.requestFocus();

        mMyScrollView.setOnScrollListener(this);
        mWindowManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        screenWidth = mWindowManager.getDefaultDisplay().getWidth();


        //当布局的状态或者控件的可见性发生改变回调的接口
        findViewById(R.id.parent_layout).getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {

            @Override
            public void onGlobalLayout() {
                //这一步很重要，使得上面的购买布局和下面的购买布局重合
                onScroll(mMyScrollView.getScrollY());

            }
        });

    }


    @Override
    public void setData(Brand brand) {
        super.setData(brand);
        mSdvBrandImg.setImageURI(brand.getImg());
        mTvBrandName.setText(brand.getName());
        setToolbarTitle(brand.getName());
        String num = "品牌热度 : <font color=\"#32DAC3\">" + brand.getHot() + "</font>";
        mTvBrandNum.setText(Html.fromHtml(num));
        mTvBrandBrief.setText(brand.getDescription());
        mTvBrandBriefShow.setText(brand.getDescription());
        if (getPresenter().mArticles.size() != 0) {
            isShowArticle = 1;
            mVpBrand.setOffscreenPageLimit(3);
        } else {
            isShowArticle = 2;
            mVpBrand.setOffscreenPageLimit(2);
        }
        mVpBrand.setNoScroll(true);
        mVpBrand.setDescendantFocusability(ViewGroup.FOCUS_BLOCK_DESCENDANTS);
        mBrandPagerAdapter = new BrandPagerAdapter(getSupportFragmentManager(), isShowArticle, brand.getId());
        mVpBrand.setAdapter(mBrandPagerAdapter);
        mTabProductBrand.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mVpBrand.resetHeight(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        mTabProductBrand.setupWithViewPager(mVpBrand);
        mTabTopProductBrand.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mVpBrand.resetHeight(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        mTabTopProductBrand.setupWithViewPager(mVpBrand);
        mLlBrandShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tag == 0) {
                    tag = 1;
                    mTvBrandBriefShow.setVisibility(View.VISIBLE);
                    mTvBrandBrief.setVisibility(View.GONE);
                    mIvBrandShow.setBackgroundResource(ic_product_detail_up);
                } else {
                    tag = 0;
                    mTvBrandBrief.setVisibility(View.VISIBLE);
                    mTvBrandBriefShow.setVisibility(View.GONE);
                    mIvBrandShow.setBackgroundResource(ic_product_detail_record_down);
                    LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
                    lp.gravity = Gravity.BOTTOM;
                    mIvBrandShow.setLayoutParams(lp);
                }
            }
        });

    }


    /**
     * 窗口有焦点的时候，即所有的布局绘制完毕的时候，我们来获取tab布局的高度和myScrollView距离父类布局的顶部位置
     */
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            tabLayoutHeight = mTabProductBrand.getHeight();
            tabLayoutTop = mTabProductBrand.getTop();

            myScrollViewTop = mMyScrollView.getTop();
        }
    }


    /**
     * 滚动的回调方法，当滚动的Y距离大于或者等于 tab布局距离父类布局顶部的位置，就显示tab的悬浮框
     * 当滚动的Y的距离小于 tab布局距离父类布局顶部的位置加上tab布局的高度就移除tab的悬浮框
     */
    @Override
    public void onScroll(int scrollY) {

        int mBuyLayout2ParentTop = Math.max(scrollY, mTabProductBrand.getTop());
        mTabTopProductBrand.layout(0, mBuyLayout2ParentTop, mTabTopProductBrand.getWidth(), mBuyLayout2ParentTop + mTabTopProductBrand.getHeight());
//        if (scrollY >= tabLayoutTop) {
//            if (suspendView == null) {
//                showSuspend();
//            }
//        } else if (scrollY <= tabLayoutTop + tabLayoutHeight) {
//            if (suspendView != null) {
//                removeSuspend();
//            }
//        }
    }


    /**
     * 显示tab的悬浮框
     */
    private void showSuspend() {
        if (suspendView == null) {
            suspendView = LayoutInflater.from(this).inflate(R.layout.brand_top_tablayout, null);
            if (suspendLayoutParams == null) {
                suspendLayoutParams = new WindowManager.LayoutParams();
                suspendLayoutParams.type = WindowManager.LayoutParams.TYPE_PHONE; //悬浮窗的类型，一般设为2002，表示在所有应用程序之上，但在状态栏之下
                suspendLayoutParams.format = PixelFormat.RGBA_8888;
                suspendLayoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
                        | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;  //悬浮窗的行为，比如说不可聚焦，非模态对话框等等
                suspendLayoutParams.gravity = Gravity.TOP;  //悬浮窗的对齐方式
                suspendLayoutParams.width = screenWidth;
                suspendLayoutParams.height = tabLayoutHeight;
                suspendLayoutParams.x = 0;  //悬浮窗X的位置
                suspendLayoutParams.y = myScrollViewTop;  ////悬浮窗Y的位置
            }
        }

        mWindowManager.addView(suspendView, suspendLayoutParams);
    }


    /**
     * 移除tab的悬浮框
     */
    private void removeSuspend() {
        if (suspendView != null) {
            mWindowManager.removeView(suspendView);
            suspendView = null;
        }
    }

}


