package com.android.collect.library.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by anzai on 2017/7/5.
 */

public class ToAppUtil {

    /**
     * 是否安装了某个应用
     *
     * @param context
     * @param packageName
     *            要检测应用的包名
     * @return 安装了返回True，没有返回False
     */
    public static boolean isInstallPackage(Context context, String packageName) {
        final PackageManager packageManager = context.getPackageManager();// 获取packagemanager
        List<PackageInfo> infos = packageManager.getInstalledPackages(0);// 获取所有已安装程序的包信息
        List<String> pName = new ArrayList<String>();// 用于存储所有已安装程序的包名
        if (infos != null) {
            for (int i = 0; i < infos.size(); i++) {
                String pn = infos.get(i).packageName;
                pName.add(pn);
            }
        }
        return pName.contains(packageName);// 判断pName中是否有目标程序的包名，有TRUE，没有FALSE
    }
}
