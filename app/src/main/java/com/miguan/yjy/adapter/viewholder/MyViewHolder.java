package com.miguan.yjy.adapter.viewholder;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.miguan.yjy.R;
import com.miguan.yjy.adapter.SkinListAdapter;
import com.miguan.yjy.model.bean.Skin;
import com.miguan.yjy.model.bean.Test;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @作者 cjh
 * @日期 2017/6/20 16:38
 * @描述
 */

public  class MyViewHolder extends BaseViewHolder<Skin> {

    private Test mTest;

    @BindView(R.id.tv_test_category_name)
    TextView mTvCategoryName;
    @BindView(R.id.rect_test_category)
    RecyclerView mRectCategory;
    @BindView(R.id.ll_test_more)
    LinearLayout mLlTestMore;


    public MyViewHolder(ViewGroup parent, Test test) {
        super(parent, R.layout.test_item_my_skin);
        ButterKnife.bind(this, itemView);
        mTest = test;
    }


    @Override
    public void setData(Skin data) {
//            如果要设置第二种产品清单的颜色为f3
        mTvCategoryName.setText(data.getCategory_name());
        mRectCategory.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        SkinListAdapter testListAdapter= new SkinListAdapter(getContext(), data.getData(),getAdapterPosition(),mTest,data.getCategory_name());
//        testListAdapter.addFooter(new RecyclerArrayAdapter.ItemView() {
//            @Override
//            public View onCreateView(ViewGroup parent) {
//                View footMoreSkin=View.inflate(getContext(), R.layout.include_foot_recommend_product, null);
//                return footMoreSkin;
//            }
//
//            @Override
//            public void onBindView(View headerView) {
//                headerView.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//
//                    }
//                });
//            }
//        });
        mRectCategory.setAdapter(testListAdapter);

//            mLlTestMore.setOnClickListener(v -> TestRecomendPresenter.star(getContext(),));
//        itemView.setOnClickListener(v -> {
//            if (mItemClickListener != null) {
//                mItemClickListener.onItemClick(getAdapterPosition());
//            }
//        });
    }
}