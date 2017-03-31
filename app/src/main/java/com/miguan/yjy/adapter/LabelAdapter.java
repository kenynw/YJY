package com.miguan.yjy.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.miguan.yjy.R;

import java.util.ArrayList;
import java.util.List;

/**
 * @作者 cjh
 * @日期 2016/11/14 15:12
 * @描述 标签适配器
 */

public class LabelAdapter<T> extends BaseAdapter {

    private final Context mContext;
    private final List<T> mDataList;
    private int type;
    public MyLabelOnItemClick myLabelOnItemClick;

    public LabelAdapter(Context context, int type) {
        this.mContext = context;
        this.type = type;
        mDataList = new ArrayList<T>();

    }

    @Override
    public int getCount() {
        return mDataList == null ? 0 : mDataList.size();
    }

    @Override
    public Object getItem(int position) {
        return mDataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.item_product_all_search, null);
        TextView textView = (TextView) convertView.findViewById(R.id.item_label_tv);
        switch (type) {//标签的各种颜色 1 2 3
//            case 1://
//                break;
//            case 2://
//
//                textView.setTextColor(mContext.getResources().getColor(R.color.default_theme));
//                textView.setBackgroundResource(R.drawable.label);
//                break;
//            case 3://
//                textView.setTextColor(mContext.getResources().getColor(R.color.fae));
//                textView.setBackgroundResource(R.drawable.label_recommed);
//                break;
            default:
                break;
        }
        T groupTag = mDataList.get(position);
//        textView.setText(groupTag.name);
        textView.setTag(position);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (myLabelOnItemClick != null) {
                    myLabelOnItemClick.labelClick(v, (Integer) v.getTag());
                }
            }
        });
        return view;
    }

    public void onlyAddAll(List<T> datas) {
        mDataList.addAll(datas);
        notifyDataSetChanged();
    }

    public void clearAndAddAll(List<T> datas) {
        mDataList.clear();
        onlyAddAll(datas);
    }

    public List<T> getmDataList() {
        return mDataList;
    }

    public interface MyLabelOnItemClick {
        public void labelClick(View v, int position);
    }

    public void setMyLabelOnItemClick(MyLabelOnItemClick myLabelOnItemClick) {
        this.myLabelOnItemClick = myLabelOnItemClick;
    }
}
