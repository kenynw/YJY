package com.miguan.yjy.adapter;

import android.app.Activity;
import android.content.Context;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.miguan.yjy.R;
import com.miguan.yjy.model.bean.Product;
import com.miguan.yjy.model.local.SystemPreferences;
import com.miguan.yjy.module.product.SearchResultPresenter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @作者 cjh
 * @日期 2017/4/21 11:35
 * @描述 为你推荐（结果）
 */

public class ProductRecommentAdapter extends RecyclerArrayAdapter<Product> {

    private String keyword;

    public ProductRecommentAdapter(Context context, List<Product> objects, String keyword) {
        super(context, objects);
        this.keyword = keyword;
    }

    @Override
    public void OnBindViewHolder(BaseViewHolder holder, int position) {
        holder.setData(mObjects.get(position));
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        ProductRecommedViewholder recommedViewholder = new ProductRecommedViewholder(parent);
        recommedViewholder.setIsRecyclable(false);
        return recommedViewholder;
    }

    public class ProductRecommedViewholder extends BaseViewHolder<Product> {
        @BindView(R.id.tv_product_think)
        TextView tvProductThink;

        public ProductRecommedViewholder(ViewGroup parent) {
            super(parent, R.layout.item_product_recommend);
            ButterKnife.bind(this, itemView);
        }

        @Override
        public void setData(Product data) {
            super.setData(data);
            if (data.getName().contains(keyword)) {
                //为文本框设置多种颜色
                SpannableStringBuilder style = new SpannableStringBuilder(data.getName());
                style.setSpan(new ForegroundColorSpan(getContext().getResources().getColor(R.color.colorPrimary)), data.getName().indexOf(keyword), data.getName().indexOf(keyword) + keyword.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                tvProductThink.setText(style);
            } else {
                tvProductThink.setText(data.getName());
            }
            tvProductThink.setOnClickListener(v -> gotoSearchResult(data.getName()));
        }

        public void gotoSearchResult(String name) {
            SearchResultPresenter.start(getContext(), name, 0, "");
            String oldName = SystemPreferences.getSearchName();
            if (!TextUtils.isEmpty(oldName)) {
                if (!oldName.contains(name))
                    SystemPreferences.setSearchName(oldName + name + ",");
            } else {
                SystemPreferences.setSearchName(name + ",");
            }
            ((Activity) getContext()).finish();
        }


    }


}