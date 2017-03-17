package com.miguan.yjy.base;

import android.app.Application;

import com.dsk.chain.Chain;
import com.dsk.chain.model.ModelManager;
import com.miguan.yjy.utils.LUtils;

/**
 * Created by LPK on 2016/11/21.
 */

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        LUtils.initialize(this);
        ModelManager.init(this);
        Chain.setLifeCycleDelegateProvide(ActivityDelegate::new);
        initShare();
    }

    // 初始化分享
    public void initShare() {
//        UMShareAPI.get(this);
//        Config.DEBUG = true;
//        PlatformConfig.setSinaWeibo("3724165953", "6c218cd5b1938037096aa8886409ba6a", "http://sns.whalecloud.com/sina2/callback");
//        PlatformConfig.setWeixin("wxfe7723ef510fe37f", "d13d6bdc14d5fe5ac6ffd2a9927a3adf");
//        PlatformConfig.setQQZone("1105939765", "EYDkf1MlqGDw0RPn");
    }

}
