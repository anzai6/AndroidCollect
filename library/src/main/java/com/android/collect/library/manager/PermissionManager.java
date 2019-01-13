package com.android.collect.library.manager;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;

import com.android.collect.library.callback.OnPermissionCallBack;
import com.android.collect.library.util.AndroidVersionUtil;
import com.android.collect.library.util.ToastUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 权限申请管理类
 * @author anzai
 */
public class PermissionManager {

    private Object mObject;
    private OnPermissionCallBack mOnPermissionCallBack;
    private int mRequestCode;

    public static PermissionManager with(Activity activity) {
        return new PermissionManager(activity);
    }

    public PermissionManager(Activity activity) {
        mObject = activity;
    }

    public PermissionManager(Fragment fragment) {
        mObject = fragment;
    }

    /**
     * 申请多个权限
     *
     * @param requestCode
     * @param permissions
     * @param onPermissionCallBack
     */
    public void requestPermissions(int requestCode, String[] permissions, OnPermissionCallBack onPermissionCallBack) {
        this.mOnPermissionCallBack = onPermissionCallBack;
        this.mRequestCode = requestCode;
        _requestPermissions(requestCode, permissions);
    }

    /**
     * 申请单个权限
     *
     * @param requestCode
     * @param permission
     * @param onPermissionCallBack
     */
    public void requestPermission(int requestCode, String permission, OnPermissionCallBack onPermissionCallBack) {
        this.mOnPermissionCallBack = onPermissionCallBack;
        this.mRequestCode = requestCode;
        _requestPermissions(requestCode, new String[]{permission});
    }

    /**
     * 显示解释申请权限
     *
     * @param requestCode
     * @param permission
     * @param rationaleMsg
     * @param mOnPermissionCallBack
     */
    public void requestPermissionShouldRationale(int requestCode, String permission, String rationaleMsg, OnPermissionCallBack mOnPermissionCallBack) {
        if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(mObject),
                permission)) {
            ToastUtils.Toast(getActivity(mObject), rationaleMsg);
        }
        requestPermission(requestCode, permission, mOnPermissionCallBack);
    }


    @TargetApi(value = Build.VERSION_CODES.M)
    private void _requestPermissions(int requestCode, String[] permissions) {
        if (!AndroidVersionUtil.isOver6()) {
            if (mOnPermissionCallBack != null)
                mOnPermissionCallBack.onSuccess(requestCode);
            return;
        }
        List<String> deniedPermissions = findDeniedPermissions(getActivity(mObject), permissions);

        if (deniedPermissions.size() > 0) {
            if (mObject instanceof Activity) {
                ((Activity) mObject).requestPermissions(deniedPermissions.toArray(new String[deniedPermissions.size()]), requestCode);
            } else if (mObject instanceof Fragment) {
                ((Fragment) mObject).requestPermissions(deniedPermissions.toArray(new String[deniedPermissions.size()]), requestCode);
            } else {
                throw new IllegalArgumentException(mObject.getClass().getName() + " is not supported!");
            }
        } else {
            if (mOnPermissionCallBack != null)
                mOnPermissionCallBack.onSuccess(requestCode);
        }


    }

    @TargetApi(value = Build.VERSION_CODES.M)
    public List<String> findDeniedPermissions(Activity activity, String... permission) {
        List<String> denyPermissions = new ArrayList<>();
        for (String value : permission) {
            if (activity.checkSelfPermission(value) != PackageManager.PERMISSION_GRANTED) {
                denyPermissions.add(value);
            }
        }
        return denyPermissions;
    }

    public Activity getActivity(Object object) {
        if (object instanceof Fragment) {
            return ((Fragment) object).getActivity();
        } else if (object instanceof Activity) {
            return (Activity) object;
        }
        return null;
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {
        if (mRequestCode == requestCode)
            requestResult(requestCode, permissions, grantResults);
    }

    private void requestResult(int requestCode, String[] permissions,
                               int[] grantResults) {
        List<String> deniedPermissions = new ArrayList<>();
        for (int i = 0; i < grantResults.length; i++) {
            if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                deniedPermissions.add(permissions[i]);
            }
        }
        if (mOnPermissionCallBack != null) {
            if (deniedPermissions.size() > 0) {
                mOnPermissionCallBack.onError(requestCode);
            } else {
                mOnPermissionCallBack.onSuccess(requestCode);
            }
        }
    }

}
