package com.dsk.chain.bijection;

import android.app.Activity;

/**
 * Copyright (c) 2015. LiaoPeiKun Inc. All rights reserved.
 */
public interface ActivityLifeCycleDelegateProvide {
    ActivityLifeCycleDelegate createActivityLifeCycleDelegate(Activity activity);
}
