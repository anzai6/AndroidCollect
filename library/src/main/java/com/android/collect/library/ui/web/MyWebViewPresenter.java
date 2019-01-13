package com.android.collect.library.ui.web;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.webkit.ValueCallback;

import com.android.collect.library.common.Constant;
import com.android.collect.library.common.KeyHelper;
import com.android.collect.library.util.BitmapUtil;
import com.android.collect.library.util.LogUtils;
import com.android.collect.library.util.Util;

import java.io.File;

public class MyWebViewPresenter implements MyWebViewContract.Presenter {

    private final String TAG = "MyWebViewPresenter";

    private Activity mActivity;

    private String mCameraFilePath;
    public static final String IMAGE_TYPE = "image/*";
    public static final int FILECHOOSER_RESULTCODE = 101;
    public static final int SYSTEM_IMAGE_CODE = 102;
    public ValueCallback<Uri> mUploadMessage = null;
    public ValueCallback<Uri[]> mFilePathCallback = null;

    private MyWebViewContract.View mMyWebViewView;

    /**
     * 回传给H5的数据
     */
    private String targetJson;

    private String webKey;

    private Bundle mBundle;

    public MyWebViewPresenter(Activity activity, MyWebViewContract.View myWebViewView) {
        this.mActivity = Util.checkNotNull(activity);
        this.mMyWebViewView = Util.checkNotNull(myWebViewView);
    }

    @Override
    public void subscribe(Bundle bundle) {
        mBundle = bundle;
        targetJson = Util.getBundleString(mBundle, KeyHelper.TargetJson, "");

        String url = Util.getBundleString(mBundle, KeyHelper.WebUrl, "");
        String title = Util.getBundleString(mBundle, KeyHelper.WebTitle, "");
        if (Constant.isReedHtmlFromLocal && !Util.isStringNull(targetJson))
            url = Constant.LocalIndexHtml;

        mMyWebViewView.loadUrl(url);
        mMyWebViewView.setTitle(title);

        LogUtils.i(TAG, "===初始化URL==BaseUrlOfWebView==" + url);
    }

    private Intent createDefaultOpenableIntent() {
        Intent intent = new Intent();
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
            intent.setAction(Intent.ACTION_OPEN_DOCUMENT);
        } else {
            intent.setAction(Intent.ACTION_GET_CONTENT);
        }
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType(IMAGE_TYPE);

        Intent chooser = createChooserIntent(createCameraIntent());
        chooser.putExtra(Intent.EXTRA_INTENT, intent);
        return chooser;
    }

    private Intent createChooserIntent(Intent... intents) {
        Intent chooser = new Intent(Intent.ACTION_CHOOSER);
        chooser.putExtra(Intent.EXTRA_INITIAL_INTENTS, intents);
        chooser.putExtra(Intent.EXTRA_TITLE, "File Chooser");
        return chooser;
    }

    private Intent createCameraIntent() {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File externalDataDir = Environment
                .getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
        File cameraDataDir = new File(externalDataDir.getAbsolutePath()
                + File.separator + "browser-photos");
        cameraDataDir.mkdirs();
        mCameraFilePath = cameraDataDir.getAbsolutePath() + File.separator
                + System.currentTimeMillis() + ".jpg";
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT,
                Uri.fromFile(new File(mCameraFilePath)));
        return cameraIntent;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == FILECHOOSER_RESULTCODE
                && resultCode == Activity.RESULT_OK) { // 文件选择
            if (null == mUploadMessage && mFilePathCallback == null)
                return;
            Uri result = data == null ? null : data.getData();
            if (result == null && data == null) {
                File cameraFile = new File(mCameraFilePath);
                if (cameraFile.exists()) {
                    result = Uri.fromFile(cameraFile);
                    // Broadcast to the media scanner that we have a new photo
                    // so it will be added into the gallery for the user.
                    mActivity.sendBroadcast(new Intent(
                            Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, result));
                }
            } else {
                File file = new File(BitmapUtil.getPathFromUri(mActivity,
                        result));
                if (file.exists())
                    result = Uri.parse("file://" + file.getAbsolutePath().replace(".JPG", ".jpg").replace(".PNG", ".png"));
            }
            if (mUploadMessage != null) {
                mUploadMessage.onReceiveValue(result);
                mUploadMessage = null;
            }

            if (mFilePathCallback != null) {
                Uri[] uris = new Uri[]{result};
                mFilePathCallback.onReceiveValue(uris);
                mFilePathCallback = null;
            }

        } else if (requestCode == FILECHOOSER_RESULTCODE) { // 文件选择：失败回传空值
            if (mUploadMessage != null) {
                mUploadMessage.onReceiveValue(null);
                mUploadMessage = null;
            }

            if (mFilePathCallback != null) {
                mFilePathCallback.onReceiveValue(null);
                mFilePathCallback = null;
            }
        } else if (requestCode == SYSTEM_IMAGE_CODE
                && Activity.RESULT_OK == resultCode) {// 从相册返回

        }
    }

    @Override
    public void onNewIntent(Intent intent) {

    }

    @Override
    public String getTargJson() {
        return targetJson;
    }

    @Override
    public void startFileSelectActivity(ValueCallback<Uri> uploadMessage, ValueCallback<Uri[]> filePathCallback) {
        if (uploadMessage != null)
            this.mUploadMessage = uploadMessage;
        if (filePathCallback != null)
            this.mFilePathCallback = filePathCallback;
        if (mUploadMessage != null || mFilePathCallback != null)
            mActivity.startActivityForResult(createDefaultOpenableIntent(),
                    FILECHOOSER_RESULTCODE);
    }

    @Override
    public void unSubscribe() {
        mFilePathCallback = null;
        mUploadMessage = null;
    }
}
