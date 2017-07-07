package com.miguan.yjy.model;

import com.dsk.chain.model.AbsModel;
import com.miguan.yjy.R;
import com.miguan.yjy.model.bean.Product;
import com.miguan.yjy.model.bean.Skin;
import com.miguan.yjy.model.bean.Test;
import com.miguan.yjy.model.local.UserPreferences;
import com.miguan.yjy.model.services.DefaultTransform;
import com.miguan.yjy.model.services.ServicesClient;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;

import static com.miguan.yjy.model.services.ServicesClient.getServices;

/**
 * @作者 cjh
 * @日期 2017/3/31 16:59
 * @描述
 */

public class TestModel extends AbsModel {

    public static TestModel getInstantce() {
        return getInstance(TestModel.class);
    }

    public ArrayList<Test> setTestData() {
        ArrayList<Test> tests = new ArrayList<>();
        int[] resImg = {R.mipmap.ic_test_guide_wrinkle, R.mipmap.ic_test_guide_oily, R.mipmap.ic_test_guide_sensitive, R.mipmap.ic_test_guide_pigment};
        int[] strTitle = {R.string.tv_test_wrinkele, R.string.tv_test_oily, R.string.tv_test_sensitive, R.string.tv_test_pigment};
        int[] strDescribe = {R.string.tv_test_wrinkele_describe, R.string.tv_test_oily_describe, R.string.tv_test_sensitive_describe, R.string.tv_test_pigment_describe};
        for (int i = 0; i < 4; i++) {
            Test test = new Test();
            test.setImg(resImg[i]);
            test.setTitle(strTitle[i]);
            test.setDescribe(strDescribe[i]);
            tests.add(test);
        }
        return tests;
    }


    public Observable<List<Test>> getTestList() {
        List<Test> tests = new ArrayList<>();
        int[] resImg = {R.mipmap.ic_test_guide_wrinkle, R.mipmap.ic_test_guide_oily, R.mipmap.ic_test_guide_sensitive, R.mipmap.ic_test_guide_pigment};
        int[] strTitle = {R.string.tv_test_wrinkele, R.string.tv_test_oily, R.string.tv_test_sensitive, R.string.tv_test_pigment};
        int[] strDescribe = {R.string.tv_test_wrinkele_describe, R.string.tv_test_oily_describe, R.string.tv_test_sensitive_describe, R.string.tv_test_pigment_describe};
        String[] strings = {"洗面奶", "测试2", "测试3", "测试4"};
        for (int i = 0; i < 4; i++) {
            Test test = new Test();
            test.setImg(resImg[i]);
            test.setTitle(strTitle[i]);
            test.setTestName(strings[i]);
            tests.add(test);
        }
        return Observable.just(tests);
    }

    public Observable<List<Skin>> getSkinList() {
        List<Skin> tests = new ArrayList<>();
        int[] valuate = {11, 22,33,44};
        int[] resImg = {R.mipmap.ic_test_guide_wrinkle, R.mipmap.ic_test_guide_oily, R.mipmap.ic_test_guide_sensitive, R.mipmap.ic_test_guide_pigment};
        int[] strTitle = {R.string.tv_test_wrinkele, R.string.tv_test_oily, R.string.tv_test_sensitive, R.string.tv_test_pigment};
        int[] strDescribe = {R.string.tv_test_wrinkele_describe, R.string.tv_test_oily_describe, R.string.tv_test_sensitive_describe, R.string.tv_test_pigment_describe};
        String[] strings = {"洗面奶", "测试2", "测试3", "测试4"};
        for (int i = 0; i < 4; i++) {
            Skin skin = new Skin();
            skin.setValuate(valuate[i]);
            tests.add(skin);
        }
        return Observable.just(tests);
    }

    /**
     * 肤质提交接口
     *
     * @param type
     * @param value
     */

    public Observable<String> saveSkin(String type, int value) {

        return getServices().saveSkin(UserPreferences.getUserID(), type, value).compose(new DefaultTransform<>());
    }

    /**
     * 用户肤质接口
     */

    public Observable<Test> userSkin() {
        return getServices().userSkin(UserPreferences.getUserID()).compose(new DefaultTransform<>());
    }

    /**
     * 肤质推荐接口
     */
    public Observable<Test> getSkinRecommend() {
        return Observable.zip(getServices().getSkinRecommend(UserPreferences.getUserID()),
                getServices().userSkin(UserPreferences.getUserID()),
                (test, test2) -> {
                    test.setStar(test2.getStar());
                    test.setDesc(test2.getDesc());
                    test.setFeatures(test2.getFeatures());
                    test.setElements(test2.getElements());
                    return test;
                })
                .compose(new DefaultTransform<>());

//        return ServicesClient.getServices().getSkinRecommend(UserPreferences.getUserID()).compose(new DefaultTransform<>());
    }

    /**
     * 肤质推荐列表接口
     */
    public Observable<List<Product>> getSkinRecommendList(String cateId,float min,float max, int page) {
        return getServices().getSkinRecommendList(UserPreferences.getUserID(), cateId,min,max, page, 10).compose(new DefaultTransform<>());
    }

    /**
     * 肤质推荐接口
     */
    public Observable<Test> getSkinRecom() {
      return ServicesClient.getServices().getSkinRecommend(UserPreferences.getUserID()).compose(new DefaultTransform<>());
    }



}
