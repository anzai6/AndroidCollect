package com.android.collect.library.http.okhttp;

/**
 * 请求体进度回调接口，比如用于文件上传中
 * Created by anzai on 2017/7/12.
 */

public interface  ProgressRequestListener {
    /**
     *
     * @param bytesWritten 已上传字节数
     * @param contentLength 总字节数
     * @param done 是否上传完成
     */
    void onRequestProgress(long bytesWritten, long contentLength, boolean done);
}
