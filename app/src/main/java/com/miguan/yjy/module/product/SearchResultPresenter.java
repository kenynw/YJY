package com.miguan.yjy.module.product;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.text.Html;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dsk.chain.expansion.list.BaseListActivityPresenter;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.jude.easyrecyclerview.decoration.DividerDecoration;
import com.miguan.yjy.R;
import com.miguan.yjy.adapter.ProductRecommentAdapter;
import com.miguan.yjy.model.ProductModel;
import com.miguan.yjy.model.bean.Product;
import com.miguan.yjy.model.bean.ProductList;
import com.miguan.yjy.model.bean.Rank;
import com.miguan.yjy.model.services.ServicesResponse;
import com.miguan.yjy.module.billboard.BillboardSimpleViewHolder;
import com.miguan.yjy.utils.LUtils;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

/**
 * @作者 cjh
 * @日期 2017/3/21 11:31
 * @描述
 */

public class SearchResultPresenter extends BaseListActivityPresenter<SearchResultActivity, Product> {

    public static final String EXTRA_KEYWORDS = "keywords";

    public static final String EXTRA_CATE_ID = "cate_id";

    public static final String EXTRA_CATE_NAME = "cate_name";

    private String mKeywords;//关键词

    private int mCateId;//分类id

    private String mCateName;//分类id

    private String mEffect;//功效关键字
    private int mType = 1;//类型 ：不传或者传1为默认搜索产品
    public EasyRecyclerView mRecySearchBillBoard;
    TextView mTvCount;


    public static void start(Context context, String keywords, int cateId, String cateName) {
        Intent intent = new Intent(context, SearchResultActivity.class);
        intent.putExtra(EXTRA_KEYWORDS, keywords);
        intent.putExtra(EXTRA_CATE_ID, cateId);
        intent.putExtra(EXTRA_CATE_NAME, cateName);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(SearchResultActivity view, Bundle saveState) {
        super.onCreate(view, saveState);
        mKeywords = view.getIntent().getStringExtra(EXTRA_KEYWORDS);
        mCateId = view.getIntent().getIntExtra(EXTRA_CATE_ID, 0);
        mCateName = view.getIntent().getStringExtra(EXTRA_CATE_NAME);
    }

    @Override
    protected void onCreateView(SearchResultActivity view) {
        super.onCreateView(view);
        getView().mEtKeywords.setText(mKeywords);
        onRefresh();
    }

    @Override
    public void onRefresh() {
        ProductModel.getInstance().searchQuery(getView().mEtKeywords.getText().toString(), mType, mCateId, mEffect, 1)
                .map(product -> {
                    getAdapter().removeAllHeader();
                    getAdapter().addHeader(new RecyclerArrayAdapter.ItemView() {

                        @Override
                        public View onCreateView(ViewGroup parent) {
                            View headBillBoard = View.inflate(getView(), R.layout.include_head_billboard, null);
                            mRecySearchBillBoard = headBillBoard.findViewById(R.id.recy_search_billBoard);
                            mTvCount = headBillBoard.findViewById(R.id.tv_product_list_count);
                            mRecySearchBillBoard.setLayoutManager(new LinearLayoutManager(getView(), LinearLayoutManager.VERTICAL, false));
                            int transparent = getView().getResources().getColor(R.color.transparent);
                            mRecySearchBillBoard.addItemDecoration(new DividerDecoration(transparent,
                                    (int) getView().getResources().getDimension(R.dimen.spacing_small)));
                            mTvCount.setText(Html.fromHtml(String.format(getView().getString(R.string.text_search_count), product.getPageTotal())));
                            return headBillBoard;
                        }

                        @Override
                        public void onBindView(View headerView) {

                            if (product.getRank().size() != 0) {
                                mRecySearchBillBoard.setVisibility(View.VISIBLE);
                                mRecySearchBillBoard.setAdapter(new RecyclerArrayAdapter<Rank>(getView(), product.getRank()) {
                                    @Override
                                    public BillboardSimpleViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
                                        return new BillboardSimpleViewHolder(parent);
                                    }
                                });
                            } else {
                                mRecySearchBillBoard.setVisibility(View.GONE);
                            }
                        }
                    });
                    getView().setData(getView().mEtKeywords.getText().toString(), product, mCateName);
                    return product.getProduct();
                })
                .unsafeSubscribe(getRefreshSubscriber());
        LUtils.closeKeyboard(getView().mEtKeywords);
    }

    @Override
    public void onLoadMore() {
        ProductModel.getInstance()
                .searchQuery(getView().mEtKeywords.getText().toString(), mType, mCateId, mEffect, getCurPage())
                .map(ProductList::getProduct)
                .unsafeSubscribe(getMoreSubscriber());
    }

    public void setCateId(int cateId) {
        mCateId = cateId;
    }

    public void setEffect(String effect) {
        mEffect = effect;
    }

    public void setType(int type) {
        mType = type;
    }

    public void setRecommendData(String s) {
        ProductModel.getInstance().searchAssociate(s).subscribe(new ServicesResponse<ProductList>() {
            @Override
            public void onNext(ProductList products) {
                if (products.getProduct().size() == 0) {
                    getView().mLlResultSencond.setVisibility(GONE);
                } else {
                    getView().mLlResultSencond.setVisibility(VISIBLE);
                    getView().mLlProductFilter.setVisibility(GONE);
                    getView().mRecyRecommend.setLayoutManager(new LinearLayoutManager(getView(), LinearLayoutManager.VERTICAL, false));
                    getView().mRecyRecommend.setAdapter(new ProductRecommentAdapter(getView(), products.getProduct(), s.toString()));
                }
            }
        });
    }
}
