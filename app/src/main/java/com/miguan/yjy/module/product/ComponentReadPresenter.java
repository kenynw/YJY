package com.miguan.yjy.module.product;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dsk.chain.expansion.list.BaseListActivityPresenter;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.miguan.yjy.R;
import com.miguan.yjy.model.ProductModel;
import com.miguan.yjy.model.bean.Component;
import com.miguan.yjy.model.bean.EntityRoot;
import com.miguan.yjy.model.bean.Product;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;
import rx.functions.Func1;

/**
 * @作者 cjh
 * @日期 2017/6/27 13:47
 * @描述
 */

public class ComponentReadPresenter extends BaseListActivityPresenter<ComponentReadActivity, Product> {

    public static final String COMPONENTID = "componentId";
    private int componentId;

    public static void start(Context context, int componentId) {
        Intent intent = new Intent(context, ComponentReadActivity.class);
        intent.putExtra(COMPONENTID, componentId);
        context.startActivity(intent);
    }

    @Override
    protected void onCreateView(ComponentReadActivity view) {
        super.onCreateView(view);
        componentId = getView().getIntent().getIntExtra(COMPONENTID, -1);
        onRefresh();

    }

    @Override
    public void onRefresh() {

        ProductModel.getInstance().componentInfo(componentId)
                .flatMap(new Func1<Component, Observable<EntityRoot<List<Product>>>>() {
                    @Override
                    public Observable<EntityRoot<List<Product>>> call(Component component) {
                        getAdapter().removeAllHeader();
                        getAdapter().addHeader(new HeadComponent(component));
                        return ProductModel.getInstance().componentProductList(componentId, 1);
                    }
                })
                .map(new Func1<EntityRoot<List<Product>>, List<Product>>() {
                    @Override
                    public List<Product> call(EntityRoot<List<Product>> listEntityList) {
                        Log.e("listEntityList", listEntityList.toString());
                        Log.e("listEntityList.get", listEntityList.getPageTotal() + "=getPageTotal==");
                        ((HeadComponent) getAdapter().getHeader(0)).setIsSetNum(listEntityList.getPageTotal());
                        return listEntityList.getData();
                    }
                }).unsafeSubscribe(getRefreshSubscriber());
    }

    @Override
    public void onLoadMore() {
        ProductModel.getInstance().componentInfo(componentId)
                .flatMap(new Func1<Component, Observable<EntityRoot<List<Product>>>>() {
                    @Override
                    public Observable<EntityRoot<List<Product>>> call(Component component) {
                        return ProductModel.getInstance().componentProductList(componentId, getCurPage());
                    }
                })
                .map(new Func1<EntityRoot<List<Product>>, List<Product>>() {
                    @Override
                    public List<Product> call(EntityRoot<List<Product>> listEntityList) {
                        return listEntityList.getData();
                    }
                }).unsafeSubscribe(getMoreSubscriber());
    }


    public class HeadComponent implements RecyclerArrayAdapter.ItemView {
        private Component component;

        @BindView(R.id.tv_component_num)
        TextView mTvComponentNum;
        @BindView(R.id.tv_component_name)
        TextView mTvComponentName;
        @BindView(R.id.tv_component_tag_name)
        TextView mTvComponentTagName;
        @BindView(R.id.tv_component_use_purpose)
        TextView mTvComponentUsePurpose;
        @BindView(R.id.tv_component_explain)
        TextView mTvComponentExplain;
        @BindView(R.id.tv_component_risk_grade)
        TextView mTvComponentRiskGrade;
        @BindView(R.id.tv_component_pox)
        TextView mTvComponentPox;
        @BindView(R.id.tv_component_active)
        TextView mTvComponentActive;
        public int isSetNum;

        public void setIsSetNum(int isSetNum) {
            this.isSetNum = isSetNum;
        }

        public HeadComponent(Component component) {
            this.component = component;
        }

        @Override
        public View onCreateView(ViewGroup parent) {
            View headComponent = View.inflate(getView(), R.layout.include_head_component_info, null);
            ButterKnife.bind(this, headComponent);
            return headComponent;
        }

        @Override
        public void onBindView(View headerView) {
            mTvComponentName.setText(component.getName());
            mTvComponentTagName.setText("英文名:" + component.getEname());
            mTvComponentUsePurpose.setText("使用目的:" + component.getComponent_action());
            mTvComponentExplain.setText(component.getDescription());

            String grade = component.getRisk_grade();
            if (TextUtils.isEmpty(grade)) {
                grade = "0";
            }
            mTvComponentRiskGrade.setText(grade);
            if (grade.contains("0") || grade.contains("1") || grade.contains("2")) {
                mTvComponentRiskGrade.setBackgroundResource(R.drawable.bg_shape_product_f8b);
            }
            if (grade.contains("3") || grade.contains("4") || grade.contains("5") || grade.contains("6")) {
                mTvComponentRiskGrade.setBackgroundResource(R.drawable.bg_shape_product_ffc3);
            }
            if (grade.contains("7") || grade.contains("8") || grade.contains("9")) {
                mTvComponentRiskGrade.setBackgroundResource(R.drawable.bg_shape_product_fb74);
            }

            if (component.getIs_active() == 1) {
                mTvComponentActive.setBackgroundResource(R.mipmap.bg_product_active);
            } else {
                mTvComponentActive.setText("—");
            }
            if (component.getIs_pox() == 1) {
                mTvComponentPox.setBackgroundResource(R.mipmap.bg_product_pox);
            } else {
                mTvComponentPox.setText("—");
            }
            if (isSetNum >= 0) {
                String componentNum = String.format(getView().getResources().getString(R.string.tv_component_num), isSetNum);
                mTvComponentNum.setText(componentNum);
            }
        }
    }

}
