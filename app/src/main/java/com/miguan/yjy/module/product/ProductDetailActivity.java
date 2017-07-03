package com.miguan.yjy.module.product;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.IdRes;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.text.Html;
import android.text.TextUtils;
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
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import com.dsk.chain.bijection.RequiresPresenter;
import com.dsk.chain.expansion.data.BaseDataActivity;
import com.facebook.drawee.view.SimpleDraweeView;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.miguan.yjy.R;
import com.miguan.yjy.adapter.EvaluateAdapter;
import com.miguan.yjy.adapter.ProductComponentAdapter;
import com.miguan.yjy.model.bean.Evaluate;
import com.miguan.yjy.model.bean.Product;
import com.miguan.yjy.module.ask.AskListActivityPresenter;
import com.miguan.yjy.module.common.LargeImageActivity;
import com.miguan.yjy.module.common.WebViewActivity;
import com.miguan.yjy.utils.LUtils;
import com.miguan.yjy.widget.CustomNestedScrollView;
import com.miguan.yjy.widget.FlowTagLayout;
import com.miguan.yjy.widget.SharePopupWindow;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


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
    EasyRecyclerView mRecyEvalutate;

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

    private boolean mIsLike;

    private boolean mIsShowAnim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_activity_detail);
        setToolbarTitle("产品详情");
        ButterKnife.bind(this);

        mRecyEvalutate.setFocusable(false);
        mRgrpEvaluateRank.setOnCheckedChangeListener(this);
    }

    int scrollY;

    @Override
    public void setData(Product product) {

        mCustSrcoll.setOnTouchListener(new View.OnTouchListener() {
            private int lastY = 0;
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


//        mCustSrcoll.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
//            @Override
//            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
//                if (scrollX != oldScrollX) {
//                    showInAnim();
//                }
//            }
//        });

        mCustSrcoll.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {

            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (mIsShowAnim) {
                } else {
                    showInAnim();
                }
            }
        });

//        showInAnim();
        mBtnAsk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mIsShowAnim) {
                    showOutAnim();
                } else {
                    AskListActivityPresenter.start(ProductDetailActivity.this, product.getId());
                }
            }
        });
        if (product.getIs_top() > 0) {
            mIvIsTop.setVisibility(View.GONE);
        } else {
            mIvIsTop.setVisibility(View.GONE);
        }
        mDvThumb.setImageURI(Uri.parse(product.getProduct_img()));
        mDvThumb.setOnClickListener(v -> LargeImageActivity.start(ProductDetailActivity.this, product.getProduct_img()));
        mTvName.setText(product.getProduct_name());

        mTvSpec.setText(product.getPrice().equals("0") ? "暂无报价" : String.format(getString(R.string.text_product_spec), product.getPrice(), product.getForm()));

        if (product.getTagList().size() >= 2) {
            StringBuilder sb = new StringBuilder();
            sb.append("#").append(product.getTagList().get(0)).append("   #").append(product.getTagList().get(1));
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
        ProductComponentAdapter effectAdapter = new ProductComponentAdapter(ProductDetailActivity.this, product.getEffect());
        flowtagEffectInfo.setTagCheckedMode(FlowTagLayout.FLOW_TAG_CHECKED_SINGLE);
        flowtagEffectInfo.setAdapter(effectAdapter);
        flowtagEffectInfo.setFocusable(false);
        effectAdapter.onlyAddAll(product.getEffect());
        effectAdapter.setSetOnTagClickListener((v, position) ->
                ProductReadPresenter.start(ProductDetailActivity.this, "功效信息", product.getComponentList(), product.getEffect(), position)
        );

        //去比价

        mTvTaobao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (LUtils.checkPackage("com.taobao.taobao")) {
                    Intent intent = new Intent();
                    intent.setAction("android.intent.action.VIEW");
                    Uri content_url = Uri.parse(product.getBuy().getTaobao()); // 淘宝商品的地址
                    intent.setData(content_url);
                    startActivity(intent);
                } else {
                    WebViewActivity.start(ProductDetailActivity.this, product.getProduct_name(), product.getBuy().getTaobao());
                }
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
                getPresenter().getEvaluateData(getPresenter().getUserEvluate(), getPresenter().SORT_DEFAULT);
            } else {
                mTvSkinSort.setText(R.string.tv_product_detail_sort_skin);
                getPresenter().getEvaluateData(getPresenter().getUserEvluate(), getPresenter().SORT_SKIN);
            }
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

        new SharePopupWindow.Builder(this)
                .setUrl(product.getLinkUrl())
                .setTitle(product.getProduct_name() + "【颜究院】")
                .setContent("科学分析产品成分，只有我们知道它适不适合你")
                .setImageUrl(product.getProduct_img())
                .setId(product.getId())
                .setType(1)
                .setWxCircleTitle(product.getProduct_name() + "里面都含有些什么成分，查了你就知道")
                .setWbContent(product.getProduct_name() + "里面都含有些什么成分，查了你就知道，分享来自#颜究院APP# " + product.getLinkUrl())
                .show(getToolbar());
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
                getPresenter().setOrder("praise");
                getPresenter().getEvaluateData(getPresenter().getSort(), getPresenter().START_PRAISE);
                break;
            case R.id.rbtn_product_medium_evaluate:
                getPresenter().setOrder("middle");
                getPresenter().getEvaluateData(getPresenter().getSort(), getPresenter().START_MIDDLE);
                break;
            case R.id.rbtn_product_bad_evaluate:
                getPresenter().setOrder("bad");
                getPresenter().getEvaluateData(getPresenter().getSort(), getPresenter().START_BAD);
                break;
        }
    }

    public void setEvaluate(List<Evaluate> list) {
        EvaluateAdapter evaluateAdapter = new EvaluateAdapter(ProductDetailActivity.this, list);
        evaluateAdapter.setMore(R.layout.default_footer_load_more, getPresenter());
        evaluateAdapter.setNoMore(R.layout.default_footer_no_more);
        mRecyEvalutate.setLayoutManager(new LinearLayoutManager(ProductDetailActivity.this, LinearLayoutManager.VERTICAL, false));
        mRecyEvalutate.setFocusable(false);
        mRecyEvalutate.setEmptyView(R.layout.empty_evaluate_list);
        mRecyEvalutate.setAdapter(evaluateAdapter);
        evaluateAdapter.notifyDataSetChanged();
        tvUserEvaluteNum.setText(String.format(getString(R.string.tv_product_detail_user_evaluate), list.size()));
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
