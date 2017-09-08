package com.miguan.yjy.base;

import android.app.ActivityManager;
import android.app.Application;
import android.content.ComponentName;
import android.content.Context;
import android.support.multidex.MultiDex;

import com.alibaba.baichuan.android.trade.AlibcTradeSDK;
import com.alibaba.baichuan.android.trade.callback.AlibcTradeInitCallback;
import com.dsk.chain.Chain;
import com.dsk.chain.model.ModelManager;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.miguan.yjy.model.local.SystemPreferences;
import com.miguan.yjy.model.local.UserPreferences;
import com.miguan.yjy.module.common.AppCrashHandler;
import com.miguan.yjy.utils.LUtils;
import com.umeng.analytics.MobclickAgent;
import com.umeng.socialize.Config;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;

import java.util.List;

import cn.jpush.android.api.JPushInterface;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        LUtils.initialize(this);
        SystemPreferences.initialize(this);
        LUtils.isDebug = false;
        Fresco.initialize(this);
        ModelManager.init(this);
        Chain.setLifeCycleDelegateProvide(ActivityDelegate::new);
        if (!LUtils.isDebug) AppCrashHandler.getInstance(this);

        JPushInterface.setDebugMode(LUtils.isDebug); 	// 设置开启日志,发布时请关闭日志
        JPushInterface.init(this);

        initUmeng();

        if (!isApplicationInBackground(this)) {
            UserPreferences.setIsShowTest(false);
        }

        //初始化阿里百川
        AlibcTradeSDK.asyncInit(this, new AlibcTradeInitCallback() {
            @Override
            public void onSuccess() {
                //初始化成功，设置相关的全局配置参数

            }

            @Override
            public void onFailure(int code, String msg) {
                //初始化失败，可以根据code和msg判断失败原因，详情参见错误说明
            }
        });

    }
    // 初始化友盟分享
    public void initUmeng() {
        MobclickAgent.setDebugMode(LUtils.isDebug);
        UMShareAPI.get(this);
        Config.DEBUG = LUtils.isDebug;
        PlatformConfig.setSinaWeibo("1021526955", "834ae396d830ddf5cb4eafab4189e74b", "http://sns.whalecloud.com/sina2/callback");
        PlatformConfig.setWeixin("wxd949cf326bc0972f", "962b92d13242d27ed55e9e44230eb744");
    }

    private boolean isApplicationInBackground(Context context) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> taskList = am.getRunningTasks(1);
        if (taskList != null && !taskList.isEmpty()) {
            ComponentName topActivity = taskList.get(0).topActivity;
            if (topActivity != null && !topActivity.getPackageName().equals(context.getPackageName())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void attachBaseContext(Context base) {
        MultiDex.install(base);
        super.attachBaseContext(base);
    }

}
