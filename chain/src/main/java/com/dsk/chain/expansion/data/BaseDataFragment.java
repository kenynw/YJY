package com.dsk.chain.expansion.data;

import com.dsk.chain.bijection.ChainBaseActivity;
import com.dsk.chain.bijection.ChainFragment;

/**
 *
 */
public class BaseDataFragment<PresenterType extends BaseDataFragmentPresenter, M> extends ChainFragment<PresenterType> {

    public void setData(M m) {}

    public void onError(Throwable throwable) {
        ((ChainBaseActivity) getActivity()).getExpansionDelegate().showErrorToast(throwable.getMessage());
    }

}
