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
            dataSet.add(FilterType.Amaro);
            dataSet.add(FilterType.Brannan);
            dataSet.add(FilterType.Hudson);
            dataSet.add(FilterType.Inkwell);
            dataSet.add(FilterType.Rise);
            dataSet.add(FilterType.Sierra);
            dataSet.add(FilterType.Sutro);
            dataSet.add(FilterType.Valencia);
            dataSet.add(FilterType.Xproll);

            mAdapter = new FilterAdapter(getView(), dataSet);
            mAdapter.setOnFilterSelectedListener(getView());
        }
        return mAdapter;
    }

}
