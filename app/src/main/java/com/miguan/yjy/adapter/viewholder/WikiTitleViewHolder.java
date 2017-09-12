package com.miguan.yjy.adapter.viewholder;

import android.view.ViewGroup;
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
public class WikiTitleViewHolder extends BaseViewHolder<Wiki> {

    @BindView(R.id.tv_wiki_name)
    TextView mTvWikiName;

    public WikiTitleViewHolder(ViewGroup parent) {
        super(parent, R.layout.item_list_wiki_title);
        ButterKnife.bind(this, itemView);
    }

    @Override
    public void setData(Wiki data) {
        String name = data.getQuestion();
        mTvWikiName.setText(name);
    }

}
