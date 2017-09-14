package com.miguan.yjy.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.miguan.yjy.R;
import com.miguan.yjy.model.bean.Effect;

import java.util.List;

/**
 * @作者 cjh
 * @日期 2017/8/28 11:19
 * @描述
 */
public class EffectSortAdapter extends BaseAdapter {

    private Context context;
    private List<Effect> lists;


    public EffectSortAdapter(Context context, List<Effect> lists) {
        this.context = context;
        this.lists = lists;
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
        textView.setBackgroundResource(R.drawable.shape_gray_f5);
        textView.setText(lists.get(position).getEffect_name());
        textView.setOnClickListener(v -> {
            if (mSetOnTagClickListener != null) {
                mSetOnTagClickListener.itemClick(v,position);
            }
//            ProductReadPresenter.start(context,lists);
        });
        return convertView;
    }
    public void onlyAddAll(List<Effect> datas) {
        lists = datas;
        notifyDataSetChanged();
    }


    public interface  SetOnTagClickListener{
        public void itemClick(View v, int position);
    }

    private SetOnTagClickListener mSetOnTagClickListener;

    public void setSetOnTagClickListener(SetOnTagClickListener setOnTagClickListener) {
        mSetOnTagClickListener = setOnTagClickListener;
    }
}





