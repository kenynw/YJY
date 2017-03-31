package com.miguan.yjy.adapter;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.miguan.yjy.R;
import com.miguan.yjy.model.bean.Component;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @作者 cjh
 * @日期 2017/3/29 17:55
 * @描述
 */

public class ProductReadAdapter extends RecyclerArrayAdapter<Component> {

    private int type = 1;

    public ProductReadAdapter(Context context, List<Component> datas) {
        super(context, datas);
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {

        return new ProductReadViewHolder(parent);
    }

    @Override
    public void OnBindViewHolder(BaseViewHolder holder, int position) {
        holder.setData(mObjects.get(position));
    }

    @Override
    public int getViewType(int position) {
        if (position % 2 != 0) {
            type = 1;
        } else {
            type = 2;
        }
        return type;
    }

    class ProductReadViewHolder extends BaseViewHolder<Component> {

        @BindView(R.id.tv_product_component_name)
        TextView mTvComponentName;
        @BindView(R.id.tv_product_component_safe)
        TextView mTvComponentSafe;
        @BindView(R.id.tv_product_component_active)
        TextView mTvComponentActive;
        @BindView(R.id.tv_product_component_pox)
        TextView mTvComponentPox;
        @BindView(R.id.tv_product_component_goal)
        TextView mTvComponentGoal;
        @BindView(R.id.ll_product_component)
        LinearLayout llProductComponent;

        public ProductReadViewHolder(ViewGroup parent) {
            super(parent, R.layout.include_component);
            ButterKnife.bind(this, itemView);
        }

        @Override
        public void setData(Component data) {
            super.setData(data);
            mTvComponentName.setText(data.getName());
            mTvComponentSafe.setText(data.getName());
            mTvComponentActive.setText(data.getName());
            mTvComponentPox.setText(data.getName());
            mTvComponentGoal.setText(data.getName());




            if (getItemViewType() == 1) {
                llProductComponent.setBackgroundColor(getContext().getResources().getColor(R.color.f5));
            } else {
                llProductComponent.setBackgroundColor(getContext().getResources().getColor(R.color.white));
            }
        }
    }


}
