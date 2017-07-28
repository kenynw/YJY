package com.miguan.yjy.model.services;


import com.miguan.yjy.utils.LUtils;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Copyright (c) 2017/3/20. LiaoPeiKun Inc. All rights reserved.
 */

public class EncryptInterceptor implements Interceptor {

    private static final String TAG = EncryptInterceptor.class.getSimpleName();

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();

        String methodStr = request.method(); //获取请求方式
        if (methodStr.equals("GET")) {
            HttpUrl requestUrl = request.url().newBuilder()
                    .addQueryParameter("time", String.valueOf(System.currentTimeMillis() / 1000))
                    .addQueryParameter("from","android")
                    .addQueryParameter("version", "111")
                    .build(); //获取请求url地址

            request = request.newBuilder().method(methodStr, request.body()).url(requestUrl).build();
        }

        LUtils.log(TAG, request.url().toString());

        return chain.proceed(request);
    }

}
