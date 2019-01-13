package com.android.collect.ui.main.contract;

import com.android.collect.bean.AdvertDetail;
import com.android.collect.library.base.BasePresenter;
import com.android.collect.library.base.BaseView;

import java.util.List;

/**
 * Created by anzai on 2017/7/11.
 */

public interface AdvertContract {

    interface Presenter extends BasePresenter {

        /**
         * 广告跳转
         *
         * @param position 广告坐标
         */
        void toAdvertTarget(int position);
    }

    interface View extends BaseView {
        void onGetAdertBitmapResult(List<AdvertDetail> list);
    }
}
