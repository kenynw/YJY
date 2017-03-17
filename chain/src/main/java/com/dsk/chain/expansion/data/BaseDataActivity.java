package com.dsk.chain.expansion.data;

import com.dsk.chain.bijection.ChainBaseActivity;
import com.dsk.chain.bijection.RequiresPresenter;

/**
 * Copyright (c) 2015. LiaoPeiKun Inc. All rights reserved.
 */
@RequiresPresenter(BaseDataActivityPresenter.class)
public class BaseDataActivity<P extends BaseDataActivityPresenter, M> extends ChainBaseActivity<P> {

    public void setData(M m) {}

    public void onError(Throwable e) {
        getExpansionDelegate().showErrorToast(e.getMessage());
    }

}
