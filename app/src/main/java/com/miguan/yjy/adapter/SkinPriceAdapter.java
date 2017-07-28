package com.miguan.yjy.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.miguan.yjy.R;
import com.miguan.yjy.model.bean.SelectPrice;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @作者 cjh
 * @日期 2017/6/22 18:08
 * @描述 肤质测试价格
 */

public class SkinPriceAdapter extends BaseAdapter {
    private Context context;
    private List<SelectPrice> datas;
    private List<SelectPrice> mdatas=new ArrayList<>();

    public SkinPriceAdapter() {

    }
    public SkinPriceAdapter(Context context, List<SelectPrice> datas) {
        this.context = context;
        this.datas = datas;
        mdatas = new ArrayList<>();
    }

    @Override
    public int getCount() {
        return mdatas.size();
    }

    @Override
    public Object getItem(int position) {
        return mdatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = View.inflate(context, R.layout.item_product_all_search, null);
        TextView textView = (TextView) convertView.findViewById(R.id.item_label_tv);
        textView.setBackgroundResource(R.drawable.bg_radius_white);


        if (mdatas.size() != 0) {

            if (position == 0) {
                textView.setText("不限");
            } else {
              BigDecimal maxValue=  new BigDecimal(mdatas.get(position).getMax());
              BigDecimal minValue=  new BigDecimal(mdatas.get(position).getMin());
                if (mdatas.get(position).getMin() == 0) {//说明有最大值 100以下 最小值为0
                    textView.setText( maxValue+ "以下");
                } else if (mdatas.get(position).getMax() == 0) {
                    textView.setText(minValue + "以上");
                } else {
                    textView.setText(minValue+ "-" +maxValue);
                }
            }

            if (mdatas.get(position).isSelect()) {
                textView.setTextColor(context.getResources().getColor(R.color.textPrimary));
            } else {
                textView.setTextColor(context.getResources().getColor(R.color.gray));
            }
//        textView.setTextColor(context.getResources().getColor(R.color.tv_skin_price_selector));
            textView.setOnClickListener(v -> {
                if (mSetOnTagClickListener != null) {
                    for (int i = 0; i < mdatas.size(); i++) {
                        mdatas.get(i).setSelect(false);
                        mdatas.get(position).setSelect(true);
                    }
                    mSetOnTagClickListener.itemClick(v, position, mdatas.get(position).getMin(), mdatas.get(position).getMax(),mdatas);
                }
                notifyDataSetChanged();
            });

        }

        return convertView;
    }

    public void onlyAddAll(List<SelectPrice> datas) {
        mdatas.clear();
        mdatas.addAll(datas);
        notifyDataSetChanged();
    }

    public interface SetOnTagClickListener {
        public void itemClick(View v, int position, float min, float max,List<SelectPrice>list);
    }

    private SetOnTagClickListener mSetOnTagClickListener;

    public void setSetOnTagClickListener(SetOnTagClickListener setOnTagClickListener) {
        mSetOnTagClickListener = setOnTagClickListener;
    }
}
