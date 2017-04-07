package com.miguan.yjy.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.miguan.yjy.R;
import com.miguan.yjy.model.bean.Test;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @作者 cjh
 * @日期 2017/4/6 11:50
 * @描述
 */

public class TestProductNameAdapter extends RecyclerArrayAdapter<Test> {


    public TestProductNameAdapter(Context context, List<Test> objects) {
        super(context, objects);
    }

    @Override
    public void OnBindViewHolder(BaseViewHolder holder, int position) {
        holder.setData(mObjects.get(position));
    }


    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(parent);
    }

    class MyViewHolder extends BaseViewHolder<Test> {

        @BindView(R.id.tv_test_recommend_name)
        TextView mTvTestRecommendName;

        public MyViewHolder(ViewGroup parent) {
            super(parent, R.layout.test_item_recommend_product);
            ButterKnife.bind(this, itemView);
        }

        @Override
        public void setData(Test data) {
            super.setData(data);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                }
            });
        }
    }

    private void changeColor() {

    }


}
