package com.miguan.yjy.module.user;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;

import com.dsk.chain.bijection.ChainBaseActivity;
import com.dsk.chain.bijection.RequiresPresenter;
import com.miguan.yjy.R;
import com.miguan.yjy.adapter.CityAdapter;
import com.miguan.yjy.model.CityData;
import com.miguan.yjy.model.bean.CityItem;
import com.miguan.yjy.module.user.widget.ContactItemInterface;
import com.miguan.yjy.module.user.widget.ContactListViewImpl;
import com.miguan.yjy.utils.LUtils;

import java.util.ArrayList;
import java.util.List;


/**
 * @作者 cjh
 * @日期 2016/8/16 10:18
 * @描述 选择城市
 */
@RequiresPresenter(CityListPresenter.class)
public class CityListActivity extends ChainBaseActivity<CityListPresenter> implements TextWatcher {
    private Context context_ = CityListActivity.this;

    private ContactListViewImpl listview;

    private EditText searchBox;
    private String searchString;

    private Object searchLock = new Object();
    boolean inSearchMode = false;

    private final static String TAG = "CityListActivity";

    List<ContactItemInterface> contactList;
    List<ContactItemInterface> filterList;
    private SearchListTask curSearchTask = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_activity_city);
        setToolbarTitle("选择城市");
        filterList = new ArrayList<ContactItemInterface>();
        contactList = CityData.getSampleContactList();

        CityAdapter adapter = new CityAdapter(this,
                R.layout.city_item, contactList);

        listview = (ContactListViewImpl) findViewById(R.id.listview);
        listview.setFastScrollEnabled(true);
        listview.setAdapter(adapter);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View v, int position,
                                    long id) {
                List<ContactItemInterface> searchList = inSearchMode ? filterList
                        : contactList;
                String city = searchList.get(position).getDisplayInfo();
                userUpdate(city);
            }
        });

        searchBox = (EditText) findViewById(R.id.input_search_query);
        searchBox.addTextChangedListener(this);
    }

    @Override
    public void afterTextChanged(Editable s) {
        searchString = searchBox.getText().toString().trim().toUpperCase();

        if (curSearchTask != null
                && curSearchTask.getStatus() != AsyncTask.Status.FINISHED) {
            try {
                curSearchTask.cancel(true);
            } catch (Exception e) {
                Log.i(TAG, "Fail to cancel running search task");
            }

        }
        curSearchTask = new SearchListTask();
        curSearchTask.execute(searchString);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count,
                                  int after) {
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        // do nothing
    }

    private class SearchListTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            filterList.clear();

            String keyword = params[0];

            inSearchMode = (keyword.length() > 0);

            if (inSearchMode) {
                // get all the items matching this
                for (ContactItemInterface item : contactList) {
                    CityItem contact = (CityItem) item;

                    boolean isPinyin = contact.getFullName().toUpperCase()
                            .indexOf(keyword) > -1;
                    boolean isChinese = contact.getNickName().indexOf(keyword) > -1;

                    if (isPinyin || isChinese) {
                        filterList.add(item);
                    }

                }

            }
            return null;
        }

        protected void onPostExecute(String result) {

            synchronized (searchLock) {

                if (inSearchMode) {

                    CityAdapter adapter = new CityAdapter(context_,
                            R.layout.city_item, filterList);
                    adapter.setInSearchMode(true);
                    listview.setInSearchMode(true);
                    listview.setAdapter(adapter);
                } else {
                    CityAdapter adapter = new CityAdapter(context_,
                            R.layout.city_item, contactList);
                    adapter.setInSearchMode(false);
                    listview.setInSearchMode(false);
                    listview.setAdapter(adapter);
                }
            }

        }
    }

    /**
     * 用户资料修改
     * userId(int) － 用户ID
     * attribute(string) － 需要修改的字段
     * content(string) － 修改的值
     * time(int) － 当前的时间戳
     * sign(string) － 按具体方式生成的字符串
     */

    private void userUpdate(final String city) {
        if (TextUtils.isEmpty(city)) {
            LUtils.toast("城市不能为空");
            return;
        }
//        Map<String, String> params = new TreeMap<>();
//        params.put("userId", UserSp.getInstance().getUserId(this) + "");
//        params.put("attribute", "city");
//        params.put("content", city);
//        params.put("time", System.currentTimeMillis() + "");
//        NetUtil.getInstance().sendGet(this, MyApi.userupdate, params, NetUtil.sort(params), new HttpResponseHandlerImpl() {
//            @Override
//            public void onSuccess(JSONObject response) {
//                super.onSuccess(response);
//                try {
//                    if (response.getInt(MyApi.CODECODENAME) == MyApi.CODESUCCESS) {
                        Intent intent = new Intent();
                        intent.putExtra("city", city);
                        setResult(RESULT_OK, intent);
                        finish();
//                    }
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//
//            }
//
//            @Override
//            public void onFaile(String error) {
//                super.onFaile(error);
//            }
//        });
    }


}
