package com.miguan.yjy.model;

import com.dsk.chain.model.AbsModel;
import com.miguan.yjy.model.bean.Brand;
import com.miguan.yjy.model.bean.Component;
import com.miguan.yjy.model.bean.Product;
import com.miguan.yjy.model.local.UserPreferences;
import com.miguan.yjy.model.services.DefaultTransform;
import com.miguan.yjy.model.services.ServicesClient;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;

/**
 * @作者 cjh
 * @日期 2017/3/21 14:04
 * @描述
 */

public class ProductModel extends AbsModel {
    public static ProductModel getInstantce() {
        return getInstance(ProductModel.class);
    }

    public Observable<List<Product>> getSearchList() {
        List<Product> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Product product = new Product();
            product.setProduct_name("sss");
            list.add(product);
        }
        return Observable.just(list);
    }

    public Observable<Product> getProductDetail(int productId) {
//        Product product = new Product();
//        product.setProduct_name("hahaha");
//        product.setBrand("理肤泉");
//        return Observable.just(product).compose(new DefaultTransform<>());
        return ServicesClient.getServices().productDetail(productId, UserPreferences.getUserID()).compose(new DefaultTransform<>());
   }

    public Observable<Product> queryCode(String brand, String name) {
        return ServicesClient.getServices().queryCode(brand, name).compose(new DefaultTransform<>());
    }

    public Observable<List<Component>> getReadList() {
        List<Component> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Component component = new Component();
            component.setName("成分名");
            list.add(component);
        }
        return Observable.just(list);
    }

    public List<Component> getReadListData() {
        List<Component> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Component component = new Component();
            component.setName("成分名");
            list.add(component);
        }
        return list;
    }

    public Observable<Brand> getBrandList() {
        List<Brand> hot = new ArrayList<>();
        for (int i=0; i<4; i++) {
            Brand brand = new Brand();
            brand.setName("Biotherm 碧欧泉");
            brand.setLetter("B");
            hot.add(brand);
        }
        String[] letters = new String[] {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S",
                "T", "U", "V", "W", "X", "Y", "Z"};
        List<Brand> other = new ArrayList<>();
        for (String letter : letters) {
            for (int i = 0; i < 5; i++) {
                Brand brand = new Brand();
                brand.setName("安娜苏");
                brand.setLetter(letter);
                other.add(brand);
            }
        }
        Brand brand = new Brand();
        brand.setHot(hot);
        brand.setOther(other);
        return Observable.just(brand).compose(new DefaultTransform<>());
    }

    public Observable<Product> like(int productId) {
        return ServicesClient.getServices().productLike(productId, UserPreferences.getUserID(), 1).compose(new DefaultTransform<>());
    }

}
