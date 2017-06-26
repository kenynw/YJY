package com.miguan.yjy.adapter;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.miguan.yjy.R;
import com.miguan.yjy.model.bean.Product;
import com.miguan.yjy.module.product.ProductDetailPresenter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @作者 cjh
 * @日期 2017/4/5 16:51
 * @描述
 */

public class TestListAdapter extends RecyclerArrayAdapter<Product> {


    public TestListAdapter(Context context, List<Product> objects) {
        super(context, objects);
    }

    @Override
    public void OnBindViewHolder(BaseViewHolder holder, int position) {
        holder.setData(mObjects.get(position));
    }

    @Override
    public int getCount() {
        return mObjects.size()+1;
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(parent);
    }


    class MyViewHolder extends BaseViewHolder<Product> {

        @BindView(R.id.iv_test_list)
        SimpleDraweeView mIvTestList;
        @BindView(R.id.tv_test_list_name)
        TextView mTvTestListName;
        @BindView(R.id.ll_test_more)
        LinearLayout mLlTestMore;

        public MyViewHolder(ViewGroup parent) {
            super(parent, R.layout.item_test_list);
            ButterKnife.bind(this, itemView);
        }


        @Override
        public void setData(Product data) {
            Log.e("getDataPosition() ", getDataPosition() + "====");
            Log.e("mObjects.size()", mObjects.size() + "====");

            if ((getDataPosition() +1)== mObjects.size()) {
                mIvTestList.setVisibility(View.GONE);
                mTvTestListName.setText("");
                mLlTestMore.setVisibility(View.VISIBLE);
//                itemView.setOnClickListener(v -> TestRecomendPresenter.star(getContext(), test.getCategoryList(), position, datas.get(position).getCategory_name()));

            } else {
                mIvTestList.setVisibility(View.VISIBLE);
                mLlTestMore.setVisibility(View.GONE);
                mTvTestListName.setText(data.getProduct_name());
                mIvTestList.setImageURI(Uri.parse(data.getProduct_img()));
                itemView.setOnClickListener(v -> ProductDetailPresenter.start(getContext(), data.getId()));
            }
//            .placeholder(R.mipmap.def_image_product).error(R.mipmap.def_image_product)
//            Glide.with(getContext()).load(data.getProduct_img()).into(mIvTestList);
//            mIvTestList.setBackgroundResource(data.getImg());

        }
    }

}
