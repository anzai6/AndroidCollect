package com.android.collect.library.util;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.ListView;

import com.android.collect.library.R;
import com.android.collect.library.callback.OnDialogCallBack;
import com.android.collect.library.callback.OnDialogItemSelectCallBack;
import com.android.collect.library.callback.OnGetDateCallBack;
import com.android.collect.library.widget.dialog.LoadingDialog;
import com.android.collect.library.widget.dialog.UserConfirmDialog;
import com.android.collect.library.widget.dialog.UserErrorDialog;
import com.android.collect.library.widget.dialog.UserTipDialog;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by anzai on 2017/4/18.
 */
public class DialogUtil {

    public static final int TIP_DIALOG_DISMISS_TIME = 3;

    private static LoadingDialog mLoadingDialog;

    /**
     * 显示加载框
     *
     * @param activity
     */
    public static void showLDialog(Activity activity) {
        showLDialog(activity, activity.getString(R.string.dialog_loading_msg));
    }

    /**
     * 显示加载框
     *
     * @param activity
     */
    public static void showLDialog(@NonNull Activity activity, String msg) {
        if (!(activity instanceof Activity))
            return;
        if (activity != null && !activity.isFinishing()) {
            if (mLoadingDialog == null || activity != mLoadingDialog.getActivity()) {
                mLoadingDialog = new LoadingDialog(activity);
            }
            if (!mLoadingDialog.isShowing())
                mLoadingDialog.show();
        }
    }

    /**
     * 显示加载框
     */
    public static void dismissLDialog() {
        try {
            if (mLoadingDialog != null && mLoadingDialog.isShowing()) {
                mLoadingDialog.dismiss();
                mLoadingDialog = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 弹出对话框提示信息(有确认回调，有取消回调)
     *
     * @param activity
     * @param message
     */
    public static void showConfirmDialog(Context activity, String message,
                                         View.OnClickListener onCancelListener, View.OnClickListener onComfirmListener) {
        UserConfirmDialog dialog = new UserConfirmDialog(activity, message,
                onCancelListener, onComfirmListener);
        dialog.show();
    }

    /**
     * 弹出对话框提示信息(有确认回调，有取消回调)
     *
     * @param activity
     * @param strId
     */
    public static void showConfirmDialog(Context activity, int strId,
                                         View.OnClickListener onCancelListener, View.OnClickListener onComfirmListener) {
        showConfirmDialog(activity, activity.getString(strId), onCancelListener, onComfirmListener);
    }

    /**
     * 普通提示
     *
     * @param context
     * @param s
     */
    public static void showTip(Context context, String s) {
        UserTipDialog dialog = new UserTipDialog(context, s);
        dialog.setCountDown(TIP_DIALOG_DISMISS_TIME, "");
        dialog.show();
    }

    /**
     * 普通提示
     *
     * @param context
     * @param msgId
     */
    public static void showTip(Context context, int msgId) {
        showTip(context, context.getString(msgId));
    }

    /**
     * 弹出对话框提示信息(有消失回调)
     *
     * @param activity
     * @param message
     * @param onDismissListener
     */
    public static void showTipWithDismiss(Context activity, String message,
                                          DialogInterface.OnDismissListener onDismissListener) {
        UserTipDialog dialog = new UserTipDialog(activity, message, onDismissListener);
        dialog.show();
    }

    /**
     * 弹出对话框提示信息(有消失回调)
     *
     * @param activity
     * @param msgId
     * @param onDismissListener
     */
    public static void showTipWithDismiss(Context activity, int msgId,
                                          DialogInterface.OnDismissListener onDismissListener) {
        showTipWithDismiss(activity, activity.getString(msgId), onDismissListener);
    }

    /**
     * 弹出对话框提示信息带自动消失(有消失回调)
     */
    public static void showTipWithTimer(Context activity, int messageId) {
        showTipWithTimer(activity, activity.getString(messageId));
    }

    public static void showTipWithTimer(Context activity, String message) {
        showTipWithDismissWithTimer(activity, message, null);
    }

    public static void showTipWithDismissWithTimer(Context activity, int messageId,
                                                   DialogInterface.OnDismissListener onDismissListener) {
        showTipWithDismissWithTimer(activity, activity.getString(messageId), onDismissListener);
    }

    public static void showTipWithDismissWithTimer(Context activity, String message,
                                                   DialogInterface.OnDismissListener onDismissListener) {
        UserTipDialog dialog = new UserTipDialog(activity, message, onDismissListener);
        dialog.setCountDown(TIP_DIALOG_DISMISS_TIME, "");
        dialog.show();
    }

    public static void showTipWithDismissWithTimer(Context activity, String message, int dismissTime,
                                                   DialogInterface.OnDismissListener onDismissListener) {
        UserTipDialog dialog = new UserTipDialog(activity, message, onDismissListener);
        dialog.setCountDown(dismissTime, null);
        dialog.show();
    }

    /**
     * 错误提示
     *
     * @param context
     * @param s
     */
    public static void showError(Context context, String s) {
        UserErrorDialog dialog = new UserErrorDialog(context, s);
        dialog.show();
    }

    /**
     * 错误提示
     *
     * @param context
     * @param msgId
     */
    public static void showError(Context context, int msgId) {
        showError(context, context.getString(msgId));
    }

    /**
     * 弹出错误提示信息(有消失回调)
     *
     * @param activity
     * @param message
     * @param onDismissListener
     */
    public static void showErrorWithDismiss(Context activity, String message,
                                            DialogInterface.OnDismissListener onDismissListener) {
        UserErrorDialog dialog = new UserErrorDialog(activity, message, onDismissListener);
        dialog.show();
    }

    /**
     * 弹出错误提示信息(有消失回调)
     *
     * @param activity
     * @param msgId
     * @param onDismissListener
     */
    public static void showErrorWithDismiss(Context activity, int msgId,
                                            DialogInterface.OnDismissListener onDismissListener) {
        showErrorWithDismiss(activity, activity.getString(msgId), onDismissListener);
    }


    /**
     * 提示框
     *
     * @param activity
     * @param strId
     * @param onDialogCallBack
     */
    public static void showMdTipDialog(@NonNull Activity activity, @NonNull int strId, @NonNull final OnDialogCallBack onDialogCallBack) {

        showMdTipDialog(activity, activity.getString(strId), onDialogCallBack);
    }

    /**
     * 提示框
     *
     * @param activity
     * @param msg
     * @param onDialogCallBack
     */
    public static void showMdTipDialog(@NonNull Activity activity, @NonNull String msg, @NonNull final OnDialogCallBack onDialogCallBack) {

        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle(activity.getString(R.string.tip))
                .setMessage(msg)
                .setPositiveButton(activity.getString(R.string.sure), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        onDialogCallBack.onConfirm();
                        dialog.dismiss();
                    }
                })
                .setNegativeButton(activity.getString(R.string.cancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        onDialogCallBack.onCancel();
                        dialog.dismiss();
                    }
                });
        AlertDialog dialog = builder.create();
        dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                onDialogCallBack.onCancel();
            }
        });
        dialog.show();
    }

    /**
     * 多项选择弹框
     *
     * @param activity
     * @param list
     * @param onDialogItemSelectCallBack
     */
    public static void showSelectListDialog(@NonNull Activity activity, @NonNull String[] list, @NonNull final OnDialogItemSelectCallBack onDialogItemSelectCallBack) {

        showSelectListDialog(activity, new ArrayList(Arrays.asList(list)), onDialogItemSelectCallBack);
    }

    /**
     * 多项选择弹框
     *
     * @param activity
     * @param data
     * @param onDialogItemSelectCallBack
     */
    public static void showSelectListDialog(@NonNull Activity activity, @NonNull List<String> data, @NonNull final OnDialogItemSelectCallBack onDialogItemSelectCallBack) {

        View view = LayoutInflater.from(activity).inflate(R.layout.dialog_listview_item_select, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setView(view);
        final AlertDialog dialog = builder.create();

        ListView listView = (ListView) view.findViewById(R.id.listview);
        listView.setAdapter(new ArrayAdapter<String>(activity, R.layout.dialog_list_item_tv, data));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                onDialogItemSelectCallBack.onItemClick(parent, view, position, id);
                dialog.dismiss();
                ;
            }
        });

        dialog.show();
    }

    /**
     * 系统日期选择框，默认返回日期格式(2014-12-12)
     */
    public static void showSystemDatePickerDialog(Context context, String str, final OnGetDateCallBack onGetDateCallBack) {
        str = str.replace("-", "");
        new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                String dateStr = year + "-"
                        + DialogUtil.addSeo(monthOfYear + 1) + "-"
                        + DialogUtil.addSeo(dayOfMonth);
                onGetDateCallBack.onGetDate(dateStr);
            }
        }, Integer.parseInt(str.substring(0, 4)), Integer.parseInt(str
                .substring(4, 6)) - 1, Integer.parseInt(str.substring(6, 8)))
                .show();
    }

    /**
     * 日期前面加0
     *
     * @param i
     * @return
     */
    public static String addSeo(int i) {
        if (i < 10 && i != 0) {
            return "0" + i;
        } else
            return i + "";
    }

}
