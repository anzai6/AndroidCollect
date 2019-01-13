package com.android.collect.library.http.okhttp;

/**
 * 响应体进度回调接口，比如用于文件下载中
 * Created by anzai on 2017/7/12.
 */
public interface ProgressResponseListener {
    /**
     * @param bytesRead 已下载字节数
     * @param contentLength 总字节数
     * @param done 是否下载完成
     */
    void onResponseProgress(long bytesRead, long contentLength, boolean done);
}
