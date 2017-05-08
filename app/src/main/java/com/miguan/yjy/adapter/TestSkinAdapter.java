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

public class TestSkinAdapter extends RecyclerArrayAdapter<Skin> {


    public TestSkinAdapter(Context context, List<Skin> objects) {
        super(context, objects);
    }

    @Override
    public void OnBindViewHolder(BaseViewHolder holder, int position) {
        holder.setData(mObjects.get(position));
    }

    @Override
    public int getCount() {
        return mObjects.size()>=2?2:mObjects.size();
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(parent);
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
        }
    }

}