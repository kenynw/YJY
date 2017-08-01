package com.miguan.yjy.adapter.viewholder;

import android.text.Html;
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

public class WikiViewHolder extends BaseViewHolder<Wiki.RelationInfo> {


    @BindView(R.id.tv_wiki_name)
    TextView mTvWikiName;

    public WikiViewHolder(ViewGroup parent) {
        super(parent, R.layout.item_wiki);
        ButterKnife.bind(this, itemView);
    }

    @Override
    public void setData(Wiki.RelationInfo data) {
        super.setData(data);

        String name = data.getQuestion() + " <b><tt><font color='#ff0000'>·NEW</font></tt></b>";
        mTvWikiName.setText(Html.fromHtml(name));
    }


}
