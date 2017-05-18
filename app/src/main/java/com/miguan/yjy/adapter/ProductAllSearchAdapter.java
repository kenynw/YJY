package com.miguan.yjy.adapter;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.miguan.yjy.R;
import com.miguan.yjy.model.bean.Product;
import com.miguan.yjy.model.local.SystemPreferences;
import com.miguan.yjy.module.product.SearchResultPresenter;

import java.util.ArrayList;
import java.util.List;

/**
 * @作者 cjh
 * @日期 2017/3/21 16:03
 * @描述 大家都在搜
 */

public class ProductAllSearchAdapter extends BaseAdapter {
    private Context context;
    private List<Product> datas;
    private List<Product> mdatas;

    public ProductAllSearchAdapter(Context context, List<Product> datas) {
        this.context = context;
        this.datas = datas;
        mdatas = new ArrayList<>();
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public Object getItem(int position) {
        return datas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = View.inflate(context, R.layout.item_product_all_search, null);
        TextView textView = (TextView) convertView.findViewById(R.id.item_label_tv);
        textView.setText(datas.get(position).getName());
        textView.setOnClickListener(v -> {
            SearchResultPresenter.start(context,datas.get(position).getName(),0,"");
            String oldName = SystemPreferences.getSearchName();
            if (!TextUtils.isEmpty(oldName)) {
                if (!oldName.contains(datas.get(position).getName()))
                    SystemPreferences.setSearchName(datas.get(position).getName() + ","+oldName);
            } else {
                SystemPreferences.setSearchName(datas.get(position).getName() + ",");
            }
            ((Activity)context).finish();
        });


        return convertView;
    }
    public void onlyAddAll(List<Product> datas) {
        mdatas.addAll(datas);
        notifyDataSetChanged();
    }
}
