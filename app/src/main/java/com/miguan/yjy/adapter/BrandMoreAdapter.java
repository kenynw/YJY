package com.miguan.yjy.adapter;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.miguan.yjy.R;
import com.miguan.yjy.model.bean.Brand;
import com.miguan.yjy.module.product.BrandMainPresenter;
import com.miguan.yjy.utils.GlideCircleTransformBorder;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
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
        ImageView mBrandMoreImg;

        public BrandMoreViewHolder(ViewGroup parent) {
            super(parent, R.layout.item_brand_more);
            ButterKnife.bind(this, itemView);
        }

        @Override
        public void setData(Brand data) {
            String uri = data.getImg();

//                byte[] t_utf8 = uri.getBytes("UTF-8");
//                String ut_utf8 = new String(t_utf8, "UTF-8");
            Glide.with(getContext()).load(uri)
                    .error(getContext().getResources().getDrawable(R.mipmap.def_image_product))
                    .placeholder(getContext().getResources().getDrawable(R.mipmap.def_image_product))
                    .transform(new GlideCircleTransformBorder(getContext(), 2, getContext().getResources().getColor(R.color.f5)))
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .into(mBrandMoreImg);
            try {
                String ut_utf8 = URLEncoder.encode(uri, "utf-8");
                String strUTF8 = URLDecoder.decode(ut_utf8, "UTF-8");

//                mBrandMoreImg.setImageURI(strUTF8);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }


            mBrandMoreImg.setOnClickListener(v -> BrandMainPresenter.star(getContext(), data.getId()));
        }

    }

}
