package com.dsk.chain.model;

import android.os.Handler;
import android.os.Looper;

import java.util.Queue;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * Copyright (c) 2017/1/4. LiaoPeiKun Inc. All rights reserved.
 */

public class BackThread extends Thread {

    private Handler mHandler;

    private Queue<Runnable> mQueue = new LinkedBlockingDeque<>();

    @Override
    public void run() {
        Looper.prepare();
        mHandler = new Handler();
        for (Runnable runnable : mQueue) {
            mHandler.post(runnable);
        }
        Looper.loop();
    }

    public void execute(Runnable runnable) {
        if (mHandler == null) mQueue.offer(runnable);
        else mHandler.post(runnable);
    }

    public void quit() {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                Looper.myLooper().quit();
            }
        });
    }

}
