package com.miguan.yjy.model.services;


import android.util.Base64;

import com.miguan.yjy.utils.LUtils;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.RequestBody;
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
        RequestBody body = request.body(); //获取请求body
        if (methodStr.equals("GET")) {
            HttpUrl requestUrl = request.url().newBuilder()
                    .addQueryParameter("time", String.valueOf(System.currentTimeMillis() / 1000))
                    .addQueryParameter("from","android")
                    .build(); //获取请求url地址

//            LUtils.log(TAG, requestUrl.query());
//
//            HttpUrl newUrl = new HttpUrl.Builder()
//                    .scheme(requestUrl.scheme())
//                    .host(requestUrl.host())
//                    .encodedPath(requestUrl.encodedPath())
//                    .addQueryParameter("token", passportEncrypt(requestUrl.query()))
//                    .build();

            request = request.newBuilder().method(methodStr, request.body()).url(requestUrl).build();
        }

        if (LUtils.isDebug) LUtils.log(TAG, request.url().toString());

        return chain.proceed(request);
    }

    private String passportEncrypt(String params) {
        char[] encryptKey = LUtils.md5(String.valueOf(Math.random() * 32000)).toCharArray();

        int ctr = 0;
        String tmp = "";
        for (int i=0; i<params.length(); i++) {
            ctr = (ctr == encryptKey.length ? 0 : ctr);
            String entryptStr = String.valueOf(encryptKey[ctr]);
            char xor = (char) (params.charAt(i) ^ encryptKey[ctr++]);
            tmp += entryptStr + String.valueOf(xor);
        }

        String passportKey = passportKey(tmp, "lspps0tewZEHZ5whWcwe8jVOgdUMKJvk");
        return new String(Base64.encode(passportKey.getBytes(), Base64.DEFAULT));
    }

    private String passportKey(String text, String key) {
        char[] encryptKey = LUtils.md5(key).toCharArray();
        int ctr = 0;
        String tmp = "";
        for (int i=0; i<text.length(); i++) {
            ctr = (ctr == encryptKey.length ? 0 : ctr);
            tmp += String.valueOf((char) (text.charAt(i) ^ encryptKey[ctr++]));
        }

        return tmp;
    }

}
