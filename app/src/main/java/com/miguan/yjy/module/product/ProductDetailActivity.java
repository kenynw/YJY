package com.miguan.yjy.module.product;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.IdRes;
import android.support.design.widget.TabLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import com.dsk.chain.bijection.RequiresPresenter;
import com.dsk.chain.expansion.data.BaseDataActivity;
import com.facebook.drawee.view.SimpleDraweeView;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.decoration.DividerDecoration;
import com.miguan.yjy.R;
import com.miguan.yjy.adapter.EvaluateAdapter;
import com.miguan.yjy.adapter.FlagShipAdapter;
import com.miguan.yjy.adapter.ProductComponentAdapter;
import com.miguan.yjy.model.bean.Evaluate;
import com.miguan.yjy.model.bean.Product;
import com.miguan.yjy.module.ask.AskListActivityPresenter;
import com.miguan.yjy.module.common.LargeImageActivity;
import com.miguan.yjy.module.common.WebViewActivity;
import com.miguan.yjy.utils.LUtils;
import com.miguan.yjy.widget.CustomNestedScrollView;
import com.miguan.yjy.widget.FlowTagLayout;
import com.miguan.yjy.widget.ShareBottomDialog;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.miguan.yjy.module.product.ProductDetailPresenter.SORT_DEFAULT;
import static com.miguan.yjy.module.product.ProductDetailPresenter.SORT_SKIN;
import static com.miguan.yjy.module.product.ProductDetailPresenter.START_BAD;
import static com.miguan.yjy.module.product.ProductDetailPresenter.START_MIDDLE;
import static com.miguan.yjy.module.product.ProductDetailPresenter.START_PRAISE;


/**
 * @作者 cjh
 * @日期 2017/3/22 10:11
 * @描述 产品详情页
 */
@RequiresPresenter(ProductDetailPresenter.class)
public class ProductDetailActivity extends BaseDataActivity<ProductDetailPresenter, Product> implements View.OnClickListener, RadioGroup.OnCheckedChangeListener {

    @BindView(R.id.dv_product_detail)
    SimpleDraweeView mDvThumb;

    @BindView(R.id.tv_product_detail_name)
    TextView mTvName;

    @BindView(R.id.tv_product_detail_spec)
    TextView mTvSpec;

    @BindView(R.id.tv_product_date_detail)
    TextView mTvQueryDate;

    @BindView(R.id.tv_product_detail_record_inf0)
    TextView tvRecordInf0;

    @BindView(R.id.tv_product_detail_provision_no)
    TextView mTvProvisionNo;

    @BindView(R.id.tv_product_detail_country)
    TextView mTvCountry;

    @BindView(R.id.tv_product_detail_company)
    TextView mTvCompany;

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

    @BindView(R.id.tv_product_user_evaluate_num)
    TextView tvUserEvaluteNum;

    @BindView(R.id.rgrp_evaluate_list_rank)
    RadioGroup mRgrpEvaluateRank;

    @BindView(R.id.tv_product_detail_like)
    TextView mTvLike;

    @BindView(R.id.iv_product_detail_like)
    ImageView mIvLike;

    @BindView(R.id.tv_product_detail_homework)
    TextView mTvTemplate;

    @BindView(R.id.tv_product_detail_remark)
    TextView tvRemark;

    @BindView(R.id.ll_product_detail_info)
    LinearLayout ll_info;

    @BindView(R.id.ll_product_include_remark_info)
    LinearLayout llRemarkInfo;

    @BindView(R.id.iv_product_down)
    ImageView mIvProductDown;

    @BindView(R.id.tv_product_taobao)
    TextView mTvTaobao;

    @BindView(R.id.tv_product_jingdong)
    TextView mTvJingdong;

    @BindView(R.id.tv_product_amazon)
    TextView mTvAmazon;

    @BindView(R.id.recy_product_evaluate)
    RecyclerView mRecyEvalutate;

    @BindView(R.id.tv_product_skin_sort)
    TextView mTvSkinSort;
    @BindView(R.id.tv_product_tag_detail)
    TextView mTvTag;
    @BindView(R.id.tv_product_detail_read_describe)
    TextView mTvReadDescribe;
    @BindView(R.id.ll_product_detail_read)
    LinearLayout mLlRead;
    @BindView(R.id.iv_is_top)
    ImageView mIvIsTop;

    @BindView(R.id.btn_product_detail_ask)
    ImageView mBtnAsk;

    @BindView(R.id.custSrcoll_product_detail)
    CustomNestedScrollView mCustSrcoll;

    @BindView(R.id.tv_evaluate_empty)
    TextView mTvEvaluateEmpty;
    @BindView(R.id.tab_product_detail)
    TabLayout mTabProductDetail;
    @BindView(R.id.rbtn_product_high_evaluate)
    RadioButton mRbtnProductHighEvaluate;
    @BindView(R.id.rbtn_product_medium_evaluate)
    RadioButton mRbtnProductMediumEvaluate;
    @BindView(R.id.rbtn_product_bad_evaluate)
    RadioButton mRbtnProductBadEvaluate;
    @BindView(R.id.view_remark_show)
    LinearLayout mViewRemarkShow;
    @BindView(R.id.view_price_show)
    LinearLayout mViewPriceShow;
    @BindView(R.id.view_evaluate_show)
    LinearLayout mViewEvaluateShow;
    @BindView(R.id.toolbar_back)
    ImageView mToolbarBack;
    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.ll_toolbar)
    LinearLayout mLlToolbar;
    @BindView(R.id.toolbar_title_img)
    SimpleDraweeView mToolbarTitleImg;
    @BindView(R.id.img_product_recommend_buy)
    ImageView mImgRecommendBuy;
    @BindView(R.id.recy_product_flagship)
    EasyRecyclerView mRecyProductFlagship;

    @BindView(R.id.ll_product_detail_ultimate)
    LinearLayout mLlUltimate;

    @BindView(R.id.ll_effect_all)
    LinearLayout mLlEffectAll;

    private boolean mIsLike;

    private boolean mIsShowAnim;

    int scrollY;
    private int curScrollY;

    private int lastY = 0;
    private boolean mIsSelect;
    private boolean mIsScrollChange;
    private String mGroups[] = {"产品", "成分", "比价", "评价"};
    private int position;
    DividerDecoration decoration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_activity_detail);
        setToolbarTitle("产品详情");
        ButterKnife.bind(this);
        decoration = new DividerDecoration(0xFFEBEBEB, LUtils.dp2px(1), LUtils.dp2px(78), LUtils.dp2px(15));
        decoration.setDrawLastItem(false);
        mRecyEvalutate.addItemDecoration(decoration);
        mRgrpEvaluateRank.setOnCheckedChangeListener(this);
        mRecyEvalutate.setLayoutManager(new LinearLayoutManager(ProductDetailActivity.this, LinearLayoutManager.VERTICAL, false));
        mImgRecommendBuy.setOnClickListener(v -> getPresenter().showExplain());
        mRecyProductFlagship.setLayoutManager(new LinearLayoutManager(ProductDetailActivity.this, LinearLayoutManager.VERTICAL, false));
    }

    @Override
    public void setData(Product product) {
        if (product.getLink_buy() != null && product.getLink_buy().size() > 0) {
            mLlUltimate.setVisibility(View.VISIBLE);
            FlagShipAdapter flagShipAdapter = new FlagShipAdapter(this, product.getLink_buy(), product);
            flagShipAdapter.notifyDataSetChanged();
            mRecyProductFlagship.setAdapter(flagShipAdapter);
        }

        mRbtnProductHighEvaluate.setText("好评(" + product.getPraise() + ")");
        mRbtnProductMediumEvaluate.setText("中评(" + product.getMiddle() + ")");
        mRbtnProductBadEvaluate.setText("差评(" + product.getBad() + ")");

        for (int i = 0; i < mGroups.length; i++) {
            TabLayout.Tab tab = mTabProductDetail.newTab();
            mTabProductDetail.addTab(tab, position = i);
            tab.setText(mGroups[i]);

        }

        mTabProductDetail.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0:
                        if (curScrollY >= 0 && curScrollY < mViewRemarkShow.getTop()) {

                        } else {
                            mCustSrcoll.smoothScrollTo(0, 0);
                        }
                        break;
                    case 1:
                        if (curScrollY >= mViewRemarkShow.getTop() && curScrollY < mViewPriceShow.getTop()) {

                        } else {
                            mCustSrcoll.smoothScrollTo(0, mViewRemarkShow.getTop());

                        }
                        break;
                    case 2:
                        if ((curScrollY >= mViewPriceShow.getTop() && curScrollY < mViewEvaluateShow.getTop())) {

                        } else {
                            mCustSrcoll.smoothScrollTo(0, mViewPriceShow.getTop());
                        }

                        break;
                    case 3:
                        if ((curScrollY >= mViewEvaluateShow.getTop())) {

                        } else {
                            mCustSrcoll.smoothScrollTo(0, mViewEvaluateShow.getTop());
                        }
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        mCustSrcoll.setOnTouchListener(new View.OnTouchListener() {

            private int touchEventId = -9983761;
            Handler handler = new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    super.handleMessage(msg);
                    View scroller = (View) msg.obj;
                    if (msg.what == touchEventId) {
                        if (lastY == scroller.getScrollY()) {
                            handleStop(scroller);

                        } else {
                            handler.sendMessageDelayed(handler.obtainMessage(touchEventId, scroller), 5);
                            lastY = scroller.getScrollY();
                        }
                    }
                }
            };

            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    handler.sendMessageDelayed(handler.obtainMessage(touchEventId, v), 5);
                }
                return false;
            }

            //处理真正的事件
            private void handleStop(Object view) {
                CustomNestedScrollView scroller = (CustomNestedScrollView) view;
                scrollY = scroller.getScrollY();
                showOutAnim();
            }

        });

        mCustSrcoll.setNestedScrollingEnabled(false);
        DisplayMetrics displaymetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        int screenHeight = displaymetrics.heightPixels;

        int actionBarHeight = 0;
        TypedValue tv = new TypedValue();
        if (getTheme().resolveAttribute(android.R.attr.actionBarSize, tv, true)) {
            actionBarHeight = TypedValue.complexToDimensionPixelSize(tv.data, getResources().getDisplayMetrics());
        }

        mCustSrcoll.setMinimumHeight(screenHeight - actionBarHeight);
        mCustSrcoll.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                curScrollY = scrollY;
                if (!mIsShowAnim) {
                    showInAnim();
                }

                if (scrollY <= 0) {   //设置标题的背景颜色
                    mTabProductDetail.setVisibility(View.GONE);
                    mToolbarTitle.setVisibility(View.VISIBLE);
                    mToolbarTitleImg.setVisibility(View.GONE);
                    mLlToolbar.setBackgroundColor(Color.argb(0, 255, 255, 255));

                } else if (scrollY > 0 && scrollY <= 100) { //滑动距离小于banner图的高度时，设置背景和字体颜色颜色透明度渐变
                    mTabProductDetail.setVisibility(View.VISIBLE);
                    float scale = (float) scrollY / 100;
                    float alpha = (255 * scale);
                    mToolbarTitle.setVisibility(View.GONE);
                    mToolbarTitleImg.setVisibility(View.VISIBLE);
                    mToolbarTitleImg.setImageURI(Uri.parse(product.getProduct_img()));
                    mLlToolbar.setBackgroundColor(Color.argb((int) alpha, 255, 255, 255));
                } else {    //滑动到banner下面设置普通颜色
                    mTabProductDetail.setVisibility(View.VISIBLE);
                    mToolbarTitle.setVisibility(View.GONE);
                    mLlToolbar.setBackgroundColor(Color.argb((int) 255, 255, 255, 255));
                    mToolbarTitleImg.setVisibility(View.VISIBLE);
                    mToolbarTitleImg.setImageURI(Uri.parse(product.getProduct_img()));
                }

                if (scrollY > oldScrollY) {
                    // 向下滑动
                    if (scrollY >= 0 && scrollY < mViewRemarkShow.getTop()) {
                        mTabProductDetail.getTabAt(0).select();
                    } else if (scrollY >= mViewRemarkShow.getTop() && scrollY < mViewPriceShow.getTop()) {
                        mTabProductDetail.getTabAt(1).select();
                    } else if (scrollY >= mViewPriceShow.getTop() && scrollY < mViewEvaluateShow.getTop()) {
                        mTabProductDetail.getTabAt(2).select();
                    } else if (scrollY >= mViewEvaluateShow.getTop()) {
                        mTabProductDetail.getTabAt(3).select();
                    } else {

                    }
                } else if (scrollY < oldScrollY) {
                    // 向上滑动
                    if (scrollY < mViewRemarkShow.getTop()) {
                        mTabProductDetail.getTabAt(0).select();
                    } else if (scrollY < mViewPriceShow.getTop()) {
                        mTabProductDetail.getTabAt(1).select();
                    } else if (scrollY < mViewEvaluateShow.getTop()) {
                        mTabProductDetail.getTabAt(2).select();
                    }
                } else if (scrollY == oldScrollY) {

                }

            }
        });

        mBtnAsk.setOnClickListener(view -> {
            if (mIsShowAnim) {
                showOutAnim();
            } else {
                AskListActivityPresenter.start(ProductDetailActivity.this, product.getId());
            }
        });
        if (product.getIs_top() > 0) {
            mIvIsTop.setVisibility(View.GONE);
        } else {
            mIvIsTop.setVisibility(View.GONE);
        }
        if (!TextUtils.isEmpty(product.getProduct_img())) {
            mDvThumb.setImageURI(Uri.parse(product.getProduct_img()));
            mDvThumb.setOnClickListener(v -> {
                String url = product.getProduct_img();
                if (url.contains("@")) {
                    url = url.substring(0, url.lastIndexOf("@"));
                }
                LargeImageActivity.start(ProductDetailActivity.this, url);
            });
        }
        mTvName.setText(product.getProduct_name());
        mTvSpec.setText(product.getSpec(ProductDetailActivity.this));

        if (product.getTagList().size() >= 2) {
            StringBuilder sb = new StringBuilder();
            sb.append("#").append(product.getTagList().get(0))
                    .append("   #").append(product.getTagList().get(1));
            mTvTag.setText(sb);
        } else if (product.getTagList().size() == 1) {
            mTvTag.setText("#" + product.getTagList().get(0));
        } else {
            mTvTag.setVisibility(View.GONE);
        }
        if (product.getRule() != 0) {
            mTvQueryDate.setVisibility(View.VISIBLE);
            mTvQueryDate.setOnClickListener(v -> QueryCodePresenter.start(this, product));
        } else {
            mTvQueryDate.setVisibility(View.GONE);
        }

        if (TextUtils.isEmpty(product.getProduct_explain())) {
            mLlRead.setVisibility(View.GONE);
        } else {
            mLlRead.setVisibility(View.VISIBLE);
            mTvReadDescribe.setText(product.getProduct_explain());
        }

        // 备案
        mTvProvisionNo.setText(product.getStandard_number());
        mTvCountry.setText(product.getProduct_country());
        mTvCompany.setText(product.getProduct_company());
        ratbarGrade.setRating(product.getStar());

        ProductComponentAdapter componentAdapter = new ProductComponentAdapter(ProductDetailActivity.this, product.getSecurity());
        flowtagGrade.setTagCheckedMode(FlowTagLayout.FLOW_TAG_CHECKED_SINGLE);
        flowtagGrade.setFocusable(false);
        flowtagGrade.setAdapter(componentAdapter);
        componentAdapter.onlyAddAll(product.getSecurity());
        componentAdapter.setSetOnTagClickListener((v, position) ->
                ProductReadPresenter.start(ProductDetailActivity.this, "安全信息", product.getComponentList(), product.getSecurity(), position)
        );
        String effectNum = "主要功效成分:<font color=\"#32DAC3\"> " + product.getEffectNum() + " </font>种";
        tvEffectInfo.setText(Html.fromHtml(effectNum));

        if (product.getEffect().size() == 0) {
            mLlEffectAll.setVisibility(View.GONE);
        } else {
            mLlEffectAll.setVisibility(View.VISIBLE);
            ProductComponentAdapter effectAdapter = new ProductComponentAdapter(ProductDetailActivity.this, product.getEffect());
            flowtagEffectInfo.setTagCheckedMode(FlowTagLayout.FLOW_TAG_CHECKED_SINGLE);
            flowtagEffectInfo.setAdapter(effectAdapter);
            flowtagEffectInfo.setFocusable(false);
            effectAdapter.onlyAddAll(product.getEffect());
            effectAdapter.setSetOnTagClickListener((v, position) ->
                    ProductReadPresenter.start(ProductDetailActivity.this, "功效信息", product.getComponentList(), product.getEffect(), position)
            );
        }

        //去比价
        mTvTaobao.setOnClickListener(v -> {
            if (LUtils.checkPackage("com.taobao.taobao")) {
                Intent intent = new Intent();
                intent.setAction("android.intent.action.VIEW");
                Uri content_url = Uri.parse(product.getBuy().getTaobao()); // 淘宝商品的地址
                intent.setData(content_url);
                startActivity(intent);
            } else {
                WebViewActivity.start(ProductDetailActivity.this, product.getProduct_name(), product.getBuy().getTaobao());
            }
        });
        mTvJingdong.setOnClickListener(v -> WebViewActivity.start(ProductDetailActivity.this, product.getProduct_name(), product.getBuy().getJd()));
        mTvAmazon.setOnClickListener(v -> WebViewActivity.start(ProductDetailActivity.this, product.getProduct_name(), product.getBuy().getAmazon()));

        // 长草按钮
        setLike(product.getIsGras() == 1);
        mTvLike.setOnClickListener(v -> getPresenter().addLike(mIsLike));

        tvRemark.setOnClickListener(v -> ProductRemarkPresenter.start(this, product));
        tvLockAllComponent.setOnClickListener(v -> ProductComponentListPresenter.start(this, product));
        ll_info.setOnClickListener(this);

        mTvSkinSort.setOnClickListener(v -> {
            if (mTvSkinSort.getText().equals("肤质排序")) {
                mTvSkinSort.setText(R.string.tv_product_detail_sort_total);
                getPresenter().setSort(SORT_DEFAULT);
            } else {
                mTvSkinSort.setText(R.string.tv_product_detail_sort_skin);
                getPresenter().setSort(SORT_SKIN);
            }
            getPresenter().getEvaluateData();
        });

        mTvTemplate.setOnClickListener(v -> AddRepositoryPresenter.start(this, null, "", product));
    }

    // 设置长草图标样式
    public void setLike(boolean isLike) {
        mIsLike = isLike;
        mIvLike.setImageResource(isLike ? R.mipmap.ic_product_like_checked : R.mipmap.ic_product_like_normal);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_share, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Product product = getPresenter().getData();

        new ShareBottomDialog.Builder(this)
                .setUrl(TextUtils.isEmpty(product.getLinkUrl()) ? "http:www.yjyapp.com" : product.getLinkUrl())
                .setTitle(product.getProduct_name() + "【颜究院】")
                .setContent("科学分析产品成分，只有我们知道它适不适合你")
                .setImageUrl(product.getProduct_img())
                .setId(product.getId())
                .setType(1)
                .setWxCircleTitle(product.getProduct_name() + "里面都含有些什么成分，查了你就知道")
                .setWbContent(product.getProduct_name() + "里面都含有些什么成分，查了你就知道，分享来自#颜究院APP# " + product.getLinkUrl())
                .show();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        if (llRemarkInfo.getVisibility() == View.VISIBLE) {
            llRemarkInfo.setVisibility(View.GONE);
            mIvProductDown.setBackgroundResource(R.mipmap.ic_product_detail_record_down);
        } else {
            llRemarkInfo.setVisibility(View.VISIBLE);
            mIvProductDown.setBackgroundResource(R.mipmap.ic_product_detail_up);
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
        switch (checkedId) {
            case R.id.rbtn_product_high_evaluate:
                getPresenter().setCondition(START_PRAISE);
                break;
            case R.id.rbtn_product_medium_evaluate:
                getPresenter().setCondition(START_MIDDLE);
                break;
            case R.id.rbtn_product_bad_evaluate:
                getPresenter().setCondition(START_BAD);
                break;
        }
        getPresenter().getEvaluateData();
    }

    public void setEvaluate(List<Evaluate> list, int size) {
        mTvEvaluateEmpty.setVisibility(list != null && list.size() > 0 ? View.GONE : View.VISIBLE);
        EvaluateAdapter evaluateAdapter = new EvaluateAdapter(ProductDetailActivity.this, list);
        evaluateAdapter.setMore(R.layout.default_footer_load_more, getPresenter());
        evaluateAdapter.setNoMore(R.layout.include_default_footer);
        mRecyEvalutate.setNestedScrollingEnabled(false);
        mRecyEvalutate.setAdapter(evaluateAdapter);
        evaluateAdapter.notifyDataSetChanged();
        tvUserEvaluteNum.setText(String.format(getString(R.string.tv_product_detail_user_evaluate), size));
    }

    private void showInAnim() {
        RotateAnimation rotateAnimation = new RotateAnimation(0, -60, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        TranslateAnimation translateAnimation = new TranslateAnimation(Animation.ABSOLUTE, 0, Animation.ABSOLUTE, mBtnAsk.getWidth() / 2 + mBtnAsk.getPaddingRight(), Animation.ABSOLUTE, 0, Animation.ABSOLUTE, mBtnAsk.getWidth() / 2 + mBtnAsk.getPaddingRight());
        AnimationSet set = new AnimationSet(false);
        set.addAnimation(rotateAnimation);
        set.addAnimation(translateAnimation);
        set.setDuration(500);
        set.setFillAfter(true);
        mBtnAsk.startAnimation(set);
        mIsShowAnim = true;
    }

    private void showOutAnim() {
        RotateAnimation rotateAnimation = new RotateAnimation(-60, 0, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        TranslateAnimation translateAnimation = new TranslateAnimation(Animation.ABSOLUTE, mBtnAsk.getWidth() / 2 + mBtnAsk.getPaddingRight(), Animation.ABSOLUTE, 0, Animation.ABSOLUTE, mBtnAsk.getWidth() / 2 + mBtnAsk.getPaddingRight(), Animation.ABSOLUTE, 0);
        AnimationSet set = new AnimationSet(false);
        set.addAnimation(rotateAnimation);
        set.addAnimation(translateAnimation);
        set.setDuration(500);
        set.setFillAfter(true);
        mBtnAsk.startAnimation(set);
        mIsShowAnim = false;
    }
}
