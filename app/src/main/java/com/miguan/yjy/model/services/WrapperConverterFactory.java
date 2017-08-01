package com.miguan.yjy.model.services;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

/**
 * Copyright (c) 2015. LiaoPeiKun Inc. All rights reserved.
 */

public class WrapperConverterFactory extends Converter.Factory {

    private final Gson gson;

    public static WrapperConverterFactory create() {
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm").create();
        return create(gson);
    }

    public static WrapperConverterFactory create(Gson gson) {
        return new WrapperConverterFactory(gson);
    }

    private WrapperConverterFactory(Gson gson) {
        if (gson == null) throw new NullPointerException("gson == null");
        this.gson = gson;
    }

    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
        return new WrapperResponseBodyConverter<>(type);
    }

}
