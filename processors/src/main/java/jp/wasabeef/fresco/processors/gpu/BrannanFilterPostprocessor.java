package jp.wasabeef.fresco.processors.gpu;

import android.content.Context;

import com.facebook.cache.common.CacheKey;
import com.facebook.cache.common.SimpleCacheKey;

import jp.co.cyberagent.android.gpuimage.GPUImageFilter;
import jp.wasabeef.fresco.processors.filter.IFBrannanFilter;

/**
 * Copyright (c) 2017/5/16. LiaoPeiKun Inc. All rights reserved.
 */

public class BrannanFilterPostprocessor extends GPUFilterPostprocessor {

    public BrannanFilterPostprocessor(Context context, GPUImageFilter filter) {
        super(context, new IFBrannanFilter(context));
    }

    @Override
    public CacheKey getPostprocessorCacheKey() {
        return new SimpleCacheKey("brannan");
    }
}
