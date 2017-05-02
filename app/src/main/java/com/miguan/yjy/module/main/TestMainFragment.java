package com.miguan.yjy.module.main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.dsk.chain.bijection.RequiresPresenter;
import com.dsk.chain.expansion.data.BaseDataFragment;
import com.miguan.yjy.R;
import com.miguan.yjy.model.UserModel;
import com.miguan.yjy.model.bean.Test;
import com.miguan.yjy.model.bean.User;
import com.miguan.yjy.model.local.UserPreferences;
import com.miguan.yjy.model.services.ServicesResponse;
import com.miguan.yjy.module.test.TestResultFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * @作者 cjh
 * @日期 2017/4/24 10:03
 * @描述
 */
@RequiresPresenter(TestFragmentPrensenter.class)
public class TestMainFragment extends BaseDataFragment<TestMainFragmentPrensenter, Test>{
    int type = 0;
    private FragmentManager fm;
    private ArrayList<Fragment> fragments;
    private User userInfo;
    private List<Integer> nums = new ArrayList<>();
    private int containerId = R.id.containerId;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initFragment();

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        FrameLayout frameLayout = new FrameLayout(getActivity());
        frameLayout.setId(containerId);
        return frameLayout;
    }

    private void initFragment() {
        fm = getChildFragmentManager();
        fragments = new ArrayList<Fragment>();
        TestFragment testFragment = new TestFragment();
        testFragment.setMyOnTabClick(new TestFragment.MyOnTabClick() {
            @Override
            public void tabClickStart() {
                showFragment(1);
            }
        });
        fragments.add(testFragment);
        TestResultFragment testResultFragment = new TestResultFragment();
        testResultFragment.setMyOnTabClick(new TestResultFragment.MyOnTabClick() {
            @Override
            public void tabClickStart() {
                showFragment(0);
            }
        });
        fragments.add(testResultFragment);
        FragmentTransaction ft = fm.beginTransaction();
        for (Fragment fragment : fragments) {
            ft.add(containerId,fragment);
        }
        ft.commit();
        loadData();
//        showFragment(0);
    }

    public void showFragment(int position) {
        hideFragments();
        Fragment fragment = fragments.get(position);
        FragmentTransaction ft = fm.beginTransaction();
        ft.show(fragment);
        ft.commit();
    }

    public void hideFragments() {
        FragmentTransaction ft = fm.beginTransaction();
        for (Fragment fragment : fragments) {
            if (fragment != null) {
                ft.hide(fragment);
            }
        }
        ft.commit();
    }

    public Fragment getFragment(int position) {
        return fragments.get(position);
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    public void loadData() {
        if (UserPreferences.getUserID() > 0) {
            UserModel.getInstance().getUserInfo().subscribe(new ServicesResponse<User>() {
                @Override
                public void onNext(User user) {
                    nums.clear();
                    userInfo = user;
                    if (!TextUtils.isEmpty(userInfo.getCompact())) {
                        nums.add(0);
                    }
                    if (!TextUtils.isEmpty(userInfo.getDry())) {
                        nums.add(1);
                    }
                    if (!TextUtils.isEmpty(userInfo.getTolerance())) {
                        nums.add(2);
                    }
                    if (!TextUtils.isEmpty(userInfo.getPigment())) {
                        nums.add(3);
                    }
                    if (nums.size() == 4) {
                        showFragment(0);
                    } else {
                        showFragment(1);
                    }

                }
            });
        } else {
            showFragment(0);
        }

    }

    @Override
    public void setMenuVisibility(boolean menuVisible) {
        super.setMenuVisibility(menuVisible);
        if (getView() != null) {
            getView().setVisibility(menuVisible ? View.VISIBLE : View.INVISIBLE);
        }
    }

}
