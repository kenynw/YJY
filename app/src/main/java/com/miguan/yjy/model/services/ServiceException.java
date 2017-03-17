package com.miguan.yjy.model.services;

import java.io.IOException;

/**
 * Copyright (c) 2015. LiaoPeiKun Inc. All rights reserved.
 */

public class ServiceException extends IOException {

    private int code;

    private String msg;

    public ServiceException(int code, String msg) {
        super(msg);
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

}
