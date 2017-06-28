package com.miguan.yjy.model;

import com.dsk.chain.model.AbsModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.miguan.yjy.model.bean.Ask;
import com.miguan.yjy.model.bean.Brand;
import com.miguan.yjy.model.bean.BrandAll;
import com.miguan.yjy.model.bean.BrandList;
import com.miguan.yjy.model.bean.Component;
import com.miguan.yjy.model.bean.EntityRoot;
import com.miguan.yjy.model.bean.Evaluate;
import com.miguan.yjy.model.bean.Product;
import com.miguan.yjy.model.bean.ProductList;
import com.miguan.yjy.model.bean.UserProduct;
import com.miguan.yjy.model.local.UserPreferences;
import com.miguan.yjy.model.services.DefaultTransform;
import com.miguan.yjy.model.services.ServicesClient;
import com.miguan.yjy.utils.LUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import rx.Observable;

/**
 * @作者 cjh
 * @日期 2017/3/21 14:04
 * @描述
 */

public class ProductModel extends AbsModel {

    private final String EXTRA_BRAND_LIST = "brand_list";

    private final String EXTRA_PRODUCT_LIST = "product_list";

    public static final int TYPE_PRODUCT = 1;

    public static ProductModel getInstance() {
        return getInstance(ProductModel.class);
    }

    /**
     * 大家都在搜
     */
    public Observable<List<Product>> searchHot() {
        return ServicesClient.getServices().searchHot().compose(new DefaultTransform<>());
    }

    /**
     * 搜索联想
     */
    public Observable<ProductList> searchAssociate(String keywords) {
        return ServicesClient.getServices().searchAssociate(keywords).compose(new DefaultTransform<>());
    }

    /**
     * 搜索结果接口
     */
    public Observable<ProductList> searchQuery(String keywords, int type, int cate_id, String effect, int page) {
        return ServicesClient.getServices().searchQuery(keywords, type, cate_id, effect, page).compose(new DefaultTransform<>());
    }

    /**
     * 添加产品库的产品列表
     */
    public Observable<EntityRoot<List<Product>>> getProductList(String keywords, int brandId, int page) {
        return ServicesClient.getServices().productList(keywords, brandId, 0, page)
                .map(listEntityRoot -> {
                    if (page == 1) {
                        List<Product> list = queryProducts();
                        if (list != null && list.size() > 0) {
                            for (Product product : list) {
                                if (product.getProduct_name().contains(keywords) && !listEntityRoot.getData().contains(product))
                                    listEntityRoot.getData().add(0, product);
                            }
                        }
                    }
                    return listEntityRoot;
                })
                .compose(new DefaultTransform<>());
    }

    public Observable<Product> getProductDetail(int productId) {
        return ServicesClient.getServices().productDetail(productId, UserPreferences.getUserID()).compose(new DefaultTransform<>());
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

    /**
     * 批号查询
     *
     * @param brandId
     * @param number
     * @return
     */
    public Observable<UserProduct> queryCode(int brandId, String number) {
        return ServicesClient.getServices().queryCode(brandId, number).compose(new DefaultTransform<>());
    }

    /**
     * 获取品牌列表
     *
     * @return
     */
    public Observable<BrandList> getBrandList(int type) {
        return ServicesClient.getServices().brandList(type)
                .map(brandList -> {
                    List<Brand> brands = queryBrands();
                    if (brands != null && brands.size() > 0) {
                        brandList.getCosmeticsList().addAll(queryBrands());
                    }
                    Collections.sort(brandList.getCosmeticsList(),
                            (brandFirst, brandSecond) -> brandFirst.getLetter().compareTo(brandSecond.getLetter()));
                    return brandList;
                })
                .compose(new DefaultTransform<>());
    }

    /**
     * 添加品牌到本地
     * @param brand
     */
    public void insertBrand(Brand brand) {
        List<Brand> brands = queryBrands();
        if (brands == null) brands = new ArrayList<>();
        brands.add(brand);
        LUtils.getPreferences().edit().putString(EXTRA_BRAND_LIST, new Gson().toJson(brands)).apply();
    }

    /**
     * 添加品牌到本地
     * @param brand
     */
    public void deleteBrand(Brand brand) {
        List<Brand> list = queryBrands();
        List<Brand> delList = new ArrayList<>();
        if (list != null) {
            for (Brand item : list) {
                if (!item.getName().equals(brand.getName())){
                    delList.add(item);
                }
            }
        }
        LUtils.getPreferences().edit().putString(EXTRA_BRAND_LIST, new Gson().toJson(delList)).apply();
    }

    /**
     * 从数据库查询品牌列表
     *
     * @return
     */
    private List<Brand> queryBrands() {
        return new Gson().fromJson(
                LUtils.getPreferences().getString(EXTRA_BRAND_LIST, ""),
                new TypeToken<List<Brand>>() {}.getType()
        );
    }

    /**
     * 插入本地产品
     * @param product
     */
    public void insertProduct(Product product) {
        List<Product> products = queryProducts();
        if (products == null) products = new ArrayList<>();
        products.add(product);
        LUtils.getPreferences().edit().putString(EXTRA_PRODUCT_LIST, new Gson().toJson(products)).apply();
    }

    /**
     * 删除本地产品
     * @param product
     */
    public void deleteProduct(Product product) {
        List<Product> list = queryProducts();
        List<Product> delList = new ArrayList<>();
        if (list != null) {
            for (Product item : list) {
                if (!item.getProduct_name().equals(product.getProduct_name())){
                    delList.add(item);
                }
            }
        }
        LUtils.getPreferences().edit().putString(EXTRA_PRODUCT_LIST, new Gson().toJson(delList)).apply();
    }

    // 获取本地产品列表
    public List<Product> queryProducts() {
        return new Gson().fromJson(
                LUtils.getPreferences().getString(EXTRA_PRODUCT_LIST, ""),
                new TypeToken<List<Product>>() {}.getType()
        );
    }

    /**
     * 添加收藏（长草）
     *
     * @param productId
     * @return
     */
    public Observable<String> addLike(int productId) {
        return ServicesClient.getServices().addStar(productId, UserPreferences.getUserID(), 1).compose(new DefaultTransform<>());
    }

    /**
     * 添加到我的产品库
     *
     * @return
     */
    public Observable<String> addRepository(int brandId, String brandName, String product, String img, int isSeal, String sealTime, int qualityTime, String overdueTime) {
        return ServicesClient.getServices().addRepository(
                UserPreferences.getUserID(), brandId, brandName, product, img, isSeal, sealTime, qualityTime, overdueTime
        ).compose(new DefaultTransform<>());
    }

    /**
     * 写点评
     */
    public Observable<String> addEvaluate(int productId, int star, String content) {
        return ServicesClient.getServices()
                .addEvaluate(productId, UserPreferences.getUserID(), TYPE_PRODUCT, star, 0, "", content)
                .compose(new DefaultTransform<>());
    }

    /**
     * 获取用户点评列表
     * id(int) － 对应ID
     * user_id(int) － 用户ID，未登录可为空
     * type(int) － 类型 1-产品，2-文章
     * orderBy(string) － 排序方式-默认default综合排序，skin 肤质排序
     * condition(string) － 筛选星级,目前有'Praise'好评,'middle'中评,'bad'差评
     * page(int) － 当前页数
     * pageSize(int) － 每页多少条
     */
    public Observable<List<Evaluate>> getEvaluate(int productId, int page, String orderBy, String condition) {
        return ServicesClient.getServices()
                .evaluateList(productId, UserPreferences.getUserID(), TYPE_PRODUCT, orderBy, condition, page)
                .compose(new DefaultTransform<>());
    }

    /**
     * 产品评论列表点赞
     */
    public Observable<String> addEvaluateLike(int evaluateId) {
        return ServicesClient.getServices().addEvaluateLike(evaluateId, UserPreferences.getUserID()).compose(new DefaultTransform<>());
    }

    /**
     * 品牌主页(品牌详情接口)
     */
    public Observable<BrandAll> getBrandInfo(long brandId) {
        return ServicesClient.getServices().brandInfo(brandId).compose(new DefaultTransform<>());
    }

    /**
     * 品牌列表
     */
    public Observable<List<Product>> getProductList(long brandId, int isTop, int page) {
        return ServicesClient.getServices().productList(brandId, isTop, page, 20).compose(new DefaultTransform<>());
    }

    /**
     * 提问列表
     */
    public Observable<Ask> getAskList(int productId, int page) {
        return ServicesClient.getServices().askList(productId, page).compose(new DefaultTransform<>());
    }

    /**
     * 提问详情
     */
    public Observable<Ask> getAskDetail(int productId, int askId, int page) {
        return ServicesClient.getServices().askDetail(productId, askId, page).compose(new DefaultTransform<>());
    }

    /**
     * 提问详情
     */
    public Observable<String> addAsk(int productId, String productName, int askType, int askId, String content) {
        return ServicesClient.getServices()
                .addAsk(UserPreferences.getUserID(), UserPreferences.getUsername(),
                        productId, productName, askType, askId, content)
                .compose(new DefaultTransform<>());
    }

}
