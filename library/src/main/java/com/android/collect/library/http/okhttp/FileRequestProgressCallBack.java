package com.android.collect.library.http.okhttp;

/**
 * 文件上传或者下载回调
 */
public interface FileRequestProgressCallBack {

    public abstract void onFailed();

    public abstract void onSucceed();

    /**
     * @param progress 已下载或者上传百分比进度（即50代表已下载或者上传50%）
     * @param done     是否下载或上传完成
     */
    public abstract void onProgress(int progress, boolean done);

}
