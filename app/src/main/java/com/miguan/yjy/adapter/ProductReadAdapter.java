package com.miguan.yjy.adapter;

import android.content.Context;
import android.view.ViewGroup;

import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.miguan.yjy.adapter.viewholder.ProductReadViewHolder;
import com.miguan.yjy.model.bean.Component;

import java.util.List;

/**
 * @作者 cjh
 * @日期 2017/3/29 17:55
 * @描述
 */

public class ProductReadAdapter extends RecyclerArrayAdapter<Component> {

    private int type = 1;

    Context mContext;

    public ProductReadAdapter(Context context, List<Component> datas) {
        super(context, datas);
        mContext = context;
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

}
