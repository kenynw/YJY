package com.miguan.yjy.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.miguan.yjy.R;
import com.miguan.yjy.module.product.SearchResultPresenter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @作者 cjh
 * @日期 2017/3/24 15:30
 * @描述 历史搜索
 */

public class HistorySearchAdpter extends RecyclerArrayAdapter<String> {

    private SetOnRemoveClicklinsener mSetRemoveClicklinsener;

    public void setRemoveClicklinsener(SetOnRemoveClicklinsener setRemoveClicklinsener) {
        mSetRemoveClicklinsener = setRemoveClicklinsener;
    }

    public HistorySearchAdpter(Context context, List<String> objects) {
        super(context, objects);
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new HistorySearchViewHolder(parent);
    }

    @Override
    public void OnBindViewHolder(BaseViewHolder holder, int position) {
        holder.setData(mObjects.get(position));
    }

    public interface SetOnRemoveClicklinsener {
        public void removeName(String data);
    }

    class HistorySearchViewHolder extends BaseViewHolder<String> {
        @BindView(R.id.tv_product_name)
        TextView tvProductName;
        @BindView(R.id.ll_product_his_close)
        LinearLayout llProductHisClose;
        @BindView(R.id.view_product_his_line)
        View viewProductHisLine;

        public HistorySearchViewHolder(ViewGroup parent) {
            super(parent, R.layout.product_item_history_serach);
            ButterKnife.bind(this, itemView);
        }

        @Override
        public void setData(String data) {
            super.setData(data);
            tvProductName.setText(data);
//            Log.e("getdataposition", mObjects.size()+"---"+getDataPosition() + "====" + getAdapterPosition() + "-------------");
            if (mObjects.size() == getAdapterPosition()+1) {
                viewProductHisLine.setVisibility(View.GONE);
            }
            llProductHisClose.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    HistorySearchAdpter.this.remove(data);
                    if (mSetRemoveClicklinsener != null) {
                        mSetRemoveClicklinsener.removeName(data);
                    }
                }
            });
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SearchResultPresenter.start(getContext(), data, 0, "");
                }
            });
        }
    }

}
