package com.miguan.yjy.model.services;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.miguan.yjy.model.bean.EntityList;
import com.miguan.yjy.utils.LUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Type;

import okhttp3.ResponseBody;
import retrofit2.Converter;

/**
 * Copyright (c) 2015. LiaoPeiKun Inc. All rights reserved.
 */

public class WrapperResponseBodyConverter<T> implements Converter<ResponseBody, T> {

    private static final String TAG = "WrapperResponseBodyConverter";

    private final Type mType;

    WrapperResponseBodyConverter(Type type) {
        this.mType = type;
    }

    @Override
    public T convert(ResponseBody value) throws IOException {
        try {
            JSONObject data = new JSONObject(value.string());

            LUtils.log(TAG, data.toString());

            int status = data.getInt("status");
            if (status != 1) {
                throw new ServiceException(status, data.getString("msg"));
            }

            String result = "";
            if (TypeToken.get(mType).toString().contains("EntityRoot")) {
                return new Gson().fromJson(data.toString(), mType);
            } else if (data.has("msg")) {
                if (!data.isNull("msg")) result = data.opt("msg").toString();
                else return null;
            } else if (data.has("data")) {
                if (!data.isNull("data")) result = data.opt("data").toString();
                else return null;
            } else {
                return new Gson().fromJson(data.toString(), mType);
            }

            T entity = new Gson().fromJson(result, mType);

            if (entity instanceof EntityList && !data.isNull("pageTotal")) {
                ((EntityList) entity).setPageTotal(data.optInt("pageTotal"));
            }

            return entity;
        } catch (JSONException | JsonSyntaxException e) {
            LUtils.log(Log.getStackTraceString(e));
            throw new ServiceException(0, "数据解析错误");
        }
    }

}
