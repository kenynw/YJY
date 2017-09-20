package com.miguan.yjy.module.test;

import com.dsk.chain.expansion.data.BaseDataActivityPresenter;
import com.miguan.yjy.model.TestModel;
import com.miguan.yjy.model.UserModel;
import com.miguan.yjy.model.bean.Test;
import com.miguan.yjy.model.bean.User;
import com.miguan.yjy.model.services.ServicesResponse;
import com.miguan.yjy.module.user.ProfilePresenter;

import rx.Observable;
import rx.functions.Func1;

/**
 *
 * 肤质测试P
 *
 */
public class TestInitActivityPresenter extends BaseDataActivityPresenter<TestInitActivity, Test> {

    private User mUser;

    @Override
    protected void onResume() {
        super.onResume();
        loadData();
    }

    public void loadData() {
        TestModel.getInstantce().userSkin().map(test -> {
            if (test.getDesc() == null || test.getDesc().size() == 0) {
                UserModel.getInstance().getUserInfo().subscribe(new ServicesResponse<User>() {
                    @Override
                    public void onNext(User user) {
                        mUser = user;
                    }
                });
            }
            return test;
        }).unsafeSubscribe(getDataSubscriber());
    }

    public void modifyProfile(int tag, String birthday, int sex) {
        UserModel.getInstance().modifyProfile(ProfilePresenter.KEY_PROFILE_BIRTHDAY, birthday)
                .flatMap(new Func1<String, Observable<String>>() {
                    @Override
                    public Observable<String> call(String s) {
                        return UserModel.getInstance().modifyProfile("sex", sex + "");
                    }
                })
                .subscribe(new ServicesResponse<String>() {
                    @Override
                    public void onNext(String s) {
                        startTest(tag);
                    }
                });
    }

    public void startTest(int tag) {
        switch (tag) {
            case 0:
                TestGuidePresenter.start(getView(), TestModel.getInstantce().setTestData().get(0), TestGuideActivity.EXTRA_TEST_FOUR_TYPE);
                break;
            case 1:
                TestGuidePresenter.start(getView(), TestModel.getInstantce().setTestData().get(1), TestGuideActivity.EXTRA_TEST_FIRST_TYPE);
                break;
            case 2:
                TestGuidePresenter.start(getView(), TestModel.getInstantce().setTestData().get(2), TestGuideActivity.EXTRA_TEST_SECOND_TYPE);
                break;
            case 3:
                TestGuidePresenter.start(getView(), TestModel.getInstantce().setTestData().get(3), TestGuideActivity.EXTRA_TEST_THIRD_TYPE);
                break;
        }
    }

    public User getUser() {
        return mUser;
    }

}