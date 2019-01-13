package com.android.collect.library.http;

import com.android.collect.library.callback.DataResultCallBack;
import com.android.collect.library.http.okhttp.FileRequestCallBack;
import com.android.collect.library.http.okhttp.FileRequestProgressCallBack;

import org.json.JSONObject;

import okhttp3.Call;


public abstract class IDataRequest {

    /**
     * 异步post请求
     *
     * @param url
     * @param jsonParams（JSON格式）
     * @param dataResultCallBack
     */
    public abstract void postRequst(String url, String jsonParams, DataResultCallBack dataResultCallBack);

    /**
     * 同步post请求
     *
     * @param url
     * @param jsonParams（JSON格式）
     * @return
     */
    public abstract JSONObject postRequst(String url, String jsonParams);

    /**
     * 异步下载文件
     *
     * @param url
     * @param saveFileDir
     * @param fileName
     */
    public abstract Call downloadFile(String url, String saveFileDir, String fileName, FileRequestCallBack fileRequestCallBack);

    /**
     * 同步下载文件
     *
     * @param url
     * @param saveFileDir
     * @param fileName
     * @return true成功，false失败
     */
    public abstract boolean downloadFile(String url, String saveFileDir, String fileName);

    /**
     * 同步下载文件(带参数)
     *
     * @param url
     * @param saveFileDir
     * @param fileName
     * @param json
     * @return true成功，false失败
     */
    public abstract boolean downloadFileWithParams(String url, String saveFileDir, String fileName, JSONObject json);

    /**
     * 异步下载文件（带进度）
     *
     * @param url
     * @param saveFileDir
     * @param fileName
     */
    public abstract Call downloadFileWithProgress(String url, String saveFileDir, String fileName, FileRequestProgressCallBack fileRequestProgressCallBack);


    /**
     * 异步上传文件
     *
     * @param url
     * @param fileDir
     * @param fileName
     */
    public abstract Call uploadFile(String url, String fileDir, String fileName, FileRequestCallBack fileRequestCallBack);

    /**
     * 同步上传文件
     *
     * @param url
     * @param fileDir
     * @param fileName
     * @return true成功，false失败
     */
    public abstract boolean uploadFile(String url, String fileDir, String fileName);

    /**
     * 异步上传文件（带进度）
     *
     * @param url
     * @param fileDir
     * @param fileName
     */
    public abstract Call uploadFileWithProgress(String url, String fileDir, String fileName, FileRequestProgressCallBack fileRequestProgressCallBack);

}
