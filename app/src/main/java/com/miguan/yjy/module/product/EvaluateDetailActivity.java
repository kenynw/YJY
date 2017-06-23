package com.miguan.yjy.module.product;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.dsk.chain.bijection.RequiresPresenter;
import com.dsk.chain.expansion.data.BaseDataActivity;
import com.facebook.drawee.view.SimpleDraweeView;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.miguan.yjy.R;
import com.miguan.yjy.model.bean.Evaluate;
import com.miguan.yjy.module.article.EvaluatePanel;
import com.miguan.yjy.module.common.LargeImageActivity;
import com.miguan.yjy.utils.LUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Copyright (c) 2017/6/16. LiaoPeiKun Inc. All rights reserved.
 */
@RequiresPresenter(EvaluateDetailPresenter.class)
public class EvaluateDetailActivity extends BaseDataActivity<EvaluateDetailPresenter, Evaluate>
        implements RecyclerArrayAdapter.OnItemClickListener {

    private EvaluatePanel mEvaluatePanel;

    private Evaluate mEvaluate;

    @BindView(R.id.dv_evaluate_thumb)
    SimpleDraweeView mDvThumb;

    @BindView(R.id.tv_evaluate_reply)
    TextView mTvReply;

    @BindView(R.id.tv_evaluate_content)
    TextView mTvEvaluateContent;

    @BindView(R.id.recy_evaluate_detail)
    RecyclerView mRecyDetail;

    @BindView(R.id.et_evaluate_add)
    EditText mEtAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_activity_evaluate_detail);
        setToolbarTitle(R.string.text_evaluate_detail);
        ButterKnife.bind(this);
        mEvaluatePanel = new EvaluatePanel(this);

        mRecyDetail.setLayoutManager(new LinearLayoutManager(this));
        mRecyDetail.setAdapter(getPresenter().getAdapter());

        mEtAdd.setOnKeyListener((view, i, keyEvent) -> {
            if (keyEvent.getAction() == KeyEvent.ACTION_DOWN) {
                if (keyEvent.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
                    if (mEtAdd.getText().length() > 0) {
                        String content = mEtAdd.getText().toString();
                        if (mEvaluate != null) {
                            content = content.replace("@" + mEvaluate.getUser().getUsername() + "：", "");
                        }
                        getPresenter().addReply(content);
                        return true;
                    }
                }
                if (keyEvent.getKeyCode() == KeyEvent.KEYCODE_DEL && mEvaluate != null) {
                    if (mEtAdd.getText().toString().equals("@" + mEvaluate.getUser().getUsername() + "：")) {
                        clearInput();
                        return true;
                    }
                }
            }
            return false;
        });
    }

    @Override
    public void setData(Evaluate evaluate) {
        mEvaluatePanel.setEvaluate(evaluate);
        mTvEvaluateContent.setText(evaluate.getComment());
        mTvReply.setVisibility(View.GONE);

        if (TextUtils.isEmpty(evaluate.getAttachment())) {
            mDvThumb.setVisibility(View.GONE);
        } else {
            mDvThumb.setVisibility(View.VISIBLE);
            mDvThumb.setImageURI(Uri.parse(evaluate.getAttachment()));
            mDvThumb.setOnClickListener(v -> LargeImageActivity.start(this, evaluate.getAttachment()));
        }
    }

    @Override
    public void onItemClick(int position) {
        mEvaluate = getPresenter().getItem(position);
        if (mEvaluate != null) {
            String replyUser = "@" + mEvaluate.getUser().getUsername() + "：";
            mEtAdd.setText(replyUser);
            mEtAdd.setSelection(replyUser.length());
            mEtAdd.requestFocus();
            LUtils.openKeyboard(mEtAdd);
        }
    }

    public void clearInput() {
        mEvaluate = null;
        mEtAdd.setText("");
        mEtAdd.setHint(R.string.hint_add_comment);
    }

    public Evaluate getEvaluate() {
        return mEvaluate;
    }

}
