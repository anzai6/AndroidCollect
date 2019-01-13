package com.android.collect.library.callback;

/**
 * Created by anzai on 2017/4/19.
 */

public interface OnPermissionCallBack {

    void onSuccess(int requestCode);

    void onError(int requestCode);

}
