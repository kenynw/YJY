package com.miguan.yjy.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.ViewGroup;
import android.widget.ImageView;
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
        @BindView(R.id.iv_product_component_active)
        ImageView mIvComponentActive;
        @BindView(R.id.iv_product_component_pox)
        ImageView mIvComponentPox;
        @BindView(R.id.tv_product_component_goal)
        TextView mTvComponentGoal;
        @BindView(R.id.ll_product_component)
        LinearLayout llProductComponent;

        public ProductReadViewHolder(ViewGroup parent) {
            super(parent, R.layout.item_product_read);
            ButterKnife.bind(this, itemView);
        }

        @Override
        public void setData(Component data) {
            super.setData(data);
            mTvComponentName.setText(data.getName());
//            0-2 绿色
//            3-6 橙色
//            7-9 红色

            String grade = data.getRisk_grade();
            if (!TextUtils.isEmpty(grade)) {
                mTvComponentSafe.setText(grade);
                if (grade.contains("0") || grade.contains("1") || grade.contains("2")) {
                    mTvComponentSafe.setBackgroundResource(R.drawable.bg_shape_product_f8b);
                }
                if (grade.contains("3") || grade.contains("4") || grade.contains("5") || grade.contains("6")) {
                    mTvComponentSafe.setBackgroundResource(R.drawable.bg_shape_product_fb74);
                }
                if (grade.contains("7") || grade.contains("8") || grade.contains("9")) {
                    mTvComponentSafe.setBackgroundResource(R.drawable.bg_shape_product_ffc3);
                }
            }

            if (data.getIs_active() == 1) {
                mIvComponentActive.setImageDrawable(getContext().getResources().getDrawable(R.mipmap.bg_product_active));
            }
            if (data.getIs_pox() == 1) {
                mIvComponentPox.setImageDrawable(getContext().getResources().getDrawable(R.mipmap.bg_product_pox));
            }

            mTvComponentGoal.setText(data.getComponent_action());

            if (getItemViewType() == 1) {
                llProductComponent.setBackgroundColor(getContext().getResources().getColor(R.color.f5));
            } else {
                llProductComponent.setBackgroundColor(getContext().getResources().getColor(R.color.white));
            }
        }
    }


}
