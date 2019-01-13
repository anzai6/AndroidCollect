package com.android.collect.library.ui.main.contract;

import com.android.collect.library.base.BasePresenter;
import com.android.collect.library.base.BaseView;


/**
 * Created by anzai on 2017/7/11.
 */

public interface MainActivityContract {

    interface Presenter extends BasePresenter {
    }

    interface View extends BaseView {
        void onLoginSuccess();

        void onLoginOut();
    }
}
