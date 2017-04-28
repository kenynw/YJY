package com.miguan.yjy.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.miguan.yjy.R;
import com.miguan.yjy.model.bean.ComponentTag;

import java.util.ArrayList;
import java.util.List;

/**
 * @作者 cjh
 * @日期 2017/3/21 16:03
 * @描述 成分
 */

public class ProductComponentAdapter extends BaseAdapter {

    private Context context;
    private List<ComponentTag> lists;
    private List<ComponentTag> mdatas;

    public ProductComponentAdapter(Context context, List<ComponentTag> lists) {
        this.context = context;
        this.lists = lists;
        mdatas = new ArrayList<>();
    }

    @Override
    public int getCount() {
        return lists.size();
    }

    @Override
    public Object getItem(int position) {
        return lists.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = View.inflate(context, R.layout.item_product_all_search, null);
        TextView textView = (TextView) convertView.findViewById(R.id.item_label_tv);
        textView.setText(lists.get(position).getName()+":"+lists.get(position).getId().size()+"种");
        textView.setOnClickListener(v -> {
            if (mSetOnTagClickListener != null) {
                mSetOnTagClickListener.itemClick(v,position);
            }
//            ProductReadPresenter.start(context,lists);
        });
        return convertView;
    }
    public void onlyAddAll(List<ComponentTag> datas) {
        mdatas.addAll(datas);
        notifyDataSetChanged();
    }


    public interface  SetOnTagClickListener{
        public void itemClick(View v,int position);
    }

    private SetOnTagClickListener mSetOnTagClickListener;

    public void setSetOnTagClickListener(SetOnTagClickListener setOnTagClickListener) {
        mSetOnTagClickListener = setOnTagClickListener;
    }
}
