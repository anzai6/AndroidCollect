package com.android.collect.library.manager;

import android.app.Activity;
import android.os.Bundle;

import com.android.collect.library.common.Constant;
import com.android.collect.library.common.KeyHelper;


/**
 * Created by anzai on 2017/7/11.
 */

public class OnNewIntentManager {

    /**
     * 跳转目的地
     */
    public static void toTarget(Activity activity, Bundle bundle) {
        int target_flag = bundle.getInt(KeyHelper.TargetFlag);
        bundle.putInt(KeyHelper.TargetFlag, 0);
        switch (target_flag) {
            case Constant.TARGET_FEED_BACK:// 意见反馈
                break;

            default:
                break;
        }
    }

}
