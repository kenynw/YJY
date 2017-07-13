package com.miguan.yjy.model;

import android.content.Context;

import com.alibaba.sdk.android.oss.ClientException;
import com.alibaba.sdk.android.oss.OSS;
import com.alibaba.sdk.android.oss.OSSClient;
import com.alibaba.sdk.android.oss.ServiceException;
import com.alibaba.sdk.android.oss.callback.OSSCompletedCallback;
import com.alibaba.sdk.android.oss.common.auth.OSSCredentialProvider;
import com.alibaba.sdk.android.oss.common.auth.OSSPlainTextAKSKCredentialProvider;
import com.alibaba.sdk.android.oss.model.PutObjectRequest;
import com.alibaba.sdk.android.oss.model.PutObjectResult;
import com.dsk.chain.model.AbsModel;
import com.miguan.yjy.model.services.DefaultTransform;
import com.miguan.yjy.utils.LUtils;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import rx.Observable;

/**
 * Copyright (c) 2017/1/18. LiaoPeiKun Inc. All rights reserved.
 */

public class ImageModel extends AbsModel {

    public static final String OSS_PATH_REPOSITORY = "user_product";

    private static final String ENDPOINT = "http://oss-cn-shenzhen.aliyuncs.com";

    private static final String OSS_PATH = (LUtils.isDebug ? "cs/" : "") + "uploads/";

    private static final String OSS_BUCKET = "oss1-yjyapp-com";

    private OSS mOSS;

    public static ImageModel getInstance() {
        return getInstance(ImageModel.class);
    }

    @Override
    protected void onAppCreate(Context context) {
        // 明文设置secret的方式建议只在测试时使用，更多鉴权模式请参考后面的`访问控制`章节
        OSSCredentialProvider credentialProvider = new OSSPlainTextAKSKCredentialProvider("LTAIdIBG1Bsmgyo7", "bB0G2tXWigjo2hWu1iMKCBPLdmbYOq");
        mOSS = new OSSClient(context, ENDPOINT, credentialProvider);
    }

    /**
     * 异步上传图片到默认路径
     *
     * @return
     */
    public Observable<String> uploadImageAsync(String filePath) {
        return uploadImageAsync("photo", filePath);
    }

    /**
     * 异步上传图片,指定路径
     *
     * @return
     */
    public Observable<String> uploadImageAsync(String ossPath, String filePath) {
        return Observable.just(createFilePath(ossPath))
                .doOnNext(s -> {
                    // 构造上传请求
                    PutObjectRequest put = new PutObjectRequest(OSS_BUCKET, OSS_PATH + s, filePath);
                    mOSS.asyncPutObject(put, new OSSCompletedCallback<PutObjectRequest, PutObjectResult>() {
                        @Override
                        public void onSuccess(PutObjectRequest request, PutObjectResult result) {
                            LUtils.toast("图片已上传");
                        }

                        @Override
                        public void onFailure(PutObjectRequest request, ClientException clientExcepion, ServiceException serviceException) {
                            LUtils.log("onFailure");
                            LUtils.toast("图片上传失败");
                            // 请求异常
                            if (clientExcepion != null) {
                                // 本地异常如网络异常等
                                clientExcepion.printStackTrace();
                                LUtils.toast("网络异常");
                            }
                            if (serviceException != null) {
                                LUtils.toast("图片上传失败");
                            }
                        }
                    });
                }).compose(new DefaultTransform<>());
    }

    /**
     * 同步上传图片
     *
     * @param file
     * @return
     */
    public Observable<String> uploadImageSync(String ossPath, File file) {
        return Observable.just(createFilePath(ossPath))
                .flatMap(s -> Observable.create((Observable.OnSubscribe<String>) subscriber -> {
                    // 构造上传请求
                    PutObjectRequest put = new PutObjectRequest(OSS_BUCKET, OSS_PATH + s, file.getPath());
                    try {
                        mOSS.putObject(put);
                        subscriber.onNext(s);
//                        LUtils.toast("图片已上传");
                    } catch (ClientException e) {
                        e.printStackTrace();
                        subscriber.onError(new Throwable("网络异常"));
                        LUtils.toast("网络异常");
                    } catch (ServiceException e) {
                        e.printStackTrace();
                        subscriber.onError(new Throwable("图片上传失败"));
                        LUtils.toast("图片上传失败");
                    }
                }))
                .compose(new DefaultTransform<>());
    }

    public String createFilePath(String path) {
        long timeMillis = System.currentTimeMillis();
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd", Locale.CHINA);
        String date = format.format(new Date(timeMillis));

        String fileName = String.valueOf(timeMillis) + String.valueOf((int) (Math.random() * 1000000 + 100000));

        return path + File.separator + date + File.separator + fileName + ".jpg";
    }

}
