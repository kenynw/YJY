package com.miguan.yjy.adapter;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.alibaba.baichuan.android.trade.AlibcTrade;
import com.alibaba.baichuan.android.trade.callback.AlibcTradeCallback;
import com.alibaba.baichuan.android.trade.model.AlibcShowParams;
import com.alibaba.baichuan.android.trade.model.OpenType;
import com.alibaba.baichuan.android.trade.page.AlibcBasePage;
import com.alibaba.baichuan.android.trade.page.AlibcPage;
import com.alibaba.baichuan.trade.biz.AlibcConstants;
import com.alibaba.baichuan.trade.biz.context.AlibcTradeResult;
import com.facebook.drawee.view.SimpleDraweeView;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.miguan.yjy.R;
import com.miguan.yjy.model.bean.Buy;
import com.miguan.yjy.model.bean.Product;
import com.miguan.yjy.module.common.WebViewActivity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @作者 cjh
 * @日期 2017/7/27 11:41
 * @描述
 */

public class FlagShipAdapter extends RecyclerArrayAdapter<Buy> {

    private Product mProduct;
    private Activity mActivity;

    public FlagShipAdapter(Activity context, List<Buy> objects, Product product) {
        super(context, objects);
        mProduct = product;
        mActivity = context;
    }

    public FlagShipAdapter(Activity context, Product product) {
        super(context);
        mProduct = product;
        mActivity = context;
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new FlagShipViewHolder(parent);
    }

    class FlagShipViewHolder extends BaseViewHolder<Buy> {
        @BindView(R.id.frame_flagship)
        FrameLayout mFrameFlagship;
        @BindView(R.id.tv_flagship_product_name)
        TextView mTvFlagshipProductName;
        @BindView(R.id.tv_flagship_product_price)
        TextView mTvFlagshipProductPrice;
        @BindView(R.id.tv_flagship)
        TextView mTvFlagship;
        @BindView(R.id.img_flagShip_price)
        SimpleDraweeView mImgFlagShipPrice;
        @BindView(R.id.tv_flagShip_name)
        TextView mTvFlagShipName;

        public FlagShipViewHolder(ViewGroup parent) {
            super(parent, R.layout.item_flagship);
            ButterKnife.bind(this, itemView);
        }

        @Override
        public void setData(Buy data) {
            super.setData(data);
            mImgFlagShipPrice.setImageURI(data.getBrand_img());
            mTvFlagshipProductName.setText(data.getBrand_name());
            mTvFlagshipProductPrice.setText(data.getLink_price());

            switch (data.getType()) {
                case 1:
                    mTvFlagShipName.setText(mActivity.getString(R.string.text_taobao));
                    mTvFlagShipName.setBackgroundResource(R.drawable.bg_radius_f38);
                    break;
                case 2:
                    mTvFlagShipName.setText(mActivity.getString(R.string.text_jingdong));
                    mTvFlagShipName.setBackgroundResource(R.drawable.bg_radius_c9);
                    break;
                case 3:
                    mTvFlagShipName.setText(mActivity.getString(R.string.text_amazon));
                    mTvFlagShipName.setBackgroundResource(R.drawable.bg_radius_transparent);
                    break;
            }

            mTvFlagship.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    switch (data.getType()) {
                        case 1:
                            //提供给三方传递配置参数
                            Map<String, String> exParams = new HashMap<>();
                            exParams.put(AlibcConstants.ISV_CODE, "appisvcode");

                            //实例化URL打开page
                            AlibcBasePage page = new AlibcPage(data.getUrl());
                            //设置页面打开方式
                            AlibcShowParams showParams = new AlibcShowParams(OpenType.Auto,false);

                            AlibcTrade.show(mActivity, page, showParams, null, exParams, new AlibcTradeCallback() {
                                @Override
                                public void onTradeSuccess(AlibcTradeResult alibcTradeResult) {
                                    Log.e("成功", "onTradeSuccess");
                                }

                                @Override
                                public void onFailure(int i, String s) {
                                    Log.e("失败", "onFailure");
                                }
                            });
//                            if (LUtils.checkPackage("com.taobao.taobao")) {
//                                Intent intent = new Intent();
//                                intent.setAction("android.intent.action.VIEW");
//                                Uri content_url = Uri.parse(data.getUrl()); // 淘宝商品的地址
//                                intent.setData(content_url);
//                                getContext().startActivity(intent);
//                            } else {
//                                WebViewActivity.start(getContext(), mProduct.getProduct_name(), data.getUrl());
//                            }
                            break;
                        case 2:
                            WebViewActivity.start(mActivity, mProduct.getProduct_name(), data.getUrl());
                            break;
                        case 3:
                            WebViewActivity.start(mActivity, mProduct.getProduct_name(), data.getUrl());
                            break;
                    }

                }
            });


        }
    }




}
