package com.lu.takeaway.view.fragment;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.lu.takeaway.R;
import com.lu.takeaway.view.app.DingDanApplication;
import com.lu.takeaway.view.widget.DialogLoading;

import util.Debug;
import util.TopNoticeDialog;

public class BaseFragment extends Fragment {
    protected DingDanApplication mContext;
    protected DialogLoading dialogLoading;

    public void initData() {
    }

    ;

    public BaseFragment() {
        mContext = DingDanApplication.getDefault();
        Debug.d(this, "BaseFragment()........................");
    }
    public void d(String msg){
        Debug.d(this, "........................"+msg);
    }
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        Debug.d(this, "onAttach()........................");
//		dialogLoading=new DialogLoading(activity);
    }

    public void currentSelected() {
    }

    ;

    protected void showLoadingDialog() {
        if (dialogLoading != null && !dialogLoading.isShowing()) {
            dialogLoading.show();
            ;
        }
    }

    protected void closeLoadingDialog() {
        if (dialogLoading != null && dialogLoading.isShowing()) {
            dialogLoading.dismiss();
        }
    }

    protected void showToast(String msg) {
        TopNoticeDialog.showToast(getActivity(), msg);
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
        emptyView.setVisibility(View.GONE);
        ((ViewGroup) listview.getParent()).addView(emptyView);
        listview.setEmptyView(emptyView);
        return emptyView;
    }
}
