package com.lu.takeaway.util;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.os.Build;
import android.os.CountDownTimer;
import android.os.Debug;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lu.takeaway.R;
import com.lu.takeaway.view.app.DingDanApplication;

/**
 * @author laichunling
 * @Package com.lcl.mybaiduapp
 * @Description: ${TODO}(顶部Toast)
 * @date 2016/1/9 10:19
 */
public class TopNoticeDialog {

    /**
     * 枚举类型
     */
    public enum TipType {
        SUCCESS_TIP, FAILURE_TIP
    }
    private static int mScreenWidth;
    private static AlertDialog dialog;
    private static CountDown mCountDown;
    private static View view;
    private static TextView tip_txt = null;
    private static LinearLayout ll = null;
    private final static int COUNTDOWN_TIME = 1000;
    private static Activity mActivity;

    private static WindowManager wm;
    private final static WindowManager.LayoutParams mParams = new WindowManager.LayoutParams();

    public TopNoticeDialog() {

    }



    /**
     * 设置内容
     *
     * @param context
     * @param text
     * @param type
     */
    private static void createDialog(Context context, CharSequence text, TipType type) {
        if (!isActivityNotFinished(context)) {
            return;
        }
        LogUtil.d(TopNoticeDialog.class, "ActivityNotFinished");

        if (view == null) {
            //获取布局
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//            view = inflater.inflate(R.layout.top_notice, null);
//            ll = new LinearLayout(context);
            tip_txt = new TextView(context);
            //设置布局的背景色，和宽度
            LinearLayout.LayoutParams params_ll = new LinearLayout.LayoutParams(ScreenUtil.getScreenSize((Activity) context)[0], ViewGroup.LayoutParams.WRAP_CONTENT);
            ll.setLayoutParams(params_ll);
        }
        setType(type, ll);
        //设置提示语
        tip_txt.setText(text);

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        if (dialog == null) {
            dialog = builder.show();
            dialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
//            dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
            dialog.getWindow().getAttributes().width = ScreenUtil.getScreenSize((Activity) context)[0];
            dialog.getWindow().setContentView(view);
            dialog.getWindow().setGravity(Gravity.TOP);
            dialog.getWindow().setWindowAnimations(android.R.style.Animation_Toast);

            LogUtil.d(TopNoticeDialog.class, "createDialog.................");
        }else{
//            update(text, type);
            LogUtil.d(TopNoticeDialog.class, "dialog is exist.................");
        }
        dialog.show();
        startCountDown();
    }
    /**
     * @param context
     * @param text
     * @param type
     */
    private static void createToast(Context context, CharSequence text, TipType type){
//        if(context==null){
//            Debug.d(TopNoticeDialog.class,"context is null");
////            throw new IllegalArgumentException("context must not be null");
//            return ;
//        }
        wm= (WindowManager) DingDanApplication.getDefault().getSystemService(Context.WINDOW_SERVICE);

        final WindowManager.LayoutParams params = mParams;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        params.width = WindowManager.LayoutParams.WRAP_CONTENT;
        params.format = PixelFormat.TRANSLUCENT;
//        params.windowAnimations = com.android.internal.R.style.Animation_Toast;
        params.type = WindowManager.LayoutParams.TYPE_TOAST;
        params.gravity= Gravity.CENTER;
        params.windowAnimations=android.R.style.Animation_Toast;
        params.setTitle("Toast");
        params.flags = WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
                | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                | WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE;
        if(tip_txt==null){
//            LayoutInflater inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//            view=inflater.inflate(R.layout.top_notice,null);
//            ll= (LinearLayout) view.findViewById(R.id.ll);
            tip_txt= new TextView(context);

            tip_txt.setBackgroundColor(context.getResources().getColor(R.color.grey));
            tip_txt.setAlpha(0.5f);
            int padiing=30;
            tip_txt.setPadding(padiing,padiing,padiing,padiing);
//            LinearLayout.LayoutParams params_ll = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//            ll.setLayoutParams(params_ll);
        }
//        setType(type,ll);
        if (tip_txt.getParent() != null) {
            Log.v("TopNoticeDialog", "REMOVE! " + view + " in TopNoticeDialog");
            wm.removeView(tip_txt);
        }
        tip_txt.setText(text);

        wm.addView(tip_txt, mParams);
        startCountDown();
    }
//    private static void update(CharSequence text, TipType type) {
//        ((TextView) view.findViewById(R.id.text)).setText(text);
//        setType(type, ((LinearLayout) view.findViewById(R.id.ll)));
//        startCountDown();
//    }

    /**
     * 当前Actitity是否finished
     *
     * @param context
     * @return true 没有finished
     */
    private static boolean isActivityNotFinished(Context context) {

        if (context == null) return false;

        if (context instanceof Activity) {
            Activity activity = (Activity) context;
            return !activity.isFinishing();
        }
        return false;
    }

    /**
     * 显示Toast
     *
     * @param context
     * @param text
     * @param type    SUCCESS_TIP, FAILURE_TIP
     */
    public static void showToast(Context context, CharSequence text, TipType type) {
//        if (dialog == null) {
        createToast(context, text, type);

//            Debug.d(TopNoticeDialog.class, "createDialog.................");
//        } else {
//            update(text, type);
//            Debug.d(TopNoticeDialog.class, "dialog is exist.................");
//            dialog.show();
//        }
    }
    /**
     * 显示Toast
     *
     * @param context
     * @param text
     */
    public static void showToast(Context context, CharSequence text) {
        createToast(context, text, TipType.SUCCESS_TIP);

    }
    /**
     * 显示Toast
     *
     * @param context
     */
    public static void showToast(Context context, int res) {
        createToast(context, context.getString(res), TipType.SUCCESS_TIP);

    }

    public static void setDialogNull() {
//        if(dialog!=null){
//            dialog.dismiss();
//            dialog = null;
//        }
//
//        view = null;
//        Debug.d(TopNoticeDialog.class, "...............set dialog is null:");
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    public static void closeDialog(boolean setNull) {
        if (tip_txt != null) {
            wm.removeView(tip_txt);
        }
        stopCountDown();
    }


    /**
     * 根据不同的类型设置线性布局的背景颜色
     *
     * @param type
     * @param linearLayout
     */
    private static void setType(TipType type, LinearLayout linearLayout) {
        switch (type) {
            case SUCCESS_TIP:
                //设置背景绿色
                linearLayout.setBackgroundColor(Color.parseColor("#10D21C"));
                break;
            case FAILURE_TIP:
                //设置背景橘黄色
                linearLayout.setBackgroundColor(Color.parseColor("#EC4018"));
                break;
        }
    }


    static class CountDown extends CountDownTimer {

        public CountDown(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {

        }

        @Override
        public void onFinish() {
            closeDialog(false);
        }
    }

    /**
     * @TODO:开始倒计时
     */
    private static void startCountDown() {
        if (mCountDown != null) {
            mCountDown.cancel();
        }
        mCountDown = new CountDown(COUNTDOWN_TIME, 1000);
        mCountDown.start();
    }

    private static void stopCountDown() {
        if (mCountDown != null) {
            mCountDown.cancel();
            mCountDown = null;
        }
    }
}

