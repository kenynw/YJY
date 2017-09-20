package com.miguan.yjy.module.test;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.dsk.chain.expansion.data.BaseDataActivityPresenter;
import com.miguan.yjy.model.TestModel;
import com.miguan.yjy.model.bean.Test;
import com.miguan.yjy.model.bean.TestStart;
import com.miguan.yjy.utils.LUtils;

import org.greenrobot.eventbus.EventBus;

import rx.functions.Action1;

/**
 * @作者 cjh
 * @日期 2017/3/31 15:39
 * @描述
 */

public class TestGuidePresenter extends BaseDataActivityPresenter<TestGuideActivity,Test>{

    public static final String EXTRA_TEST = "test";

    public static final String EXTRA_TEST_TYPE = "type";

    public static void start(Context context, Test test, int type) {
        Intent intent = new Intent(context, TestGuideActivity.class);
        intent.putExtra(EXTRA_TEST, test);
        intent.putExtra(EXTRA_TEST_TYPE, type);
        ((Activity) context).startActivityForResult(intent, Activity.RESULT_FIRST_USER);
    }

    public void submitSkin(String key, String value) {
        TestModel.getInstantce().saveSkin(key, value).doOnNext(new Action1<Object>() {
            @Override
            public void call(Object o) {
                LUtils.toast("提交成功");
                getView().finish();
                EventBus.getDefault().post(new TestStart());
            }
        }).subscribe();
    }

}
