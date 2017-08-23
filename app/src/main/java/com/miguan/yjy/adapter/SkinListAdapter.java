package com.miguan.yjy.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.miguan.yjy.R;
import com.miguan.yjy.model.bean.Product;
import com.miguan.yjy.model.bean.Test;
import com.miguan.yjy.module.product.ProductDetailPresenter;
import com.miguan.yjy.module.test.TestRecomendPresenter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;



/**
 * @作者 cjh
 * @日期 2017/6/21 10:548
 * @描述
 */

public class SkinListAdapter extends RecyclerView.Adapter<SkinListAdapter.MyViewHolder> {

    private Context mContext;
    private List<Product> mProducts;
    private int mCurPosition;
    private Test mTest;
    private String mName;

    public SkinListAdapter(Context context, List<Product> objects,int curPosition, Test test, String name) {
        mContext = context;
        mProducts = objects;
        mCurPosition = curPosition;
        mTest = test;
        mName = name;

    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(mContext, R.layout.item_test_list, null);
        MyViewHolder viewholder = new MyViewHolder(view);
        return viewholder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        if ((position) == mProducts.size()) {
            holder.mIvTestList.setVisibility(View.GONE);
            holder.mTvTestListName.setText("");
            holder.mLlTestMore.setVisibility(View.VISIBLE);
            holder.itemView.setOnClickListener(v -> TestRecomendPresenter.star(mContext, mTest,mCurPosition, mName));
        } else {
            holder.mIvTestList.setVisibility(View.VISIBLE);
            holder.mLlTestMore.setVisibility(View.GONE);
            holder.mTvTestListName.setText(mProducts.get(position).getProduct_name());
            holder.mIvTestList.setImageURI(Uri.parse(mProducts.get(position).getProduct_img()));
            holder.itemView.setOnClickListener(v -> ProductDetailPresenter.start(mContext, mProducts.get(position).getId()));
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return mProducts.size() + 1;
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_test_list)
        SimpleDraweeView mIvTestList;
        @BindView(R.id.tv_test_list_name)
        TextView mTvTestListName;
        @BindView(R.id.ll_test_more)
        LinearLayout mLlTestMore;

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }


    }


//    class MyViewHolder extends BaseViewHolder<Product> {
//
//        @BindView(R.id.iv_test_list)
//        SimpleDraweeView mIvTestList;
//        @BindView(R.id.tv_test_list_name)
//        TextView mTvTestListName;
//        @BindView(R.id.ll_test_more)
//        LinearLayout mLlTestMore;
//
//        public MyViewHolder(ViewGroup parent) {
//            super(parent, R.layout.item_test_list);
//            ButterKnife.bind(this, itemView);
//        }
//
//
//        @Override
//        public void setData(Product data) {
//            Log.e("getDataPosition() ", getDataPosition() + "====");
//            Log.e("mObjects.size()", mProducts.size() + "====");
//
//            if ((getDataPosition() +1)== mProducts.size()) {
//                mIvTestList.setVisibility(View.GONE);
//                mTvTestListName.setText("");
//                mLlTestMore.setVisibility(View.VISIBLE);
////                itemView.setOnClickListener(v -> TestRecomendPresenter.star(getContext(), test.getCategoryList(), position, datas.get(position).getCategory_name()));
//
//            } else {
//                mIvTestList.setVisibility(View.VISIBLE);
//                mLlTestMore.setVisibility(View.GONE);
//                mTvTestListName.setText(data.getProduct_name());
//                mIvTestList.setImageURI(Uri.parse(data.getProduct_img()));
//                itemView.setOnClickListener(v -> ProductDetailPresenter.start(getContext(), data.getId()));
//            }
////            .placeholder(R.mipmap.def_image_product).error(R.mipmap.def_image_product)
////            Glide.with(getContext()).load(data.getProduct_img()).into(mIvTestList);
////            mIvTestList.setBackgroundResource(data.getImg());
//
//        }
//    }

}
