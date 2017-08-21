package com.miguan.yjy.module.user;

import android.os.Bundle;

import com.dsk.chain.bijection.Presenter;
import com.miguan.yjy.utils.LUtils;

/**
 * @作者 cjh
 * @日期 2017/8/21 9:42
 * @描述
 */

public class OfficialWechatPresenter extends Presenter<OfficialWechatActivity> {

    @Override
    protected void onCreate(OfficialWechatActivity view, Bundle saveState) {
        super.onCreate(view, saveState);
    }

    @Override
    protected void onCreateView(OfficialWechatActivity view) {
        super.onCreateView(view);
    }

    public void openWechat() {

        if (LUtils.isWeixinAvilible(getView())) {
            LUtils.startToWechat(getView());
        } else {
            LUtils.toast("您还没有安装微信喔~");
        }

    }
}
