package com.miguan.yjy.module.main;

import com.dsk.chain.expansion.data.BaseDataFragmentPresenter;
import com.miguan.yjy.model.bean.Test;
import com.umeng.analytics.MobclickAgent;


/**
 * @作者 cjh
 * @日期 2017/3/30 16:01
 * @描述
 */

public class TestFragmentPrensenter extends BaseDataFragmentPresenter<TestFragment,Test> {

    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart("首页");
    }

    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("首页");
    }

}
