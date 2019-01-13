package com.android.collect.library.manager;

/**
 * Created by anzai on 2017/7/14.
 */

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;

import com.android.collect.library.R;
import com.android.collect.library.common.FileConstant;
import com.android.collect.library.util.DialogUtil;
import com.android.collect.library.util.LogUtils;
import com.android.collect.library.util.file.FileUtil;

import java.io.File;

/**
 * 拍照和相册取图管理
 */
public class CameraDicmManager {

    private final String TAG = LogUtils.getLogTag(CameraDicmManager.class);

    private Activity mActivity;

    private final int IMAGE_CODE = 0;
    private final int TAKE_IMAGE_CODE = 1;
    private final String IMAGE_TYPE = "image/*";

    private Uri uri;

    public CameraDicmManager(Activity activity) {
        this.mActivity = activity;
    }

    /**
     * 去系统相册
     */
    public void goSystemDicm() {
        Intent intent = new Intent();
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
            intent.setAction(Intent.ACTION_OPEN_DOCUMENT);
        } else {
            intent.setAction(Intent.ACTION_GET_CONTENT);
        }
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType(IMAGE_TYPE);
        mActivity.startActivityForResult(intent, IMAGE_CODE);
    }

    /**
     * 去系统相机
     */
    public void goSystemCamera() {
        if (FileUtil.isSdCardUsable()) {
            File file = null;
            if (uri == null) {
                File files = new File(FileConstant.APP_FILE_SD_IMG_DIR);
                if (!files.exists())
                    files.mkdirs();
                file = new File(files, System.currentTimeMillis() + ".png");
                uri = Uri.fromFile(file);
            }
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            // 指定一个图片路径对应的file对象
            int currentapiVersion = android.os.Build.VERSION.SDK_INT;
            if (currentapiVersion < 24) {
                intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
            } else {
                ContentValues contentValues = new ContentValues(1);
                contentValues.put(MediaStore.Images.Media.DATA, file.getAbsolutePath());
                Uri uri = mActivity.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
            }
            mActivity.startActivityForResult(intent, TAKE_IMAGE_CODE);
        } else {
            DialogUtil.showError(mActivity, R.string.error_no_sd_card);
        }
    }

    public Uri onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == IMAGE_CODE && Activity.RESULT_OK == resultCode) {// 从相册返回
            Uri returnUri = data.getData(); // 获得图片的uri
            if (returnUri != null) {
                return returnUri;
            }
        } else if (requestCode == TAKE_IMAGE_CODE && Activity.RESULT_OK == resultCode) {// 从相机返回
            if (uri != null) {
                return uri;
            }
        }
        return null;
    }


}
