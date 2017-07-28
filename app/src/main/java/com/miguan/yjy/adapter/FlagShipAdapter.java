package com.miguan.yjy.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.miguan.yjy.R;
import com.miguan.yjy.model.bean.Buy;
import com.miguan.yjy.model.bean.Product;
import com.miguan.yjy.module.common.WebViewActivity;
import com.miguan.yjy.utils.LUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @作者 cjh
 * @日期 2017/7/27 11:41
 * @描述
 */

public class FlagShipAdapter extends RecyclerArrayAdapter<Buy> {

    private Product mProduct;

    public FlagShipAdapter(Context context, List<Buy> objects, Product product) {
        super(context, objects);
        mProduct = product;
    }

    public FlagShipAdapter(Context context, Product product) {
        super(context);
        mProduct = product;
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
            mImgFlagShipPrice.setImageURI(mProduct.getProduct_img());
            mTvFlagshipProductName.setText(mProduct.getBrand_name());
            mTvFlagshipProductPrice.setText(data.getLink_price());

            switch (data.getType()) {
                case 1:
                    mTvFlagShipName.setText(getContext().getString(R.string.text_taobao));
                    mTvFlagShipName.setBackgroundResource(R.drawable.bg_radius_f38);
                    break;
                case 2:
                    mTvFlagShipName.setText(getContext().getString(R.string.text_jingdong));
                    mTvFlagShipName.setBackgroundResource(R.drawable.bg_radius_c9);
                    break;
                case 3:
                    mTvFlagShipName.setText(getContext().getString(R.string.text_amazon));
                    mTvFlagShipName.setBackgroundResource(R.drawable.bg_radius_transparent);
                    break;
            }

            mTvFlagship.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    switch (data.getType()) {
                        case 1:
                            if (LUtils.checkPackage("com.taobao.taobao")) {
                                Intent intent = new Intent();
                                intent.setAction("android.intent.action.VIEW");
                                Uri content_url = Uri.parse(data.getUrl()); // 淘宝商品的地址
                                intent.setData(content_url);
                                getContext().startActivity(intent);
                            } else {
                                WebViewActivity.start(getContext(), mProduct.getProduct_name(), data.getUrl());
                            }
                            break;
                        case 2:
                            WebViewActivity.start(getContext(), mProduct.getProduct_name(), data.getUrl());
                            break;
                        case 3:
                            WebViewActivity.start(getContext(), mProduct.getProduct_name(), data.getUrl());
                            break;
                    }

                }
            });


        }
    }


}
