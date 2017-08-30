package com.miguan.yjy.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.miguan.yjy.R;
import com.miguan.yjy.model.bean.Skin;

import java.util.List;

/**
 * @作者 cjh
 * @日期 2017/6/19 15:29
 * @描述
 */

public class MySkinAdapter extends BaseAdapter {

    private final String[] DEFAULT_NAME = new String[] {"干/油", "敏感度", "色素性", "皱纹性"};

    private Context mContext;

    private List<Skin> mSkinList;

    private boolean mShowDefault;

    public MySkinAdapter(Context context, List<Skin> objects) {
        this(context, objects, false);
    }

    public MySkinAdapter(Context context, List<Skin> skinList, boolean showDefault) {
        mContext = context;
        mSkinList = skinList;
        mShowDefault = showDefault;
    }

    @Override
    public int getCount() {
        return mShowDefault ? 4 : mSkinList.size();
    }

    @Override
    public Object getItem(int position) {
        if (mShowDefault && position >= mSkinList.size()) {
            Skin skin = new Skin();
            skin.setName(DEFAULT_NAME[position]);
            skin.setLetter("?");
            return skin;
        } else {
            return mSkinList.get(position);
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MySkinViewHolder mySkinViewHolder;
        if (convertView == null) {
            mySkinViewHolder = new MySkinViewHolder();
            convertView = View.inflate(mContext, R.layout.item_my_skin, null);
            mySkinViewHolder.mTvSkinLetter = convertView.findViewById(R.id.tv_skin_letter);
            mySkinViewHolder.mTvSkinName = convertView.findViewById(R.id.tv_skin_name);
            convertView.setTag(mySkinViewHolder);
        } else {
            mySkinViewHolder = (MySkinViewHolder) convertView.getTag();
        }
        Skin skin = (Skin) getItem(position);
        mySkinViewHolder.mTvSkinLetter.setText(skin.getLetter());
        mySkinViewHolder.mTvSkinName.setText(skin.getName());
        return convertView;
    }

    private class MySkinViewHolder {
        TextView mTvSkinLetter;
        TextView mTvSkinName;
    }

}


//public class MySkinAdapter extends RecyclerArrayAdapter<Skin> {
//    public MySkinAdapter(Context context, List<Skin> objects) {
//        super(context, objects);
//    }
//
//    @Override
//    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
//        return new MySkinViewHolder(parent);
//    }
//
//    @Override
//    public void OnBindViewHolder(BaseViewHolder holder, int position) {
//        holder.setData(mObjects.get(position));
//    }
//
//    class MySkinViewHolder extends BaseViewHolder<Skin> {
//        @BindView(R.id.tv_skin_letter)
//        TextView mTvSkinLetter;
//        @BindView(R.id.tv_skin_name)
//        TextView mTvSkinName;
//
//        public MySkinViewHolder(ViewGroup parent) {
//            super(parent, R.layout.item_my_skin);
//            ButterKnife.bind(this, itemView);
//        }
//
//        @Override
//        public void setData(Skin data) {
//            super.setData(data);
//            mTvSkinLetter.setText(data.getLetter());
//            mTvSkinName.setText(data.getName());
//        }
//    }
//
//}
