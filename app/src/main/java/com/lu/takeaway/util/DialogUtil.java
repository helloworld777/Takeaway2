package com.lu.takeaway.util;


import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Build;
import android.text.TextUtils;
import android.view.Window;
import android.widget.TextView;

import com.lu.takeaway.R;
import com.lu.takeaway.view.widget.ProgressWheel;

public class DialogUtil {
    private static AlertDialog dialog = null;

    /**
     * @param context
     */
    public static void showToast(Context context, String msg) {
        TopNoticeDialog.showToast(context, msg);
    }

    /**
     * @param context
     */
    public static void showAlertDialog(Context context, String title, String[] items, OnClickListener dialogInterface) {
        if (!checkActivityisDestroyed(context)){
            LogUtil.d(DialogUtil.class.getSimpleName(),"activity is destroy");
            return;
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title).setItems(items, dialogInterface);
        dialog = builder.create();
        dialog.show();
    }

    /**
     */
    public static void showAlertDialog(Context context, String title, int items, OnClickListener dialogInterface) {
        if (!checkActivityisDestroyed(context)){
            LogUtil.d(DialogUtil.class.getSimpleName(),"activity is destroy");
            return;
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title).setItems(items, dialogInterface);
        dialog = builder.create();
        dialog.show();
    }

    /**
     */
    public static void showMessageAlertDialog(Context context, String title, String msg, OnClickListener dialogInterface) {
        if (!checkActivityisDestroyed(context)){
            LogUtil.d(DialogUtil.class.getSimpleName(), "activity is destroy");
            return;
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title).setMessage(msg).setPositiveButton("OK", dialogInterface);
        dialog = builder.create();
        dialog.show();
    }

    /**
     */
    public static void closeAlertDialog() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }

    /**
     * show a closeDialog
     *
     * @param context
     */
    public static void showExitAlertDialog(Context context, OnClickListener dialogInterface) {
        if (!checkActivityisDestroyed(context)){
            LogUtil.d(DialogUtil.class.getSimpleName(),"activity is destroy");
            return;
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("close ").setMessage("sure close?")
                .setNegativeButton("ok", new OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        closeAlertDialog();
                    }
                })
                .setPositiveButton("cancel", dialogInterface);
        dialog = builder.create();
        dialog.show();
    }

    public static void showWaitDialog(Context context, String message) {
        if (!checkActivityisDestroyed(context)){
            LogUtil.d(DialogUtil.class.getSimpleName(),"activity is destroy");
            return;
        }
        dialog = new AlertDialog.Builder(context).create();
        dialog.show();
        Window window = dialog.getWindow();
        window.setContentView(R.layout.dialog_loading);
        window.setBackgroundDrawableResource(android.R.color.transparent);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(false);
        TextView tvDialogLoading = (TextView) window.findViewById(R.id.tvDialogLoading);
        ProgressWheel progressWheel = (ProgressWheel) window.findViewById(R.id.progress_wheel);
        progressWheel.spin();
        if (!TextUtils.isEmpty(message)) {
            tvDialogLoading.setText(message);
        }

    }

    public static void showWaitDialog(Context context, String title, String message) {
        if (!checkActivityisDestroyed(context)){
            LogUtil.d(DialogUtil.class.getSimpleName(),"activity is destroy");
            return;
        }
        dialog = ProgressDialog.show(context, title, message);
    }

    /**
     * @param context
     * @return true activity not distory
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    public static boolean checkActivityisDestroyed(Context context) {
//        boolean isActivity=context instanceof Activity;
        if (context == null) return false;
        if (!(context instanceof Activity)) {
            return false;
        }
        Activity activity = (Activity) context;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                return !activity.isDestroyed();
        }else{
            return !activity.isFinishing();
        }

    }
}
