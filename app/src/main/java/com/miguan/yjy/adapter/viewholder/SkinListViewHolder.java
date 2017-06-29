package com.miguan.yjy.adapter.viewholder;

import android.os.Build;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.TextView;

import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.miguan.yjy.R;
import com.miguan.yjy.model.bean.Skin;
import com.miguan.yjy.utils.LUtils;
import com.miguan.yjy.widget.SpringProgressView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @作者 cjh
 * @日期 2017/6/16 18:51
 * @描述
 */

public class SkinListViewHolder extends BaseViewHolder<Skin> {

    @BindView(R.id.tv_skin_test_name)
    TextView mTvSkinTestName;
    @BindView(R.id.tv_skin_valute)
    TextView mTvSkinValute;
    @BindView(R.id.tv_skin_low)
    TextView mTvSkinLow;
    @BindView(R.id.progress_skin)
    SpringProgressView mProgressSkin;
    @BindView(R.id.tv_skin_high)
    TextView mTvSkinHigh;
    @BindView(R.id.tv_skin_dec)
    TextView mTvSkinDec;

    int skinWith;

    public SkinListViewHolder(ViewGroup parent) {
        super(parent, R.layout.skin_list);
        ButterKnife.bind(this, itemView);
    }

    @Override
    public void setData(Skin data) {
        super.setData(data);

        mTvSkinLow.setText(data.leftSkin[getDataPosition()]);
        mTvSkinHigh.setText(data.rightSkin[getDataPosition()]);
        mTvSkinValute.setText(data.getName()+":"+(int)data.getScore()+"分");
        mTvSkinTestName.setText(data.skinDesc[getDataPosition()]);
        mTvSkinDec.setText(data.getUnscramble());
        mProgressSkin.setMaxCount(100f);
        mProgressSkin.setCurrentCount(((data.getScore() / data.getMaximum()) * 100));
        Log.e("setCurrentCount", ((data.getScore() / 44f) * 100) + "-=======");

        mTvSkinValute.post(new Runnable() {
            @Override
            public void run() {
                skinWith = mTvSkinValute.getWidth();
                int tW =skinWith / 2;
                int left = mTvSkinLow.getWidth();
                int right =mTvSkinHigh.getWidth();
                Log.e("left ", left + "=======");
                Log.e("right ", right + "=======");
                Log.e("tW ", tW + "=======");
                int w = LUtils.getScreenWidth() - left - right;
                Log.e("w=====", "" + w);

                ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) mTvSkinValute.getLayoutParams();
                float pro = mProgressSkin.getCurrentCount();

                params.leftMargin = (int) (pro * w / 100) + left - tW;
                Log.e("leftMargin=====", "" + params.leftMargin);
                mTvSkinValute.setLayoutParams(params);
                Log.e("skinWith", skinWith + "==-----");
            }
        });

//
//        final ViewTreeObserver observer = mTvSkinValute.getViewTreeObserver();
//        observer.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
//            @Override
//            public void onGlobalLayout() {
//                //此处不能写 observer.removeOnGlobalLayoutListener(this); 否则会报错
//                removeOnGlobalLayoutListener(mTvSkinValute,this);
//
//                skinWith = mTvSkinValute.getMeasuredWidth();
//                int tW =skinWith / 2;
//                int left = mTvSkinLow.getWidth();
//                int right =mTvSkinHigh.getWidth();
//                Log.e("left ", left + "=======");
//                Log.e("right ", right + "=======");
//                Log.e("tW ", tW + "=======");
//                int w = LUtils.getScreenWidth() - left - right;
//                Log.e("w=====", "" + w);
//
//                ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) mTvSkinValute.getLayoutParams();
//                float pro = mProgressSkin.getCurrentCount();
//
//                params.leftMargin = (int) (pro * w / 100) + left - tW;
//                Log.e("leftMargin=====", "" + params.leftMargin);
//                mTvSkinValute.setLayoutParams(params);
//                Log.e("skinWith", skinWith + "==-----");
//            }
//
//        });
    }

    public static void removeOnGlobalLayoutListener(View v, ViewTreeObserver.OnGlobalLayoutListener listener){
        if (Build.VERSION.SDK_INT < 16) {
            v.getViewTreeObserver().removeGlobalOnLayoutListener(listener);
        } else {
            v.getViewTreeObserver().removeOnGlobalLayoutListener(listener);
        }
    }


}
