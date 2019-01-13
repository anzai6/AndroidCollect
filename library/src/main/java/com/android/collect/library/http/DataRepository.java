package com.android.collect.library.http;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;

import com.android.collect.library.R;
import com.android.collect.library.callback.DataResultCallBack;
import com.android.collect.library.http.okhttp.OkHttpRequest;
import com.android.collect.library.manager.UserManager;
import com.android.collect.library.ui.main.MainActivity;
import com.android.collect.library.ui.splash.SplashActivity;
import com.android.collect.library.util.ActUtil;
import com.android.collect.library.util.DeviceInfoUtil;
import com.android.collect.library.util.DialogUtil;
import com.android.collect.library.util.JSONUtil;
import com.android.collect.library.util.NetUtils;
import com.android.collect.library.util.ToastUtils;
import com.android.collect.library.util.Util;
import com.android.collect.library.widget.dialog.LoadingDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * 网络数据请求类
 *
 * @author anzai
 */
public class DataRepository implements HttpConstant {

    private Context mContext;
    private String url;
    private JSONObject json;
    private DataResultCallBack dataResultCallBack;
    private IDataRequest iDataRequest = null;
    public static int errorDialogCounts = 0;

    private int notErrorShow = 0, errorDialogWithDismiss = 0, showDialogFlag = 0;

    private LoadingDialog mLoadingDialog = null;

    public DataRepository(Context mContext, String url, JSONObject obj, DataResultCallBack dataResultCallBack) {

        iDataRequest = OkHttpRequest.getInstance(mContext);
//		iDataRequest = RetrofitRequest.getInstance(mContext);
        this.url = url;
        if (obj != null)
            json = obj;
        else
            json = new JSONObject();
        this.mContext = Util.checkNotNull(mContext);
        this.dataResultCallBack = Util.checkNotNull(dataResultCallBack);

    }

    public DataRepository(Context mContext, String url, JSONObject obj) {

        iDataRequest = OkHttpRequest.getInstance(mContext);
//		iDataRequest = RetrofitRequest.getInstance(mContext);
        this.url = url;
        if (obj != null)
            json = obj;
        else
            json = new JSONObject();
        this.mContext = Util.checkNotNull(mContext);

    }

    public void postRequest() {

        Util.hideSoftInput2(mContext);

        if (!NetUtils.isNetAvailable(mContext)) {
            showErrorDialog();
            return;
        }

        if (showDialogFlag == 1)
            showLoadDialog();

        iDataRequest.postRequst(url.startsWith("http") ? url : CommDictAction.Url + url, addPublicParams(), new DataResultCallBack() {
            @Override
            public void onSucceed(JSONObject jsonObject) {
                if (showDialogFlag == 1)
                    dismissLoadDialog();
                // 先埋点一次失败，如果成功后面会更新问成功
                resutlHandle(jsonObject);
            }

            @Override
            public void onFailed(String returnCode, String errorMsg) {
                if (showDialogFlag == 1)
                    dismissLoadDialog();
//				dataResultCallBack.onFailed(returnCode, errorMsg);
                showErrorDialog();
            }
        });

    }

    /**
     * 同步请求
     */
    public JSONObject postRequestOnSaveThread() {

        if (!NetUtils.isNetAvailable(mContext)) {
            return null;
        }

        JSONObject jsonObject = iDataRequest.postRequst(CommDictAction.Url + url, addPublicParams());
        if (jsonObject != null) {
            final String return_code = jsonObject.optString(RETURN_CODE, "");
            if (return_code.equalsIgnoreCase(RETURN_CODE_SUC)) {//成功
                return jsonObject;
            } else {
                return null;
            }
        } else {
            return null;
        }

    }

    /**
     * 数据返回处理
     *
     * @param result
     */
    private void resutlHandle(JSONObject result) {

        if (mContext == null) {
            clearTask();
            return;
        }

        // 屏蔽错误
        if (mContext instanceof Context && ((Activity) mContext).isFinishing()) {
            clearTask();
            return;
        }

        if (result == null) {
            showErrorDialog();
            return;
        }

        if (url.contains("echannel/doqueue")) {//多渠道视屏特殊 处理
            dataResultCallBack.onSucceed(result);
            clearTask();
            return;
        }

        String returnCode = result.optString(RETURN_CODE, "");

        if (returnCode.equalsIgnoreCase(RETURN_CODE_SUC)) { // 交易成功
            dataResultCallBack.onSucceed(result);
            return;
        } else { // 失败
            JSONArray jsonError = JSONUtil.getJSONArray(result, "jsonError");
            JSONObject jsonObject = null;
            try {
                jsonObject = jsonError.getJSONObject(0);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            final String errorCode = JSONUtil.getString(jsonObject, ERROR_CODE);
            final String ReturnMessage = JSONUtil.getString(jsonObject, RETURN_MESSAGE, UNKNOWN_ERROR_MSG);
            // 被登出
            if (mContext instanceof Context
                    && errorCode.equalsIgnoreCase(
                    "forceout")) {
                loginAgain(R.string.http_error_forceout);
                return;
            }

            // 会话超时
            if (mContext instanceof Context
                    && errorCode.equalsIgnoreCase(
                    "role.invalid_user")) {
                loginAgain(R.string.http_error_timeout);
                return;
            }

            if (notErrorShow == 1) {// 屏蔽报错
                dataResultCallBack.onFailed(errorCode, ReturnMessage);
                clearTask();
                return;
            }

            if (mContext instanceof Context) {
                if (errorDialogWithDismiss == 1) {
                    DialogUtil.showErrorWithDismiss(mContext, ReturnMessage, new OnDismissListener() {
                        @Override
                        public void onDismiss(DialogInterface dialog) {
                            dataResultCallBack.onFailed(errorCode, ReturnMessage);
                            clearTask();
                        }
                    });
                } else {
                    DialogUtil.showError(mContext, ReturnMessage);
                    dataResultCallBack.onFailed(errorCode, ReturnMessage);
                    clearTask();
                }
            } else {
                ToastUtils.Toast(mContext, ReturnMessage);
                dataResultCallBack.onFailed(errorCode, ReturnMessage);
                clearTask();
            }
        }
    }

    /**
     * 添加公共请求参数
     *
     * @return
     */
    private String addPublicParams() {
        try {
            // 设备类型（ANDROID，IOS）
            json.put("EqmtVerCd", "ANDROID");
            // EqmtIdNo
            json.put("EqmtIdNo", DeviceInfoUtil.getDeviceIMEI(mContext));
            // 设备型号
            json.put("EqmtModel", DeviceInfoUtil.getModel());
            // 友盟渠道好
            json.put("EqmtSource", DeviceInfoUtil.getUmengChannelValue(mContext));

            // App版本号
            json.put("EqmtAppVersion", DeviceInfoUtil.getAppVersionCode(mContext));
            json.put("_ChannelId", "PBOP");
            json.put("BankId", "9999");

            json.put("_local", "zh_CN");
            if (JSONUtil.getString(json, "LoginType").equals("P")) {
                json.put("LoginType", "P");
            } else {
                json.put("LoginType", "P");
            }
        } catch (JSONException e1) {
            e1.printStackTrace();
        }

        return json.toString();
    }

    public DataRepository setNotErrorShow() {
        notErrorShow = 1;
        return this;
    }

    public DataRepository setErrorDialogWithDismiss() {
        errorDialogWithDismiss = 1;
        return this;
    }

    public DataRepository showDialog() {
        showDialogFlag = 1;
        return this;
    }

    private void showErrorDialog() {

        if (mContext == null) {
            clearTask();
            return;
        }

        // 屏蔽错误
        if (mContext instanceof Context && ((Activity) mContext).isFinishing()) {
            clearTask();
            return;
        }

        if (errorDialogCounts != 0) {
            return;
        }

        if (mContext instanceof Context) {
            DialogUtil.showErrorWithDismiss(mContext, R.string.http_connect_error,
                    new OnDismissListener() {

                        @Override
                        public void onDismiss(DialogInterface dialog) {
                            errorDialogCounts = 0;
                            if (mContext instanceof SplashActivity) {
                                ((Activity) mContext).finish();
                            }
                            clearTask();
                        }
                    });
        } else
            clearTask();

        errorDialogCounts++;
        DialogUtil.dismissLDialog();
    }

    private void showLoadDialog() {
        if (!(mContext instanceof Context))
            return;

        if (mContext != null && !((Activity) mContext).isFinishing()) {
            if (mLoadingDialog == null)
                mLoadingDialog = new LoadingDialog((Activity) mContext);
            if (!mLoadingDialog.isShowing())
                mLoadingDialog.show();
        }
    }

    private void dismissLoadDialog() {
        try {
            if (mLoadingDialog != null && mLoadingDialog.isShowing()) {
                mLoadingDialog.dismiss();
                mLoadingDialog = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void clearTask() {
        try {
            dataResultCallBack = null;
            json = null;
            mContext = null;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 取消当前页面的请求
     *
     * @param mContext
     */
    public static void cancelRequest(Context mContext) {
        if (mContext != null)
            OkHttpRequest.getInstance(mContext).cancelTag(mContext);
    }

    private void loginAgain(int errorStrId) {
        DialogUtil.dismissLDialog();
        DialogUtil.showErrorWithDismiss(mContext, errorStrId,
                new OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {

                        UserManager.isLogin = false;
                        UserManager.getInstance().clearUser();
                        Util.sendLogoutBroadcast(mContext);
                        ActUtil.startActivityNeedLogin((Activity) mContext, MainActivity.class);
                    }
                });
    }
}
