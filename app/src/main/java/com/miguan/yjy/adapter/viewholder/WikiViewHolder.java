package com.miguan.yjy.adapter.viewholder;

import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.miguan.yjy.R;
import com.miguan.yjy.model.bean.Wiki;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * @作者 cjh
 * @日期 2017/7/25 9:41
 * @描述
 */

public class WikiViewHolder extends BaseViewHolder<Wiki.RelationInfo> {

    @BindView(R.id.view_wiki_line)
    View mViewWikiLine;
    private int type;

    @BindView(R.id.ll_wiki_bg)
    LinearLayout mLlWikiBg;

    @BindView(R.id.tv_wiki_name)
    TextView mTvWikiName;

    public WikiViewHolder(ViewGroup parent, int type) {
        super(parent, R.layout.item_wiki);
        ButterKnife.bind(this, itemView);
        this.type = type;
    }

    @Override
    public void setData(Wiki.RelationInfo data) {
        super.setData(data);
        if (type == 1) {
            mLlWikiBg.setBackgroundResource(R.color.bgWindow);
            mViewWikiLine.setBackgroundResource(R.color.fed);
        } else {
            mLlWikiBg.setBackgroundResource(R.color.white);
            mViewWikiLine.setBackgroundResource(R.color.f5);
        }
        String name = data.getQuestion();//+ " <b><tt><font color='#ff0000'>·NEW</font></tt></b>"}}
        mTvWikiName.setText(name);
    }


}
