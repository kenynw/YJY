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

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();

        String methodStr = request.method(); //获取请求方式
        RequestBody body = request.body(); //获取请求body
        if (methodStr.equals("GET")) {
            HttpUrl requestUrl = request.url().newBuilder()
                    .addQueryParameter("time", "123")
                    .addQueryParameter("from","android")
                    .build(); //获取请求url地址

            HttpUrl newUrl = new HttpUrl.Builder()
                    .scheme(requestUrl.scheme())
                    .host(requestUrl.host())
                    .encodedPath(requestUrl.encodedPath())
                    .addQueryParameter("token", passportEncrypt(requestUrl.query()))
                    .build();

//            LUtils.log("url: " + newUrl.toString() + ", \nquery: " + requestUrl.toString());

            request = request.newBuilder().method(methodStr, request.body()).url(newUrl).build();
        }

//        if (body != null && body instanceof FormBody) {
//            FormBody formBody = (FormBody) body;
//            for (int i=0; i<formBody.size(); i++) {
//                LUtils.log("name: " + formBody.encodedName(i) + ", value: " + formBody.encodedValue(i));
//            }
//        }
//
//        String bodyStr = (body==null?"":body.toString());

        return chain.proceed(request);
    }

    // 直接抄服务端的加密方式，我也不理解是怎么回事
    private String passportEncrypt(String params) {
        String encryptKey = LUtils.md5("123");
        int ctr = 0;
        String tmp = "";
        for (int i=0; i<params.length(); i++) {
            ctr = (ctr == encryptKey.length() - 1 ? 0 : ctr);
            char xor = (char) (params.charAt(i) ^ encryptKey.charAt(ctr++));
            String entryptStr = String.valueOf(encryptKey.charAt(ctr));
            LUtils.log(entryptStr + String.valueOf(xor));
            tmp += entryptStr + String.valueOf(xor);
        }

        LUtils.log("tmp: " + tmp);
        String passportKey = passportKey(tmp, encryptKey);
//        LUtils.log("passport key: " + passportKey);
        String base64 = new String(Base64.encode(passportKey.getBytes(), Base64.DEFAULT));
//        LUtils.log("base64 passport key: " + base64);
        return base64;
    }

    private String passportKey(String text, String encryptKey) {
        encryptKey = LUtils.md5(encryptKey);
        int ctr = 0;
        String tmp = "";
        for (int i=0; i<text.length(); i++) {
            ctr = ctr == encryptKey.length() ? 0 : ctr;
            tmp += text.charAt(i) ^ encryptKey.charAt(ctr++);
        }

        return tmp;
    }

}
