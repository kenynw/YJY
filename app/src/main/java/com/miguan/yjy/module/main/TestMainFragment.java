package com.miguan.yjy.module.main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
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
public class TestMainFragment extends BaseDataFragment<TestMainFragmentPrensenter, Test> {
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

    public void initFragment() {
        fm = getChildFragmentManager();
        fragments = new ArrayList<Fragment>();
        TestFragment testFragment = new TestFragment();
        testFragment.setMyOnTabClick(new TestFragment.MyOnTabClick() {
            @Override
            public void tabClickStart() {
                showFragment(1);
                UserPreferences.setIsShowTest(false);
            }
        });
        fragments.add(testFragment);
        TestResultFragment testResultFragment = new TestResultFragment();
        testResultFragment.setMyOnTabClick(new TestResultFragment.MyOnTabClick() {
            @Override
            public void tabClickStart() {
                showFragment(0);
                UserPreferences.setIsShowTest(true);
            }
        });
        fragments.add(testResultFragment);
        FragmentTransaction ft = fm.beginTransaction();
        for (Fragment fragment : fragments) {
            ft.add(containerId, fragment);
        }
        ft.commitAllowingStateLoss();
        loadData();
//        showFragment(0);
    }

    public void showFragment(int position) {
        hideFragments();
        Fragment fragment = fragments.get(position);
        FragmentTransaction ft = fm.beginTransaction();
        ft.show(fragment);
        ft.commitAllowingStateLoss();
    }

    public void hideFragments() {
        FragmentTransaction ft = fm.beginTransaction();
        for (Fragment fragment : fragments) {
            if (fragment != null) {
                ft.hide(fragment);
            }
        }
        ft.commitAllowingStateLoss();
    }

    public Fragment getFragment(int position) {
        return fragments.get(position);
    }

    @Override
    public void onResume() {
        super.onResume();
        loadData();
    }

    public void loadData() {
        if (UserPreferences.getUserID() > 0) {
            UserModel.getInstance().getUserInfo().subscribe(new ServicesResponse<User>() {
                @Override
                public void onNext(User user) {
                    nums.clear();
                    userInfo = user;
                    if (userInfo.getCompact()!=0) {
                        Log.e("userInfo.getCompact()", userInfo.getCompact()+"------");
                        nums.add(0);
                    }
                    if (userInfo.getDry()!=0) {
                        nums.add(1);
                        Log.e("userInfo.getDry()", userInfo.getDry()+"------");
                    }
                    if (userInfo.getTolerance()!=0) {
                        nums.add(2);
                        Log.e("userInfo.getTolerance()", userInfo.getTolerance()+"------");
                    }
                    if (userInfo.getPigment()!=0) {
                        nums.add(3);
                        Log.e("userInfo.getPigment()", userInfo.getPigment()+"------");
                    }
                    Log.e("nums", nums.size() + "--大小 boolean--" + UserPreferences.getIsShowTest());
                    if (nums.size() == 4 && !UserPreferences.getIsShowTest()) {
                        showFragment(1);
                    } else {
                        showFragment(0);
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
