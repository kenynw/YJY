package com.miguan.yjy.module.user;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.dsk.chain.bijection.ChainBaseActivity;
import com.dsk.chain.bijection.RequiresPresenter;
import com.miguan.yjy.R;
import com.miguan.yjy.utils.LUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @作者 cjh
 * @日期 2017/8/21 9:23
 * @描述
 */
@RequiresPresenter(OfficialWechatPresenter.class)
public class OfficialWechatActivity extends ChainBaseActivity<OfficialWechatPresenter> {

    @BindView(R.id.tv_copy_wetchat)
    TextView mTvCopyWetchat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_activity_official_wechat);
        ButterKnife.bind(this);
        setToolbarTitle("官方微信");
        mTvCopyWetchat.setOnClickListener(v-> LUtils.clipText(this,"tmshuo520"));
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



