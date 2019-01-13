package com.android.collect.library.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.Signature;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * APK签名验证
 * @author anzai
 *
 */
public class CheckApkSignUtil {

	/**
	 * Package的签名,使用同一个签名文件keystore签名，产生的签名就相同
	 */
	private static String SIGNATURE_FLAG = "";
	/**
	 * APK文件的MD5签名
	 */
	private static String APKMD5_FLAG = "";

	/**
	 * 获取APK文件的MD5签名，会耗费比较长的时间进行MD5值计算，用于对包文件完整性校验
	 * 
	 * @param context
	 * @return
	 */
	public static String getApkFileMd5String(Context context) {
		try {
			PackageManager packageManager = context.getPackageManager();
			PackageInfo packageInfo = packageManager.getPackageInfo(
					context.getPackageName(), PackageManager.GET_SIGNATURES);
			File f = new File(packageInfo.applicationInfo.sourceDir);
			for (Signature signature : packageInfo.signatures) {
				// 取到Package的签名
				SIGNATURE_FLAG = MD5Util
						.getMD5String(signature.toCharsString());
			}
			// 取到APKMD5
			APKMD5_FLAG = MD5Util.getFileMD5String(f);
		} catch (NameNotFoundException e) {
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}

		return APKMD5_FLAG;
	}

	/**
	 * 只有使用同一个签名文件keystore签名，产生的签名就相同。
	 * 
	 * @param context
	 * @return
	 */
	public static String getAppSignature(Context context) {
		try {
			PackageManager packageManager = context.getPackageManager();
			PackageInfo packageInfo = packageManager.getPackageInfo(
					context.getPackageName(), PackageManager.GET_SIGNATURES);
			SIGNATURE_FLAG = MD5Util.getMD5String(packageInfo.signatures[0]
					.toString());
		} catch (NameNotFoundException e) {
			e.printStackTrace();
			return null;
		}

		return SIGNATURE_FLAG;
	}

	/**
	 * apk生成的classes.dex主要由java文件生成的，它是整个apk的逻辑实现。
	 * 所以我们可以对classes.dex文件进行完整性校验，来保证整个程序的逻辑不被修改。
	 * 所以只要Java文件没有变动，可以运行一次程序获取classes.dex的哈希值再存储到R.string.classesdex_crc中
	 * 
	 * @param context
	 * @return
	 */
	public static String getAppClassesdexCrc(Context context) {
		String apkPath = context.getPackageCodePath();

		String app_classesdex_crc = null;
		// Long dexCrc = Long.parseLong(getString(R.string.classesdex_crc));
		try {
			ZipFile zipfile = new ZipFile(apkPath);
			ZipEntry dexentry = zipfile.getEntry("classes.dex");
			Log.i("verification", "classes.dexcrc=" + dexentry.getCrc());

			app_classesdex_crc = dexentry.getCrc() + "";
			/*
			 * if (dexentry.getCrc() != dexCrc) {
			 * 
			 * Log.i("verification", "Dexhas been modified!");
			 * 
			 * } else {
			 * 
			 * Log.i("verification", "Dex hasn't been modified!");
			 * 
			 * }
			 */

		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		return app_classesdex_crc;
	}

	/**
	 * 对整个apk的完整性进行校验，因为apk中任何的改动都会引起最终apk生成的哈希值的不同
	 * 用linux下的sha1sum命令计算我们的apk的哈希值，命令如下： sha1sum test.apk
	 * 将生成的哈希值存到服务器上，然后在我们的代码中从服务器获取进行完整性比较
	 * @param context
	 * @return
	 */
	public static String getAppCrc(Context context) {
		String apkPath = context.getPackageCodePath();
		String apk_sha = null;

		MessageDigest msgDigest = null;
		try {

			msgDigest = MessageDigest.getInstance("SHA-1");

			byte[] bytes = new byte[1024];

			int byteCount;

			FileInputStream fis = new FileInputStream(new File(apkPath));

			while ((byteCount = fis.read(bytes)) > 0)

			{
				msgDigest.update(bytes, 0, byteCount);
			}

			BigInteger bi = new BigInteger(1, msgDigest.digest());
			apk_sha = bi.toString(16);
			fis.close();

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return apk_sha;
	}

}
