package com.miguan.yjy.module.product;

import android.os.CountDownTimer;
import android.text.Html;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.miguan.yjy.R;
import com.miguan.yjy.model.bean.Benefit;
import com.miguan.yjy.module.article.ArticleDetailPresenter;
import com.miguan.yjy.module.common.WebViewActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Copyright (c) 2017/8/28. LiaoPeiKun Inc. All rights reserved.
 */

public class BenefitViewHolder extends BaseViewHolder<Benefit> {

    @BindView(R.id.dv_benefit_thumb)
    SimpleDraweeView mDvThumb;

    @BindView(R.id.tv_benefit_sum)
    TextView mTvSum;

    @BindView(R.id.tv_benefit_countdown)
    TextView mTvCountdown;

    @BindView(R.id.iv_benefit_ended)
    ImageView mIvEnded;

    private CountDownTimer mCountDownTimer;

    public BenefitViewHolder(ViewGroup parent) {
        super(parent, R.layout.item_list_benefit);
        ButterKnife.bind(this, itemView);
    }

    @Override
    public void setData(Benefit data) {
        mDvThumb.setImageURI(data.getPicture());

        String sum = String.format(getContext().getString(R.string.label_benefit_sum), data.getPrize_num());
        mTvSum.setText(Html.fromHtml(sum));

        final int status;
        if (data.getEndtime() < data.getCurrent_time()) {
            status = 0;
            mTvCountdown.setText("活动已结束");
            mIvEnded.setVisibility(View.VISIBLE);
            if (mCountDownTimer != null) mCountDownTimer.cancel();
        } else {
            mIvEnded.setVisibility(View.GONE);
            long del;
            final String timeStr;
            if (data.getStarttime() > data.getCurrent_time()) {
                del = data.getStarttime() - data.getCurrent_time();
                timeStr = "距离开始：";
                status = 1;
            } else {
                del = data.getEndtime() - data.getCurrent_time();
                timeStr = "距离结束：";
                status = 2;
            }
            mTvCountdown.setText(Html.fromHtml(timeStr + "<font color=\"#FF7069\">" + getFormatDate(del) + "</font>"));
            if (!timeStr.equals("天") && del > 0) {
                if (mCountDownTimer != null) mCountDownTimer.cancel();
                mCountDownTimer = new CountDownTimer(del * 1000, 1000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                        mTvCountdown.setText(Html.fromHtml(timeStr + "<font color=\"#FF7069\">" + getFormatDate(millisUntilFinished / 1000) + "</font>"));
                    }

                    @Override
                    public void onFinish() {

                    }
                }.start();
            } else if (mCountDownTimer != null) {
                mCountDownTimer.cancel();
            }
        }
        itemView.setOnClickListener(v -> {
            if (status == 2) {
                if (data.getType() == 0) {
                    WebViewActivity.start(getContext(), "", data.getRelation());
                } else if (data.getType() == 1) {
                    ProductDetailPresenter.start(getContext(), Integer.valueOf(data.getRelation()));
                } else if (data.getType() == 2) {
                    ArticleDetailPresenter.start(getContext(), Integer.valueOf(data.getRelation()));
                }
            }
        });
    }

    private String getFormatDate(long time) {
        long days = (time / (3600 * 24));
        long hours = ((time % (3600 * 24)) / 3600);
        String result;
        if (days > 0) {
            result = days + "天" + hours + "个小时";
        } else {
            long minutes = ((time % 3600) / 60);
            long seconds = time % 60;
            if (hours > 0) {
                result = String.format("%1$02d:%2$02d:%3$02d", hours, minutes, seconds);
            } else {
                result = String.format("%1$02d:%2$02d", minutes, seconds);
            }
        }
        return result;
    }

}
