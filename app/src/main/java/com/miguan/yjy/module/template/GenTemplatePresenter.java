package com.miguan.yjy.module.template;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.dsk.chain.bijection.Presenter;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.jude.library.imageprovider.ImageProvider;
import com.miguan.yjy.R;
import com.miguan.yjy.model.bean.Product;
import com.miguan.yjy.model.local.UserPreferences;
import com.miguan.yjy.utils.DateUtils;
import com.miguan.yjy.utils.LUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;


/**
 * Copyright (c) 2017/4/5. LiaoPeiKun Inc. All rights reserved.
 */

public class GenTemplatePresenter extends Presenter<GenTemplateActivity> {

    public static final String EXTRA_TEMPLATE = "template";

    public static void start(Context context, int index) {
        Intent intent = new Intent(context, GenTemplateActivity.class);
        intent.putExtra(EXTRA_TEMPLATE, index);
        context.startActivity(intent);
    }

    private TemplateType mTemplate;

    private TemplateAdapter mAdapter;

    public List<Product> mProductList;

    public View mHeader;

    private View mFooter;

    @Override
    protected void onCreate(GenTemplateActivity view, Bundle saveState) {
        super.onCreate(view, saveState);
        EventBus.getDefault().register(this);

    }

    @Override
    protected void onCreateView(GenTemplateActivity view) {
        super.onCreateView(view);

        int index = getView().getIntent().getIntExtra(EXTRA_TEMPLATE, 0);
        String templateStr;
        if (!(templateStr = LUtils.getPreferences().getString(EXTRA_TEMPLATE, "")).isEmpty()) {
            new AlertDialog.Builder(getView())
                    .setCancelable(false)
                    .setTitle("上次的创作还未完成哦~")
                    .setItems(new String[]{"加载草稿", "重新创作", "取消"}, (dialog, which) -> {
                        if (which == 0) {
                            Template template = new Gson().fromJson(templateStr, Template.class);
                            getView().getIntent().putExtra(EXTRA_TEMPLATE, template.getType());
                            getView().initTemplate(template.getType(), template);
                        } else {
                            if (which == 1) {
                                LUtils.getPreferences().edit().putString(EXTRA_TEMPLATE, "").apply();
                            }
                            getView().initTemplate(index, null);
                        }
                    })
                    .show();
        } else {
            getView().initTemplate(index, null);
        }
    }

    @Override
    protected void onDestroyView() {
        super.onDestroyView();
        Template template = new Template();
        template.setType(getView().getIntent().getIntExtra(EXTRA_TEMPLATE, 0));
        if (getView().getHeader() != null) {
            EditText et = (EditText) getView().getHeader().findViewById(R.id.et_template_header);
            if (et != null) {
                template.setTitle(et.getText().toString());
            }
            EditText et2 = (EditText) getView().getHeader().findViewById(R.id.et_template_header_2);
            if (et2 != null) {
                template.setDesc(et2.getText().toString());
            }
        }
        if (getView().getTemplateList() != null && getView().getTemplateList().size() > 0) {
            List<Template.Item> itemList = new ArrayList<>();
            for (TemplateView templateView : getView().getTemplateList()) {
                Template.Item item = new Template.Item();
                item.setEtContents(templateView.getTexts());
                item.setUris(templateView.getUris());
                itemList.add(item);
            }
            template.setItems(itemList);
        }
        LUtils.getPreferences().edit().putString(EXTRA_TEMPLATE, new Gson().toJson(template)).apply();
    }

    public void hideCursor() {
        if (mHeader != null) {
            EditText et = (EditText) mHeader.findViewById(R.id.et_template_header);
            if (et != null) {
                et.setCursorVisible(false);
            }
            EditText et2 = (EditText) mHeader.findViewById(R.id.et_template_header_2);
            if (et2 != null) {
                et2.setCursorVisible(false);
            }
        }
    }

    public BaseViewHolder createTemplateViewHolder(ViewGroup parent) {
        BaseTemplateViewHolder templateViewHolder = null;
        try {
            Constructor<? extends BaseTemplateViewHolder> constructor = mTemplate.mClass.getDeclaredConstructor(ViewGroup.class);
            templateViewHolder = constructor.newInstance(parent);
            getView().getViewHolders().add(templateViewHolder);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return templateViewHolder;
    }

    public TemplateAdapter getAdapter() {
        if (mAdapter == null) {
            mAdapter = new TemplateAdapter(getView());
            mAdapter.add(new Product());

            if (mHeader != null) mAdapter.addHeader(new RecyclerArrayAdapter.ItemView() {
                @Override
                public View onCreateView(ViewGroup parent) {
                    return mHeader;
                }

                @Override
                public void onBindView(View headerView) {

                }

            });
            if (mFooter != null) mAdapter.addFooter(new RecyclerArrayAdapter.ItemView() {

                private SimpleDraweeView mDvAvatar;

                private TextView mTvUsername;

                private TextView mTvTime;

                @Override
                public View onCreateView(ViewGroup parent) {
                    return mFooter;
                }

                @Override
                public void onBindView(View view) {
                    mTvTime = ButterKnife.findById(view, R.id.tv_template_time);
                    if (mTvTime != null) {
                        mTvTime.setText(DateUtils.getCurrentFormatDate("yyyy年MM月dd日"));
                    }

                    if (UserPreferences.getUserID() > 0) {
                        mDvAvatar = ButterKnife.findById(view, R.id.dv_template_avatar);
                        mTvUsername = ButterKnife.findById(view, R.id.tv_template_username);
                        if (mDvAvatar != null) {
                            mDvAvatar.setImageURI(Uri.parse(UserPreferences.getAvatar()));
                        }
                        if (mTvUsername != null) {
                            mTvUsername.setText(UserPreferences.getUsername());
                        }
                    }
                }
            });
        }
        return mAdapter;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void onResult(int requestCode, int resultCode, Intent data) {
        super.onResult(requestCode, resultCode, data);
        ImageProvider.getInstance(getView()).onActivityResult(requestCode, resultCode, data);
    }

    @Subscribe(threadMode = ThreadMode.MAIN) // 删除模板
    public void deleteItem(BaseTemplateViewHolder.TemplateDeleteEvent event) {
        if (mAdapter.getCount() <= 1) {
            LUtils.toast("至少要保留一个吧");
            return;
        }

        mAdapter.remove(event.getPosition());
    }

    public class TemplateAdapter extends RecyclerArrayAdapter<Product> {

        public TemplateAdapter(Context context) {
            super(context);
        }

        @Override
        public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
            return createTemplateViewHolder(parent);
        }

        public List<Product> getData() {
            return mObjects;
        }

    }
}
