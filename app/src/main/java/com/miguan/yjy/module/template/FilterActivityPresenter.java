package com.miguan.yjy.module.template;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.SparseArray;

import com.dsk.chain.bijection.Presenter;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;

import jp.co.cyberagent.android.gpuimage.GPUImageFilter;
import jp.wasabeef.fresco.processors.filter.IF1977Filter;
import jp.wasabeef.fresco.processors.filter.IFAmaroFilter;
import jp.wasabeef.fresco.processors.filter.IFBrannanFilter;
import jp.wasabeef.fresco.processors.filter.IFHudsonFilter;
import jp.wasabeef.fresco.processors.filter.IFInkwellFilter;
import jp.wasabeef.fresco.processors.filter.IFNormalFilter;
import jp.wasabeef.fresco.processors.filter.IFRiseFilter;
import jp.wasabeef.fresco.processors.filter.IFSierraFilter;
import jp.wasabeef.fresco.processors.filter.IFValenciaFilter;
import jp.wasabeef.fresco.processors.filter.IFXproIIFilter;

/**
 * Copyright (c) 2017/4/26. LiaoPeiKun Inc. All rights reserved.
 */

public class FilterActivityPresenter extends Presenter<FilterActivity> {

    private SparseArray<GPUImageFilter> mFilters;

    @Override
    protected void onCreate(FilterActivity view, Bundle saveState) {
        super.onCreate(view, saveState);
        mFilters = new SparseArray<>();
    }

    public GPUImageFilter getFilter(int type) {
        if (mFilters.size() > 0 && mFilters.size() > type && mFilters.get(type) != null)
            return mFilters.get(type);
        GPUImageFilter filter;
        switch (type) {
            case 1:
                filter = new IFAmaroFilter(getView());
                break;
            case 2:
                filter = new IFBrannanFilter(getView());
                break;
            case 3:
                filter = new IFHudsonFilter(getView());
                break;
            case 4:
                filter = new IFInkwellFilter(getView());
                break;
            case 5:
                filter = new IFRiseFilter(getView());
                break;
            case 6:
                filter = new IFSierraFilter(getView());
                break;
//            case 7:
//                filter = new IFSutroFilter(mContext);
//                break;
            case 7:
                filter = new IFValenciaFilter(getView());
                break;
            case 8:
                filter = new IFXproIIFilter(getView());
                break;
            case 9:
                filter = new IF1977Filter(getView());
                break;
            default:
                filter = new IFNormalFilter(getView());
                break;
        }
        mFilters.put(type, filter);
        return filter;
    }

    private class FilterSoftRef extends SoftReference<Bitmap> {

        private String _key;

        public FilterSoftRef(String key, Bitmap referent) {
            super(referent);
            _key = key;
        }

        public FilterSoftRef(Bitmap referent, ReferenceQueue<? super Bitmap> q) {
            super(referent, q);
        }

    }

}
