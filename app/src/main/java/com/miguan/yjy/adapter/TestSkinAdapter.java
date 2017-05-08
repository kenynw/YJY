package com.miguan.yjy.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.miguan.yjy.R;
import com.miguan.yjy.model.bean.Skin;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @作者 cjh
 * @日期 2017/4/21 11:35
 * @描述 推荐产品清单
 */

public class TestSkinAdapter extends RecyclerView.Adapter<TestSkinAdapter.MyViewHolder> {

    private Context mContext;

    private List<Skin> mSkinList;

    private RecyclerArrayAdapter.OnItemClickListener mItemClickListener;

    public TestSkinAdapter(Context context, List<Skin> skinList) {
        mContext = context;
        mSkinList = skinList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(parent);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.setData(mSkinList.get(position));
    }

    @Override
    public int getItemCount() {
        return mSkinList.size();
    }

    public void setOnItemClickListener(RecyclerArrayAdapter.OnItemClickListener onItemClickListener) {
        mItemClickListener = onItemClickListener;
    }

    class MyViewHolder extends BaseViewHolder<Skin> {

        @BindView(R.id.tv_test_category_name)
        TextView mTvCategoryName;
        @BindView(R.id.rect_test_category)
        RecyclerView mRectCategory;
        @BindView(R.id.ll_test_more)
        LinearLayout mLlTestMore;

        public MyViewHolder(ViewGroup parent) {
            super(parent, R.layout.test_item_my_skin);
            ButterKnife.bind(this, itemView);
        }


        @Override
        public void setData(Skin data) {
//            如果要设置第二种产品清单的颜色为f3
            mTvCategoryName.setText(data.getCategory_name());
            mRectCategory.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
            mRectCategory.setAdapter(new TestListAdapter(getContext(), data.getData()));
//            mLlTestMore.setOnClickListener(v -> TestRecomendPresenter.star(getContext(),));
            itemView.setOnClickListener(v -> {
                if (mItemClickListener != null) {
                    mItemClickListener.onItemClick(getAdapterPosition());
                }
            });
         }
    }

}