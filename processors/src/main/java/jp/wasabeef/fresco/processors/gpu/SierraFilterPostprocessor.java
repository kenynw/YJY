package jp.wasabeef.fresco.processors.gpu;

import android.content.Context;

import com.facebook.cache.common.CacheKey;
import com.facebook.cache.common.SimpleCacheKey;

import jp.wasabeef.fresco.processors.filter.IFSierraFilter;

/**
 * Copyright (c) 2017/5/16. LiaoPeiKun Inc. All rights reserved.
 */

public class SierraFilterPostprocessor extends GPUFilterPostprocessor {

    public SierraFilterPostprocessor(Context context) {
        super(context, new IFSierraFilter(context));
    }

    @Override
    public CacheKey getPostprocessorCacheKey() {
        return new SimpleCacheKey("sierra");
    }
}
