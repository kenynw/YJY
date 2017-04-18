package com.miguan.yjy.adapter;

import android.content.Context;
import android.view.ViewGroup;

import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.miguan.yjy.adapter.viewholder.EvaluateViewHolder;
import com.miguan.yjy.model.bean.Evaluate;

import java.util.List;

/**
 * @作者 cjh
 * @日期 2017/4/12 15:40
 * @描述 文章(产品)评论列表
 */

public class EvaluateAdapter extends RecyclerArrayAdapter<Evaluate> {
    public EvaluateAdapter(Context context, List<Evaluate> objects) {
        super(context, objects);
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new EvaluateViewHolder(parent);
    }

    @Override
    public void OnBindViewHolder(BaseViewHolder holder, int position) {
        super.OnBindViewHolder(holder, position);
    }



}
