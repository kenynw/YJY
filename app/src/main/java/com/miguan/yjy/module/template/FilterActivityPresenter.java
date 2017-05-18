package com.miguan.yjy.module.template;

import com.dsk.chain.bijection.Presenter;
import com.miguan.yjy.module.template.FilterAdapter.FilterType;

import java.util.ArrayList;
import java.util.List;

/**
 * Copyright (c) 2017/4/26. LiaoPeiKun Inc. All rights reserved.
 */

public class FilterActivityPresenter extends Presenter<FilterActivity> {

    private FilterAdapter mAdapter;

    public FilterAdapter createAdapter() {
        if (mAdapter == null) {
            List<FilterType> dataSet = new ArrayList<>();
            dataSet.add(FilterType.Original);
            dataSet.add(FilterType.Grayscale);
            dataSet.add(FilterType.ColorFilter);
            dataSet.add(FilterType.Pixel);
            dataSet.add(FilterType.Vignette);
            dataSet.add(FilterType.Brightness);
            dataSet.add(FilterType.Sketch);
            dataSet.add(FilterType.Invert);
            dataSet.add(FilterType.Contrast);
            dataSet.add(FilterType.Sepia);
            dataSet.add(FilterType.Toon);
            dataSet.add(FilterType.Amaro);
            dataSet.add(FilterType.IF1997);

            mAdapter = new FilterAdapter(getView(), dataSet);
            mAdapter.setOnFilterSelectedListener(getView());
        }
        return mAdapter;
    }

}
