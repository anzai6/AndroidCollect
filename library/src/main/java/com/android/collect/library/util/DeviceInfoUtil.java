package com.android.collect.library.util;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;

import com.android.collect.library.common.Constant;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class DeviceInfoUtil {

    private Context mContext;
    private Map<String, String> map = new HashMap<String, String>();
    ;
    private JSONObject js = null;

    public DeviceInfoUtil(Context context) {
        super();
        this.mContext = context;
    }

    public JSONObject getDeviceInfo4js() {
        js = new JSONObject();
        try {
            // IMEI
            js.put("DeviceId", getDeviceIMEI(mContext));
            // 判断sd卡是否存在,1存在，0不存在
            js.put("SDFlag", String.valueOf(isSDCard()));
            // 获取设备型号
            js.put("DeviceModel", getModel());
            // 设备类型,0：手机1：平板2：电视3：其他
            js.put("DeviceType", "0");
            js.put("DeviceOsType", "android");
            // Mac地址
            js.put("DeviceMac", getMac(mContext));
            // 系统版本号
            js.put("SYSVersion", String.valueOf(getAndroidSDKVersion()));
            // 版本号
            js.put("VersionCode", String.valueOf(getAppVersionCode(mContext)));
            // 版本名称
            js.put("VersionName", getAppVersionName(mContext));
            // 客户端类型
            js.put("ClientType", "0");
            // 屏幕信息
            js.put("DeviceMetrics", getDisplayMetrics(mContext));
            js.put("VersionType", "");
            js.put("VersionSource", "");
            // Root标志0：没有root（未越狱）1：已经root（越狱）
            js.put("RootFlag", String.valueOf(isRootSystem()));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return js;
    }

    public Map<String, String> getDeviceInfo() {
        map.clear();
        // IMEI
        map.put("DeviceId", getDeviceIMEI(mContext));
        // 判断sd卡是否存在,1存在，0不存在
        map.put("SDFlag", String.valueOf(isSDCard()));
        // 获取设备型号
        map.put("DeviceModel", getModel());
        // 设备类型,0：手机1：平板2：电视3：其他
        map.put("DeviceType", "1");
        // Mac地址
        map.put("DeviceMac", getMac(mContext));
        // 系统版本号
        map.put("SYSVersion", String.valueOf(getAndroidSDKVersion()));
        // 版本号
        map.put("VersionCode", String.valueOf(getAppVersionCode(mContext)));
        // 版本名称
        map.put("VersionName", getAppVersionName(mContext));
        // 客户端类型
        map.put("DeviceOsType", "android");
        // 屏幕信息
        map.put("DisplayMetrics", getDisplayMetrics(mContext));
        return map;

    }

    /**
     * 判断sd卡是否存在
     *
     * @return boolean
     */
    public int isSDCard() {
        String SDState = android.os.Environment.getExternalStorageState();
        if (SDState.equals(android.os.Environment.MEDIA_MOUNTED)) {
            return 1;
        } else {
            return 0;
        }
    }

    /**
     * 获取手机型号
     *
     * @return String
     */
    public static String getModel() {
        Build bd = new Build();
        String model = bd.MODEL;
        return TextUtils.isEmpty(model) ? "Android" : model;
    }

    /**
     * 获取手机品牌和型号
     *
     * @return String
     */
    public static String getBrandAndModel() {
        Build bd = new Build();
        String model = Build.BRAND + " " + Build.MODEL;
        return TextUtils.isEmpty(model) ? "Android" : model;
    }

    /**
     * 获取手机系统版本，如： Android 5.1
     *
     * @return String
     */
    public static String getPhoneOsVersion() {
        return "Android " + Build.VERSION.RELEASE;
    }

    /**
     * 获取手机MAC地址
     *
     * @return String
     */
    public static String getMac(Context context) {
        WifiManager wifi = (WifiManager) context
                .getSystemService(Context.WIFI_SERVICE);

        WifiInfo info = wifi.getConnectionInfo();

        if (info.getMacAddress() != null) {
            return info.getMacAddress();
        } else {
            return "";
        }

    }

    /**
     * 获取手机设别android SDK版本号
     *
     * @return int
     */
    public static String getAndroidSDKVersion() {
        int version = 0;
        try {
            version = Integer.valueOf(Build.VERSION.SDK_INT);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return version + "";
    }

    /**
     * 硬件设备类型
     *
     * @return String
     */
    public static String getDeviceType() {
        return "android";
    }

    /**
     * 获取手机设备IMEI
     *
     * @return String
     */
    public static String getDeviceIMEI(Context context) {
        TelephonyManager tm = (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);
        return tm.getDeviceId();
    }

    /**
     * 获取手机屏幕尺寸
     *
     * @return String
     */
    public static String getDisplayMetrics(Context context) {
        // String str = "";
        DisplayMetrics dm = new DisplayMetrics();
        dm = context.getResources().getDisplayMetrics();
        int screenWidth = dm.widthPixels;// 屏幕宽（像素，如：480px）
        int screenHeight = dm.heightPixels;// 屏幕高（像素，如：800px）
        return String.valueOf(screenWidth) + "*" + String.valueOf(screenHeight);
    }

    /**
     * 获取手机屏幕尺寸 高度
     *
     * @return int
     */
    public static int getDisplayMetricsHeight(Context context) {
        // String str = "";
        DisplayMetrics dm = new DisplayMetrics();
        dm = context.getResources().getDisplayMetrics();
        int screenHeight = dm.heightPixels;// 屏幕高（像素，如：800px）
        return screenHeight;
    }

    /**
     * 获取手机屏幕尺寸 宽度
     *
     * @return int
     */
    public static int getDisplayMetricsWidth(Context context) {
        // String str = "";
        DisplayMetrics dm = new DisplayMetrics();
        dm = context.getResources().getDisplayMetrics();
        int screenWidth = dm.widthPixels;// 屏幕高（像素，如：800px）
        return screenWidth;
    }

    /**
     * 获取应用程序版本名
     *
     * @return String
     */
    public static String getAppVersionName(Context context) {
        String AppVersionName = "";
        try {
            AppVersionName = context.getPackageManager().getPackageInfo(
                    context.getPackageName(), 0).versionName;
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        return AppVersionName;
    }

    /**
     * 获取应用程序版本号
     *
     * @return int
     */
    public static String getAppVersionCode(Context context) {
        int AppVersionCode = 0;
        try {
            AppVersionCode = context.getPackageManager().getPackageInfo(
                    context.getPackageName(), 0).versionCode;
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        Constant.APPVERSION_CODE = AppVersionCode;
        return AppVersionCode + "";
    }

    /**
     * 判断系统是否root
     *
     * @return boolean
     */
    public int isRoot() {
        int isroot = 0;
        try {
            if (Runtime.getRuntime().exec("su").getOutputStream() == null) {
                // 没有root
                isroot = 0;
            } else {
                isroot = 1;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return isroot;
    }

    private final static int kSystemRootStateUnknow = -1;
    private final static int kSystemRootStateDisable = 0;
    private final static int kSystemRootStateEnable = 1;
    private static int systemRootState = kSystemRootStateUnknow;

    public static int isRootSystem() {
        if (systemRootState == kSystemRootStateEnable) {
            return 1;
        } else if (systemRootState == kSystemRootStateDisable) {

            return 0;
        }
        File f = null;
        final String kSuSearchPaths[] = {"/system/bin/", "/system/xbin/",
                "/system/sbin/", "/sbin/", "/vendor/bin/"};
        try {
            for (int i = 0; i < kSuSearchPaths.length; i++) {
                f = new File(kSuSearchPaths[i] + "su");
                if (f != null && f.exists()) {
                    systemRootState = kSystemRootStateEnable;
                    return 1;
                }
            }
        } catch (Exception e) {
        }
        systemRootState = kSystemRootStateDisable;
        return 0;
    }

    /**
     * 判断设备是否是平板 平板：true 手机：false
     */
    private String isTabletDevice() {
        return "0";
    }

    /**
     * 获取手机型号
     *
     * @return String
     */
    public static String getUmengChannelValue(Context context) {
        ApplicationInfo appInfo = null;
        try {
            appInfo = context.getPackageManager()
                    .getApplicationInfo(context.getPackageName(),
                            PackageManager.GET_META_DATA);
            String channel = appInfo.metaData.getString("UMENG_CHANNEL");
            return channel;
        } catch (NameNotFoundException e) {
            e.printStackTrace();
            return "";
        }
    }

}
