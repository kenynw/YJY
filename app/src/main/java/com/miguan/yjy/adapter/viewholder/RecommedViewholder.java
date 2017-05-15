package com.miguan.yjy.adapter.viewholder;

import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.miguan.yjy.R;
import com.miguan.yjy.model.bean.Product;
import com.miguan.yjy.model.local.SystemPreferences;
import com.miguan.yjy.module.product.SearchResultPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @作者 cjh
 * @日期 2017/3/21 12:00
 * @描述 为你推荐
 */
public class RecommedViewholder extends BaseViewHolder<Product> {
    @BindView(R.id.tv_product_think)
    TextView tvProductThink;
    String keyword;

    public RecommedViewholder(ViewGroup parent, String keyword) {
        super(parent, R.layout.item_product_recommend);
        ButterKnife.bind(this, itemView);
        this.keyword = keyword;

    }

    @Override
    public void setData(Product data) {
        super.setData(data);
        if (data.getName().contains(keyword)) {
            //为文本框设置多种颜色
            SpannableStringBuilder style = new SpannableStringBuilder(data.getName());
            style.setSpan(new ForegroundColorSpan(  getContext().getResources().getColor(R.color.colorPrimary)), data.getName().indexOf(keyword), data.getName().indexOf(keyword) + keyword.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            tvProductThink.setText(style);
        } else {
            tvProductThink.setText(data.getName());
        }
//
        tvProductThink.setOnClickListener(v -> gotoSearchResult(data.getName()));
    }

    public void gotoSearchResult(String name) {
        SearchResultPresenter.start(getContext(), name, 0, "");
        String oldName = SystemPreferences.getSearchName();
        if (!TextUtils.isEmpty(oldName)) {
            if (!oldName.contains(name))
                SystemPreferences.setSearchName(name + "," + oldName);
        } else {
            SystemPreferences.setSearchName(name + ",");
        }
    }


}
