package com.miguan.yjy.adapter;

import android.content.Context;
import android.view.ViewGroup;

import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.miguan.yjy.adapter.viewholder.SearchReslutViewHolder;
import com.miguan.yjy.model.bean.Product;

import java.util.List;

/**
 * @作者 cjh
 * @日期 2017/4/12 15:40
 * @描述 成分表(产品解读)
 */

public class ComponentAdapter extends RecyclerArrayAdapter<Product> {
    public ComponentAdapter(Context context, List<Product> objects) {
        super(context, objects);
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new SearchReslutViewHolder(parent,false);
    }

    @Override
    public void OnBindViewHolder(BaseViewHolder holder, int position) {
        super.OnBindViewHolder(holder, position);
    }

}
