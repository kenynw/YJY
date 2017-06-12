package jp.wasabeef.fresco.processors.gpu;

import android.content.Context;

import com.facebook.cache.common.CacheKey;
import com.facebook.cache.common.SimpleCacheKey;

import jp.wasabeef.fresco.processors.filter.IFRiseFilter;

/**
 * Copyright (c) 2017/5/16. LiaoPeiKun Inc. All rights reserved.
 */

public class RiseFilterPostprocessor extends GPUFilterPostprocessor {

    public RiseFilterPostprocessor(Context context) {
        super(context, new IFRiseFilter(context));
    }

    @Override
    public CacheKey getPostprocessorCacheKey() {
        return new SimpleCacheKey("rise");
    }
}
