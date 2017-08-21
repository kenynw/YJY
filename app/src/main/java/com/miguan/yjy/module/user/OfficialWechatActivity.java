package com.miguan.yjy.module.user;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.dsk.chain.bijection.ChainBaseActivity;
import com.dsk.chain.bijection.RequiresPresenter;
import com.miguan.yjy.R;

/**
 * @作者 cjh
 * @日期 2017/8/21 9:23
 * @描述
 */
@RequiresPresenter(OfficialWechatPresenter.class)
public class OfficialWechatActivity extends ChainBaseActivity<OfficialWechatPresenter> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_activity_official_wechat);
        setToolbarTitle("官方微信");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_open_weixin, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        getPresenter().openWechat();
        return super.onOptionsItemSelected(item);
    }


}



