package com.miguan.yjy.module.user;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import com.miguan.yjy.model.CommonModel;
import com.miguan.yjy.model.services.ServicesResponse;
import com.miguan.yjy.module.common.WebViewActivity;
import com.miguan.yjy.module.main.ArticleDetailPresenter;
import com.miguan.yjy.module.main.MainActivity;
import com.miguan.yjy.module.product.ProductDetailPresenter;
import com.miguan.yjy.utils.LUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

import cn.jpush.android.api.JPushInterface;

/**
 * Copyright (c) 2017/4/21. LiaoPeiKun Inc. All rights reserved.
 */

public class MessageReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
        LUtils.log("[MyReceiver] onReceive - " + intent.getAction() + ", extras: " + printBundle(bundle));

        if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
            openNotification(context,bundle);
        }
    }

    // 打印所有的 intent extra 数据
    private static String printBundle(Bundle bundle) {
        StringBuilder sb = new StringBuilder();
        for (String key : bundle.keySet()) {
            if (key.equals(JPushInterface.EXTRA_NOTIFICATION_ID)) {
                sb.append("\nkey:" + key + ", value:" + bundle.getInt(key));
            }else if(key.equals(JPushInterface.EXTRA_CONNECTION_CHANGE)){
                sb.append("\nkey:" + key + ", value:" + bundle.getBoolean(key));
            } else if (key.equals(JPushInterface.EXTRA_EXTRA)) {
                if (TextUtils.isEmpty(bundle.getString(JPushInterface.EXTRA_EXTRA))) {
                    LUtils.log("This message has no Extra data");
                    continue;
                }

                try {
                    JSONObject json = new JSONObject(bundle.getString(JPushInterface.EXTRA_EXTRA));
                    Iterator<String> it =  json.keys();

                    while (it.hasNext()) {
                        String myKey = it.next().toString();
                        sb.append("\nkey:" + key + ", value: [" +
                                myKey + " - " +json.optString(myKey) + "]");
                    }
                } catch (JSONException e) {
                    LUtils.log("Get message extra JSON error!");
                }

            } else {
                sb.append("\nkey:" + key + ", value:" + bundle.getString(key));
            }
        }
        return sb.toString();
    }

    private void openNotification(Context context, Bundle bundle){
        String extras = bundle.getString(JPushInterface.EXTRA_EXTRA);
        int type;
        String relation;
        int id;
        try {
            JSONObject extrasJson = new JSONObject(extras);
            type = extrasJson.optInt("type");
            relation = extrasJson.optString("relation");
            id = extrasJson.optInt("id");
        } catch (Exception e) {
            LUtils.log("Unexpected: extras is not a valid json");
            return;
        }

        if (type == 1) { // 过期
            CommonModel.getInstance().setMsgRead(id, "notice").unsafeSubscribe(new ServicesResponse<String>() {
                @Override
                public void onNext(String s) {
                    Intent intent = new Intent(context, UsedListActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
            });
        } else if (type == 2) { // H5
            WebViewActivity.start(context, "", relation);
        } else if (type == 3) { // 文章
            ArticleDetailPresenter.start(context, Integer.valueOf(relation));
        } else if (type == 4){ // 产品
            ProductDetailPresenter.start(context, Integer.valueOf(relation));
        } else {
            Intent intent = new Intent(context, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        }

    }
}
