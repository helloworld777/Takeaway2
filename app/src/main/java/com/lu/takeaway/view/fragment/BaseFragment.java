package com.lu.takeaway.view.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ListView;
import android.widget.TextView;

import com.lu.takeaway.R;
import com.lu.takeaway.util.DialogUtil;
import com.lu.takeaway.util.LogUtil;
import com.lu.takeaway.util.TopNoticeDialog;
import com.lu.takeaway.view.app.DingDanApplication;

public class BaseFragment extends Fragment {
    protected DingDanApplication mContext;
//    protected DialogLoading dialogLoading;

    public void initData() {
    }
    protected void initWidget(View view){

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogUtil.d(this, "onCreate()........................");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public BaseFragment() {
        mContext = DingDanApplication.getDefault();
        LogUtil.d(this, "BaseFragment()........................");
    }
    public void d(String msg){
        LogUtil.d(this, "........................"+msg);
    }
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        LogUtil.d(this, "onAttach()........................");
//		dialogLoading=new DialogLoading(activity);
    }
    protected void openSoftInput(View view) {
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(view, InputMethodManager.SHOW_FORCED);
    }

    protected void closeSoftInput() {
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getActivity().getWindow().getDecorView().getWindowToken(), 0); //强制隐藏键盘
    }
    public void currentSelected() {
    }

    ;

    protected void showLoadingDialog() {
        DialogUtil.showWaitDialog(getActivity(), null);
    }

    protected void closeLoadingDialog() {
//        if (dialogLoading != null && dialogLoading.isShowing()) {
//            dialogLoading.dismiss();
//        }
        DialogUtil.closeAlertDialog();
    }

    protected void showToast(String msg) {
        TopNoticeDialog.showToast(getActivity(), msg);
    }
    protected void showToast(int res) {
        TopNoticeDialog.showToast(getActivity(), res);
    }

    protected <T extends View> T findViewById(View view, int res) {
        return (T) view.findViewById(res);
    }

    protected void startActivityTransition(Class<?> class1) {
        startActivityTransition(new Intent(getActivity(), class1));
    }

    protected void startActivityTransition(Intent intent) {
        startActivity(intent);
        getActivity().overridePendingTransition(R.anim.out_to_left, R.anim.in_from_right);
    }

    protected void startActivityForResultTransition(Class<?> class1, int requestCode) {
        startActivityForResultTransition(new Intent(getActivity(), class1), requestCode);
    }

    protected void startActivityForResultTransition(Intent intent, int requestCode) {
        startActivityForResult(intent, requestCode);
        getActivity().overridePendingTransition(R.anim.out_to_left, R.anim.in_from_right);
    }

    protected TextView setListViewEmptyView(ListView listview, String emptyTips) {
        TextView emptyView = new TextView(getActivity());
        emptyView.setGravity(Gravity.CENTER);
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        emptyView.setLayoutParams(layoutParams);
        if (null == emptyTips) {
            emptyTips = String.valueOf(getText(R.string.empty_data));
        }
        emptyView.setText(emptyTips);
        emptyView.setTextColor(Color.BLACK);
        emptyView.setVisibility(View.GONE);
        ((ViewGroup) listview.getParent()).addView(emptyView);
        listview.setEmptyView(emptyView);
        return emptyView;
    }
}
