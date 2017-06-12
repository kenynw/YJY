package jp.wasabeef.fresco.processors.gpu;

import android.content.Context;

import com.facebook.cache.common.CacheKey;
import com.facebook.cache.common.SimpleCacheKey;

import jp.wasabeef.fresco.processors.filter.IFValenciaFilter;

/**
 * Copyright (c) 2017/5/16. LiaoPeiKun Inc. All rights reserved.
 */

public class ValenciaFilterPostprocessor extends GPUFilterPostprocessor {

    public ValenciaFilterPostprocessor(Context context) {
        super(context, new IFValenciaFilter(context));
    }

    @Override
    public CacheKey getPostprocessorCacheKey() {
        return new SimpleCacheKey("valencia");
    }
}
