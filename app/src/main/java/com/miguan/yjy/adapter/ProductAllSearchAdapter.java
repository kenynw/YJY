package com.miguan.yjy.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.miguan.yjy.R;
import com.miguan.yjy.model.bean.Product;
import com.miguan.yjy.module.product.SearchResultActivity;

import java.util.List;

/**
 * @作者 cjh
 * @日期 2017/3/21 16:03
 * @描述 大家都在搜
 */

public class ProductAllSearchAdapter extends BaseAdapter {
    private Context context;
    private List<Product> datas;

    public ProductAllSearchAdapter(Context context, List<Product> datas) {
        this.context = context;
        this.datas = datas;
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
            Intent intent = new Intent(context, SearchResultActivity.class);
            intent.putExtra("keywords", datas.get(position).getName());
            context.startActivity(intent);
        });
        return convertView;
    }
    public void onlyAddAll(List<Product> datas) {
        datas.addAll(datas);
        notifyDataSetChanged();
    }
}
