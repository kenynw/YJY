package com.miguan.yjy.module.article;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.Html;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.dsk.chain.bijection.RequiresPresenter;
import com.dsk.chain.expansion.data.BaseDataActivity;
import com.facebook.drawee.view.SimpleDraweeView;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.jude.easyrecyclerview.decoration.DividerDecoration;
import com.miguan.yjy.R;
import com.miguan.yjy.model.bean.Evaluate;
import com.miguan.yjy.module.common.LargeImageActivity;
import com.miguan.yjy.module.product.ProductDetailActivity;
import com.miguan.yjy.module.product.ProductDetailPresenter;
import com.miguan.yjy.utils.LUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Copyright (c) 2017/6/16. LiaoPeiKun Inc. All rights reserved.
 */
@RequiresPresenter(EvaluateDetailPresenter.class)
public class EvaluateDetailActivity extends BaseDataActivity<EvaluateDetailPresenter, Evaluate>
        implements RecyclerArrayAdapter.OnItemClickListener, TextWatcher {

    private EvaluatePanel mEvaluatePanel;

    private Evaluate mEvaluate;

    @BindView(R.id.ll_detail_top_product)
    LinearLayout mLlTopProduct;

    @BindView(R.id.dv_detail_top_thumb)
    SimpleDraweeView mDvTopThumb;

    @BindView(R.id.tv_detail_top_product)
    TextView mTvTopProduct;

    @BindView(R.id.dv_evaluate_thumb)
    SimpleDraweeView mDvThumb;

    @BindView(R.id.tv_evaluate_reply)
    TextView mTvReply;

    @BindView(R.id.tv_evaluate_content)
    TextView mTvEvaluateContent;

    @BindView(R.id.recy_evaluate_detail)
    RecyclerView mRecyDetail;

    @BindView(R.id.et_input_input)
    EditText mEtAdd;

    @BindView(R.id.tv_input_send)
    TextView mTvSend;

    @BindView(R.id.evaluate_ratbar_product)
    RatingBar mRatbarProduct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mImmersionBar.keyboardEnable(true);
        setContentView(R.layout.product_activity_evaluate_detail);
        setToolbarTitle(R.string.text_evaluate_detail);
        ButterKnife.bind(this);
        mEvaluatePanel = new EvaluatePanel(this);

        mRatbarProduct.setVisibility(View.GONE);
        mRecyDetail.setLayoutManager(new LinearLayoutManager(this));
        mRecyDetail.setAdapter(getPresenter().getAdapter());
        DividerDecoration decoration = new DividerDecoration(0xFFEBEBEB, LUtils.dp2px(1), LUtils.dp2px(78), LUtils.dp2px(15));
        decoration.setDrawLastItem(false);
        mRecyDetail.addItemDecoration(decoration);

        mEtAdd.addTextChangedListener(this);
        mEtAdd.setOnKeyListener((view, i, keyEvent) -> {
            if (keyEvent.getAction() == KeyEvent.ACTION_DOWN) {
                if (keyEvent.getKeyCode() == KeyEvent.KEYCODE_DEL && mEvaluate != null) {
                    if (mEtAdd.getText().toString().equals("@" + mEvaluate.getUser().getUsername() + "：")) {
                        clearInput();
                        return true;
                    }
                }
            }
            return false;
        });
        mTvSend.setOnClickListener(v -> {
            String content = mEtAdd.getText().toString();
            if (mEvaluate != null) {
                content = content.replace("@" + mEvaluate.getUser().getUsername() + "：", "");
                if (TextUtils.isEmpty(content)) {
                    LUtils.toast("内容不能为空");
                    return;
                }
            }
            getPresenter().addReply(content);
        });

    }

    @Override
    public void setData(Evaluate evaluate) {
        if (evaluate.getType() == 1) {
            if (!TextUtils.isEmpty(evaluate.getProduct_img())) {
                mDvTopThumb.setImageURI(Uri.parse(evaluate.getProduct_img()));
            } else {
                mDvTopThumb.setVisibility(View.GONE);
            }
            mLlTopProduct.setOnClickListener(v -> {
                Intent intent = new Intent(this, ProductDetailActivity.class);
                intent.putExtra(ProductDetailPresenter.EXTRA_PRODUCT_ID, evaluate.getPost_id());
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
            });
        } else {
            mDvTopThumb.setVisibility(View.GONE);
            mLlTopProduct.setOnClickListener(v -> {
                Intent intent = new Intent(this, ArticleDetailActivity.class);
                intent.putExtra(ArticleDetailPresenter.EXTRA_ARTICLE_ID, evaluate.getPost_id());
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
            });
        }
        mTvTopProduct.setText(evaluate.getTitle());
        mEvaluatePanel.setEvaluate(evaluate);
        mTvEvaluateContent.setText(Html.fromHtml(evaluate.getComment().replaceAll("(?:\n|\n\r)", "<br>")));
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
        LUtils.closeKeyboard(mEtAdd);
    }

    public Evaluate getEvaluate() {
        return mEvaluate;
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        mTvSend.setEnabled(mEtAdd.getText().length() > 0);
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }

}
