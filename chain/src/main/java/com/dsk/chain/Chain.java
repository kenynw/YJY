package com.dsk.chain;

import android.app.Activity;

import com.dsk.chain.bijection.ActivityLifeCycleDelegate;
import com.dsk.chain.bijection.ActivityLifeCycleDelegateProvide;


/**
 * Copyright (c) 2015. LiaoPeiKun Inc. All rights reserved.
 */
public class Chain {

    private static ActivityLifeCycleDelegateProvide sLifeCycleDelegateProvide;

    public static ActivityLifeCycleDelegate createActivityLifeCycleDelegate(Activity activity) {
        if (sLifeCycleDelegateProvide != null)
            return sLifeCycleDelegateProvide.createActivityLifeCycleDelegate(activity);
        return null;
    }

    public static void setLifeCycleDelegateProvide(ActivityLifeCycleDelegateProvide lifeCycleDelegateProvide) {
        sLifeCycleDelegateProvide  = lifeCycleDelegateProvide;
    }

}
