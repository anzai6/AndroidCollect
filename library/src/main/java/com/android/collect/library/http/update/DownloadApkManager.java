package com.android.collect.library.http.update;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.collect.library.R;
import com.android.collect.library.common.FileConstant;
import com.android.collect.library.http.HttpConstant;
import com.android.collect.library.http.okhttp.FileRequestProgressCallBack;
import com.android.collect.library.http.okhttp.OkHttpRequest;
import com.android.collect.library.util.DialogUtil;
import com.android.collect.library.util.ToastUtils;
import com.android.collect.library.util.file.FileUtil;
import com.android.collect.library.widget.dialog.UserDefinedDialog;

import java.io.File;

import okhttp3.Call;


/**
 * 下载apk并安装
 * 
 * @author anzai
 * 
 */
public class DownloadApkManager implements HttpConstant {

	/* 更新进度条 */
	private ProgressBar mProgress;
	private TextView update_text_left;
	private UserDefinedDialog mDownloadDialog;
	private TextView update_text_right;
	/* 下载保存路径 */
	private String mSavePath;
	/* apk名字 */
	private String apkName;

	/* 下载链接 */
	private String url;
	private Context mContext;
	private DownloadCallback mDownloadCallback;
	private Call call;
	
	public static final String TAG = "DownloadApkManager";

	public DownloadApkManager(Context context, String url, String apkName,
			DownloadCallback mDownloadCallback) {
		mContext = context;
		this.url = url;
		this.apkName = apkName;
		this.mDownloadCallback = mDownloadCallback;
	}

	/**
	 * 显示软件下载对话框
	 */
	public void showDownloadDialog() {
		// 构造软件下载对话框
		Builder builder = new Builder(mContext);
		builder.setTitle(R.string.soft_updating);
		// 给下载对话框增加进度条
		final LayoutInflater inflater = LayoutInflater.from(mContext);
		View v = inflater.inflate(R.layout.dialog_download_apk_progress, null);
		update_text_left = (TextView) v.findViewById(R.id.update_text_left);
		update_text_right = (TextView) v.findViewById(R.id.update_text_right);

		mProgress = (ProgressBar) v.findViewById(R.id.update_progress);
		Button btn_download_cancel = (Button) v
				.findViewById(R.id.btn_download_cancel);

		btn_download_cancel.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if (call != null)
					call.cancel();
				mDownloadDialog.dismiss();
				// 设置取消状态
				mDownloadCallback.isField();
			}
		});

		mDownloadDialog = new UserDefinedDialog(mContext, v);
		mDownloadDialog.show();
		mDownloadDialog.setCanceledOnTouchOutside(false);
		mDownloadDialog.setCancelable(false);
		downloadApk();
	}

	/**
	 * 下载apk文件
	 */
	private void downloadApk() {
		// 启动新线程下载软件

		// 判断SD卡是否存在，并且是否具有读写权限
		if (FileUtil.isSdCardUsable()) {
			// 获得存储卡的路径
			mSavePath = FileConstant.APP_FILE_SD_DOWNLOAD_DIR;
		} else {
			ToastUtils.Toast(mContext, R.string.error_no_sd_card);
			return;
		}
		call = OkHttpRequest.getInstance(mContext).downloadFileWithProgress(url, mSavePath, apkName, new FileRequestProgressCallBack() {
			@Override
			public void onFailed() {
				DialogUtil.showErrorWithDismiss(mContext,
						mContext.getString(R.string.update_network_error),
						new OnDismissListener() {

							@Override
							public void onDismiss(DialogInterface dialog) {
								((Activity) mContext).finish();
							}
						});
				call = null;
				mDownloadCallback.isField();
			}

			@Override
			public void onSucceed() {
				call = null;
				installApk();
			}

			@Override
			public void onProgress(int progress, boolean done) {
				update_text_left.setText(progress + "%");
				update_text_right.setText(progress + "/100");
				mProgress.setProgress(progress);
			}
		});

	}

	/**
	 * 安装APK文件
	 */
	private void installApk() {
		File apkfile = new File(mSavePath, apkName);
		if (!apkfile.exists()) {
			return;
		}

		String cmd = "chmod 777 " + apkfile.toString();
		try {
			Runtime.getRuntime().exec(cmd);
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 通过Intent安装APK文件
		Intent i = new Intent(Intent.ACTION_VIEW);
		i.setDataAndType(Uri.parse("file://" + apkfile.toString()),
				"application/vnd.android.package-archive");
		i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		mContext.startActivity(i);
		mDownloadCallback.isSucceed();
	}

}
