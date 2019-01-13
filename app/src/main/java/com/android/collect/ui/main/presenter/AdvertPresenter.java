package com.android.collect.ui.main.presenter;

import android.app.Activity;
import android.os.Bundle;

import com.android.collect.bean.AdvertDetail;
import com.android.collect.library.common.FileConstant;
import com.android.collect.library.util.LogUtils;
import com.android.collect.ui.main.contract.AdvertContract;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.disposables.Disposable;

public class AdvertPresenter implements AdvertContract.Presenter {

    private final String TAG = LogUtils.getLogTag(AdvertPresenter.class);

    private AdvertContract.View mAdvertView;
    private Activity mActivity;
    private final String ADV_FLAG = "1";
    private String imgFileDir = "";
    private List<AdvertDetail> list;

    private Disposable mDisposable;

    public AdvertPresenter(Activity activity, AdvertContract.View mAdvertView) {
        this.mAdvertView = mAdvertView;
        this.mActivity = activity;
        imgFileDir = FileConstant.APP_FILE_SD_ADVERT_IMG_DIR;
    }


    @Override
    public void subscribe(Bundle bundle) {
        list = new ArrayList<>();
        mAdvertView.onGetAdertBitmapResult(list);
    }

    @Override
    public void toAdvertTarget(int position) {
    }


    @Override
    public void unSubscribe() {

    }
}
