package com.miguan.yjy.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.ViewGroup;

import com.facebook.drawee.view.SimpleDraweeView;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.miguan.yjy.R;
import com.miguan.yjy.model.bean.Brand;
import com.miguan.yjy.module.product.BrandMainPresenter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @作者 cjh
 * @日期 2017/6/1 11:01
 * @描述
 */

public class BrandMoreAdapter extends RecyclerArrayAdapter<Brand> {

    public BrandMoreAdapter(Context context, List<Brand> objects) {
        super(context, objects);
    }


    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        BrandMoreViewHolder brandMoreViewHolder = new BrandMoreViewHolder(parent);
        brandMoreViewHolder.setIsRecyclable(false);
        return brandMoreViewHolder;
    }

    class BrandMoreViewHolder extends BaseViewHolder<Brand> {

        @BindView(R.id.brand_more_img)
        SimpleDraweeView mBrandMoreImg;

        public BrandMoreViewHolder(ViewGroup parent) {
            super(parent, R.layout.item_brand_more);
            ButterKnife.bind(this, itemView);
        }

        @Override
        public void setData(Brand data) {
            mBrandMoreImg.setImageURI(Uri.parse(data.getImg()));
            mBrandMoreImg.setOnClickListener(v -> BrandMainPresenter.star(getContext(), data.getId()));
        }

    }

}
