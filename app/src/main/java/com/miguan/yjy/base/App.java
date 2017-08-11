package com.miguan.yjy.base;

import android.app.ActivityManager;
import android.app.Application;
import android.content.ComponentName;
import android.content.Context;

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

}
