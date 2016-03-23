package com.lu.takeaway.view.fragment;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lu.takeaway.R;
import com.lu.takeaway.view.app.DingDanApplication;

import util.Debug;
import util.TopNoticeDialog;

public abstract class BaseFragment extends Fragment {
	protected DingDanApplication mContext;
	public abstract void initData();
	public BaseFragment(){
		mContext=DingDanApplication.getDefault();
		Debug.d(this,"BaseFragment()........................");
	}
	public abstract void currentSelected();
	protected void showToast(String msg){
		TopNoticeDialog.showToast(getActivity(),msg);
	}
	protected <T extends View> T  findViewById(View view,int res){
		return (T)view.findViewById(res);
	}
	protected void startActivityTransition(Class<?> class1){
		startActivityTransition(new Intent(getActivity(), class1));
	}
	protected void startActivityTransition(Intent intent){
		startActivity(intent);
		getActivity().overridePendingTransition(R.anim.out_to_left, R.anim.in_from_right);
	}
	protected void startActivityForResultTransition(Class<?> class1,int requestCode) {
		startActivityForResultTransition(new Intent(getActivity(),class1), requestCode);
	}
	protected void startActivityForResultTransition(Intent intent,int requestCode) {
		startActivityForResult(intent, requestCode);
		getActivity().overridePendingTransition(R.anim.out_to_left, R.anim.in_from_right);
	}
	protected TextView createEmpty(String emptyTips){
		TextView textView=new TextView(getActivity());
		ViewGroup.LayoutParams layoutParams=new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT);
		textView.setLayoutParams(layoutParams);
		if(null==emptyTips){
			emptyTips=String.valueOf(getText(R.string.empty_data));
		}
		textView.setText(emptyTips);
		return textView;
	}
}
