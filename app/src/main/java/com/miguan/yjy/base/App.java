package com.miguan.yjy.base;

import android.app.Application;

import com.dsk.chain.Chain;
import com.dsk.chain.model.ModelManager;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.miguan.yjy.utils.LUtils;
import com.umeng.socialize.Config;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;

/**
 * Created by LPK on 2016/11/21.
 */

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        LUtils.initialize(this);
        LUtils.isDebug = true;
        Fresco.initialize(this);
        ModelManager.init(this);
        Chain.setLifeCycleDelegateProvide(ActivityDelegate::new);

        initShare();
    }

    // 初始化友盟分享
    public void initShare() {
        UMShareAPI.get(this);
        Config.DEBUG = true;
        PlatformConfig.setSinaWeibo("1021526955", "834ae396d830ddf5cb4eafab4189e74b", "http://sns.whalecloud.com/sina2/callback");
        PlatformConfig.setWeixin("wxd949cf326bc0972f", "962b92d13242d27ed55e9e44230eb744");
    }

}
