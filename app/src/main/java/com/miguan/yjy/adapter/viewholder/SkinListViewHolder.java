package com.miguan.yjy.adapter.viewholder;

import android.view.ViewGroup;
import android.widget.TextView;

import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.miguan.yjy.R;
import com.miguan.yjy.model.bean.Skin;
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

    public SkinListViewHolder(ViewGroup parent) {
        super(parent, R.layout.skin_list);
        ButterKnife.bind(this, itemView);
    }

    @Override
    public void setData(Skin data) {
        super.setData(data);
        mTvSkinTestName.setText(data.skinDesc[getDataPosition()]);
        mTvSkinDec.setText(data.getUnscramble());
        mProgressSkin.setMaxCount(100f);
        mProgressSkin.setCurrentCount(((data.getScore() / data.getMaximum()) * 100));
        mProgressSkin.setGetLocationOnScreenXy(new SpringProgressView.GetLocationOnScreenXy() {
            @Override
            public void getXy(int x, int y) {

                int skinWith = mTvSkinValute.getMeasuredWidth();
                int tW = skinWith / 2;

                ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) mTvSkinValute.getLayoutParams();
                params.leftMargin = x - tW;
                mTvSkinValute.setLayoutParams(params);
                mTvSkinLow.setText(data.leftSkin[getDataPosition()]);
                mTvSkinHigh.setText(data.rightSkin[getDataPosition()]);
                mTvSkinValute.setText(data.getName() + ":" + (int) data.getScore() + "分");
            }
        });
    }


}
