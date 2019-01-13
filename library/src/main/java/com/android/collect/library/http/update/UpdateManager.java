package com.android.collect.library.http.update;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.Signature;
import android.view.View;

import com.android.collect.library.R;
import com.android.collect.library.callback.DataResultCallBack;
import com.android.collect.library.common.KeyHelper;
import com.android.collect.library.http.DataRepository;
import com.android.collect.library.http.HttpHelper;
import com.android.collect.library.ui.splash.SplashActivity;
import com.android.collect.library.util.DeviceInfoUtil;
import com.android.collect.library.util.DialogUtil;
import com.android.collect.library.util.JSONUtil;
import com.android.collect.library.util.MD5Util;
import com.android.collect.library.util.SharedPreferencesUtil;
import com.android.collect.library.util.ToastUtils;
import com.android.collect.library.util.Util;

import org.json.JSONObject;

import java.io.File;
import java.io.IOException;

/**
 * @author anzai
 */

public class UpdateManager {

    public static final String APK_NAME = "zbdirect.apk";

    private Context mContext;

    private String updateUrl;
    private String updateInfo;
    private JSONObject resultJson;

    private UpdateIsFinish mUpdateIsFinish;

    private UpdateDefinedDialog definedDialog;
    private UpdateDefinedDialog noticeDialog;
    private DownloadApkManager downloadApkManager;

    public UpdateManager(Context context, UpdateIsFinish isFinnsh) {
        this.mContext = context;
        this.mUpdateIsFinish = isFinnsh;
    }

    /**
     * 检测软件更新
     */
    public void checkUpdate() {
        PackageManager packageManager = mContext.getPackageManager();
        JSONObject json = null;
        try {
            PackageInfo packageInfo = packageManager.getPackageInfo(
                    mContext.getPackageName(),
                    PackageManager.GET_SIGNATURES);
            String SIGNATURE_FLAG = "";
            String APKMD5_FLAG = "";
            File f = new File(packageInfo.applicationInfo.sourceDir);
            for (Signature signature : packageInfo.signatures) {
                // 取到Package的签名
                SIGNATURE_FLAG = MD5Util.getMD5String(signature
                        .toCharsString());
            }
            // 取到APKMD5
            APKMD5_FLAG = MD5Util.getFileMD5String(f);
            // 设备信息
            DeviceInfoUtil deviceinfoutil = new DeviceInfoUtil(mContext);
            json = deviceinfoutil.getDeviceInfo4js();// 获取对应信息
            json.put("Signature_1", SIGNATURE_FLAG);
            // json.put("Signature", APKMD5_FLAG);
            // hrxj要求的设备信息
            json.put("Signature", APKMD5_FLAG);
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (json == null)
            json = new JSONObject();
//        JSONUtil.setString(json, "TerminalId", "android");
//        JSONUtil.setString(json, "CurrentVersion", DeviceInfoUtil.getAppVersionCode(mContext) + "");

        new DataRepository(mContext, HttpHelper.MobilePhoneVersionQuery, json,
                new DataResultCallBack() {

                    @Override
                    public void onSucceed(JSONObject jsonObject) {
                        DialogUtil.dismissLDialog();

                        resultJson = jsonObject;
                        updateUrl = JSONUtil.getString(jsonObject, "AakURL");
                        updateInfo = JSONUtil.getString(jsonObject, KeyHelper.UpdateHint).replace("#", "\n");

                        SharedPreferencesUtil.saveString(mContext, KeyHelper.UpdateHint, updateInfo);
                        int updateMode = Integer.parseInt(JSONUtil.getString(
                                jsonObject, "Flag"));
                        switch (updateMode) {
                            case 0:// 0 不更新
                                ToastUtils.Toast(mContext, R.string.update_app_is_new);
                                mUpdateIsFinish.isSucceed(resultJson);
                                break;
                            case 1:// 1 提示更新
                                showNoticeDialog();
                                break;
                            case 2:// 强制更新
                                showMustNoticeDialog();
                                break;
                            default:
                                break;
                        }
                    }

                    @Override
                    public void onFailed(String statusCode, String msg) {
                        if (mContext instanceof SplashActivity)
                            Util.exitApp();
                    }
                }).setErrorDialogWithDismiss().postRequest();
    }

    /**
     * 显示软件更新对话框
     */
    private void showNoticeDialog() {

        noticeDialog = new UpdateDefinedDialog(mContext, updateInfo, "1",
                new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        if (noticeDialog != null && noticeDialog.isShowing())
                            noticeDialog.dismiss();
                        downloadApkManager = new DownloadApkManager(mContext,
                                updateUrl, APK_NAME,
                                new DownloadCallback() {
                                    @Override
                                    public void isSucceed() {
                                        if (mContext instanceof SplashActivity)
                                            Util.exitApp();
                                    }

                                    @Override
                                    public void isField() {
                                        mUpdateIsFinish.isSucceed(resultJson);
                                    }
                                });
                        downloadApkManager.showDownloadDialog();
                    }

                }, new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (noticeDialog != null && noticeDialog.isShowing())
                    noticeDialog.dismiss();
                mUpdateIsFinish.isSucceed(resultJson);
            }
        });
        noticeDialog.show();
    }

    /**
     * 强制更新
     */
    private void showMustNoticeDialog() {

        definedDialog = new UpdateDefinedDialog(mContext, updateInfo, "2",
                new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        if (mContext instanceof SplashActivity)
                            Util.exitApp();
                    }

                }, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (definedDialog != null && definedDialog.isShowing())
                    definedDialog.dismiss();
                downloadApkManager = new DownloadApkManager(mContext,
                        updateUrl, APK_NAME,
                        new DownloadCallback() {
                            @Override
                            public void isSucceed() {
                                if (mContext instanceof SplashActivity)
                                    Util.exitApp();
                            }

                            @Override
                            public void isField() {
                                if (mContext instanceof SplashActivity)
                                    Util.exitApp();
                            }
                        });
                downloadApkManager.showDownloadDialog();
            }
        });
        definedDialog.show();
    }

}
